package domain.web.resource;

import com.google.gson.Gson;
import domain.model.Occupation;
import domain.model.Profession;
import domain.model.User;
import domain.repository.UserRepository;
import domain.service.DisconnectUserService;
import domain.utils.myCareerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Controller
@RequestMapping("/user")
public class UserResource {

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
            profession.add(new Profession(occupation[i], (double) myCareerUtils.randInt(0, 5)));
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

}
