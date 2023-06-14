package ifrn.laj.cume.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.laj.cume.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
	
	List<Role> findAllByOrderByNameAsc();

}
