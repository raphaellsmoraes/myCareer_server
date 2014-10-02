package domain.utils;

import domain.model.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by rapha_000 on 01/10/2014.
 */
public class PredictionUtils {

    private static final Logger LOGGER = Logger.getLogger(PredictionUtils.class);

    public static ArrayList<Prediction> getPredictions(User baseUser, ArrayList<Neighbor> neighborhood) {

        Double baseAverage = getAverageRating(baseUser);
        ArrayList<Prediction> arrayPredictions = new ArrayList<>();

        /* Non-rated occupations */
        ArrayList<Occupation> baseNonRated = getNonRatedOccupations(baseUser);

        for (Occupation o : baseNonRated) {

            Double ratingSum = 0.0;
            Double correlationSum = 0.0;

            for (Neighbor n : neighborhood) {

                /* SUM (Rit(Rating neighbour-item) - Ai(Average user rating)) * sim(u,i)(Pearson correlation btw base and neighbour) */
                /* SUM sim(u,i)(Pearson correlation btw base and neighbour) */

                for (int i = 0; i <= (n.getUser().getProfessions().size() - 1); i++) {
                    /* Search for profession and check if it was rated */
                    if (n.getUser().getProfessions().get(i).getOccupation().getId().equals(o.getId())) {
                        if (n.getUser().getProfessions().get(i).getRating() != -1) {

                            /* rating Sum */
                            ratingSum = ratingSum +
                                    (
                                            n.getUser().getProfessions().get(i).getRating() - getAverageRating(n.getUser())
                                                    *
                                                    n.getCorrelation()
                                    );

                            /* Correlation Sum */
                            correlationSum = correlationSum + n.getCorrelation();
                        }
                    }
                }
            }

            Double prediction = 0.0;
            prediction = baseAverage = (ratingSum / correlationSum);
            arrayPredictions.add(new Prediction(o.getId(), prediction));
        }

        return arrayPredictions;
    }

    /*
        @getAverageRating(User user)
        returns the average rating of all occupations excluding non-rated ones
     */
    private static Double getAverageRating(User user) {
        int i = 0;
        Double average = 0.0;

        for (Profession n : user.getProfessions()) {
            if (n.getRating() != -1) {
                average = average + n.getRating();
                i++;
            }
        }

        return average / i;
    }

    private static ArrayList<Occupation> getNonRatedOccupations(User user) {
        ArrayList<Occupation> occupations = new ArrayList<>();

        for (Profession n : user.getProfessions()) {
            if (n.getRating() == -1) {
                occupations.add(n.getOccupation());
            }
        }

        return occupations;
    }
}
