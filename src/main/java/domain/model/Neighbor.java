package domain.model;

/**
 * Created by rapha_000 on 24/08/2014.
 */
public class Neighbor implements Comparable<Neighbor> {

    User user;
    double correlation;

    public Neighbor(User user, double correlation) {
        this.user = user;
        this.correlation = correlation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getCorrelation() {
        return correlation;
    }

    public void setCorrelation(double correlation) {
        this.correlation = correlation;
    }


    @Override
    public int compareTo(Neighbor o) {
        return new Double(correlation).compareTo(o.correlation);
    }
}
