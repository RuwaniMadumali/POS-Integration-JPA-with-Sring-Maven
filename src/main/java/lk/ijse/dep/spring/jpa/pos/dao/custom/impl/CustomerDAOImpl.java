package lk.ijse.dep.spring.jpa.pos.dao.custom.impl;

import lk.ijse.dep.spring.jpa.pos.dao.CrudDAOImpl;
import lk.ijse.dep.spring.jpa.pos.dao.custom.CustomerDAO;
import lk.ijse.dep.spring.jpa.pos.entity.Customer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
@Repository
public class CustomerDAOImpl extends CrudDAOImpl<Customer,String> implements CustomerDAO {

    @Override
    public String getLastCustomerId() throws Exception {
        try {
            return (String) entityManager.createNativeQuery("SELECT id FROM Customer ORDER BY id DESC LIMIT 1 ").getSingleResult();
        } catch (NoResultException e) {
          return null;
        }
    }


}
