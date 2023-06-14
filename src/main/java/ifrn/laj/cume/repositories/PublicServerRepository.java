package ifrn.laj.cume.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.laj.cume.models.PublicServer;

public interface PublicServerRepository extends JpaRepository<PublicServer, Long> {

	Optional<PublicServer> findByRegistration(String Registration);
	
}
