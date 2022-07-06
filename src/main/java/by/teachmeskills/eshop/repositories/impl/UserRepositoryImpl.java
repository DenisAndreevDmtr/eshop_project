package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("select u from User u where u.login=:login and u.password=:password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("select u from User u where u.login=:login");
        query.setParameter("login", login);
        try {
            return Optional.ofNullable(query.getSingleResult());
        } catch (Exception e) {
            System.out.println("Not unique user");
        }
        return Optional.empty();
    }

    @Override
    public User create(User entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    //method should be updated
    @Override
    public List<User> read() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" from User").list();
    }

    //method should be updated
    @Override
    public User update(User entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, entity.getId());
        user.setName(entity.getName());
        user.setSurname(entity.getSurname());
        user.setBalance(entity.getBalance());
        user.setDateBorn(entity.getDateBorn());
        user.setLogin(entity.getLogin());
        user.setPassword(entity.getPassword());
        session.update(user);
        transaction.commit();
        session.close();
        return user;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, id);
        session.delete(user);
        transaction.commit();
        session.close();
    }
}