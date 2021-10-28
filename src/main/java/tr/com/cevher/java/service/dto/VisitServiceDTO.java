package tr.com.cevher.java.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link tr.com.cevher.java.domain.VisitService} entity.
 */
public class VisitServiceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    private Boolean active;

    private Double price;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisitServiceDTO)) {
            return false;
        }

        VisitServiceDTO visitServiceDTO = (VisitServiceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, visitServiceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisitServiceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + getActive() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
