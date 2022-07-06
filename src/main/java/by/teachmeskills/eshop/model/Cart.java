package by.teachmeskills.eshop.model;

import by.teachmeskills.eshop.entities.BaseEntity;
import by.teachmeskills.eshop.entities.Product;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
public class Cart extends BaseEntity {

    private Map<Product, Integer> products;
    private BigDecimal totalPrice = new BigDecimal(0);

    public Cart() {
        this.products = new HashMap<>();
    }

    public void addProduct(Product product) {
        if (products.containsKey(product)) {
            Integer amount = products.get(product);
            amount = amount + 1;
            products.put(product, amount);
        } else {
            Integer amount = 1;
            products.put(product, amount);
        }
        totalPrice = totalPrice.add(product.getPrice());
    }

    public void removeProduct(Product product) {
        BigDecimal sumDecrease = product.getPrice().multiply(BigDecimal.valueOf(products.get(product)));
        totalPrice = totalPrice.subtract(sumDecrease);
        products.remove(product);
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.keySet());
    }

    public int getAmount(Product product) {
        return products.get(product);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void clear() {
        products.clear();
    }

    public void decreaseAmount(Product product) {
        int amount = products.get(product);
        if (amount > 1) {
            amount = amount - 1;
            products.put(product, amount);
        } else {
            products.remove(product);
        }
        totalPrice = totalPrice.subtract(product.getPrice());
    }
    public List<Product> getProductsAndAmount() {
        List<Product> listProducts = new ArrayList(products.keySet());
        List<Integer> count = new ArrayList(products.values());
        List<Product> resultProduct = new ArrayList<>();
        for (int i = 0; i < listProducts.size(); i++) {
            for (int j = 0; j < count.get(i); j++) {
                resultProduct.add(listProducts.get(i));
            }
        }
        return resultProduct;
    }
}