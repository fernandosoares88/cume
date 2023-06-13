package ifrn.laj.cume.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.laj.cume.models.LunchRequest;

public interface LunchRequestRepository extends JpaRepository<LunchRequest, Long> {

}
