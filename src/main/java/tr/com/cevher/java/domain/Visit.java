package tr.com.cevher.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import tr.com.cevher.java.domain.enumeration.VisitType;

/**
 * A Visit.
 */
@Entity
@Table(name = "visit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private VisitType type;

    @ManyToOne
    private Patient patient;

    @ManyToOne
    @JsonIgnoreProperties(value = { "departments" }, allowSetters = true)
    private Doctor doctor;

    @ManyToOne
    @JsonIgnoreProperties(value = { "doctors" }, allowSetters = true)
    private Department department;

    @ManyToMany
    @JoinTable(
        name = "rel_visit__visit_service",
        joinColumns = @JoinColumn(name = "visit_id"),
        inverseJoinColumns = @JoinColumn(name = "visit_service_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "visits" }, allowSetters = true)
    private Set<VisitService> visitServices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Visit id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return this.date;
    }

    public Visit date(Instant date) {
        this.setDate(date);
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public VisitType getType() {
        return this.type;
    }

    public Visit type(VisitType type) {
        this.setType(type);
        return this;
    }

    public void setType(VisitType type) {
        this.type = type;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Visit patient(Patient patient) {
        this.setPatient(patient);
        return this;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Visit doctor(Doctor doctor) {
        this.setDoctor(doctor);
        return this;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Visit department(Department department) {
        this.setDepartment(department);
        return this;
    }

    public Set<VisitService> getVisitServices() {
        return this.visitServices;
    }

    public void setVisitServices(Set<VisitService> visitServices) {
        this.visitServices = visitServices;
    }

    public Visit visitServices(Set<VisitService> visitServices) {
        this.setVisitServices(visitServices);
        return this;
    }

    public Visit addVisitService(VisitService visitService) {
        this.visitServices.add(visitService);
        visitService.getVisits().add(this);
        return this;
    }

    public Visit removeVisitService(VisitService visitService) {
        this.visitServices.remove(visitService);
        visitService.getVisits().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Visit)) {
            return false;
        }
        return id != null && id.equals(((Visit) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Visit{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
