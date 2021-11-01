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
import tr.com.cevher.java.domain.SystemSetup;
import tr.com.cevher.java.repository.SystemSetupRepository;
import tr.com.cevher.java.service.dto.SystemSetupDTO;
import tr.com.cevher.java.service.mapper.SystemSetupMapper;

/**
 * Integration tests for the {@link SystemSetupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SystemSetupResourceIT {

    private static final String DEFAULT_PARAM_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PARAM_VAL = "AAAAAAAAAA";
    private static final String UPDATED_PARAM_VAL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/system-setups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SystemSetupRepository systemSetupRepository;

    @Autowired
    private SystemSetupMapper systemSetupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSystemSetupMockMvc;

    private SystemSetup systemSetup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetup createEntity(EntityManager em) {
        SystemSetup systemSetup = new SystemSetup()
            .paramKey(DEFAULT_PARAM_KEY)
            .paramVal(DEFAULT_PARAM_VAL)
            .description(DEFAULT_DESCRIPTION);
        return systemSetup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SystemSetup createUpdatedEntity(EntityManager em) {
        SystemSetup systemSetup = new SystemSetup()
            .paramKey(UPDATED_PARAM_KEY)
            .paramVal(UPDATED_PARAM_VAL)
            .description(UPDATED_DESCRIPTION);
        return systemSetup;
    }

    @BeforeEach
    public void initTest() {
        systemSetup = createEntity(em);
    }

    @Test
    @Transactional
    void createSystemSetup() throws Exception {
        int databaseSizeBeforeCreate = systemSetupRepository.findAll().size();
        // Create the SystemSetup
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);
        restSystemSetupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeCreate + 1);
        SystemSetup testSystemSetup = systemSetupList.get(systemSetupList.size() - 1);
        assertThat(testSystemSetup.getParamKey()).isEqualTo(DEFAULT_PARAM_KEY);
        assertThat(testSystemSetup.getParamVal()).isEqualTo(DEFAULT_PARAM_VAL);
        assertThat(testSystemSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createSystemSetupWithExistingId() throws Exception {
        // Create the SystemSetup with an existing ID
        systemSetup.setId(1L);
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        int databaseSizeBeforeCreate = systemSetupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSystemSetupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkParamKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = systemSetupRepository.findAll().size();
        // set the field null
        systemSetup.setParamKey(null);

        // Create the SystemSetup, which fails.
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        restSystemSetupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isBadRequest());

        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSystemSetups() throws Exception {
        // Initialize the database
        systemSetupRepository.saveAndFlush(systemSetup);

        // Get all the systemSetupList
        restSystemSetupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(systemSetup.getId().intValue())))
            .andExpect(jsonPath("$.[*].paramKey").value(hasItem(DEFAULT_PARAM_KEY)))
            .andExpect(jsonPath("$.[*].paramVal").value(hasItem(DEFAULT_PARAM_VAL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getSystemSetup() throws Exception {
        // Initialize the database
        systemSetupRepository.saveAndFlush(systemSetup);

        // Get the systemSetup
        restSystemSetupMockMvc
            .perform(get(ENTITY_API_URL_ID, systemSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(systemSetup.getId().intValue()))
            .andExpect(jsonPath("$.paramKey").value(DEFAULT_PARAM_KEY))
            .andExpect(jsonPath("$.paramVal").value(DEFAULT_PARAM_VAL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingSystemSetup() throws Exception {
        // Get the systemSetup
        restSystemSetupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSystemSetup() throws Exception {
        // Initialize the database
        systemSetupRepository.saveAndFlush(systemSetup);

        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();

        // Update the systemSetup
        SystemSetup updatedSystemSetup = systemSetupRepository.findById(systemSetup.getId()).get();
        // Disconnect from session so that the updates on updatedSystemSetup are not directly saved in db
        em.detach(updatedSystemSetup);
        updatedSystemSetup.paramKey(UPDATED_PARAM_KEY).paramVal(UPDATED_PARAM_VAL).description(UPDATED_DESCRIPTION);
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(updatedSystemSetup);

        restSystemSetupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, systemSetupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isOk());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
        SystemSetup testSystemSetup = systemSetupList.get(systemSetupList.size() - 1);
        assertThat(testSystemSetup.getParamKey()).isEqualTo(UPDATED_PARAM_KEY);
        assertThat(testSystemSetup.getParamVal()).isEqualTo(UPDATED_PARAM_VAL);
        assertThat(testSystemSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingSystemSetup() throws Exception {
        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();
        systemSetup.setId(count.incrementAndGet());

        // Create the SystemSetup
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemSetupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, systemSetupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSystemSetup() throws Exception {
        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();
        systemSetup.setId(count.incrementAndGet());

        // Create the SystemSetup
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSetupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSystemSetup() throws Exception {
        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();
        systemSetup.setId(count.incrementAndGet());

        // Create the SystemSetup
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSetupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(systemSetupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSystemSetupWithPatch() throws Exception {
        // Initialize the database
        systemSetupRepository.saveAndFlush(systemSetup);

        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();

        // Update the systemSetup using partial update
        SystemSetup partialUpdatedSystemSetup = new SystemSetup();
        partialUpdatedSystemSetup.setId(systemSetup.getId());

        partialUpdatedSystemSetup.paramKey(UPDATED_PARAM_KEY).paramVal(UPDATED_PARAM_VAL);

        restSystemSetupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemSetup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSystemSetup))
            )
            .andExpect(status().isOk());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
        SystemSetup testSystemSetup = systemSetupList.get(systemSetupList.size() - 1);
        assertThat(testSystemSetup.getParamKey()).isEqualTo(UPDATED_PARAM_KEY);
        assertThat(testSystemSetup.getParamVal()).isEqualTo(UPDATED_PARAM_VAL);
        assertThat(testSystemSetup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateSystemSetupWithPatch() throws Exception {
        // Initialize the database
        systemSetupRepository.saveAndFlush(systemSetup);

        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();

        // Update the systemSetup using partial update
        SystemSetup partialUpdatedSystemSetup = new SystemSetup();
        partialUpdatedSystemSetup.setId(systemSetup.getId());

        partialUpdatedSystemSetup.paramKey(UPDATED_PARAM_KEY).paramVal(UPDATED_PARAM_VAL).description(UPDATED_DESCRIPTION);

        restSystemSetupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSystemSetup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSystemSetup))
            )
            .andExpect(status().isOk());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
        SystemSetup testSystemSetup = systemSetupList.get(systemSetupList.size() - 1);
        assertThat(testSystemSetup.getParamKey()).isEqualTo(UPDATED_PARAM_KEY);
        assertThat(testSystemSetup.getParamVal()).isEqualTo(UPDATED_PARAM_VAL);
        assertThat(testSystemSetup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingSystemSetup() throws Exception {
        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();
        systemSetup.setId(count.incrementAndGet());

        // Create the SystemSetup
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSystemSetupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, systemSetupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSystemSetup() throws Exception {
        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();
        systemSetup.setId(count.incrementAndGet());

        // Create the SystemSetup
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSetupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSystemSetup() throws Exception {
        int databaseSizeBeforeUpdate = systemSetupRepository.findAll().size();
        systemSetup.setId(count.incrementAndGet());

        // Create the SystemSetup
        SystemSetupDTO systemSetupDTO = systemSetupMapper.toDto(systemSetup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSystemSetupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(systemSetupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SystemSetup in the database
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSystemSetup() throws Exception {
        // Initialize the database
        systemSetupRepository.saveAndFlush(systemSetup);

        int databaseSizeBeforeDelete = systemSetupRepository.findAll().size();

        // Delete the systemSetup
        restSystemSetupMockMvc
            .perform(delete(ENTITY_API_URL_ID, systemSetup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SystemSetup> systemSetupList = systemSetupRepository.findAll();
        assertThat(systemSetupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
