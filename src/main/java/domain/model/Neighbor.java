package domain.model;

import java.util.Comparator;

/**
 * Created by rapha_000 on 24/08/2014.
 */
public class Neighbor implements Comparator {

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
    public int compare(Object o1, Object o2) {
        if (((Neighbor) o1).getCorrelation() >= ((Neighbor) o2).getCorrelation())
            return 0;
        else
            return 1;
    }
}
