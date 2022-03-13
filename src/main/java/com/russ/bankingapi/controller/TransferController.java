package com.russ.bankingapi.controller;

import com.russ.bankingapi.dto.Transaction;
import com.russ.bankingapi.dto.Transfer;
import com.russ.bankingapi.service.AccountService;
import java.util.List;
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
@RequestMapping("/transfers")
public class TransferController {

  Logger logger = LoggerFactory.getLogger(TransferController.class);

  private final AccountService accountService;

  @Autowired
  public TransferController(AccountService service) {
    this.accountService = service;
  }

  @PostMapping()
  public ResponseEntity<String> initiateTransfer(@RequestBody Transfer transfer) {
    try {
      Boolean res = accountService.transfer(transfer);
      return ResponseEntity.ok("{'success':true}");
    } catch (IllegalArgumentException e) {
      logger.error("Couldn't transfer funds", e);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{'success':false,'error':'" + e.getMessage() + "'}");
    } catch (Exception e) {
      logger.error("Couldn't transfer funds", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  @GetMapping()
  public ResponseEntity<List<Transaction>> listTransfers(@RequestParam String customerId, @RequestParam String accountId) {
    try {
      List<Transaction> transactions = accountService.listTransactions(customerId, accountId);

      if (transactions == null) {
        logger.error("Couldn't find transactions for {}, {}", customerId, accountId);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
      }

      return ResponseEntity.ok(transactions);
    } catch (Exception e) {
      logger.error("Couldn't list transactions: ", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }
}
