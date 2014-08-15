package domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Component
public class GroupScheduler {

    @Autowired
    private GroupServiceCreator groupServiceCreator;

    @Scheduled(fixedRate = 5000)
    public void run() {
        //groupServiceCreator.execute();
    }

}
