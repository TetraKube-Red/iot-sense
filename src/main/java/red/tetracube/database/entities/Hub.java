package red.tetracube.database.entities;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection = "hubs")
public class Hub extends PanacheMongoEntity{

    @BsonProperty("slug")
    public String slug;

    @BsonProperty("name")    
    public String name;

    @BsonProperty("rooms")
    public List<Room> rooms = new ArrayList<>();
    
}
