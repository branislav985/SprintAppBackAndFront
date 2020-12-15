package sprintovi.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sprint {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ime;
	private Integer brojBodova = 0;
	
	@OneToMany(mappedBy="sprint", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Zadatak> zadaci = new ArrayList<>();
	
	public Sprint() {
		super();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public Integer getBrojBodova() {
		return brojBodova;
	}

	public void setBrojBodova(Integer brojBodova) {
		this.brojBodova = brojBodova;
	}

	public List<Zadatak> getZadaci() {
		return zadaci;
	}

	public void setZadaci(List<Zadatak> zadaci) {
		this.zadaci = zadaci;
	}
	
	public void addZadatak(Zadatak zadatak) {
		if(zadatak.getSprint() != this) {
			zadatak.setSprint(this);
		}
		zadaci.add(zadatak);
	}
}
