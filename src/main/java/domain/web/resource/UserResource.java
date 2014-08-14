package domain.web.resource;

import domain.NewConnection;
import domain.User;
import domain.UserSteps;
import domain.repository.NewConnectionRepository;
import domain.repository.UserRepository;
import domain.repository.UserStepsRepository;
import domain.service.DisconnectUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@RestController
@RequestMapping("/user")
public class UserResource {

    @Autowired
    private NewConnectionRepository newConnectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DisconnectUserService disconnectUserService;

    @Autowired
    private UserStepsRepository userStepsRepository;

    @RequestMapping(value = "/connect",method = RequestMethod.GET)
    public ResponseEntity<String> receiveRequest(@RequestParam("username")String userId,@RequestParam("lat")BigDecimal lat,@RequestParam("lon")BigDecimal lon){
        newConnectionRepository.save(NewConnection.newConnection(userId, lat.doubleValue(), lon.doubleValue()));
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @RequestMapping(value = "/disconnect",method = RequestMethod.GET)
    public ResponseEntity<String> receiveDisconnectRequest(@RequestParam("username")String username){
        disconnectUserService.disconnect(username);
        return new ResponseEntity<>(String.format("User %s disconnected",username), HttpStatus.OK);
    }

    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public ResponseEntity<List<User>> users(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/numberOfUsers",method = RequestMethod.GET)
    public ResponseEntity<Long> numberOfUsers(){
        return new ResponseEntity<>(userRepository.count(), HttpStatus.OK);
    }

    @RequestMapping(value = "/groupInfo",method = RequestMethod.GET)
    public ResponseEntity<List<UserSteps>> userGroupInfo(@RequestParam("username")String username){
        User user = userRepository.findOne(username);
        List<UserSteps> userStepses = userStepsRepository.findByGroupId(user.getGroupId());
        return new ResponseEntity<>(userStepses,HttpStatus.OK);
    }

    @RequestMapping(value = "/check",method = RequestMethod.GET)
    public ResponseEntity<String> receiveCheckSteps(@RequestParam("username")String username,@RequestParam("steps")Long steps){
        UserSteps userSteps = userStepsRepository.findOne(username);
        userSteps.addSteps(steps);
        userStepsRepository.save(userSteps);
        return new ResponseEntity<>(String.format("Steps of user %s updated. %s steps. ",username,String.valueOf(userSteps.getSteps())), HttpStatus.OK);
    }

}
