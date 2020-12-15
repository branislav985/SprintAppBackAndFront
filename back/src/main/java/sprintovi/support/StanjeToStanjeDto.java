package sprintovi.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.Stanje;
import sprintovi.web.dto.StanjeDto;

@Component
public class StanjeToStanjeDto implements Converter<Stanje, StanjeDto>{

	@Override
	public StanjeDto convert(Stanje source) {
		StanjeDto dto = new StanjeDto();
		dto.setId(source.getId());
		dto.setIme(source.getIme());
		
		return dto;
	}
	
	public List<StanjeDto> convert(List<Stanje> source){
		List<StanjeDto> retVal = new ArrayList<>();
		
		for(Stanje s : source) {
			StanjeDto dto = convert(s);
			retVal.add(dto);
		}
		
		return retVal;
	}

}
