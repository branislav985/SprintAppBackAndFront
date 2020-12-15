package sprintovi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sprintovi.model.Stanje;
import sprintovi.repository.StanjeRepository;
import sprintovi.service.StanjeService;

@Service
public class JpaStanjeService implements StanjeService {
	
	@Autowired
	private StanjeRepository stanjeRepository;

	@Override
	public List<Stanje> all() {
		return stanjeRepository.findAll();
	}

	@Override
	public Optional<Stanje> one(Long id) {
		return stanjeRepository.findById(id);
	}

	@Override
	public Stanje save(Stanje stanje) {
		return stanjeRepository.save(stanje);
	}

}
