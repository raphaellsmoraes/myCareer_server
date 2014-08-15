package domain.web.resource;

import domain.model.User;
import domain.repository.UserRepository;
import domain.service.DisconnectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public HttpStatus newUser(User user) {
        userRepository.save(User.newUser(user.getId(),
                user.getName(), user.getFacebookId(),
                user.getBirthday(),user.getFavoriteBooks(),
                user.getFavoriteMovies(),user.getFavoriteMusics(),
                user.getFavoriteAthletes(), user.getLocation(),
                user.getGender(), user.getPersonality()));

        return HttpStatus.OK;
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
