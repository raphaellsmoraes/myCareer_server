package domain;

import com.google.common.collect.Lists;
import domain.model.User;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Created by rapha_000 on 17/08/2014.
 */
public class TestTenThousandUsers {

    private List<User> data = Lists.newArrayList();
    private final String NAME_PATTERN = "%s";


    @Before
    public void setUp() {

    }

    @Test
    public void TestTenThousand() {
        int initValue = 1;
        int iteractions = 10000;


        for (int index = initValue; index < initValue + iteractions; index++) {

            for (int innerIndex = 0; innerIndex < 8; innerIndex++) {
                String uuid = UUID.randomUUID().toString();
                String name = String.format(NAME_PATTERN, uuid);
            }
                /* Random facebook user generated */
            //data.add(User.newUser());
        }
    }

    /*String URL = "http://localhost:8080/";
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8080));
    requestFactory.setProxy(proxy);
    RestTemplate restTemplate = new RestTemplate(requestFactory);
        /*for (User user : data) {
            restTemplate.getForEntity(String.format(URL, user.getId()), Void.class);
        }*/


}
