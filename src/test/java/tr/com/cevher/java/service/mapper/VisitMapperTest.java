package tr.com.cevher.java.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VisitMapperTest {

    private VisitMapper visitMapper;

    @BeforeEach
    public void setUp() {
        visitMapper = new VisitMapperImpl();
    }
}
