package tr.com.cevher.java.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VisitServiceMapperTest {

    private VisitServiceMapper visitServiceMapper;

    @BeforeEach
    public void setUp() {
        visitServiceMapper = new VisitServiceMapperImpl();
    }
}
