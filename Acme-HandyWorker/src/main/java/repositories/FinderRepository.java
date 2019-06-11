
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from Finder f join f.fixUpTasks t where t.id = ?1")
	Collection<Finder> findFindersByFixUpTaskId(int fixUpTaskId);

	@Query("select f from Finder f where f.handyWorker.id = ?1")
	Finder findFinderByHW(int actorId);

}
