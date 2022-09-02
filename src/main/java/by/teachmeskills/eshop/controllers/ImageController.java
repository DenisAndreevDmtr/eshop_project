package by.teachmeskills.eshop.controllers;

import by.teachmeskills.eshop.services.ImageService;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

import static by.teachmeskills.eshop.utils.EshopConstants.FILE;

@Log4j
@RestController
@RequestMapping("/image")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ModelAndView categoryAddImage(@RequestParam(FILE) MultipartFile file) {
        return imageService.saveImagesFromFile(file);
    }

    @PostMapping("/delete")
    public ModelAndView deleteImageById(int imageIdDelete) {
        return imageService.deleteImageById(imageIdDelete);
    }

    @GetMapping("/download")
    public void getImagesToScvFile(HttpServletResponse response) {
        imageService.downloadImagesToCsvFile(response);
    }
}