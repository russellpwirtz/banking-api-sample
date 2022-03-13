package com.russ.bankingapi.controller;

import com.russ.bankingapi.dto.Account;
import com.russ.bankingapi.dto.Balance;
import com.russ.bankingapi.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/balances")
public class BalanceController {

  Logger logger = LoggerFactory.getLogger(BalanceController.class);

  private final AccountService accountService;

  @Autowired
  public BalanceController(AccountService service) {
    this.accountService = service;
  }

  @GetMapping()
  public ResponseEntity<Balance> getBalance(@RequestParam String customerId, @RequestParam String accountId) throws IllegalArgumentException {
    try {
      Account account = accountService.getAccount(customerId, accountId);

      if (account == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      Balance balance = new Balance(account.getBalance(), account.getCurrency());
      return ResponseEntity.ok(balance);
    } catch (Exception e) {
      logger.error("Couldn't get balance: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
