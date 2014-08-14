package domain.repository;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
@Service
public class GroupRepository {

    private AtomicLong atomicLong = new AtomicLong(0);

    public String nextGroupId(){
        return String.valueOf(atomicLong.addAndGet(1));
    }

}
