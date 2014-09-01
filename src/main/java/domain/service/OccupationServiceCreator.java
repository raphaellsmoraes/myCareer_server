package domain.service;

import com.google.common.base.Stopwatch;
import domain.model.Occupation;
import domain.repository.OccupationRepository;
import domain.utils.ClusterUtils;
import domain.utils.MapUtilities;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Service
public class OccupationServiceCreator {

    private static final Logger LOGGER = Logger.getLogger(OccupationServiceCreator.class);

    @Autowired
    private OccupationRepository occupationRepository;


    public void execute() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.info(String.format("Start occupation sort. Thread name %s", Thread.currentThread().getName()));

        /* Retrieve all occupations */
        List<Occupation> occupationList = occupationRepository.findAll();
        Map<String, Integer> occupationDistance = new HashMap<>();

        /* Loop through all the occupations to check their similarity */
        for (int i = 0; i <= occupationList.size() - 1; i++) {
            String baseDesc = occupationList.get(i).getDescription();

            /* Empty our List */
            occupationDistance.clear();

            /* Loop checking all distances */
            for (int j = 0; j <= occupationList.size() - 1; j++) {
                String compareDesc = occupationList.get(j).getDescription();
                occupationDistance.put(occupationList.get(j).getId(), new Integer(ClusterUtils.getLevenshteinDistance(baseDesc, compareDesc)));
            }

            /* Order and save top 10 similar occupations descriptions */
            List<Map.Entry<String, Integer>> sortedMap = MapUtilities.sortByValue(occupationDistance);
            Occupation occupation = occupationRepository.findOne(occupationList.get(i).getId());
            //occupation.setSimilarOccupations(sortedMap);
            occupationRepository.save(occupation);
        }


        stopwatch.stop();
        LOGGER.info(String.format("End occupation sort. Thread name %s", Thread.currentThread().getName()));
    }
}
