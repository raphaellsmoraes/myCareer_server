package domain.web.resource;

import com.google.gson.Gson;
import domain.model.User;
import domain.repository.UserRepository;
import domain.service.DisconnectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@RestController
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
