package by.teachmeskills.eshop.services;

import by.teachmeskills.eshop.entities.Image;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

public interface ImageService extends BaseService<Image> {
    ModelAndView saveImagesFromFile(MultipartFile file);

    ModelAndView deleteImageById(int id);

    void downloadImagesToCsvFile(HttpServletResponse response);
}