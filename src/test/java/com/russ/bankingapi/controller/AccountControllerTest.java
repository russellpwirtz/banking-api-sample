package com.russ.bankingapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.russ.bankingapi.controller.AccountController;
import com.russ.bankingapi.dto.Account;
import com.russ.bankingapi.service.AccountService;
import java.math.BigDecimal;
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
        AccountController.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    private AccountController accountController;

    @Test
    public void createAccountTest() {
        Account account = new Account("Customer1", BigDecimal.ZERO, "DEPOSIT", "USD");
        account.setAccountId("accountid1");

        doReturn(account).when(accountService).createAccount(any(Account.class));

        ResponseEntity<Account> result = accountController.createAccount(account);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(account, result.getBody());

        doThrow(new RuntimeException()).when(accountService).createAccount(any(Account.class));

        ResponseEntity<Account> errorResult = accountController.createAccount(account);

        assertNotNull(errorResult);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResult.getStatusCode());
        assertNull(errorResult.getBody());
    }

    @Test
    public void getAccount() {
        Account account = new Account("AccountId1", BigDecimal.ZERO, "DEPOSIT", "USD");
        account.setAccountId("a1");
        doReturn(account).when(accountService).getAccount("1", "a1");
        ResponseEntity<Account> result = accountController.getAccount("1", "a1");

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(account, result.getBody());

        doThrow(new RuntimeException()).when(accountService).getAccount("3", "33");
        ResponseEntity<Account> errorResult = accountController.getAccount("3", "33");

        assertNotNull(errorResult);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, errorResult.getStatusCode());
        assertNull(errorResult.getBody());
    }
}
