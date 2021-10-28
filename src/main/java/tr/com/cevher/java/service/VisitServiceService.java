package tr.com.cevher.java.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tr.com.cevher.java.service.dto.VisitServiceDTO;

/**
 * Service Interface for managing {@link tr.com.cevher.java.domain.VisitService}.
 */
public interface VisitServiceService {
    /**
     * Save a visitService.
     *
     * @param visitServiceDTO the entity to save.
     * @return the persisted entity.
     */
    VisitServiceDTO save(VisitServiceDTO visitServiceDTO);

    /**
     * Partially updates a visitService.
     *
     * @param visitServiceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<VisitServiceDTO> partialUpdate(VisitServiceDTO visitServiceDTO);

    /**
     * Get all the visitServices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<VisitServiceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" visitService.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VisitServiceDTO> findOne(Long id);

    /**
     * Delete the "id" visitService.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
