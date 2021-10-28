package tr.com.cevher.java.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import tr.com.cevher.java.domain.Doctor;
import tr.com.cevher.java.service.dto.DoctorDTO;

/**
 * Mapper for the entity {@link Doctor} and its DTO {@link DoctorDTO}.
 */
@Mapper(componentModel = "spring", uses = { DepartmentMapper.class })
public interface DoctorMapper extends EntityMapper<DoctorDTO, Doctor> {
    @Mapping(target = "departments", source = "departments", qualifiedByName = "nameSet")
    DoctorDTO toDto(Doctor s);

    @Mapping(target = "removeDepartment", ignore = true)
    Doctor toEntity(DoctorDTO doctorDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DoctorDTO toDtoName(Doctor doctor);
}
