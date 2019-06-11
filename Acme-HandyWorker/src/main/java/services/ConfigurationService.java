
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import domain.Actor;
import domain.Box;
import domain.Configuration;
import domain.Message;

@Service
@Transactional
public class ConfigurationService {

	//Managed Repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	//Supporting services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private BoxService				boxService;


	//Simple CRUD methods

	public Configuration save(final Configuration c) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Configuration configuration = this.configurationRepository.save(c);

		Assert.notNull(configuration);

		return configuration;
	}

	public Configuration findOne(final int configurationId) {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Configuration configuration = this.configurationRepository.findOne(configurationId);

		Assert.notNull(configuration);

		return configuration;
	}

	public Collection<Configuration> findAll() {

		final Actor actor = this.actorService.findByPrincipal();
		Assert.notNull(actor);
		final Authority authorityAdmin = new Authority();
		authorityAdmin.setAuthority(Authority.ADMIN);
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authorityAdmin));

		final Collection<Configuration> configurations = this.configurationRepository.findAll();

		Assert.notNull(configurations);

		return configurations;
	}

	public Configuration findConfiguration() {
		Configuration config;
		config = this.configurationRepository.findAll().get(0);
		Assert.notNull(config);
		return config;
	}

	public Boolean spamContent(final String text) {

		Boolean result = false;
		if (!text.isEmpty()) {
			final Configuration config = this.findConfiguration();

			final Collection<String> spamWords = config.getSpamWord();

			for (final String word : spamWords)
				if (text.toLowerCase().contains(word.toLowerCase())) {
					result = true;
					break;
				}
			if (result == true)
				this.actorService.convertToSuspiciousActor();
		}

		return result;
	}

	public Boolean creditCardMakesContent(final String text) {

		Boolean result = false;

		final Configuration config = this.findConfiguration();

		final Collection<String> makes = config.getCreditCardMakes();

		for (final String word : makes)
			if (text.contains(word)) {
				result = true;
				break;
			}

		return result;
	}

	public void notificationStatus(final int applicationId) {

		final Actor sender = this.administratorService.findAdministrator();

		final Actor recipient1 = this.handyWorkerService.findHandyWorkerByApplicationId(applicationId);

		final Box inbox1 = this.boxService.findInBoxByActorId(recipient1.getId());

		final Actor recipient2 = this.actorService.findByPrincipal();

		final Box inbox2 = this.boxService.findInBoxByActorId(recipient2.getId());

		final Message message1 = this.messageService.create2();

		message1.setSender(sender);
		message1.setRecipient(recipient1);
		message1.setSubject("Change of status of your application/Cambio de estado de su solicitud");
		message1.setBody("Status your application has been modified/El estado de su solicitud ha sido modificado");

		final Collection<Box> boxes1 = message1.getBoxes();
		boxes1.add(inbox1);
		message1.setBoxes(boxes1);
		this.messageService.save2(message1);

		final Message message2 = this.messageService.create2();

		message2.setSender(sender);
		message2.setRecipient(recipient2);
		message2.setSubject("Change of status made successfully/Cambio de estado realizado con éxito");
		message2.setBody("You have modified status of an application/Usted ha modificado el estado de una solicitud");

		final Collection<Box> boxes2 = message2.getBoxes();
		boxes2.add(inbox2);
		message2.setBoxes(boxes2);
		this.messageService.save2(message2);

	}
}
