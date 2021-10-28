package tr.com.cevher.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import tr.com.cevher.java.domain.enumeration.DepartmentType;

/**
 * A DTO for the {@link tr.com.cevher.java.domain.Department} entity.
 */
public class DepartmentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    private DepartmentType type;

    @Size(max = 1000)
    private String description;

    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentType getType() {
        return type;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentDTO)) {
            return false;
        }

        DepartmentDTO departmentDTO = (DepartmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, departmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
