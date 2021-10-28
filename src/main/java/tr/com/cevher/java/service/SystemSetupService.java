package tr.com.cevher.java.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tr.com.cevher.java.service.dto.SystemSetupDTO;

/**
 * Service Interface for managing {@link tr.com.cevher.java.domain.SystemSetup}.
 */
public interface SystemSetupService {
    /**
     * Save a systemSetup.
     *
     * @param systemSetupDTO the entity to save.
     * @return the persisted entity.
     */
    SystemSetupDTO save(SystemSetupDTO systemSetupDTO);

    /**
     * Partially updates a systemSetup.
     *
     * @param systemSetupDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SystemSetupDTO> partialUpdate(SystemSetupDTO systemSetupDTO);

    /**
     * Get all the systemSetups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SystemSetupDTO> findAll(Pageable pageable);

    /**
     * Get the "id" systemSetup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SystemSetupDTO> findOne(Long id);

    /**
     * Delete the "id" systemSetup.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
