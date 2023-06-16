package ifrn.laj.cume.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserChangePasswordDTO {

	@NotBlank
	private String actualPass;
	@NotBlank
	private String newPass;
	@NotBlank
	private String confirmPass;

	public String getActualPass() {
		return actualPass;
	}

	public void setActualPass(String actualPass) {
		this.actualPass = actualPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}

}
