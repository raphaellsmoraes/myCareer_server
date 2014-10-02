package domain.model;

/**
 * Created by rapha_000 on 01/10/2014.
 */
public class Prediction {

    /* Occupation ID */
    String id;

    /* Prediction value */
    Double prediction;

    public Prediction(String id, Double prediction) {
        this.id = id;
        this.prediction = prediction;
    }

    public Prediction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getPrediction() {
        return prediction;
    }

    public void setPrediction(double prediction) {
        this.prediction = prediction;
    }
}
