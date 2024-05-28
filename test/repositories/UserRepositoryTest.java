package repositories;

import org.bobbysStore.User;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    @Test
    public void testUpdateUser(){
        Long userId = 2L;
        Long walletId = 3L;
        User user =userRepository.updateUser(userId, walletId);
        assertEquals(3L,user.getWalletId());
    }
    @Test
    public void testDeleteUser(){
        Long userId = 1L;
        userRepository.deleteUser(userId);
        assertEquals(8L,userRepository.count());
    }
    @Test
    public void testDeleteUser1(){
        Long userId = 2L;
        userRepository.deleteUser(userId);
        assertThrows(RuntimeException.class,()->userRepository.findUserBy(userId));
    }
    @Test
    public void testFindAll(){
        List<User> users = userRepository.findAll();
        assertEquals(9,users.size());
    }
}