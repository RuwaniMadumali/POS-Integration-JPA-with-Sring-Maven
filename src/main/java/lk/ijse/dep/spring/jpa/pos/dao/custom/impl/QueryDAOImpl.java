package lk.ijse.dep.spring.jpa.pos.dao.custom.impl;

import lk.ijse.dep.spring.jpa.pos.dao.custom.QueryDAO;
import lk.ijse.dep.spring.jpa.pos.entity.CustomEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.sql.Date;
@Repository
public class QueryDAOImpl implements QueryDAO {
    @PersistenceContext
private EntityManager entityManager;
    @Override
    public CustomEntity getOrderDetail(String orderId) throws Exception {

        try {
            Object[]result = (Object[]) entityManager.createNativeQuery("SELECT o.id AS orderId,c.name AS " +
                    "customerName,o.date AS OrderDate FROM `Order o INNER JOIN Customer c ON o.customerId = c.id WHERE o.id=?1`").
                    setParameter(1,orderId).getSingleResult();
            return new CustomEntity((String) result[0],(String) result[1],(Date) result[2]);
        } catch (NoResultException e) {
            return null;
        }


    }

    @Override
    public CustomEntity getOrderDetail2(String orderId) throws Exception {
        try {
            return (CustomEntity) entityManager.createQuery("SELECT NEW lk.ijse.dep.spring.jpa.pos.entity.CustomEntity(C.id,C.name,O.id)" +
                    "FROM Order O INNER JOIN O.customer C WHERE O.id=:id ").setParameter("id",orderId).
                    getSingleResult();
    } catch (NoResultException e) {
            return null;
        }


    }


}
