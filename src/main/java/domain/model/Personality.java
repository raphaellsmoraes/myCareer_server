package domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by rapha_000 on 15/08/2014.
 */
@Document(collection = "personality")
public class Personality implements Serializable {

    @Id
    private String id;

    private Integer realistic;
    private Integer investigative;
    private Integer artistic;
    private Integer social;
    private Integer enterprising;
    private Integer conventional;

    public Personality(String id, Integer realistic, Integer investigative, Integer artistic, Integer social, Integer enterprising, Integer conventional) {
        this.id = id;
        this.realistic = realistic;
        this.investigative = investigative;
        this.artistic = artistic;
        this.social = social;
        this.enterprising = enterprising;
        this.conventional = conventional;
    }

    public Personality() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRealistic() {
        return realistic;
    }

    public void setRealistic(Integer realistic) {
        this.realistic = realistic;
    }

    public Integer getInvestigative() {
        return investigative;
    }

    public void setInvestigative(Integer investigative) {
        this.investigative = investigative;
    }

    public Integer getArtistic() {
        return artistic;
    }

    public void setArtistic(Integer artistic) {
        this.artistic = artistic;
    }

    public Integer getSocial() {
        return social;
    }

    public void setSocial(Integer social) {
        this.social = social;
    }

    public Integer getEnterprising() {
        return enterprising;
    }

    public void setEnterprising(Integer enterprising) {
        this.enterprising = enterprising;
    }

    public Integer getConventional() {
        return conventional;
    }

    public void setConventional(Integer conventional) {
        this.conventional = conventional;
    }
}
