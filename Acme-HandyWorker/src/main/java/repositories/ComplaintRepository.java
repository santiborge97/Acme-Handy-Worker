
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select cu.complaints from Customer cu where cu.id = ?1")
	Collection<Complaint> findAllComplaintsOfCustomer(int customerId);

	@Query("select c from Complaint c where c.referee is null")
	Collection<Complaint> findAllUnassigned();

	@Query("select c from Complaint c where c.referee.id = ?1")
	Collection<Complaint> findAllComplaintsOfReferee(int refereeId);

	@Query("select c from HandyWorker hw join hw.applications a join a.fixUpTask f join f.complaints c where hw.id = ?1")
	Collection<Complaint> findAllComplaintsOfHandyWorker(int handyWorkerId);
}
