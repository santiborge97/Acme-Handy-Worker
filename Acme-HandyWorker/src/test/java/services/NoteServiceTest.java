
package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private NoteService	noteService;


	//Tests -------------------------------------------------------

	@Test
	public void NoteCreateTest() {

		super.authenticate("referee1");

		final Note note = this.noteService.create(this.getEntityId("report1")); //TODO: Esto hay que tocarlo

		Assert.notNull(note.getMoment());
		Assert.isNull(note.getCommentCustomer());
		Assert.isNull(note.getCommentReferee());
		Assert.isNull(note.getCommentHandyWorker());

		super.authenticate(null);

	}
	@Test
	public void NoteFindOneTest() {

		super.authenticate("referee1");

		final Note note = this.noteService.create(this.getEntityId("report1")); //TODO: Esto hay que tocarlo

		note.setCommentReferee("Example22");
		note.setCommentCustomer("");
		note.setCommentHandyWorker("");

		final Note saved = this.noteService.save(note);

		final Note finded = this.noteService.findOne(saved.getId());

		Assert.isTrue(finded.getCommentReferee().equals(saved.getCommentReferee()));
		Assert.isTrue(finded.getCommentCustomer().equals(""));
		Assert.isTrue(finded.getCommentHandyWorker().equals(""));

		super.authenticate(null);
	}
}
