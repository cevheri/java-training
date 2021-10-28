package tr.com.cevher.java.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import tr.com.cevher.java.IntegrationTest;
import tr.com.cevher.java.domain.Doctor;
import tr.com.cevher.java.repository.DoctorRepository;
import tr.com.cevher.java.service.DoctorService;
import tr.com.cevher.java.service.dto.DoctorDTO;
import tr.com.cevher.java.service.mapper.DoctorMapper;

/**
 * Integration tests for the {@link DoctorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DoctorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/doctors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DoctorRepository doctorRepository;

    @Mock
    private DoctorRepository doctorRepositoryMock;

    @Autowired
    private DoctorMapper doctorMapper;

    @Mock
    private DoctorService doctorServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorMockMvc;

    private Doctor doctor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createEntity(EntityManager em) {
        Doctor doctor = new Doctor().name(DEFAULT_NAME).phone(DEFAULT_PHONE);
        return doctor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Doctor createUpdatedEntity(EntityManager em) {
        Doctor doctor = new Doctor().name(UPDATED_NAME).phone(UPDATED_PHONE);
        return doctor;
    }

    @BeforeEach
    public void initTest() {
        doctor = createEntity(em);
    }

    @Test
    @Transactional
    void createDoctor() throws Exception {
        int databaseSizeBeforeCreate = doctorRepository.findAll().size();
        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);
        restDoctorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isCreated());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate + 1);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctor.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createDoctorWithExistingId() throws Exception {
        // Create the Doctor with an existing ID
        doctor.setId(1L);
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        int databaseSizeBeforeCreate = doctorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorRepository.findAll().size();
        // set the field null
        doctor.setName(null);

        // Create the Doctor, which fails.
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        restDoctorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isBadRequest());

        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDoctors() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get all the doctorList
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDoctorsWithEagerRelationshipsIsEnabled() throws Exception {
        when(doctorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDoctorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(doctorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDoctorsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(doctorServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDoctorMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(doctorServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        // Get the doctor
        restDoctorMockMvc
            .perform(get(ENTITY_API_URL_ID, doctor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingDoctor() throws Exception {
        // Get the doctor
        restDoctorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor
        Doctor updatedDoctor = doctorRepository.findById(doctor.getId()).get();
        // Disconnect from session so that the updates on updatedDoctor are not directly saved in db
        em.detach(updatedDoctor);
        updatedDoctor.name(UPDATED_NAME).phone(UPDATED_PHONE);
        DoctorDTO doctorDTO = doctorMapper.toDto(updatedDoctor);

        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctor.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, doctorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(doctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(doctorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDoctorWithPatch() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor using partial update
        Doctor partialUpdatedDoctor = new Doctor();
        partialUpdatedDoctor.setId(doctor.getId());

        partialUpdatedDoctor.name(UPDATED_NAME).phone(UPDATED_PHONE);

        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctor.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateDoctorWithPatch() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();

        // Update the doctor using partial update
        Doctor partialUpdatedDoctor = new Doctor();
        partialUpdatedDoctor.setId(doctor.getId());

        partialUpdatedDoctor.name(UPDATED_NAME).phone(UPDATED_PHONE);

        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDoctor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDoctor))
            )
            .andExpect(status().isOk());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
        Doctor testDoctor = doctorList.get(doctorList.size() - 1);
        assertThat(testDoctor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctor.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, doctorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(doctorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDoctor() throws Exception {
        int databaseSizeBeforeUpdate = doctorRepository.findAll().size();
        doctor.setId(count.incrementAndGet());

        // Create the Doctor
        DoctorDTO doctorDTO = doctorMapper.toDto(doctor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDoctorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(doctorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Doctor in the database
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDoctor() throws Exception {
        // Initialize the database
        doctorRepository.saveAndFlush(doctor);

        int databaseSizeBeforeDelete = doctorRepository.findAll().size();

        // Delete the doctor
        restDoctorMockMvc
            .perform(delete(ENTITY_API_URL_ID, doctor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Doctor> doctorList = doctorRepository.findAll();
        assertThat(doctorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
