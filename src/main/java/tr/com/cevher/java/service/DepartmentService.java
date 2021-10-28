package tr.com.cevher.java.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tr.com.cevher.java.service.dto.DepartmentDTO;

/**
 * Service Interface for managing {@link tr.com.cevher.java.domain.Department}.
 */
public interface DepartmentService {
    /**
     * Save a department.
     *
     * @param departmentDTO the entity to save.
     * @return the persisted entity.
     */
    DepartmentDTO save(DepartmentDTO departmentDTO);

    /**
     * Partially updates a department.
     *
     * @param departmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DepartmentDTO> partialUpdate(DepartmentDTO departmentDTO);

    /**
     * Get all the departments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepartmentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" department.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepartmentDTO> findOne(Long id);

    /**
     * Delete the "id" department.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
