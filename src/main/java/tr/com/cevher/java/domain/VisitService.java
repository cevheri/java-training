package tr.com.cevher.java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A VisitService.
 */
@Entity
@Table(name = "visit_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class VisitService implements Serializable {

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

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "price")
    private Double price;

    @ManyToMany(mappedBy = "visitServices")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patient", "doctor", "department", "visitServices" }, allowSetters = true)
    private Set<Visit> visits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VisitService id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public VisitService name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public VisitService description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return this.active;
    }

    public VisitService active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Double getPrice() {
        return this.price;
    }

    public VisitService price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Visit> getVisits() {
        return this.visits;
    }

    public void setVisits(Set<Visit> visits) {
        if (this.visits != null) {
            this.visits.forEach(i -> i.removeVisitService(this));
        }
        if (visits != null) {
            visits.forEach(i -> i.addVisitService(this));
        }
        this.visits = visits;
    }

    public VisitService visits(Set<Visit> visits) {
        this.setVisits(visits);
        return this;
    }

    public VisitService addVisit(Visit visit) {
        this.visits.add(visit);
        visit.getVisitServices().add(this);
        return this;
    }

    public VisitService removeVisit(Visit visit) {
        this.visits.remove(visit);
        visit.getVisitServices().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VisitService)) {
            return false;
        }
        return id != null && id.equals(((VisitService) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VisitService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + getActive() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
