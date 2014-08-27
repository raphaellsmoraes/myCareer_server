package domain.service;

import com.google.common.base.Stopwatch;
import domain.model.DemographicNeighbor;
import domain.model.Neighbor;
import domain.model.User;
import domain.repository.UserRepository;
import domain.utils.ClusterUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Service
public class ClusterServiceCreator {

    private static final Logger LOGGER = Logger.getLogger(ClusterServiceCreator.class);

    @Autowired
    private UserRepository userRepository;


    public void execute() {

        Stopwatch stopwatch = Stopwatch.createStarted();

        /* Retrieve all users to cluster */
        User baseUser = userRepository.findOne("53f0e1a2be99d3c50b43d026");
        List<User> users = userRepository.findAll();

        ArrayList<Neighbor> normalSimilarity = getCollaborativeNeighborhood(baseUser, users);
        ArrayList<Neighbor> demographicSimillarity = getDemographicNeighborhood(baseUser, users);

        for (int i = 0; i <= (normalSimilarity.size() - 1); i++) {

            LOGGER.debug(String.format("[User: %s] ", normalSimilarity.get(i).getUser().getName())
                    + String.format("[Normal Correlation: %s] [Demographic Correlation : %s] [Similarity : %s]",
                    normalSimilarity.get(i).getCorrelation(), demographicSimillarity.get(i).getCorrelation(),
                    (normalSimilarity.get(i).getCorrelation() +
                            (normalSimilarity.get(i).getCorrelation()
                                    * demographicSimillarity.get(i).getCorrelation()))
            ));

        }


        stopwatch.stop();
    }

    private ArrayList<Neighbor> getCollaborativeNeighborhood(User baseUser, List<User> users) {

        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();

        /* Neighborhood Users*/
        ArrayList<Neighbor> neighborhood = new ArrayList<Neighbor>();

        /* baseUser double array */
        ArrayList<Double> baseArray = new ArrayList<Double>();
        for (int looper = 0; looper <= (baseUser.getProfessions().size() - 1); looper++) {
            baseArray.add(baseUser.getProfessions().get(looper).getRating());
        }

        /* search and remove requested id */
        Iterator<User> iterator;
        for (iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId().equals(baseUser.getId())) {
                iterator.remove();
            }
        }

        /* Creating correlations Matrix */
        for (int i = 0; i <= (users.size() - 1); i++) {

            ArrayList<Double> similarArray = new ArrayList<Double>();
            for (int looper = 0; looper <= (users.get(i).getProfessions().size() - 1); looper++) {
                similarArray.add(users.get(i).getProfessions().get(looper).getRating());
            }

            neighborhood.add(new Neighbor(
                            users.get(i),
                            pearsonsCorrelation.correlation(
                                    ArrayUtils.toPrimitive(similarArray.toArray(new Double[similarArray.size()]))
                                    , ArrayUtils.toPrimitive(baseArray.toArray(new Double[baseArray.size()]))))
            );
        }

        return neighborhood;
    }

    private ArrayList<Neighbor> getDemographicNeighborhood(User baseUser, List<User> users) {
        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();

        /* Neighborhood Users*/
        ArrayList<Neighbor> neighborhood = new ArrayList<Neighbor>();

        /* Demographic Neighbors */
        ArrayList<DemographicNeighbor> udemNeighborhood = new ArrayList<DemographicNeighbor>();

        /* Criando correlação do baseUser - Basicamente uma matrix demografica */
        DemographicNeighbor udemBaseUser = getDemographicUser(baseUser);


        /* search and remove requested id */
        Iterator<User> iterator;
        for (iterator = users.iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getId().equals(baseUser.getId())) {
                iterator.remove();
            }
        }

        for (int i = 0; i <= (users.size() - 1); i++) {

            ArrayList<Double> similarArray = getDemographicUser(users.get(i)).toDoubleArray();

            neighborhood.add(new Neighbor(
                            users.get(i),
                            pearsonsCorrelation.correlation(
                                    ArrayUtils.toPrimitive(similarArray.toArray(new Double[similarArray.size()]))
                                    , ArrayUtils.toPrimitive(udemBaseUser.toDoubleArray().toArray(
                                            new Double[udemBaseUser.toDoubleArray().size()]))))
            );

        }

        return neighborhood;
    }

    /* {18, 1829, 2949, 49, male, female} */
    private DemographicNeighbor getDemographicUser(User user) {

        String birthday = user.getBirthday();
        String gender = user.getGender();

        if (ClusterUtils.getDemographicClusterAge(birthday)
                .equals(ClusterUtils.CLUSTER_EIGHTEEN)) {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0);
            }

        } else if (ClusterUtils.getDemographicClusterAge(birthday)
                .equals(ClusterUtils.CLUSTER_OVER_EIGHTEEN_UNDER_TWENTYNINE)) {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 0.0, 1.0, 0.0, 0.0, 0.0, 1.0);
            }

        } else if (ClusterUtils.getDemographicClusterAge(birthday)
                .equals(ClusterUtils.CLUSTER_OVER_TWENTYNINE_UNDER_FORTYNINE)) {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0);
            }

        } else {

            if (gender.equals("male")) {
                return new DemographicNeighbor(user, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0);
            } else {
                return new DemographicNeighbor(user, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0);
            }
        }
    }

}
