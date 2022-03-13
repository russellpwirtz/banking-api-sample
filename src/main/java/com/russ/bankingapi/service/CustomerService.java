package com.russ.bankingapi.service;

import com.russ.bankingapi.dto.Customer;

public interface CustomerService {

  Customer createCustomer(Customer customer);

  Customer getCustomer(String id);
}
