package com.russ.bankingapi.dto;

import java.math.BigDecimal;

public class Transfer {

  private String fromCustomerId;
  private String fromAccountId;
  private String toCustomerId;
  private String toAccountId;
  private BigDecimal amount;

  public Transfer(String fromCustomerId, String fromAccount, String toCustomerId, String toAccountId, BigDecimal amount) {
    this.fromCustomerId = fromCustomerId;
    this.fromAccountId = fromAccount;
    this.toCustomerId = toCustomerId;
    this.toAccountId = toAccountId;
    this.amount = amount;
  }

  public String getFromCustomerId() {
    return fromCustomerId;
  }

  public void setFromCustomerId(String fromCustomerId) {
    this.fromCustomerId = fromCustomerId;
  }

  public String getFromAccountId() {
    return fromAccountId;
  }

  public void setFromAccountId(String fromAccountId) {
    this.fromAccountId = fromAccountId;
  }

  public String getToCustomerId() {
    return toCustomerId;
  }

  public void setToCustomerId(String toCustomerId) {
    this.toCustomerId = toCustomerId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(String toAccountId) {
    this.toAccountId = toAccountId;
  }
}

