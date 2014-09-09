package domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rapha_000 on 19/08/2014.
 */

@Document(collection = "occupation")
public class Occupation implements Serializable {

    @Id
    String id;
    String onet_soc;
    String title;
    String description;
    List<String> similarOccupations;

    public Occupation() {
    }

    public Occupation(String id, String onet_soc, String title, String description) {
        this.id = id;
        this.onet_soc = onet_soc;
        this.title = title;
        this.description = description;
    }

    public Occupation(String onet_soc, String title, String description) {
        this.onet_soc = onet_soc;
        this.title = title;
        this.description = description;
    }

    public static Occupation newOccupation(Occupation occupation) {

        return new Occupation(occupation.getOnet_soc(), occupation.getTitle(), occupation.getDescription());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOnet_soc() {
        return onet_soc;
    }

    public void setOnet_soc(String onet_soc) {
        this.onet_soc = onet_soc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSimilarOccupations() {
        return similarOccupations;
    }

    public void setSimilarOccupations(List<String> similarOccupations) {
        this.similarOccupations = similarOccupations;
    }
}
