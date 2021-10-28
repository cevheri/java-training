package tr.com.cevher.java.service.mapper;

import java.util.Set;
import org.mapstruct.*;
import tr.com.cevher.java.domain.Department;
import tr.com.cevher.java.service.dto.DepartmentDTO;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Named("nameSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    Set<DepartmentDTO> toDtoNameSet(Set<Department> department);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DepartmentDTO toDtoName(Department department);
}
