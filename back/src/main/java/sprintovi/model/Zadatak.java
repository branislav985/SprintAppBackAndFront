package sprintovi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Zadatak {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable=false, unique = true)
	private String ime;
	
	@Column(nullable=false)
	private String zaduzeni;
	
	@Column
	private Integer bodovi;	
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Sprint sprint;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Stanje stanje;
	
	public Zadatak() {
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

	public String getZaduzeni() {
		return zaduzeni;
	}

	public void setZaduzeni(String zaduzeni) {
		this.zaduzeni = zaduzeni;
	}

	public Integer getBodovi() {
		return bodovi;
	}

	public void setBodovi(Integer bodovi) {
		this.bodovi = bodovi;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
		if(!sprint.getZadaci().contains(this)){
			sprint.getZadaci().add(this);
		}
	}

	public Stanje getStanje() {
		return stanje;
	}

	public void setStanje(Stanje stanje) {
		this.stanje = stanje;
		if(!stanje.getZadaci().contains(this)){
			stanje.getZadaci().add(this);
		}
	}
	
	
}
