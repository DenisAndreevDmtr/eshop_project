package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.BaseEntity;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.entities.Product;
import by.teachmeskills.eshop.repositories.ProductRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final SessionFactory sessionFactory;

    public ProductRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Product> getAllProductsByCategoryId(int categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("select p from Product p where p.category.id=:categoryId");
        query.setParameter("categoryId", categoryId);
        return query.list();
    }

    public Product getProductById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Product.class, id);
    }

    @Override
    public List<Product> getListProductsByNameOrDesc(String param) {
        String requestDB = '%' + param + '%';
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select p from Product p where p.name like :requestDB or p.description like: requestDB");
        query.setParameter("requestDB", requestDB);
        return query.list();
    }

    //method should be updated
    @Override
    public Product create(Product entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public List<Product> read() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" from Product ").list();
    }

    @Override
    public Map<Product, Integer> getAllProductsByOrderId(int orderId) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, orderId);
        return order.getProducts();
    }

    //method should be updated
    @Override
    public Product update(Product entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class, entity.getId());
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setImageName(entity.getImageName());
        product.setPrice(entity.getPrice());
        product.setCategory(entity.getCategory());
        session.update(product);
        transaction.commit();
        session.close();
        return product;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Product product = session.get(Product.class, id);
        session.delete(product);
        transaction.commit();
        session.close();
    }

    @Override
    public long countAllProductsByCategory(int categoryId){
        int pageSize = 1;
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("select count(p) from Product p where p.category.id=:categoryId");
        query.setParameter("categoryId", categoryId);
        long resultQuery=query.getSingleResult();
        if(resultQuery%pageSize!=0){
            return query.getSingleResult()/pageSize+1;
        }
        return query.getSingleResult()/pageSize;
    }

    @Override
    public List<Product> getAllProductsByCategoryIdPaging(int categoryId, int pageReq) {
        int pageSize = 1;
        int firstResult;
        if(pageReq>1){
            firstResult=(pageReq-1)*pageSize;
        }
        else {
            firstResult=0;
        }
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("select p from Product p where p.category.id=:categoryId order by p.name asc");
        query.setParameter("categoryId", categoryId);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.list();
    }

    @Override
    public long countProductsByNameOrDesc(String param) {
        int pageSize = 5;
        String requestDB = '%' + param + '%';
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("select count(p) from Product p where p.name like :requestDB or p.description like: requestDB order by p.name asc");
        query.setParameter("requestDB", requestDB);
        long resultQuery=query.getSingleResult();
        if(resultQuery%pageSize!=0){
            return query.getSingleResult()/pageSize+1;
        }
        return query.getSingleResult()/pageSize;
    }
}