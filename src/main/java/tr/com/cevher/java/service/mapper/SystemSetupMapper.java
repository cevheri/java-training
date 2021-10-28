package tr.com.cevher.java.service.mapper;

import org.mapstruct.*;
import tr.com.cevher.java.domain.SystemSetup;
import tr.com.cevher.java.service.dto.SystemSetupDTO;

/**
 * Mapper for the entity {@link SystemSetup} and its DTO {@link SystemSetupDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SystemSetupMapper extends EntityMapper<SystemSetupDTO, SystemSetup> {}
