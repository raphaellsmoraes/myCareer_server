package domain.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by rapha_000 on 15/08/2014.
 */
@Document(collection = "trending")
public class Trending implements Serializable, Comparable<Trending> {

    private Occupation occupation;
    private double rating_medio;
    private double rating_total;
    private int n_votes;
    private double bayesian;


    public Trending(Occupation occupation, double rating_total, int n_votes) {
        this.rating_total = rating_total;
        this.n_votes = n_votes;
        this.occupation = occupation;
    }

    public double getRating_total() {
        return rating_total;
    }

    public void setRating_total(double rating_total) {
        this.rating_total = rating_total;
    }

    public Trending() {
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public double getRating_medio() {
        return rating_medio;
    }

    public void setRating_medio(double rating_medio) {
        this.rating_medio = rating_medio;
    }

    public int getN_votes() {
        return n_votes;
    }

    public void setN_votes(int n_votes) {
        this.n_votes = n_votes;
    }

    public double getBayesian() {
        return bayesian;
    }

    public void setBayesian(double bayesian) {
        this.bayesian = bayesian;
    }

    @Override
    public int compareTo(Trending o) {
        return new Double(bayesian).compareTo(o.bayesian);
    }

    @Override
    public String toString() {
        return "Trending{" +
                "occupation=" + occupation +
                ", rating_medio=" + rating_medio +
                ", n_votes=" + n_votes +
                '}';
    }
}
