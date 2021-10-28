package tr.com.cevher.java.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class PatientDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientDTO.class);
        PatientDTO patientDTO1 = new PatientDTO();
        patientDTO1.setId(1L);
        PatientDTO patientDTO2 = new PatientDTO();
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO2.setId(patientDTO1.getId());
        assertThat(patientDTO1).isEqualTo(patientDTO2);
        patientDTO2.setId(2L);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
        patientDTO1.setId(null);
        assertThat(patientDTO1).isNotEqualTo(patientDTO2);
    }
}
