package tr.com.cevher.java.service.mapper;

import org.mapstruct.*;
import tr.com.cevher.java.domain.Patient;
import tr.com.cevher.java.service.dto.PatientDTO;

/**
 * Mapper for the entity {@link Patient} and its DTO {@link PatientDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PatientMapper extends EntityMapper<PatientDTO, Patient> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PatientDTO toDtoId(Patient patient);
}
