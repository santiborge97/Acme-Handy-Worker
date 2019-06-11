
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed Repository

	@Autowired
	private MessageRepository		messageRepository;

	//Supporting services

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD methods

	public Message create(final int recipientId) {

		Assert.notNull(recipientId);
		Assert.isTrue(recipientId != 0);

		final Message result = new Message();
		Actor recipient;
		Actor sender;

		recipient = this.actorService.findOne(recipientId);
		Assert.notNull(recipient);

		result.setRecipient(recipient);

		sender = this.actorService.findByPrincipal();
		result.setSender(sender);

		Date momentSent;

		momentSent = new Date(System.currentTimeMillis() - 1000);

		result.setMoment(momentSent);
		result.setBoxes(new ArrayList<Box>());

		return result;

	}

	public Message create2() {

		final Message result = new Message();

		Date momentSent;

		final Actor actor = this.actorService.findByPrincipal();

		momentSent = new Date(System.currentTimeMillis() - 1000);

		final Boolean spam = false;

		result.setSender(actor);
		result.setRecipient(actor);
		result.setMoment(momentSent);
		result.setSpam(spam);
		result.setPriority("NEUTRAL");
		result.setBoxes(new ArrayList<Box>());

		return result;

	}

	public Collection<Message> findAll() {

		final Collection<Message> messages = this.messageRepository.findAll();

		Assert.notNull(messages);

		return messages;
	}

	public Message findOne(final int messageId) {

		final Message message = this.messageRepository.findOne(messageId);

		Assert.notNull(message);

		return message;
	}

	public Message save(final Message message) {

		if (message.getId() != 0)
			Assert.isTrue((message.getSender() == this.actorService.findByPrincipal()) || message.getRecipient() == this.actorService.findByPrincipal());

		Assert.notNull(message);

		Message result;

		final Boolean spam1 = this.configurationService.spamContent(message.getSubject());

		final Boolean spam2 = this.configurationService.spamContent(message.getBody());

		final Boolean spam3 = this.configurationService.spamContent(message.getTags());
		final Collection<Box> boxes = message.getBoxes();
		final Box inBoxReceiver = this.boxService.findInBoxByActorId(message.getRecipient().getId());
		final Box outBoxSender = this.boxService.findOutBoxByActorId(message.getSender().getId());

		if (spam1 || spam2 || spam3) {

			message.setSpam(true);

			boxes.removeAll(boxes);

			final Actor recipient = message.getRecipient();

			final Box spamBox = this.boxService.findSpamBoxByActorId(recipient.getId());

			boxes.add(outBoxSender);
			boxes.add(spamBox);

			message.setBoxes(boxes);

		} else {
			boxes.add(outBoxSender);
			boxes.add(inBoxReceiver);
			message.setBoxes(boxes);
		}

		result = this.messageRepository.save(message);

		return result;
	}

	public Message save2(final Message message) {

		Assert.notNull(message);

		Message result;

		result = this.messageRepository.save(message);

		return result;

	}

	public void delete(final Message message) {

		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(message.getRecipient().equals(actor) || message.getSender().equals(actor));

		final Box tb = this.boxService.findTrashBoxByActorId(actor.getId());
		final Collection<Box> boxes = message.getBoxes();
		final Collection<Box> allBoxes = this.boxService.findAllBoxByActor(actor.getId());
		if (boxes.contains(tb)) {
			boxes.removeAll(boxes);
			message.setBoxes(boxes);
			this.messageRepository.delete(message);
		} else {
			boxes.removeAll(allBoxes);
			boxes.add(tb);
			message.setBoxes(boxes);
			this.save(message);
		}
	}

	public void broadcastSystem(final Message message) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));

		final Collection<Actor> actores = this.actorService.findAll();
		actores.remove(actor);
		final Collection<Box> boxes = message.getBoxes();
		final Box ob = this.boxService.findOutBoxByActorId(actor.getId());

		for (final Actor a : actores) {

			final Box ib = this.boxService.findInBoxByActorId(a.getId());
			boxes.add(ib);

		}
		boxes.add(ob);
		message.setBoxes(boxes);

	}

	public Collection<Message> findMessagesByBoxId(final int boxId) {

		final Collection<Message> result = this.messageRepository.findMessagesByBoxId(boxId);

		return result;

	}

	public Boolean securityMessage(final int boxId) {

		Boolean res = false;

		final Actor ownerBox = this.boxService.findOne(boxId).getActor();

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(ownerBox))
			res = true;

		return res;
	}
}
