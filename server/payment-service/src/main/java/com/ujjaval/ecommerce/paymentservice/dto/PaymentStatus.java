package com.ujjaval.ecommerce.paymentservice.dto;


public record PaymentStatus (Long order_id, boolean payment_failed, String charge_id, String txn_id, String receipt_url) {
}