package com.russ.bankingapi.service;

import com.russ.bankingapi.dto.Account;
import com.russ.bankingapi.dto.Transaction;
import com.russ.bankingapi.dto.Transfer;
import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

  Account createAccount(Account account);

  Account getAccount(String userId, String accountId);

  Boolean transfer(Transfer transfer) throws IllegalArgumentException;

  List<Transaction> listTransactions(String customerId, String accountId) throws IllegalArgumentException;
}
