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
import tr.com.cevher.java.repository.DepartmentRepository;
import tr.com.cevher.java.service.DepartmentService;
import tr.com.cevher.java.service.dto.DepartmentDTO;
import tr.com.cevher.java.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link tr.com.cevher.java.domain.Department}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentResource.class);

    private static final String ENTITY_NAME = "department";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartmentService departmentService;

    private final DepartmentRepository departmentRepository;

    public DepartmentResource(DepartmentService departmentService, DepartmentRepository departmentRepository) {
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    /**
     * {@code POST  /departments} : Create a new department.
     *
     * @param departmentDTO the departmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departmentDTO, or with status {@code 400 (Bad Request)} if the department has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departments")
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) throws URISyntaxException {
        log.debug("REST request to save Department : {}", departmentDTO);
        if (departmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new department cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity
            .created(new URI("/api/departments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /departments/:id} : Updates an existing department.
     *
     * @param id the id of the departmentDTO to save.
     * @param departmentDTO the departmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departmentDTO,
     * or with status {@code 400 (Bad Request)} if the departmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/departments/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DepartmentDTO departmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Department : {}, {}", id, departmentDTO);
        if (departmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DepartmentDTO result = departmentService.save(departmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /departments/:id} : Partial updates given fields of an existing department, field will ignore if it is null
     *
     * @param id the id of the departmentDTO to save.
     * @param departmentDTO the departmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departmentDTO,
     * or with status {@code 400 (Bad Request)} if the departmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the departmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the departmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/departments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DepartmentDTO> partialUpdateDepartment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DepartmentDTO departmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Department partially : {}, {}", id, departmentDTO);
        if (departmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, departmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!departmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DepartmentDTO> result = departmentService.partialUpdate(departmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /departments} : get all the departments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departments in body.
     */
    @GetMapping("/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments(Pageable pageable) {
        log.debug("REST request to get a page of Departments");
        Page<DepartmentDTO> page = departmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /departments/:id} : get the "id" department.
     *
     * @param id the id of the departmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departments/{id}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        Optional<DepartmentDTO> departmentDTO = departmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(departmentDTO);
    }

    /**
     * {@code DELETE  /departments/:id} : delete the "id" department.
     *
     * @param id the id of the departmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departments/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
