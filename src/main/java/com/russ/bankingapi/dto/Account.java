package com.russ.bankingapi.dto;

import java.math.BigDecimal;

public class Account {

  private String accountId;
  private String customerId;
  private BigDecimal balance;
  private String accountType;
  private String currency;

  public Account(String customerId, BigDecimal balance, String accountType, String currency) {
    this.customerId = customerId;
    this.balance = balance;
    this.accountType = accountType;
    this.currency = currency;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public String getAccountType() {
    return accountType;
  }

  public void setAccountType(String accountType) {
    this.accountType = accountType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }
}

