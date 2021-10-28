package tr.com.cevher.java.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class SystemSetupDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemSetupDTO.class);
        SystemSetupDTO systemSetupDTO1 = new SystemSetupDTO();
        systemSetupDTO1.setId(1L);
        SystemSetupDTO systemSetupDTO2 = new SystemSetupDTO();
        assertThat(systemSetupDTO1).isNotEqualTo(systemSetupDTO2);
        systemSetupDTO2.setId(systemSetupDTO1.getId());
        assertThat(systemSetupDTO1).isEqualTo(systemSetupDTO2);
        systemSetupDTO2.setId(2L);
        assertThat(systemSetupDTO1).isNotEqualTo(systemSetupDTO2);
        systemSetupDTO1.setId(null);
        assertThat(systemSetupDTO1).isNotEqualTo(systemSetupDTO2);
    }
}
