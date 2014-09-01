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

    @Scheduled(fixedRate = 6000000)
    public void run() {
        occupationServiceCreator.execute();
    }
}
