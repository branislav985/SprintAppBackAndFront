package sprintovi.web.dto;

public class SprintDto {
	private Long id;
	private String ime;
	private Integer brojBodova;
	
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
	
}
