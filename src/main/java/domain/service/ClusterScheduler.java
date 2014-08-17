package domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Component
public class ClusterScheduler {

    @Autowired
    private ClusterServiceCreator clusterServiceCreator;

    @Scheduled(fixedRate = 5000)
    public void run() {
        clusterServiceCreator.execute();
    }

}
