package tr.com.cevher.java.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SystemSetup.
 */
@Entity
@Table(name = "system_setup")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemSetup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "param_key", nullable = false, unique = true)
    private String paramKey;

    @Column(name = "param_val")
    private String paramVal;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SystemSetup id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamKey() {
        return this.paramKey;
    }

    public SystemSetup paramKey(String paramKey) {
        this.setParamKey(paramKey);
        return this;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamVal() {
        return this.paramVal;
    }

    public SystemSetup paramVal(String paramVal) {
        this.setParamVal(paramVal);
        return this;
    }

    public void setParamVal(String paramVal) {
        this.paramVal = paramVal;
    }

    public String getDescription() {
        return this.description;
    }

    public SystemSetup description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SystemSetup)) {
            return false;
        }
        return id != null && id.equals(((SystemSetup) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SystemSetup{" +
            "id=" + getId() +
            ", paramKey='" + getParamKey() + "'" +
            ", paramVal='" + getParamVal() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
