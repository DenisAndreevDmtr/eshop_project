package by.teachmeskills.eshop.utils;

import by.teachmeskills.eshop.entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import static by.teachmeskills.eshop.utils.EshopConstants.IS_FIRT_PAGE;
import static by.teachmeskills.eshop.utils.EshopConstants.IS_LAST_PAGE;
import static by.teachmeskills.eshop.utils.EshopConstants.NUMBER_OF_PAGES;
import static by.teachmeskills.eshop.utils.EshopConstants.PAGE_NUMBER;
import static by.teachmeskills.eshop.utils.EshopConstants.PAGE_SIZE;

@Component
public class PagingUtil {

    public void setPagingAttributes(ModelMap modelMap, Page<? extends BaseEntity> etityPage, int pageNumber, int pageSize) {
        modelMap.addAttribute(NUMBER_OF_PAGES, etityPage.getTotalPages());
        modelMap.addAttribute(IS_FIRT_PAGE, etityPage.isFirst());
        modelMap.addAttribute(IS_LAST_PAGE, etityPage.isLast());
        modelMap.addAttribute(PAGE_NUMBER, pageNumber);
        modelMap.addAttribute(PAGE_SIZE, pageSize);
    }
}