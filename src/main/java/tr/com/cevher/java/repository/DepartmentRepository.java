package tr.com.cevher.java.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import tr.com.cevher.java.domain.Department;

/**
 * Spring Data SQL repository for the Department entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {}
