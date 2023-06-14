package ifrn.laj.cume.dtos;

import jakarta.validation.constraints.NotBlank;

public class StudentDTO {

	@NotBlank
	private String registration;
	@NotBlank
	private String name;
	@NotBlank
	private String clazz;

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

}
