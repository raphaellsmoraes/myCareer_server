package domain.web.resource;

import com.google.gson.Gson;
import domain.model.*;
import domain.repository.UserRepository;
import domain.service.DisconnectUserService;
import domain.utils.ClusterUtils;
import domain.utils.myCareerUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Controller
@RequestMapping("/user")
public class UserResource {

    private static final Logger LOGGER = Logger.getLogger(UserResource.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DisconnectUserService disconnectUserService;

    @RequestMapping(value = "/connect", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity newUser(@RequestBody String data) {

        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);

        userRepository.save(User.newUser(user));

        return new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ocuppations", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity updateOccupations(@RequestParam String id, @RequestBody String data) {

        Gson gson = new Gson();
        Occupation[] occupation = gson.fromJson(data.toString(), Occupation[].class);
        ArrayList<Profession> profession = new ArrayList<Profession>();

        for (int i = 0; (i <= occupation.length - 1); i++) {
            profession.add(new Profession(occupation[i], (double) myCareerUtils.randInt(-1, 5)));
        }

        User updatedUser = userRepository.findOne(id);
        updatedUser.setProfessions(profession);
        userRepository.save(updatedUser);

        return new ResponseEntity<>(userRepository.findOne(id).toString(), HttpStatus.OK);
    }

    @RequestMapping(value = "/disconnect", method = RequestMethod.GET)
    public ResponseEntity<String> receiveDisconnectRequest(@RequestParam("username") String username) {
        disconnectUserService.disconnect(username);
        return new ResponseEntity<>(String.format("User %s disconnected", username), HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> users() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/numberOfUsers", method = RequestMethod.GET)
    public ResponseEntity<Long> numberOfUsers() {
        return new ResponseEntity<>(userRepository.count(), HttpStatus.OK);
    }

    /* @getRecommendations:
     * This method implements the Hybrid recommender system, first it finds most similar users by
     * checking their tastes (favorite books, musics, movies and athletes) them the next recommender
     * algorithm filter users based on Demographic correlation (Location, gender and age), it also features
     * their ratings on each product, the output will be a list with the items most appealing to the user */
    @RequestMapping(value = "/recommendations", method = RequestMethod.GET)
    public ResponseEntity<String> getRecommendations(@RequestParam("id") String id) {

         /* Retrieve all users to cluster */
        User baseUser = userRepository.findOne(id);
        if (baseUser != null) {
            List<User> users = userRepository.findAll();

            /* Knowledge Correlation */
            ArrayList<Neighbor> tasteSimilarity = getKnowledgeNeighborhood(baseUser, users);

            /* Demographic Correlation */
            ArrayList<Neighbor> normalSimilarity = getCollaborativeNeighborhood(baseUser, users);
            ArrayList<Neighbor> demographicSimillarity = getDemographicNeighborhood(baseUser, users);


            ArrayList<Neighbor> neighborhood = getMergedCorrelations(normalSimilarity, demographicSimillarity);

            /* Order arrayList by similar users from highest to lowest correlation */
            Collections.sort(neighborhood);

            String result = "";
            for (Neighbor n : neighborhood) {
                result = result + " /" + n.getUser().getId() + ":" + n.getCorrelation() + "\n";
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("id not found", HttpStatus.BAD_REQUEST);
        }

    }

    /* @getKnowledgeNeighborhood
       returns an neighborhood of users based on tastes (movies, books, athletes and musics)
    */
    private ArrayList<Neighbor> getKnowledgeNeighborhood(User baseUser, List<User> users) {

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

    /* @getCollaborativeNeighborhood
    returns an neighborhood of users based on ratings
     */
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

    /* @getDemographicNeighborhood
returns an neighborhood of users based on demographics (Age, gender and birthday)
    */
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

    /* Merge Demographic and Normal Correlation and retrieve an List of Users */
    private ArrayList<Neighbor> getMergedCorrelations(ArrayList<Neighbor> normalSimilarity, ArrayList<Neighbor> demographicSimilarity) {

        int length = normalSimilarity.size() == demographicSimilarity.size() ? normalSimilarity.size() : 0;
        ArrayList<Neighbor> neighboors = new ArrayList<>();

        if (length != 0) {
            for (int i = 0; i <= (length - 1); i++) {
                if (normalSimilarity.get(i).getUser().getId().equals(demographicSimilarity.get(i).getUser().getId())) {

                    neighboors.add(new Neighbor(normalSimilarity.get(i).getUser(),
                            (normalSimilarity.get(i).getCorrelation() +
                                    (normalSimilarity.get(i).getCorrelation()
                                            * demographicSimilarity.get(i).getCorrelation()))));
                }
            }
            return neighboors;

        } else return null;
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
