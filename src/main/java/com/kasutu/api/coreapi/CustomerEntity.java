package com.kasutu.api.coreapi;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity(name = "CustomerEntity")
@Table(name = "customers")

@NamedNativeQueries({
    @NamedNativeQuery(name = "Customer.getAllCustomers", query = "SELECT * FROM customers", resultClass = CustomerEntity.class),

    @NamedNativeQuery(name = "Customer.getCustomerById", query = "SELECT * "
        + "FROM customers " + "WHERE customerNumber = :number", resultClass = CustomerEntity.class)
})

public class CustomerEntity {

  @Id
  private Integer customerNumber;
  private String customerName;
  private String phone;

  public CustomerEntity() {
    // default
  }

  public CustomerEntity(String customerName, String phone) {
    this.customerName = customerName;
    this.phone = phone;
  }

  public Integer getCustomerNumber() {
    return customerNumber;
  }

  public void setCustomerNumber(Integer customerNumber) {
    this.customerNumber = customerNumber;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "customerNumber=" + this.customerNumber + " customerName=" + this.customerName + " phone=" + this.phone;
  }

}
