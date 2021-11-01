package tr.com.cevher.java.service.mapper;

import org.mapstruct.*;
import tr.com.cevher.java.domain.Company;
import tr.com.cevher.java.service.dto.CompanyDTO;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {}
