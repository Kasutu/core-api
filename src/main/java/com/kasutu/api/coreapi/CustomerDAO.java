package com.kasutu.api.coreapi;

import java.util.List;

public interface CustomerDAO {

  public List<CustomerEntity> getAllCustomers();

  public CustomerEntity getCustomerById(Integer id);
}
