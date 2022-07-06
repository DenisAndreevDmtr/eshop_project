package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.Category;
import by.teachmeskills.eshop.repositories.CategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {
    private final SessionFactory sessionFactory;

    public CategoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Category> getAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" from Category").list();
    }

    @Override
    public String getCategoryNameByID(int id) {
        Session session = sessionFactory.getCurrentSession();
        return (String) session.get(Category.class.getName(), id);
    }


    @Override
    public Category getCategoryById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Category.class, id);
    }

    //method should be updated
    @Override
    public Category create(Category entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    private Category getCategoryByName(String nameCategory) {
        Session session = sessionFactory.getCurrentSession();
        Query<Category> query = session.createQuery("select c from Category c where c.name=:nameCategory");
        query.setParameter("nameCategory", nameCategory);
        return query.getSingleResult();
    }

    //method should be updated
    @Override
    public List<Category> read() {
        return getAllCategories();
    }

    //method should be updated
    @Override
    public Category update(Category entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Category category = session.get(Category.class, entity.getId());
        category.setName(category.getName());
        category.setImageName(entity.getImageName());
        category.setRating(entity.getRating());
        session.update(category);
        transaction.commit();
        session.close();
        return category;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Category category = session.get(Category.class, id);
        session.delete(category);
        transaction.commit();
        session.close();
    }
}