package tr.com.cevher.java.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tr.com.cevher.java.IntegrationTest;
import tr.com.cevher.java.domain.VisitService;
import tr.com.cevher.java.repository.VisitServiceRepository;
import tr.com.cevher.java.service.dto.VisitServiceDTO;
import tr.com.cevher.java.service.mapper.VisitServiceMapper;

/**
 * Integration tests for the {@link VisitServiceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class VisitServiceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String ENTITY_API_URL = "/api/visit-services";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VisitServiceRepository visitServiceRepository;

    @Autowired
    private VisitServiceMapper visitServiceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVisitServiceMockMvc;

    private VisitService visitService;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisitService createEntity(EntityManager em) {
        VisitService visitService = new VisitService()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .active(DEFAULT_ACTIVE)
            .price(DEFAULT_PRICE);
        return visitService;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VisitService createUpdatedEntity(EntityManager em) {
        VisitService visitService = new VisitService()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .active(UPDATED_ACTIVE)
            .price(UPDATED_PRICE);
        return visitService;
    }

    @BeforeEach
    public void initTest() {
        visitService = createEntity(em);
    }

    @Test
    @Transactional
    void createVisitService() throws Exception {
        int databaseSizeBeforeCreate = visitServiceRepository.findAll().size();
        // Create the VisitService
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);
        restVisitServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeCreate + 1);
        VisitService testVisitService = visitServiceList.get(visitServiceList.size() - 1);
        assertThat(testVisitService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVisitService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVisitService.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVisitService.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void createVisitServiceWithExistingId() throws Exception {
        // Create the VisitService with an existing ID
        visitService.setId(1L);
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        int databaseSizeBeforeCreate = visitServiceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitServiceRepository.findAll().size();
        // set the field null
        visitService.setName(null);

        // Create the VisitService, which fails.
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        restVisitServiceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isBadRequest());

        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVisitServices() throws Exception {
        // Initialize the database
        visitServiceRepository.saveAndFlush(visitService);

        // Get all the visitServiceList
        restVisitServiceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    void getVisitService() throws Exception {
        // Initialize the database
        visitServiceRepository.saveAndFlush(visitService);

        // Get the visitService
        restVisitServiceMockMvc
            .perform(get(ENTITY_API_URL_ID, visitService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visitService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingVisitService() throws Exception {
        // Get the visitService
        restVisitServiceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVisitService() throws Exception {
        // Initialize the database
        visitServiceRepository.saveAndFlush(visitService);

        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();

        // Update the visitService
        VisitService updatedVisitService = visitServiceRepository.findById(visitService.getId()).get();
        // Disconnect from session so that the updates on updatedVisitService are not directly saved in db
        em.detach(updatedVisitService);
        updatedVisitService.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).active(UPDATED_ACTIVE).price(UPDATED_PRICE);
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(updatedVisitService);

        restVisitServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, visitServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isOk());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
        VisitService testVisitService = visitServiceList.get(visitServiceList.size() - 1);
        assertThat(testVisitService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVisitService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVisitService.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVisitService.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void putNonExistingVisitService() throws Exception {
        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();
        visitService.setId(count.incrementAndGet());

        // Create the VisitService
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, visitServiceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVisitService() throws Exception {
        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();
        visitService.setId(count.incrementAndGet());

        // Create the VisitService
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitServiceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVisitService() throws Exception {
        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();
        visitService.setId(count.incrementAndGet());

        // Create the VisitService
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitServiceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVisitServiceWithPatch() throws Exception {
        // Initialize the database
        visitServiceRepository.saveAndFlush(visitService);

        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();

        // Update the visitService using partial update
        VisitService partialUpdatedVisitService = new VisitService();
        partialUpdatedVisitService.setId(visitService.getId());

        partialUpdatedVisitService.name(UPDATED_NAME);

        restVisitServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisitService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisitService))
            )
            .andExpect(status().isOk());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
        VisitService testVisitService = visitServiceList.get(visitServiceList.size() - 1);
        assertThat(testVisitService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVisitService.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVisitService.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVisitService.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    void fullUpdateVisitServiceWithPatch() throws Exception {
        // Initialize the database
        visitServiceRepository.saveAndFlush(visitService);

        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();

        // Update the visitService using partial update
        VisitService partialUpdatedVisitService = new VisitService();
        partialUpdatedVisitService.setId(visitService.getId());

        partialUpdatedVisitService.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).active(UPDATED_ACTIVE).price(UPDATED_PRICE);

        restVisitServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVisitService.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVisitService))
            )
            .andExpect(status().isOk());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
        VisitService testVisitService = visitServiceList.get(visitServiceList.size() - 1);
        assertThat(testVisitService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVisitService.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVisitService.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVisitService.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    void patchNonExistingVisitService() throws Exception {
        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();
        visitService.setId(count.incrementAndGet());

        // Create the VisitService
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, visitServiceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVisitService() throws Exception {
        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();
        visitService.setId(count.incrementAndGet());

        // Create the VisitService
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitServiceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVisitService() throws Exception {
        int databaseSizeBeforeUpdate = visitServiceRepository.findAll().size();
        visitService.setId(count.incrementAndGet());

        // Create the VisitService
        VisitServiceDTO visitServiceDTO = visitServiceMapper.toDto(visitService);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVisitServiceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(visitServiceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the VisitService in the database
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVisitService() throws Exception {
        // Initialize the database
        visitServiceRepository.saveAndFlush(visitService);

        int databaseSizeBeforeDelete = visitServiceRepository.findAll().size();

        // Delete the visitService
        restVisitServiceMockMvc
            .perform(delete(ENTITY_API_URL_ID, visitService.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VisitService> visitServiceList = visitServiceRepository.findAll();
        assertThat(visitServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
