package tr.com.cevher.java.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductsMapperTest {

    private ProductsMapper productsMapper;

    @BeforeEach
    public void setUp() {
        productsMapper = new ProductsMapperImpl();
    }
}
