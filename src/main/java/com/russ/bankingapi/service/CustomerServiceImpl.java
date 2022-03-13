package com.russ.bankingapi.service;

import com.russ.bankingapi.dto.Customer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;

// TODO: implement database-backed customer service
@Service
public class CustomerServiceImpl implements CustomerService {

  private final Map<String, Customer> customers = new HashMap<>();

  @Override
  public Customer createCustomer(Customer customer) {
    String id = UUID.randomUUID().toString();

    while (customers.get(id) != null) {
      id = UUID.randomUUID().toString();
    }

    customer.setId(id);
    customers.put(id, customer);

    return customer;
  }

  @Override
  public Customer getCustomer(String id) {
    return customers.get(id);
  }
}
