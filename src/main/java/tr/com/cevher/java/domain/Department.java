package tr.com.cevher.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tr.com.cevher.java.domain.enumeration.DepartmentType;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "name", length = 200, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DepartmentType type;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "active")
    private Boolean active;

    @ManyToMany(mappedBy = "departments")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "departments" }, allowSetters = true)
    private Set<Doctor> doctors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Department id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Department name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DepartmentType getType() {
        return this.type;
    }

    public Department type(DepartmentType type) {
        this.setType(type);
        return this;
    }

    public void setType(DepartmentType type) {
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public Department description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Department active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<Doctor> getDoctors() {
        return this.doctors;
    }

    public void setDoctors(Set<Doctor> doctors) {
        if (this.doctors != null) {
            this.doctors.forEach(i -> i.removeDepartment(this));
        }
        if (doctors != null) {
            doctors.forEach(i -> i.addDepartment(this));
        }
        this.doctors = doctors;
    }

    public Department doctors(Set<Doctor> doctors) {
        this.setDoctors(doctors);
        return this;
    }

    public Department addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.getDepartments().add(this);
        return this;
    }

    public Department removeDoctor(Doctor doctor) {
        this.doctors.remove(doctor);
        doctor.getDepartments().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
