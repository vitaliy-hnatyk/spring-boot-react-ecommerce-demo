package com.ujjaval.ecommerce.paymentservice.controller;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.ujjaval.ecommerce.paymentservice.dto.CardToken;
import com.ujjaval.ecommerce.paymentservice.dto.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PaymentController {

    @Autowired
    private Environment env;

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("success");
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentStatus> chargeCustomer(@RequestBody CardToken cardToken) {

        Stripe.apiKey = env.getProperty("STRIPE_SECRET_KEY");
        Stripe.setMaxNetworkRetries(2);

        Charge charge;
        PaymentStatus paymentStatus;
        ChargeCreateParams params =
                ChargeCreateParams.builder()
                        .setAmount(cardToken.amount())
                        .setCurrency(cardToken.currency())
                        .setDescription("Shopper Buy")
                        .setSource(cardToken.id())
                        .build();

        try {
            charge = Charge.create(params);


            System.out.println("Charge = " + charge);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            paymentStatus = new PaymentStatus(timestamp.getTime(), false,
                    charge.getId(),
                    charge.getBalanceTransaction(),
                    charge.getReceiptUrl()
                    );

        } catch (Exception e) {
           // paymentStatus = new PaymentStatus();
           // paymentStatus.setPayment_failed(true);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("Something went wrong with Stripe API");
            return ResponseEntity.badRequest().body(new PaymentStatus(timestamp.getTime(), true,
                    "",
                    "charge.getBalanceTransaction()",
                    "charge.getReceiptUrl()"
            ));
        }

        System.out.println("Payment is successful....");
        return ResponseEntity.ok(paymentStatus);
    }
}
