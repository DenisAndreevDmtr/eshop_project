package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindUserByLoginExist() {
        Optional<User> user = userRepository.findUserByLogin("vcxz");
        assertThat(user).isPresent();
        assertThat(user.get().getId()).isEqualTo(2);
    }

    @Test
    void testFindUserByLoginNotExist() {
        Optional<User> user = userRepository.findUserByLogin("abcde");
        assertThat(user).isNotPresent();
    }
}