package com.russ.bankingapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.russ.bankingapi.controller.CustomerController;
import com.russ.bankingapi.dto.Customer;
import com.russ.bankingapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, classes = {
        CustomerController.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private CustomerController customerController;

    @Test
    public void createCustomerTest() {
        Customer customer = new Customer("asdf");
        customer.setId("1");

        doReturn(customer).when(customerService).createCustomer(any(Customer.class));

        ResponseEntity<Customer> result = customerController.createCustomer(customer);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(customer, result.getBody());

        doThrow(new RuntimeException()).when(customerService).createCustomer(any(Customer.class));

        ResponseEntity<Customer> errorResult = customerController.createCustomer(customer);

        assertNotNull(errorResult);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResult.getStatusCode());
        assertNull(errorResult.getBody());
    }

    @Test
    public void getCustomer() {
        Customer customer = new Customer("asdf");
        customer.setId("1");
        doReturn(customer).when(customerService).getCustomer("1");
        ResponseEntity<Customer> result = customerController.getCustomer("1");

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(customer, result.getBody());

        doThrow(new RuntimeException()).when(customerService).getCustomer("3");
        ResponseEntity<Customer> errorResult = customerController.getCustomer("3");

        assertNotNull(errorResult);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResult.getStatusCode());
        assertNull(errorResult.getBody());
    }
}
