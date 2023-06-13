package ifrn.laj.cume.models;

import jakarta.persistence.Entity;

@Entity
public class Student extends User {

	private String clazz;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

}
