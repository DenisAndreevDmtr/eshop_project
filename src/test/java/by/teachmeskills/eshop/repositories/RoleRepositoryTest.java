package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void testFindRoleByNameExist() {
        Optional<Role> role = roleRepository.findRoleByName("ROLE_ADMIN");
        assertThat(role).isPresent();
        assertThat(role.get().getId()).isEqualTo(1);
    }

    @Test
    void testFindRoleByNameNotExist() {
        Optional<Role> role = roleRepository.findRoleByName("ADMIN");
        assertThat(role).isNotPresent();
    }
}