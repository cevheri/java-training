package tr.com.cevher.java.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import tr.com.cevher.java.domain.Visit;
import tr.com.cevher.java.service.dto.VisitDTO;

/**
 * Mapper for the entity {@link Visit} and its DTO {@link VisitDTO}.
 */
@Mapper(componentModel = "spring", uses = { PatientMapper.class, DoctorMapper.class, DepartmentMapper.class, VisitServiceMapper.class })
public interface VisitMapper extends EntityMapper<VisitDTO, Visit> {
    @Mapping(target = "patient", source = "patient", qualifiedByName = "name")
    @Mapping(target = "doctor", source = "doctor", qualifiedByName = "name")
    @Mapping(target = "department", source = "department", qualifiedByName = "name")
    @Mapping(target = "visitServices", source = "visitServices", qualifiedByName = "nameSet")
    VisitDTO toDto(Visit s);

    @Mapping(target = "removeVisitService", ignore = true)
    Visit toEntity(VisitDTO visitDTO);
}
