package domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by rapha_000 on 15/08/2014.
 */
@Document(collection = "music")
public class FavoriteMusics implements Serializable {

    @Id
    private String id;

    private String name;

    public FavoriteMusics(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public FavoriteMusics() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
