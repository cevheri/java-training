package tr.com.cevher.java.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tr.com.cevher.java.domain.VisitService;

/**
 * Spring Data SQL repository for the VisitService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitServiceRepository extends JpaRepository<VisitService, Long> {}
