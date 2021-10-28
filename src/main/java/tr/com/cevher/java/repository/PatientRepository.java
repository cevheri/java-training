package tr.com.cevher.java.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tr.com.cevher.java.domain.Patient;

/**
 * Spring Data SQL repository for the Patient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {}
