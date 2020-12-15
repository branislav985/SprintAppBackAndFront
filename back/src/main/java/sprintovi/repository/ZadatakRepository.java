package sprintovi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sprintovi.model.Zadatak;

@Repository
public interface ZadatakRepository extends JpaRepository<Zadatak, Long> {

	@Query("SELECT z FROM Zadatak z WHERE" +
			"(:imeZadatka = NULL OR z.ime LIKE :imeZadatka) AND " + 
			"(:idSprinta = NULL OR z.sprint.id = :idSprinta)")
	Page<Zadatak> search(@Param("imeZadatka") String imeZadatka, @Param("idSprinta") Long idSprinta, Pageable pageable);

}
