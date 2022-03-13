package com.russ.bankingapi.service;

import com.russ.bankingapi.dto.Account;
import com.russ.bankingapi.dto.Transaction;
import com.russ.bankingapi.dto.Transfer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

  private static final String INITIAL_BALANCE = "INITIAL_BALANCE";

  // TODO: implement database backing instead of in-mem
  private final Map<String, Account> accounts;
  private final Map<String, List<Transaction>> transactions;

  public AccountServiceImpl() {
    this.accounts = new HashMap<>();
    this.transactions = new HashMap<>();
  }

  @Override
  public Account createAccount(Account account) {
    String accountId = generateAccountId();

    while (accounts.get(getAccountKey(account.getCustomerId(), accountId)) != null) {
      accountId = generateAccountId();
    }

    account.setAccountId(accountId);

    final String accountKey = getAccountKey(account.getCustomerId(), accountId);

    accounts.put(accountKey, account);
    transactions.put(accountKey, List.of(
        new Transaction(INITIAL_BALANCE, INITIAL_BALANCE, account.getCustomerId(), account.getAccountId(), account.getBalance(),
            LocalDateTime.now(ZoneOffset.UTC))));

    return account;
  }

  @Override
  public Account getAccount(String userId, String accountId) {
    return accounts.get(getAccountKey(userId, accountId));
  }

  @Override
  public Boolean transfer(Transfer transfer) throws IllegalArgumentException {
    final String fromAccountKey = getAccountKey(transfer.getFromCustomerId(), transfer.getFromAccountId());
    final String toAccountKey = getAccountKey(transfer.getToCustomerId(), transfer.getToAccountId());

    final Account fromAccount = accounts.get(fromAccountKey);
    final Account toAccount = accounts.get(toAccountKey);

    if (fromAccount == null) {
      throw new IllegalArgumentException("Source account not found");
    }
    if (toAccount == null) {
      throw new IllegalArgumentException("Destination account not found");
    }

    synchronized (fromAccountKey) {
      if (fromAccount.getBalance().compareTo(transfer.getAmount()) <= 0) {
        LOGGER.warn("Couldn't transfer; not enough funds");
        throw new IllegalArgumentException("Insufficient funds");
      }

      fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.getAmount()));
      toAccount.setBalance(toAccount.getBalance().add(transfer.getAmount()));

      final List<Transaction> fromTransactions = new ArrayList<>(transactions.get(fromAccountKey));
      fromTransactions.add(
          new Transaction(fromAccount.getCustomerId(), fromAccount.getAccountId(), toAccount.getCustomerId(), toAccount.getAccountId(),
              transfer.getAmount().negate(), LocalDateTime.now(ZoneOffset.UTC)));

      final List<Transaction> toTransactions = new ArrayList<>(transactions.get(toAccountKey));
      toTransactions.add(new Transaction(fromAccount.getCustomerId(), fromAccount.getAccountId(), toAccount.getCustomerId(), toAccount.getAccountId(),
          transfer.getAmount(), LocalDateTime.now(ZoneOffset.UTC)));

      transactions.put(fromAccountKey, fromTransactions);
      transactions.put(toAccountKey, toTransactions);
    }

    return true;
  }

  @Override
  public List<Transaction> listTransactions(String customerId, String accountId) throws IllegalArgumentException {
    return transactions.get(getAccountKey(customerId, accountId));
  }

  private String generateAccountId() {
    return UUID.randomUUID().toString();
  }

  private String getAccountKey(String customerId, String accountId) {
    return customerId + ":" + accountId;
  }
}
