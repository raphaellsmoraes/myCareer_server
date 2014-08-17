package domain.service;

import domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Service
public class DisconnectUserService {

    @Autowired
    private UserRepository userRepository;

    public void disconnect(String username){
    }


}
