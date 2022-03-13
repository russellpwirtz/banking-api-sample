package com.russ.bankingapi.controller;

import com.russ.bankingapi.dto.Account;
import com.russ.bankingapi.service.AccountService;
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
@RequestMapping("/accounts")
public class AccountController {

  Logger logger = LoggerFactory.getLogger(AccountController.class);

  private final AccountService accountService;

  @Autowired
  public AccountController(AccountService service) {
    this.accountService = service;
  }

  @PostMapping()
  public ResponseEntity<Account> createAccount(@RequestBody Account account) {
    try {
      account = accountService.createAccount(account);

      return ResponseEntity.ok(account);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping()
  public ResponseEntity<Account> getAccount(@RequestParam String userId, @RequestParam String accountId) throws IllegalArgumentException {
    try {
      Account account = accountService.getAccount(userId, accountId);

      if (account == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      return ResponseEntity.ok(account);
    } catch (Exception e) {
      logger.error("Couldn't get account: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
