package domain.service;

import domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Service
public class DisconnectUserService {

    @Autowired
    private UserRepository userRepository;

    public void disconnect(String username){
    }


}
