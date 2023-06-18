package ifrn.laj.cume.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.laj.cume.models.Clazz;
import ifrn.laj.cume.models.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	
	Optional<Student> findByRegistration(String Registration);
	
	List<Student> findAllByOrderByName();
	
	List<Student> findAllByNameContainingOrderByName(String name);
	
	List<Student> findAllByNameContainingAndClazzOrderByName(String name, Clazz clazz);

}
