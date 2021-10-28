package tr.com.cevher.java.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import tr.com.cevher.java.domain.VisitService;
import tr.com.cevher.java.service.dto.VisitServiceDTO;

/**
 * Mapper for the entity {@link VisitService} and its DTO {@link VisitServiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface VisitServiceMapper extends EntityMapper<VisitServiceDTO, VisitService> {
    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<VisitServiceDTO> toDtoNameSet(Set<VisitService> visitService);
}
