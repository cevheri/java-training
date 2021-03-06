package tr.com.cevher.java.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tr.com.cevher.java.domain.Visit;

/**
 * Spring Data SQL repository for the Visit entity.
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query(
        value = "select distinct visit from Visit visit left join fetch visit.visitServices",
        countQuery = "select count(distinct visit) from Visit visit"
    )
    Page<Visit> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct visit from Visit visit left join fetch visit.visitServices")
    List<Visit> findAllWithEagerRelationships();

    @Query("select visit from Visit visit left join fetch visit.visitServices where visit.id =:id")
    Optional<Visit> findOneWithEagerRelationships(@Param("id") Long id);
}
