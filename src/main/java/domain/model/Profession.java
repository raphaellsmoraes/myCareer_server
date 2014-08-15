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
    private String jobId;
    private String onetCode;
    private String name;
    private double rating;

    public Profession(String id, String jobId, String onetCode, String name, double rating) {
        this.id = id;
        this.jobId = jobId;
        this.onetCode = onetCode;
        this.name = name;
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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getOnetCode() {
        return onetCode;
    }

    public void setOnetCode(String onetCode) {
        this.onetCode = onetCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", jobId='" + jobId + '\'' +
                ", onetCode='" + onetCode + '\'' +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                '}';
    }
}
