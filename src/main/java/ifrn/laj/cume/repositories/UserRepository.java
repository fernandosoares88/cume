package ifrn.laj.cume.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.laj.cume.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByRegistration(String registration);
	
}
