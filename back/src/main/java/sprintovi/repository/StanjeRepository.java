package sprintovi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sprintovi.model.Stanje;

@Repository
public interface StanjeRepository extends JpaRepository<Stanje, Long>{

}
