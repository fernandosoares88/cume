package ifrn.laj.cume.dtos;

import jakarta.validation.constraints.NotBlank;

public class PublicServerDTO {

	@NotBlank
	private String registration;
	@NotBlank
	private String name;

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

}
