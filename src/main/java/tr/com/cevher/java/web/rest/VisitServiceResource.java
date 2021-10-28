package tr.com.cevher.java.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import tr.com.cevher.java.repository.VisitServiceRepository;
import tr.com.cevher.java.service.VisitServiceService;
import tr.com.cevher.java.service.dto.VisitServiceDTO;
import tr.com.cevher.java.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tr.com.cevher.java.domain.VisitService}.
 */
@RestController
@RequestMapping("/api")
public class VisitServiceResource {

    private final Logger log = LoggerFactory.getLogger(VisitServiceResource.class);

    private static final String ENTITY_NAME = "visitService";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisitServiceService visitServiceService;

    private final VisitServiceRepository visitServiceRepository;

    public VisitServiceResource(VisitServiceService visitServiceService, VisitServiceRepository visitServiceRepository) {
        this.visitServiceService = visitServiceService;
        this.visitServiceRepository = visitServiceRepository;
    }

    /**
     * {@code POST  /visit-services} : Create a new visitService.
     *
     * @param visitServiceDTO the visitServiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visitServiceDTO, or with status {@code 400 (Bad Request)} if the visitService has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visit-services")
    public ResponseEntity<VisitServiceDTO> createVisitService(@Valid @RequestBody VisitServiceDTO visitServiceDTO)
        throws URISyntaxException {
        log.debug("REST request to save VisitService : {}", visitServiceDTO);
        if (visitServiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new visitService cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VisitServiceDTO result = visitServiceService.save(visitServiceDTO);
        return ResponseEntity
            .created(new URI("/api/visit-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visit-services/:id} : Updates an existing visitService.
     *
     * @param id the id of the visitServiceDTO to save.
     * @param visitServiceDTO the visitServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visitServiceDTO,
     * or with status {@code 400 (Bad Request)} if the visitServiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visitServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visit-services/{id}")
    public ResponseEntity<VisitServiceDTO> updateVisitService(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody VisitServiceDTO visitServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update VisitService : {}, {}", id, visitServiceDTO);
        if (visitServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, visitServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!visitServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VisitServiceDTO result = visitServiceService.save(visitServiceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visitServiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /visit-services/:id} : Partial updates given fields of an existing visitService, field will ignore if it is null
     *
     * @param id the id of the visitServiceDTO to save.
     * @param visitServiceDTO the visitServiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visitServiceDTO,
     * or with status {@code 400 (Bad Request)} if the visitServiceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the visitServiceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the visitServiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/visit-services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VisitServiceDTO> partialUpdateVisitService(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody VisitServiceDTO visitServiceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update VisitService partially : {}, {}", id, visitServiceDTO);
        if (visitServiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, visitServiceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!visitServiceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VisitServiceDTO> result = visitServiceService.partialUpdate(visitServiceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visitServiceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /visit-services} : get all the visitServices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visitServices in body.
     */
    @GetMapping("/visit-services")
    public ResponseEntity<List<VisitServiceDTO>> getAllVisitServices(Pageable pageable) {
        log.debug("REST request to get a page of VisitServices");
        Page<VisitServiceDTO> page = visitServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visit-services/:id} : get the "id" visitService.
     *
     * @param id the id of the visitServiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visitServiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visit-services/{id}")
    public ResponseEntity<VisitServiceDTO> getVisitService(@PathVariable Long id) {
        log.debug("REST request to get VisitService : {}", id);
        Optional<VisitServiceDTO> visitServiceDTO = visitServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visitServiceDTO);
    }

    /**
     * {@code DELETE  /visit-services/:id} : delete the "id" visitService.
     *
     * @param id the id of the visitServiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visit-services/{id}")
    public ResponseEntity<Void> deleteVisitService(@PathVariable Long id) {
        log.debug("REST request to delete VisitService : {}", id);
        visitServiceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
