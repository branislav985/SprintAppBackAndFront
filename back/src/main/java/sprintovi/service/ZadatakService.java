package sprintovi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import sprintovi.model.Zadatak;
import sprintovi.web.dto.ZadatakDto;

public interface ZadatakService {
	
	Page<Zadatak> search(String imeZadatka, Long idSprinta, int pageNum);
	Page<Zadatak> all(int page);
	Optional<Zadatak> one(Long id);
	Zadatak save(ZadatakDto zadatak);
	Zadatak delete(Long id);
	Zadatak prelazak(Long id);
	
}
