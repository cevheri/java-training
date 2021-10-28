package tr.com.cevher.java.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class VisitServiceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitServiceDTO.class);
        VisitServiceDTO visitServiceDTO1 = new VisitServiceDTO();
        visitServiceDTO1.setId(1L);
        VisitServiceDTO visitServiceDTO2 = new VisitServiceDTO();
        assertThat(visitServiceDTO1).isNotEqualTo(visitServiceDTO2);
        visitServiceDTO2.setId(visitServiceDTO1.getId());
        assertThat(visitServiceDTO1).isEqualTo(visitServiceDTO2);
        visitServiceDTO2.setId(2L);
        assertThat(visitServiceDTO1).isNotEqualTo(visitServiceDTO2);
        visitServiceDTO1.setId(null);
        assertThat(visitServiceDTO1).isNotEqualTo(visitServiceDTO2);
    }
}
