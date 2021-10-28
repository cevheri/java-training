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
import tr.com.cevher.java.repository.SystemSetupRepository;
import tr.com.cevher.java.service.SystemSetupService;
import tr.com.cevher.java.service.dto.SystemSetupDTO;
import tr.com.cevher.java.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tr.com.cevher.java.domain.SystemSetup}.
 */
@RestController
@RequestMapping("/api")
public class SystemSetupResource {

    private final Logger log = LoggerFactory.getLogger(SystemSetupResource.class);

    private static final String ENTITY_NAME = "systemSetup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SystemSetupService systemSetupService;

    private final SystemSetupRepository systemSetupRepository;

    public SystemSetupResource(SystemSetupService systemSetupService, SystemSetupRepository systemSetupRepository) {
        this.systemSetupService = systemSetupService;
        this.systemSetupRepository = systemSetupRepository;
    }

    /**
     * {@code POST  /system-setups} : Create a new systemSetup.
     *
     * @param systemSetupDTO the systemSetupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new systemSetupDTO, or with status {@code 400 (Bad Request)} if the systemSetup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/system-setups")
    public ResponseEntity<SystemSetupDTO> createSystemSetup(@Valid @RequestBody SystemSetupDTO systemSetupDTO) throws URISyntaxException {
        log.debug("REST request to save SystemSetup : {}", systemSetupDTO);
        if (systemSetupDTO.getId() != null) {
            throw new BadRequestAlertException("A new systemSetup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemSetupDTO result = systemSetupService.save(systemSetupDTO);
        return ResponseEntity
            .created(new URI("/api/system-setups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /system-setups/:id} : Updates an existing systemSetup.
     *
     * @param id the id of the systemSetupDTO to save.
     * @param systemSetupDTO the systemSetupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSetupDTO,
     * or with status {@code 400 (Bad Request)} if the systemSetupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the systemSetupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/system-setups/{id}")
    public ResponseEntity<SystemSetupDTO> updateSystemSetup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SystemSetupDTO systemSetupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SystemSetup : {}, {}", id, systemSetupDTO);
        if (systemSetupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemSetupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemSetupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SystemSetupDTO result = systemSetupService.save(systemSetupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemSetupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /system-setups/:id} : Partial updates given fields of an existing systemSetup, field will ignore if it is null
     *
     * @param id the id of the systemSetupDTO to save.
     * @param systemSetupDTO the systemSetupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated systemSetupDTO,
     * or with status {@code 400 (Bad Request)} if the systemSetupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the systemSetupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the systemSetupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/system-setups/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SystemSetupDTO> partialUpdateSystemSetup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SystemSetupDTO systemSetupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SystemSetup partially : {}, {}", id, systemSetupDTO);
        if (systemSetupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, systemSetupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!systemSetupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SystemSetupDTO> result = systemSetupService.partialUpdate(systemSetupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, systemSetupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /system-setups} : get all the systemSetups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of systemSetups in body.
     */
    @GetMapping("/system-setups")
    public ResponseEntity<List<SystemSetupDTO>> getAllSystemSetups(Pageable pageable) {
        log.debug("REST request to get a page of SystemSetups");
        Page<SystemSetupDTO> page = systemSetupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /system-setups/:id} : get the "id" systemSetup.
     *
     * @param id the id of the systemSetupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the systemSetupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/system-setups/{id}")
    public ResponseEntity<SystemSetupDTO> getSystemSetup(@PathVariable Long id) {
        log.debug("REST request to get SystemSetup : {}", id);
        Optional<SystemSetupDTO> systemSetupDTO = systemSetupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemSetupDTO);
    }

    /**
     * {@code DELETE  /system-setups/:id} : delete the "id" systemSetup.
     *
     * @param id the id of the systemSetupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/system-setups/{id}")
    public ResponseEntity<Void> deleteSystemSetup(@PathVariable Long id) {
        log.debug("REST request to delete SystemSetup : {}", id);
        systemSetupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
