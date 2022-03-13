package com.russ.bankingapi.controller;

import com.russ.bankingapi.dto.Customer;
import com.russ.bankingapi.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/customers")
public class CustomerController {

  Logger logger = LoggerFactory.getLogger(CustomerController.class);

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService service) {
    this.customerService = service;
  }

  @PostMapping()
  public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
    try {
      Customer res = customerService.createCustomer(customer);
      return ResponseEntity.ok(res);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping()
  public ResponseEntity<Customer> getCustomer(@RequestParam String id) {
    try {
      Customer customer = customerService.getCustomer(id);

      if (customer == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      return ResponseEntity.ok(customer);
    } catch (Exception e) {
      logger.error("Couldn't get customer: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
