package domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by rapha_000 on 15/08/2014.
 */
@Document(collection = "profession")
public class Profession implements Serializable {

    @Id
    private String id;
    private Occupation occupation;
    private double rating;

    public Profession(Occupation occupation, double rating) {
        //this.id = UUID.randomUUID().toString();
        this.occupation = occupation;
        this.rating = rating;
    }

    public Profession() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Profession{" +
                "id='" + id + '\'' +
                ", occupation=" + occupation +
                ", rating=" + rating +
                '}';
    }
}
