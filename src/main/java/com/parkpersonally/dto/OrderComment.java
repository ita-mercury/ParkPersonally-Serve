package com.parkpersonally.dto;

import java.security.PrivateKey;

public class OrderComment {
     private long orderId;
     private String comment;

     public OrderComment(long orderId, String comment) {
          this.orderId = orderId;
          this.comment = comment;
     }

     public OrderComment() {
     }

     public long getOrderId() {
          return orderId;
     }

     public void setOrderId(long orderId) {
          this.orderId = orderId;
     }

     public String getComment() {
          return comment;
     }

     public void setComment(String comment) {
          this.comment = comment;
     }
}
