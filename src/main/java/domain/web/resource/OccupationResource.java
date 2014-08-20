package domain.web.resource;

import com.google.gson.Gson;
import domain.model.Occupation;
import domain.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rapha_000 on 19/08/2014.
 */
@RestController
@RequestMapping("/occupation")
public class OccupationResource {

    @Autowired
    private OccupationRepository occupationRepository;

    @RequestMapping(value = "/connect", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity newUser(@RequestBody String data) {

        Gson gson = new Gson();
        Occupation occupation = gson.fromJson(data, Occupation.class);

        //occupationRepository.save(Occupation.newOccupation(occupation));

        return new ResponseEntity<>(occupation.toString(), HttpStatus.OK);
    }
}
