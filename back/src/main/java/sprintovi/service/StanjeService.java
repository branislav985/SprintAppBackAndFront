package sprintovi.service;

import java.util.List;
import java.util.Optional;

import sprintovi.model.Stanje;

public interface StanjeService {

	List<Stanje> all();
	Optional<Stanje> one(Long id);
	Stanje save(Stanje stanje);

}
