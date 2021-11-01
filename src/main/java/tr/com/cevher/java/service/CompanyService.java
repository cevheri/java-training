package tr.com.cevher.java.service;

import java.util.List;
import java.util.Optional;
import tr.com.cevher.java.service.dto.CompanyDTO;

/**
 * Service Interface for managing {@link tr.com.cevher.java.domain.Company}.
 */
public interface CompanyService {
    /**
     * Save a company.
     *
     * @param companyDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyDTO save(CompanyDTO companyDTO);

    /**
     * Partially updates a company.
     *
     * @param companyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyDTO> partialUpdate(CompanyDTO companyDTO);

    /**
     * Get all the companies.
     *
     * @return the list of entities.
     */
    List<CompanyDTO> findAll();

    /**
     * Get the "id" company.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyDTO> findOne(Long id);

    /**
     * Delete the "id" company.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
