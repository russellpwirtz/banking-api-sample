package com.russ.bankingapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import com.russ.bankingapi.controller.TransferController;
import com.russ.bankingapi.dto.Account;
import com.russ.bankingapi.dto.Transfer;
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
        TransferController.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransferControllerTest {

    @MockBean
    private AccountService accountService;

    @Autowired
    private TransferController transferController;

    @Test
    public void tranferFundsTest() {
        Account account1 = new Account("1", BigDecimal.ZERO, "DEPOSIT", "USD");
        account1.setAccountId("1a");
        Account account2 = new Account("2", BigDecimal.ZERO, "DEPOSIT", "USD");
        account1.setAccountId("2a");

        Transfer transfer = new Transfer(account1.getCustomerId(), account1.getAccountId(), account2.getCustomerId(), account2.getAccountId(), BigDecimal.ONE);

        doReturn(true).when(accountService).transfer(any(Transfer.class));

        ResponseEntity<String> result = transferController.initiateTransfer(transfer);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("{'success':true}", result.getBody());
    }
}
