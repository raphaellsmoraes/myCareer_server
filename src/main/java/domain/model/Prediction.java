package domain.model;

/**
 * Created by rapha_000 on 01/10/2014.
 */
public class Prediction implements Comparable<Prediction> {

    /* Occupation ID */
    String id;

    Occupation occupation;

    /* Prediction value */
    Double prediction;

    public Prediction(String id, Double prediction) {
        this.id = id;
        this.prediction = prediction;
    }

    public Prediction(String id, Occupation occupation, Double prediction) {
        this.id = id;
        this.occupation = occupation;
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

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public void setPrediction(Double prediction) {
        this.prediction = prediction;
    }

    @Override
    public int compareTo(Prediction o) {
        return new Double(prediction).compareTo(o.prediction);
    }
}
