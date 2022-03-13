package com.russ.bankingapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

  private String fromCustomerId;
  private String fromAccountId;
  private String toCustomerId;
  private String toAccountId;
  private BigDecimal amount;
  private LocalDateTime timestamp;

  public Transaction(String fromCustomerId, String fromAccountId, String toCustomerId, String toAccountId, BigDecimal amount,
      LocalDateTime timestamp) {
    this.fromCustomerId = fromCustomerId;
    this.fromAccountId = fromAccountId;
    this.toCustomerId = toCustomerId;
    this.toAccountId = toAccountId;
    this.amount = amount;
    this.timestamp = timestamp;
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

  public String getToAccountId() {
    return toAccountId;
  }

  public void setToAccountId(String toAccountId) {
    this.toAccountId = toAccountId;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }
}

