package com.kasutu.api.coreapi;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerDAOimpl implements CustomerDAO {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<CustomerEntity> getAllCustomers() {
    return em.createNamedQuery("Customer.getAllCustomers", CustomerEntity.class).getResultList();
  }

  @Override
  public CustomerEntity getCustomerById(Integer id) {
    try {
      return em.createNamedQuery("Customer.getCustomerById", CustomerEntity.class).setParameter("number", id)
          .getSingleResult();

    } catch (Exception e) {
      return null;
    }
  }

}
