package sprintovi.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import sprintovi.model.Sprint;
import sprintovi.model.Stanje;
import sprintovi.model.Zadatak;
import sprintovi.repository.SprintRepository;
import sprintovi.repository.StanjeRepository;
import sprintovi.repository.ZadatakRepository;
import sprintovi.service.ZadatakService;
import sprintovi.support.ZadatakDtoToZadatak;
import sprintovi.web.dto.ZadatakDto;


@Service
@Transactional
public class JpaZadatakService implements ZadatakService {
	
	@Autowired
	private ZadatakDtoToZadatak toEntity;
	
	@Autowired
	private ZadatakRepository zadatakRepository;
	
	@Autowired
	private SprintRepository sprintRepository;
	
	@Autowired
	private StanjeRepository stanjeRepository;
	
	@Override
	public Page<Zadatak> all(int page) {
		return zadatakRepository.findAll(PageRequest.of(page, 5));
	}
	
	@Override
	public Page<Zadatak> search(String imeZadatka, Long idSprinta, int page){
		
		if(imeZadatka != null) {
			imeZadatka = "%" + imeZadatka + "%";
		}
		
		return zadatakRepository.search(imeZadatka, idSprinta, PageRequest.of(page, 5));
	}
	
	@Override
	public Optional<Zadatak> one(Long id) {
		return zadatakRepository.findById(id);
	}

	@Override
	public Zadatak save(ZadatakDto zadatakDto) {
		
		Long id = zadatakDto.getId();
		
		Integer stariBrojBodovaZadatka = 0;
		if(id != null) { // Trebalo bi da postoji u bazi
			Optional<Zadatak> stariZadatakOptional = one(id);
			if(stariZadatakOptional.isPresent()) {
				// Ako je zadatak koji se trenutno čuva već prisutan u bazi,
				// uzmi broj bodova koji je nosio (kako bi oduzeo od sprinta)				
				Zadatak stariZadatak = stariZadatakOptional.get();
				stariBrojBodovaZadatka = stariZadatak.getBodovi();				
			}
		}
		
		Zadatak zadatak = toEntity.convert(zadatakDto);
		if(zadatak.getId() == null) {
			// Zbog 3. zadatka, podešavamo stanje na "nov",
			// ako još uvek ne postoji u bazi
			Stanje nov = stanjeRepository.findById(1L).get();
			zadatak.setStanje(nov);
		}
		
		Sprint sprint = zadatak.getSprint();
		
		Integer osnovniBrojBodova = sprint.getBrojBodova() - stariBrojBodovaZadatka;
		Integer noviBrojBodova = osnovniBrojBodova + zadatak.getBodovi();
		sprint.setBrojBodova(noviBrojBodova);
		
		Zadatak sacuvaniZadatak = zadatakRepository.save(zadatak);
		sprintRepository.save(sprint);
		return sacuvaniZadatak;
	}


	@Override
	public Zadatak delete(Long id) {
		Optional<Zadatak> zadatakOptional = zadatakRepository.findById(id);
		if(zadatakOptional.isPresent()) {
			Zadatak zadatak = zadatakOptional.get();
			
			Sprint sprint = zadatak.getSprint();
			Integer noviBrojBodova = sprint.getBrojBodova() - zadatak.getBodovi();
			sprint.setBrojBodova(noviBrojBodova);
			sprintRepository.save(sprint);
			
			zadatakRepository.deleteById(id);
			return zadatak;
		}
		
		return null;
	}

	@Override
	public Zadatak prelazak(Long id) {
		
		Zadatak zadatak = zadatakRepository.getOne(id);
		if(zadatak != null) {
			Stanje trenutnoStanje = zadatak.getStanje();
			if(trenutnoStanje.getId().equals(1L)) {
				Stanje uToku = stanjeRepository.getOne(2L);
				zadatak.setStanje(uToku);
			}
			else if(trenutnoStanje.getId().equals(2L)) {
				Stanje gotov = stanjeRepository.getOne(3L);
				zadatak.setStanje(gotov);
			}
			
			return zadatakRepository.save(zadatak);
		}
		
		return null;
	}
	
	

}
