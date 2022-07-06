package by.teachmeskills.eshop.repositories.impl;

import by.teachmeskills.eshop.entities.BaseEntity;
import by.teachmeskills.eshop.entities.Order;
import by.teachmeskills.eshop.repositories.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    private final SessionFactory sessionFactory;

    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Order create(Order entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return entity;
    }

    @Override
    public Order getOrderByUserIdAndMaxId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("select o from Order o where o.user.id=:id order by o.id desc");
        query.setParameter("id", id);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    @Override
    public List<Integer> getAllOrdersIdsByUserId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("select o from Order o where o.user.id=:id order by o.date desc");
        query.setParameter("id", id);
        return query.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @Override
    public long countAllOrdersByUser(int id){
        int pageSize = 5;
        Session session = sessionFactory.getCurrentSession();
        Query<Long> query = session.createQuery("select count(o) from Order o where o.user.id=:id");
        query.setParameter("id", id);
        long resultQuery=query.getSingleResult();
        if(resultQuery%pageSize!=0){
            return query.getSingleResult()/pageSize+1;
        }
        return query.getSingleResult()/pageSize;
    }

    @Override
    public List<Integer> getAllOrdersIdsByUserIdPaging(int userId, int pageReq) {
        int pageSize = 5;
        int firstResult;
        if(pageReq>1){
            firstResult=(pageReq-1)*pageSize;
        }
        else {
            firstResult=0;
        }
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("select o from Order o where o.user.id=:id order by o.id desc");
        query.setParameter("id", userId);
        query.setFirstResult(firstResult);
        query.setMaxResults(pageSize);
        return query.stream().map(BaseEntity::getId).collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Order.class, id);
    }

    @Override
    public List<Order> read() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(" from Order").list();
    }

    //method should be updated
    @Override
    public Order update(Order entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(Order.class, entity.getId());
        order.setPriceOrder(entity.getPriceOrder());
        order.setProductsInOrder(entity.getProductsInOrder());
        order.setUser(entity.getUser());
        session.update(order);
        transaction.commit();
        session.close();
        return order;
    }

    //method should be updated
    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Order order = session.get(Order.class, id);
        session.delete(order);
        transaction.commit();
        session.close();
    }
}