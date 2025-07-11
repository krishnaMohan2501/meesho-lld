package com.mc.lld;

import com.mc.lld.service.PaymentProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles prepaid payment processing with validation and logging.
 */
public class PrepaidPayment implements PaymentProcessor {
    private static final double MINIMUM_PAYMENT_AMOUNT = 0.01;
    private static final Logger logger = LoggerFactory.getLogger(PrepaidPayment.class);

    /**
     * Processes a prepaid payment after validating the amount.
     * @param value the payment amount
     * @return true if payment is processed successfully, false otherwise
     */
    @Override
    public boolean processPayment(Double value) {
        try {
            validatePayment(value);
            logger.info("Processing prepaid payment of amount: {}", value);
            // Add actual payment processing logic here
            logger.info("Successfully processed prepaid payment of amount: {}", value);
            return true;
        } catch (IllegalArgumentException e) {
            logger.error("Failed to process prepaid payment: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Unexpected error during prepaid payment processing", e);
            return false;
        }
    }

    /**
     * Validates the payment amount.
     * @param value the payment amount
     */
    private void validatePayment(Double value) {
        if (value == null) {
            throw new IllegalArgumentException("Payment amount cannot be null");
        }
        if (value < MINIMUM_PAYMENT_AMOUNT) {
            throw new IllegalArgumentException("Payment amount must be greater than " + MINIMUM_PAYMENT_AMOUNT);
        }
    }
}
