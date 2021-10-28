package tr.com.cevher.java.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import tr.com.cevher.java.domain.enumeration.VisitType;

/**
 * A DTO for the {@link tr.com.cevher.java.domain.Visit} entity.
 */
public class VisitDTO implements Serializable {

    private Long id;

    private Instant date;

    private VisitType type;

    private PatientDTO patient;

    private DoctorDTO doctor;

    private DepartmentDTO department;

    private Set<VisitServiceDTO> visitServices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public VisitType getType() {
        return type;
    }

    public void setType(VisitType type) {
        this.type = type;
    }

    public PatientDTO getPatient() {
        return patient;
    }

    public void setPatient(PatientDTO patient) {
        this.patient = patient;
    }

    public DoctorDTO getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDTO doctor) {
        this.doctor = doctor;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public Set<VisitServiceDTO> getVisitServices() {
        return visitServices;
    }

    public void setVisitServices(Set<VisitServiceDTO> visitServices) {
        this.visitServices = visitServices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisitDTO)) {
            return false;
        }

        VisitDTO visitDTO = (VisitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, visitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisitDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            ", patient=" + getPatient() +
            ", doctor=" + getDoctor() +
            ", department=" + getDepartment() +
            ", visitServices=" + getVisitServices() +
            "}";
    }
}
