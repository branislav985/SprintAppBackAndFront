package sprintovi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sprintovi.model.Stanje;
import sprintovi.service.StanjeService;
import sprintovi.support.StanjeToStanjeDto;
import sprintovi.web.dto.StanjeDto;

@RestController
@RequestMapping("api/stanja")
public class ApiStanjeController {

	@Autowired
	private StanjeService stanjeService;
	
	@Autowired
	private StanjeToStanjeDto toDto;
	
	@GetMapping
	public ResponseEntity<List<StanjeDto>> getAll(
			@RequestParam(required = false) String name){
		
		List<Stanje> stanja = stanjeService.all();
		return new ResponseEntity<>(toDto.convert(stanja), HttpStatus.OK);
	}
	
}
