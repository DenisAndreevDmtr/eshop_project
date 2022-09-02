package by.teachmeskills.eshop.utils;

public enum PagesPathEnum {
    HOME_PAGE("home"),
    SIGN_IN_PAGE("signin"),
    CATEGORY_PAGE("category"),
    CART_PAGE("cart"),
    PRODUCT_PAGE("product"),
    REGISTER_PAGE("register"),
    PROFILE_PAGE("profile"),
    ORDER_PAGE("order"),
    SEARCH_PRODUCT_PAGE("searchproduct"),
    ERROR_PAGE("error"),
    HOT_PRICES("hotprices"),
    FIELDS_REDACTION_PAGE("redactionuser"),
    SHOPPING_CART("cart"),
    DATA_MANAGEMENT_PAGE("datamanagement");

    private final String path;

    PagesPathEnum(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}