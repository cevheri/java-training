package tr.com.cevher.java.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class DoctorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doctor.class);
        Doctor doctor1 = new Doctor();
        doctor1.setId(1L);
        Doctor doctor2 = new Doctor();
        doctor2.setId(doctor1.getId());
        assertThat(doctor1).isEqualTo(doctor2);
        doctor2.setId(2L);
        assertThat(doctor1).isNotEqualTo(doctor2);
        doctor1.setId(null);
        assertThat(doctor1).isNotEqualTo(doctor2);
    }
}
