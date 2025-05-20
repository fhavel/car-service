package cz.frantisekhavel.carservice.car;

import java.time.Instant;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.google.common.base.MoreObjects;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String name;
    @CreationTimestamp
    private Instant created;

    protected Car() {}

    public Car(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(Car.class).add("id", id).add("name", name).add("created", created).toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof final Car car)) {
            return false;
        }
        return Objects.equals(id, car.id) && Objects.equals(name, car.name) && Objects.equals(created, car.created);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
