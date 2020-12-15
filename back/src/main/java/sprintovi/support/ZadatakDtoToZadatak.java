package sprintovi.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.Sprint;
import sprintovi.model.Stanje;
import sprintovi.model.Zadatak;
import sprintovi.service.SprintService;
import sprintovi.service.StanjeService;
import sprintovi.service.ZadatakService;
import sprintovi.web.dto.ZadatakDto;

@Component
public class ZadatakDtoToZadatak implements Converter<ZadatakDto, Zadatak>{

	@Autowired
	private ZadatakService zadatakService;
	
	@Autowired
	private SprintService sprintService;
	
	@Autowired
	private StanjeService stanjeService;
	
	@Override
	public Zadatak convert(ZadatakDto zadatakDto) {

		Sprint sprint = null;
		if(zadatakDto.getSprintId() != null) {
			sprint = sprintService.one(zadatakDto.getSprintId()).get();
		}
		
		Stanje stanje = null;
		if(zadatakDto.getStanjeId() != null) {
			stanje = stanjeService.one(zadatakDto.getStanjeId()).get();
		}
		
		if(sprint!=null) {
			
			Long id = zadatakDto.getId();
			Zadatak zadatak = id == null ? new Zadatak() : zadatakService.one(id).get();
			
			if(zadatak != null) {
				zadatak.setId(zadatakDto.getId());
				zadatak.setIme(zadatakDto.getIme());
				zadatak.setBodovi(zadatakDto.getBodovi());
				zadatak.setZaduzeni(zadatakDto.getZaduzeni());
				
				zadatak.setSprint(sprint);
				
				// Zbog 3. zadatka - stanje ne mora da se dodaje na početku
				if(stanje != null) {
					zadatak.setStanje(stanje);	
				}
			}
			
			return zadatak;
			
		}else {
			throw new IllegalStateException("Trying to attach to non-existant entities");
		}
		
	}
}
