package sprintovi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sprintovi.model.Sprint;
import sprintovi.model.Stanje;
import sprintovi.model.User;
import sprintovi.model.UserRole;
import sprintovi.model.Zadatak;
import sprintovi.repository.SprintRepository;
import sprintovi.repository.StanjeRepository;
import sprintovi.repository.ZadatakRepository;
import sprintovi.service.UserService;

@Component
public class TestData {

	@Autowired
	private UserService userService;
	
	@Autowired
	private SprintRepository sprintRepository;
	
	@Autowired
	private StanjeRepository stanjeRepository;
	
	@Autowired
	private ZadatakRepository zadatakRepository;
		
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
			
		Sprint sprint1 = new Sprint();
		sprint1.setIme("Sprint 1");
		
		Sprint sprint2 = new Sprint();
		sprint2.setIme("Sprint 2");
		
		sprint1 = sprintRepository.save(sprint1);
		sprint2 = sprintRepository.save(sprint2);
		
		Stanje nov = new Stanje();
		nov.setIme("Nov");
		
		Stanje uToku = new Stanje();
		uToku.setIme("U toku");
		
		Stanje gotov = new Stanje();
		gotov.setIme("Gotov");
		
		nov = stanjeRepository.save(nov);
		uToku = stanjeRepository.save(uToku);
		gotov = stanjeRepository.save(gotov);
		
		
		Zadatak zadatak1 = new Zadatak();
		zadatak1.setIme("Kreirati rest servis");
		zadatak1.setBodovi(8);
		zadatak1.setZaduzeni("Nikola");
		zadatak1.setSprint(sprint1);
		zadatak1.setStanje(uToku);
		
		Zadatak zadatak2 = new Zadatak();
		zadatak2.setIme("Kreirati logo");
		zadatak2.setBodovi(5);
		zadatak2.setZaduzeni("Ana");
		zadatak2.setSprint(sprint1);
		zadatak2.setStanje(gotov);
		
		Zadatak zadatak3 = new Zadatak();
		zadatak3.setIme("Kreirati početnu stranicu");
		zadatak3.setBodovi(5);
		zadatak3.setZaduzeni("Branimir");
		zadatak3.setSprint(sprint2);
		zadatak3.setStanje(nov);
		
		zadatakRepository.save(zadatak1);
		zadatakRepository.save(zadatak2);
		zadatakRepository.save(zadatak3);
		
		sprint1.setBrojBodova(13);
		sprintRepository.save(sprint1);
		
		sprint2.setBrojBodova(5);
		sprintRepository.save(sprint2);
		
		List<User> users = new ArrayList<User>();
		for (int i = 1; i <= 3; i++) {
			User user = new User();
			user.setUsername("user" + i);
			user.setFirstName("First " + i);
			user.setLastName("Last " + i);
			user.setEmail("user"+i+"@mail.com");
			user.setDateOfBirth(LocalDateTime.now().minusYears(20 + i));

			// ubacen deo koda zbog greske koja se desavala ako kroz ubacivanje podataka dva puta
			// kriptujemo lozinku
			String encodedPass = passwordEncoder.encode("pass"+i);
			user.setPassword(encodedPass);

			List<UserRole> roles = Arrays.asList(UserRole.values());
			Random random = new Random();
			user.setRole(roles.get(random.nextInt(3)));
			
			users.add(user);
			userService.save(user);
			
		}
	}
}
