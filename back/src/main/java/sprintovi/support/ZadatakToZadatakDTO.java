package sprintovi.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.Zadatak;
import sprintovi.web.dto.ZadatakDto;


@Component
public class ZadatakToZadatakDTO implements Converter<Zadatak, ZadatakDto>{

	@Override
	public ZadatakDto convert(Zadatak zadatak) {
		
		ZadatakDto retValue = new ZadatakDto();
		retValue.setId(zadatak.getId());
		retValue.setIme(zadatak.getIme());
		retValue.setBodovi(zadatak.getBodovi());
		retValue.setZaduzeni(zadatak.getZaduzeni());
		
		retValue.setSprintId(zadatak.getSprint().getId());
		retValue.setSprintIme(zadatak.getSprint().getIme());
		
		retValue.setStanjeId(zadatak.getStanje().getId());
		retValue.setStanjeIme(zadatak.getStanje().getIme());
		
		return retValue;
	}

	public List<ZadatakDto> convert(List<Zadatak> zadaci){
		List<ZadatakDto> ret = new ArrayList<>();
		
		for(Zadatak z : zadaci){
			ret.add(convert(z));
		}
		
		return ret;
	}

}
