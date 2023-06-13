package ifrn.laj.cume.dtos;

public class LunchRequestDTO {

	private boolean confirmation;

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	@Override
	public String toString() {
		return "LunchRequestDTO [confirmation=" + confirmation + "]";
	}

}
