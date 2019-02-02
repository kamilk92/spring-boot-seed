package pl.kkp.core.controller.mapping.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.controller.mapping.IterableBeanMapper;
import pl.kkp.core.controller.model.UserModel;
import pl.kkp.core.db.entity.User;
import pl.kkp.core.testing.SpringBootBaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class TestIterableBeanMapper extends SpringBootBaseTest {

    @Autowired
    private IterableBeanMapper iterableBeanMapper;

    @Test
    public void isMapIterable() {
        List<String> userNames = new ArrayList<String>() {
            {
                add("user0");
                add("user1");
            }
        };

        List<User> users = userNames.stream()
                .map(User::new)
                .collect(Collectors.toList());

        List<UserModel> userModels = iterableBeanMapper.map(users, UserModel.class);

        assertThat(userModels)
                .size()
                .isGreaterThanOrEqualTo(2);

        assertThat(userModels)
                .extracting("getUserByLogin")
                .containsAll(userNames);
    }
}
