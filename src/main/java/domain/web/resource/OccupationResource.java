package domain.web.resource;

import domain.model.Occupation;
import domain.repository.OccupationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Controller
@RequestMapping("/occupation")
public class OccupationResource {

    @Autowired
    private OccupationRepository occupationRepository;

    @RequestMapping(value = "/connect", method = RequestMethod.POST, consumes = "text/plain")
    public ResponseEntity newOccupation(@RequestBody String data) {

        String[] separatingOccupations = data.split("\t|\n");
        Occupation parsedOccupation = null;

        for (int i = 0; i < (separatingOccupations.length); i = i + 3) {
            parsedOccupation = Occupation.newOccupation(new Occupation(separatingOccupations[i],
                    separatingOccupations[i + 1], separatingOccupations[i + 2]));

            occupationRepository.save(parsedOccupation);
        }

        return new ResponseEntity<>(occupationRepository.count(), HttpStatus.OK);
    }


    @RequestMapping(value = "/occupations", method = RequestMethod.GET)
    public ResponseEntity<List<Occupation>> users() {
        return new ResponseEntity<>(occupationRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/numberOfOccupations", method = RequestMethod.GET)
    public ResponseEntity<Long> numberOfOccupations() {
        return new ResponseEntity<>(occupationRepository.count(), HttpStatus.OK);
    }

}
