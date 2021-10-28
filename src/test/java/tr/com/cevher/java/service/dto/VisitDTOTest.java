package tr.com.cevher.java.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import tr.com.cevher.java.web.rest.TestUtil;

class VisitDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VisitDTO.class);
        VisitDTO visitDTO1 = new VisitDTO();
        visitDTO1.setId(1L);
        VisitDTO visitDTO2 = new VisitDTO();
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
        visitDTO2.setId(visitDTO1.getId());
        assertThat(visitDTO1).isEqualTo(visitDTO2);
        visitDTO2.setId(2L);
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
        visitDTO1.setId(null);
        assertThat(visitDTO1).isNotEqualTo(visitDTO2);
    }
}
