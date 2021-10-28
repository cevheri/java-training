package tr.com.cevher.java.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.cevher.java.domain.Doctor;

/**
 * Spring Data SQL repository for the Doctor entity.
 */
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(
        value = "select distinct doctor from Doctor doctor left join fetch doctor.departments",
        countQuery = "select count(distinct doctor) from Doctor doctor"
    )
    Page<Doctor> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct doctor from Doctor doctor left join fetch doctor.departments")
    List<Doctor> findAllWithEagerRelationships();

    @Query("select doctor from Doctor doctor left join fetch doctor.departments where doctor.id =:id")
    Optional<Doctor> findOneWithEagerRelationships(@Param("id") Long id);
}
