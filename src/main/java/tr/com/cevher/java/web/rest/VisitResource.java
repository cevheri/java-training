package tr.com.cevher.java.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import tr.com.cevher.java.repository.VisitRepository;
import tr.com.cevher.java.service.VisitService;
import tr.com.cevher.java.service.dto.VisitDTO;
import tr.com.cevher.java.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tr.com.cevher.java.domain.Visit}.
 */
@RestController
@RequestMapping("/api")
public class VisitResource {

    private final Logger log = LoggerFactory.getLogger(VisitResource.class);

    private static final String ENTITY_NAME = "visit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisitService visitService;

    private final VisitRepository visitRepository;

    public VisitResource(VisitService visitService, VisitRepository visitRepository) {
        this.visitService = visitService;
        this.visitRepository = visitRepository;
    }

    /**
     * {@code POST  /visits} : Create a new visit.
     *
     * @param visitDTO the visitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visitDTO, or with status {@code 400 (Bad Request)} if the visit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visits")
    public ResponseEntity<VisitDTO> createVisit(@RequestBody VisitDTO visitDTO) throws URISyntaxException {
        log.debug("REST request to save Visit : {}", visitDTO);
        if (visitDTO.getId() != null) {
            throw new BadRequestAlertException("A new visit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VisitDTO result = visitService.save(visitDTO);
        return ResponseEntity
            .created(new URI("/api/visits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visits/:id} : Updates an existing visit.
     *
     * @param id the id of the visitDTO to save.
     * @param visitDTO the visitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visitDTO,
     * or with status {@code 400 (Bad Request)} if the visitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visits/{id}")
    public ResponseEntity<VisitDTO> updateVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VisitDTO visitDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Visit : {}, {}", id, visitDTO);
        if (visitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, visitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!visitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        VisitDTO result = visitService.save(visitDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /visits/:id} : Partial updates given fields of an existing visit, field will ignore if it is null
     *
     * @param id the id of the visitDTO to save.
     * @param visitDTO the visitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visitDTO,
     * or with status {@code 400 (Bad Request)} if the visitDTO is not valid,
     * or with status {@code 404 (Not Found)} if the visitDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the visitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/visits/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VisitDTO> partialUpdateVisit(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody VisitDTO visitDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Visit partially : {}, {}", id, visitDTO);
        if (visitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, visitDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!visitRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VisitDTO> result = visitService.partialUpdate(visitDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visitDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /visits} : get all the visits.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visits in body.
     */
    @GetMapping("/visits")
    public ResponseEntity<List<VisitDTO>> getAllVisits(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Visits");
        Page<VisitDTO> page;
        if (eagerload) {
            page = visitService.findAllWithEagerRelationships(pageable);
        } else {
            page = visitService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visits/:id} : get the "id" visit.
     *
     * @param id the id of the visitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visits/{id}")
    public ResponseEntity<VisitDTO> getVisit(@PathVariable Long id) {
        log.debug("REST request to get Visit : {}", id);
        Optional<VisitDTO> visitDTO = visitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visitDTO);
    }

    /**
     * {@code DELETE  /visits/:id} : delete the "id" visit.
     *
     * @param id the id of the visitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visits/{id}")
    public ResponseEntity<Void> deleteVisit(@PathVariable Long id) {
        log.debug("REST request to delete Visit : {}", id);
        visitService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
