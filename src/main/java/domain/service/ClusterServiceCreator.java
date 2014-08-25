package domain.service;

import com.google.common.base.Stopwatch;
import domain.model.Neighbor;
import domain.model.User;
import domain.repository.UserRepository;
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

        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();

        /* Retrieve all users to cluster */
        User baseUser = userRepository.findOne("53f0e1a2be99d3c50b43d026");
        List<User> users = userRepository.findAll();

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

            LOGGER.debug(String.format("[User: %s] ", neighborhood.get(i).getUser().getName())
                    + String.format("[Correlation: %s]", neighborhood.get(i).getCorrelation()));
        }

        LOGGER.debug("Total:" + neighborhood.size());
        stopwatch.stop();
    }

}
