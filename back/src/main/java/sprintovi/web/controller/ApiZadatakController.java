package sprintovi.web.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sprintovi.model.Zadatak;
import sprintovi.service.ZadatakService;
import sprintovi.support.ZadatakToZadatakDTO;
import sprintovi.web.dto.ZadatakDto;

@RestController
@RequestMapping(value = "/api/zadaci")
public class ApiZadatakController {
	@Autowired
	private ZadatakService zadatakService;

	@Autowired
	private ZadatakToZadatakDTO toDTO;

	@RequestMapping(method = RequestMethod.GET)
	ResponseEntity<List<ZadatakDto>> get(@RequestParam(value = "imeZadatka", required = false) String imeZadatka,
			@RequestParam(value = "idSprinta", required = false) Long idSprinta,
			@RequestParam(value = "pageNum", defaultValue = "0") int pageNum) {

		Page<Zadatak> page = null;

		if (imeZadatka != null || idSprinta != null) {
			page = zadatakService.search(imeZadatka, idSprinta, pageNum);
		} else {
			page = zadatakService.all(pageNum);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("Total-Pages", Integer.toString(page.getTotalPages()));

		return new ResponseEntity<>(toDTO.convert(page.getContent()), headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	ResponseEntity<ZadatakDto> getOne(@PathVariable Long id) {
		Optional<Zadatak> zadatak = zadatakService.one(id);
		if (!zadatak.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(zadatak.get()), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	ResponseEntity<ZadatakDto> delete(@PathVariable Long id) {
		Zadatak deleted = zadatakService.delete(id);

		if (deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(toDTO.convert(deleted), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<ZadatakDto> add(@Validated @RequestBody ZadatakDto newDto) {

		Zadatak saved = zadatakService.save(newDto);

		return new ResponseEntity<>(toDTO.convert(saved), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public ResponseEntity<ZadatakDto> edit(@Validated @RequestBody ZadatakDto zadatakDTO, @PathVariable Long id) {

		if (!id.equals(zadatakDTO.getId())) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Zadatak persisted = zadatakService.save(zadatakDTO);

		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/prelazak", method = RequestMethod.POST)
	public ResponseEntity prelazak(@PathVariable Long id) {
		
		Zadatak zadatak = zadatakService.prelazak(id);
		if(zadatak != null) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
