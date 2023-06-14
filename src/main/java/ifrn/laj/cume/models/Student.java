package ifrn.laj.cume.models;

import jakarta.persistence.Entity;

@Entity
public class Student extends User {

	private static final long serialVersionUID = 1L;
	
	private String clazz;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

}
