
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	// Managed repository

	@Autowired
	private BoxRepository	boxRepository;

	// Suporting services

	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;


	// Simple CRUD methods

	public Box create() {

		Box result;

		result = new Box();

		return result;

	}

	public Collection<Box> findAll() {

		final Collection<Box> boxes = this.boxRepository.findAll();

		Assert.notNull(boxes);

		return boxes;

	}

	public Box findOne(final int boxID) {

		final Box box = this.boxRepository.findOne(boxID);

		Assert.notNull(box);

		return box;

	}

	public Box save(final Box box) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Actor owner = box.getActor();
		final String name = box.getName();

		if (box.getId() == 0)
			box.setActor(actor);
		else
			Assert.isTrue(actor.getId() == owner.getId());

		Assert.isTrue(name != "in box");
		Assert.isTrue(name != "out box");
		Assert.isTrue(name != "trash box");
		Assert.isTrue(name != "spam box");

		Assert.isTrue(!box.getByDefault());

		Assert.notNull(box);

		final Box result = this.boxRepository.save(box);

		return result;

	}

	public Box saveNewActor(final Box box) {

		Assert.notNull(box);

		final Box result = this.boxRepository.save(box);

		return result;

	}

	public void delete(final Box box) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);

		final Actor owner = box.getActor();

		Assert.isTrue(actor.getId() == owner.getId());

		Assert.notNull(box);
		Assert.isTrue(box.getId() != 0);
		Assert.isTrue(box.getByDefault() == false);

		final Collection<Message> messages = this.messageService.findMessagesByBoxId(box.getId());

		for (final Message m : messages)
			this.messageService.delete(m);

		this.boxRepository.delete(box);

	}
	// Other business methods

	public Box findTrashBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findTrashBoxByActorId(actorId);
		return result;
	}

	public Box findInBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findInBoxByActorId(actorId);
		return result;
	}

	public Box findOutBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findOutBoxByActorId(actorId);
		return result;
	}

	public Box findSpamBoxByActorId(final int actorId) {
		Box result;
		result = this.boxRepository.findSpamBoxByActorId(actorId);
		return result;
	}

	public Collection<Box> findAllBoxByActor(final int actorId) {
		Collection<Box> boxes = new ArrayList<Box>();
		boxes = this.boxRepository.findAllBoxByActorId(actorId);
		return boxes;
	}

	public Boolean boxSecurity(final int boxId) {

		Boolean res = false;

		final Actor owner = this.boxRepository.findOne(boxId).getActor();

		final Actor login = this.actorService.findByPrincipal();

		if (login.equals(owner))
			res = true;

		return res;
	}
}
