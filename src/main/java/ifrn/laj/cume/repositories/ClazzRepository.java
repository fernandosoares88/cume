package ifrn.laj.cume.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.laj.cume.models.Clazz;

public interface ClazzRepository extends JpaRepository<Clazz, Long>{
	
	List<Clazz> findAllByOrderByName();

}
