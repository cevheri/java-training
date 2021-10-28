package tr.com.cevher.java.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class PatientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patient.class);
        Patient patient1 = new Patient();
        patient1.setId(1L);
        Patient patient2 = new Patient();
        patient2.setId(patient1.getId());
        assertThat(patient1).isEqualTo(patient2);
        patient2.setId(2L);
        assertThat(patient1).isNotEqualTo(patient2);
        patient1.setId(null);
        assertThat(patient1).isNotEqualTo(patient2);
    }
}
