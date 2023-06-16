package ifrn.laj.cume.repositories;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.laj.cume.models.LunchRequest;
import ifrn.laj.cume.models.Student;

public interface LunchRequestRepository extends JpaRepository<LunchRequest, Long> {
	
	Optional<LunchRequest> findByStudentAndLunchRequestDate(Student student, LocalDate lunchReuqestDate);

}
