package red.tetracube.database.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room extends PanacheEntity {

    @Column(name = "slug", nullable = false, unique = true)
    public String slug;

    @Column(name = "name", nullable = false)
    public String name;

    @JoinColumn(name = "hub_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = true, targetEntity = Hub.class)
    public Hub hub;

}
