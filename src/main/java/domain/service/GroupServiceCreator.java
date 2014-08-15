package domain.service;

import domain.repository.GroupRepository;
import domain.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Service
public class GroupServiceCreator {

    private static final Logger LOGGER = Logger.getLogger(GroupServiceCreator.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    /*
    public void execute() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        LOGGER.info(String.format("Starting create groups....Thread name %s", Thread.currentThread().getName()));
        Set<NewConnection> newConnections = Sets.newHashSet(newConnectionRepository.findAll());
        LOGGER.debug(String.format("Number of new connections %s", String.valueOf(newConnections.size())));

        List<NewConnection> copyConnections = Lists.newArrayList(newConnections);
        Map<String, Set<NewConnection>> tasks = new HashMap<>();
        Set<NewConnection> newConnectionsMatched = Sets.newHashSet();

        Iterator<NewConnection> newConnectionIterator = newConnections.iterator();

        while (newConnectionIterator.hasNext()) {

            NewConnection newConnection = newConnectionIterator.next();
            Set<NewConnection> newUsers = Sets.newHashSet();

            Iterator<NewConnection> copyConnectionsIterator = copyConnections.iterator();

            while (copyConnectionsIterator.hasNext()) {
                NewConnection connection = copyConnectionsIterator.next();
                if (new NewConnectionIsMatch(connection).isSatisfiedBy(newConnection) && !newConnectionsMatched.contains(connection)) {
                    newUsers.add(connection);
                    copyConnectionsIterator.remove();
                    newConnectionsMatched.add(connection);
                }
            }
            // Match!!!
            if (newUsers.size() > 1) {
                String id = UUID.randomUUID().toString();
                tasks.put(id, newUsers);
            }
        }

        LOGGER.info("Tasks to execute: " + tasks.size());

        Set<String> groupKeys = tasks.keySet();
        for (String groupKey : groupKeys) {
            Set<NewConnection> matchedConnections = tasks.get(groupKey);
            Set<NewConnection> copyMatchedConnections = Sets.newHashSet(matchedConnections);

            boolean createNewGroup = true;
            for (NewConnection matchedConnection : matchedConnections) {
                User user = userRepository.findOne(matchedConnection.getId());
                if (user != null) {
                    for (NewConnection copyConnection : copyMatchedConnections) {
                        userRepository.save(User.newUserWithGroupId(copyConnection.getId(), copyConnection.getLat(), copyConnection.getLon(), user.getGroupId()));
                        newConnectionRepository.delete(copyConnection.getId());
                        userStepsRepository.save(UserSteps.newUserSteps(copyConnection.getId(), user.getGroupId()));
                        createNewGroup = false;
                    }
                }
            }

            LOGGER.info("Create new Group: " + createNewGroup);

            if (createNewGroup) {
                String groupId = groupRepository.nextGroupId();
                for (NewConnection matchedConnection : matchedConnections) {
                    userRepository.save(User.newUserWithGroupId(matchedConnection.getId(), matchedConnection.getLat(), matchedConnection.getLon(), groupId));
                    userStepsRepository.save(UserSteps.newUserSteps(matchedConnection.getId(), groupId));
                    newConnectionRepository.delete(matchedConnection.getId());
                }
            }

        }

        stopwatch.stop();
        LOGGER.info(String.format("End create groups.Thread name %s", Thread.currentThread().getName()));
        LOGGER.info(String.format("Time for process groups %s milliseconds", String.valueOf(stopwatch.elapsed(TimeUnit.MILLISECONDS))));
        */

}
