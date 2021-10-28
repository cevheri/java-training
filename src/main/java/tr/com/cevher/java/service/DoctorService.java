package tr.com.cevher.java.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tr.com.cevher.java.service.dto.DoctorDTO;

/**
 * Service Interface for managing {@link tr.com.cevher.java.domain.Doctor}.
 */
public interface DoctorService {
    /**
     * Save a doctor.
     *
     * @param doctorDTO the entity to save.
     * @return the persisted entity.
     */
    DoctorDTO save(DoctorDTO doctorDTO);

    /**
     * Partially updates a doctor.
     *
     * @param doctorDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DoctorDTO> partialUpdate(DoctorDTO doctorDTO);

    /**
     * Get all the doctors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DoctorDTO> findAll(Pageable pageable);

    /**
     * Get all the doctors with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DoctorDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" doctor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoctorDTO> findOne(Long id);

    /**
     * Delete the "id" doctor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
