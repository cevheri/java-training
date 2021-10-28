package tr.com.cevher.java.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class VisitServiceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitService.class);
        VisitService visitService1 = new VisitService();
        visitService1.setId(1L);
        VisitService visitService2 = new VisitService();
        visitService2.setId(visitService1.getId());
        assertThat(visitService1).isEqualTo(visitService2);
        visitService2.setId(2L);
        assertThat(visitService1).isNotEqualTo(visitService2);
        visitService1.setId(null);
        assertThat(visitService1).isNotEqualTo(visitService2);
    }
}
