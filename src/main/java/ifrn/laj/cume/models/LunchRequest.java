package ifrn.laj.cume.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class LunchRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate lunchRequestDate;
	private LocalDateTime dateTimeOfRequest;
	@ManyToOne
	private Student student;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getLunchRequestDate() {
		return lunchRequestDate;
	}

	public void setLunchRequestDate(LocalDate lunchRequestDate) {
		this.lunchRequestDate = lunchRequestDate;
	}

	public LocalDateTime getDateTimeOfRequest() {
		return dateTimeOfRequest;
	}

	public void setDateTimeOfRequest(LocalDateTime dateTimeOfRequest) {
		this.dateTimeOfRequest = dateTimeOfRequest;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
