package domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Component
public class OccupationScheduler {

    @Autowired
    private OccupationServiceCreator occupationServiceCreator;

    /* Schedule to every 24 hours */
    @Scheduled(fixedRate = 86400000)
    public void run() {
        //occupationServiceCreator.execute();
    }
}
