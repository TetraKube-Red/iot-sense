package red.tetracube.database.entities;

import java.util.ArrayList;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "hubs")
public class Hub extends PanacheEntity{

    @Column(name = "slug", nullable = false, unique = true)
    public String slug;

    @Column(name = "name", nullable = false)    
    public String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hub", targetEntity = Room.class)
    public List<Room> rooms = new ArrayList<>();
    
}
