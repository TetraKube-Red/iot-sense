package red.tetracube.services;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import red.tetracube.database.entities.Hub;

@ApplicationScoped
public class HubServices {
    
    public Optional<Hub> getHubBySlug(String slug) {
        return Hub.find("slug", slug).firstResultOptional();
    } 

}
