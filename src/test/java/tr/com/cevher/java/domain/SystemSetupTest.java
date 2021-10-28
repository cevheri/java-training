package tr.com.cevher.java.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class SystemSetupTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SystemSetup.class);
        SystemSetup systemSetup1 = new SystemSetup();
        systemSetup1.setId(1L);
        SystemSetup systemSetup2 = new SystemSetup();
        systemSetup2.setId(systemSetup1.getId());
        assertThat(systemSetup1).isEqualTo(systemSetup2);
        systemSetup2.setId(2L);
        assertThat(systemSetup1).isNotEqualTo(systemSetup2);
        systemSetup1.setId(null);
        assertThat(systemSetup1).isNotEqualTo(systemSetup2);
    }
}
