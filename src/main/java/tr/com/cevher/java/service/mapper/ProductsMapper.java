package tr.com.cevher.java.service.mapper;

import org.mapstruct.*;
import tr.com.cevher.java.domain.Products;
import tr.com.cevher.java.service.dto.ProductsDTO;

/**
 * Mapper for the entity {@link Products} and its DTO {@link ProductsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProductsMapper extends EntityMapper<ProductsDTO, Products> {}
