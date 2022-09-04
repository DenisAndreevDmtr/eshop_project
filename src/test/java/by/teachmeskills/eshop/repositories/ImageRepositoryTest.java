package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Image;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void testFindImageByCategoryIdExist() {
        Image imageFound = imageRepository.findImageByCategory_Id(2);
        assertThat(imageFound.getCategory().getName()).isEqualTo("Monitors");
    }

    @Test
    void testFindImageByCategoryIdNotExist() {
        Image image = imageRepository.findImageByCategory_Id(21);
        assertThat(image).isNull();
    }

    @Test
    void testGetByIdExist() {
        Image imageFound = imageRepository.getById(3);
        assertThat(imageFound.getImagePath()).isEqualTo("pads.jpg");
    }

    @Test
    void testGetByIdNotExist() {
        try {
            Image image = imageRepository.getById(300);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(EntityNotFoundException.class);
        }
    }
}