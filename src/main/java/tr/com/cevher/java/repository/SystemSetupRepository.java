package tr.com.cevher.java.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tr.com.cevher.java.domain.SystemSetup;

/**
 * Spring Data SQL repository for the SystemSetup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SystemSetupRepository extends JpaRepository<SystemSetup, Long> {}
