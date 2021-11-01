package tr.com.cevher.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tr.com.cevher.java.domain.SystemSetup} entity.
 */
public class SystemSetupDTO implements Serializable {

    private Long id;

    @NotNull
    private String paramKey;

    private String paramVal;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamVal() {
        return paramVal;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemSetupDTO)) {
            return false;
        }

        SystemSetupDTO systemSetupDTO = (SystemSetupDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, systemSetupDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemSetupDTO{" +
            "id=" + getId() +
            ", paramKey='" + getParamKey() + "'" +
            ", paramVal='" + getParamVal() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
