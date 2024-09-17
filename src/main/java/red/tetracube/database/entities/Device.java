package red.tetracube.database.entities;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import red.tetracube.models.enumerations.DeviceType;

@Entity
@Table(name = "devices")
public class Device extends PanacheEntity {

    @Column(name = "internal_name", unique = true, nullable = false)
    public String internalName;

    @Column(name = "slug", unique = true, nullable = false)
    public String slug;

    @Column(name = "human_name", nullable = false)
    public String humanName;

    @JoinColumn(name = "room_id", nullable = true)
    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY, optional = true)
    public Room room;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_type", nullable = false)
    public DeviceType deviceType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "device", orphanRemoval = true, targetEntity = DeviceInteraction.class)
    public List<DeviceInteraction> deviceInteractions;

    public static Device getByInternalName(String internalName) {
        return Device.<Device>find("internal_name", internalName)
                .firstResult();
    }

}
