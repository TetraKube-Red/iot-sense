package red.tetracube.database.entities;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Room extends PanacheMongoEntity {

    @BsonProperty("slug")
    public String slug;

    @BsonProperty("name")
    public String name;

}
