package by.teachmeskills.eshop.repositories;

import by.teachmeskills.eshop.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    Image findImageByCategory_Id(int id);

    Image getById(int id);

    @Modifying
    @Query("delete from Image where id = :idParam")
    void deleteById(@Param("idParam") int id);
}