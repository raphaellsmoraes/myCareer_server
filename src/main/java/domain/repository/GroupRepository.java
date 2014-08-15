package domain.repository;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Raphael Moraes (raphael.lsmoraes@gmail.com)
 */
@Service
public class GroupRepository {

    private AtomicLong atomicLong = new AtomicLong(0);

    public String nextGroupId() {
        return String.valueOf(atomicLong.addAndGet(1));
    }

}
