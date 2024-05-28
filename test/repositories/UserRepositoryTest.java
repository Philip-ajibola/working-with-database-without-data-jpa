package repositories;

import org.bobbysStore.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {
    private final UserRepository userRepository = new UserRepository();

    @Test
    void saveUserTest() {
        User user = new User();
//        user.setId(1l);
        User savedUser = userRepository.saveUser(user);
        assertNotNull(savedUser);
    }
}