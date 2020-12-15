package sprintovi.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import sprintovi.model.User;
import sprintovi.service.UserService;
import sprintovi.web.dto.UserDto;

@Component
public class UserDtoToUser implements Converter<UserDto, User> {

	@Autowired
	private UserService userService;
	
	@Override
	public User convert(UserDto source) {
		User target = null;
		if(source.getId() != null) {
			target = userService.one(source.getId()).get();
		}
		
		// čak i da je došao dto sa popunjenim ID
		// moguće je da ne postoji, pa ga onda treba kreirati
		if(target == null) { 
			target = new User();
		}
				
		target.setUsername(source.getEmail());
		target.setDateOfBirth(source.getDateOfBirth());
		target.setEmail(source.getEmail());
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		
		return target;
	}

}
