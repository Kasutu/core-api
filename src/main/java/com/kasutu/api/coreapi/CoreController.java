package com.kasutu.api.coreapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/api")
public class CoreController {

  @Autowired
  private CustomerDAOimpl customerDAO;

  @GetMapping(value = "/")
  public String getPage() {
    return "Depression";
  }

  @GetMapping(value = "/customers")
  public String getAllCustomers() {
    List<CustomerEntity> customerList = customerDAO.getAllCustomers();

    return new Gson().toJson(customerList);
  }

  @GetMapping(value = "/customers/{id}")
  public String getAllCustomersById(@PathVariable Integer id) {
    CustomerEntity customer = customerDAO.getCustomerById(id);

    return new Gson().toJson(customer);
  }
}
