package domain.service;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Sets;
import domain.model.User;
import domain.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        Set<User> users = Sets.newHashSet(userRepository.findAll());
        LOGGER.debug(String.format("Number of users %s", String.valueOf(users.size())));

        stopwatch.stop();
    }

}
