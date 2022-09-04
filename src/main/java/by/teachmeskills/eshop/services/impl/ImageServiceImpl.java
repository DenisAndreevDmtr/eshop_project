package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.Image;
import by.teachmeskills.eshop.repositories.ImageRepository;
import by.teachmeskills.eshop.services.ImageService;
import by.teachmeskills.eshop.utils.CsvUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

import static by.teachmeskills.eshop.utils.EshopConstants.IMAGES_CSV_FILE_NAME;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_FAIL;
import static by.teachmeskills.eshop.utils.EshopConstants.OPERATION_STATUS_SUCCESS;
import static by.teachmeskills.eshop.utils.PagesPathEnum.DATA_MANAGEMENT_PAGE;

@Log4j
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final CsvUtil csvUtil;

    public ImageServiceImpl(ImageRepository imageRepository, CsvUtil csvUtil) {
        this.imageRepository = imageRepository;
        this.csvUtil = csvUtil;
    }

    @Override
    public Image create(Image entity) {
        return imageRepository.save(entity);
    }

    @Override
    public List<Image> read() {
        return imageRepository.findAll();
    }

    @Override
    public Image update(Image entity) {
        return imageRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        imageRepository.deleteById(id);
    }

    @Override
    public ModelAndView saveImagesFromFile(MultipartFile file) {
        ModelMap model = new ModelMap();
        try {
            List<Image> csvImages = csvUtil.parseCsvImage(file);
            if (Optional.ofNullable(csvImages).isPresent() && csvImages.size() > 0) {
                imageRepository.saveAll(csvImages);
                model.addAttribute(OPERATION_STATUS_SUCCESS, "You saved images from file " + file.getOriginalFilename());
                log.info("Images from file " + file.getOriginalFilename() + " have been saved");
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("Сan`t save images from file " + file.getOriginalFilename());
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t save images from the file " + file.getOriginalFilename() + ". Check, if such file is correct");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public ModelAndView deleteImageById(int id) {
        ModelMap model = new ModelMap();
        try {
            Image image = imageRepository.getById(id);
            imageRepository.deleteById(image.getId());
            model.addAttribute(OPERATION_STATUS_SUCCESS, "You deleted image with id № " + id);
            log.info("Image with id " + id + " was deleted");
        } catch (Exception e) {
            log.error("Сan`t delete order with id " + id);
            model.addAttribute(OPERATION_STATUS_FAIL, "Сan`t delete image with id " + id + ". Check, if such id is exist");
        }
        return new ModelAndView(DATA_MANAGEMENT_PAGE.getPath(), model);
    }

    @Override
    public void downloadImagesToCsvFile(HttpServletResponse response) {
        try {
            Writer writer = csvUtil.setResponsePropertiesAndGetWriter(response, IMAGES_CSV_FILE_NAME);
            List<Image> images = read();
            csvUtil.saveImagesToCsvFile(writer, images);
            log.info("Images have been downloaded to file name " + IMAGES_CSV_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("error in method saveAllImagesToCsvFile " + e.getMessage());
        }
    }
}