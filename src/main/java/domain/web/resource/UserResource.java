package domain.web.resource;

import com.google.gson.Gson;
import domain.model.*;
import domain.repository.OccupationRepository;
import domain.repository.UserRepository;
import domain.utils.ClusterUtils;
import domain.utils.PredictionUtils;
import domain.utils.myCareerUtils;
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
    private OccupationRepository occupationRepository;

    @RequestMapping(value = "/connect", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity newUser(@RequestBody String data) {

        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);

        ArrayList<Occupation> occupations = new ArrayList<Occupation>();
        ArrayList<Profession> profession = new ArrayList<Profession>();

        for (Occupation occupation : occupationRepository.findAll()) {
            profession.add(new Profession(occupation, -1.0 /* myCareerUtils.randInt(-1, 5)*/));
        }

        user.setProfessions(profession);

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

    /* initiate user*/
    @RequestMapping(value = "/updateOcuppation", method = RequestMethod.GET)
    public ResponseEntity updateOccupation(@RequestParam String id, @RequestParam String occupationId) {

        User updatedUser = userRepository.findOne(id);

        /* search for occupation and updates it */
        userRepository.save(updatedUser);

        return new ResponseEntity<>(userRepository.findOne(id).toString(), HttpStatus.OK);
    }

    /* Iterate through all ocupations setting -1 */


    @RequestMapping(value = "/getOccupation", method = RequestMethod.GET)
    public ResponseEntity<String> getOccupation(@RequestParam("id") String id) {
        return new ResponseEntity<>(occupationRepository.findOne(id).getTitle(), HttpStatus.OK);
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
            ArrayList<Neighbor> knowledgeSimilarity = ClusterUtils.getKnowledgeNeighborhood(baseUser, users);

            List<User> filteredUsers = new ArrayList<User>();
            for (int i = 0; i <= (knowledgeSimilarity.size() - 1); i++) {
                filteredUsers.add(knowledgeSimilarity.get(i).getUser());
            }

            ArrayList<Neighbor> neighborhood = ClusterUtils.getMergedCorrelations(baseUser, filteredUsers);

            /* Order arrayList by similar users from highest to lowest correlation */
            Collections.sort(neighborhood);

            ArrayList<Prediction> predictions = PredictionUtils.getPredictions(baseUser, neighborhood);
            Collections.sort(predictions);

            String result = "";
            for (Prediction n : predictions) {
                result = result + " /" + n.getId() + ":" + n.getPrediction() + "\n";
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("id not found", HttpStatus.BAD_REQUEST);
        }

    }
}
