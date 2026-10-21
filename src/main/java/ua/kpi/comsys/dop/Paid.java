package ua.kpi.comsys.dop;

import java.math.BigDecimal;

public record Paid(String paymentID, BigDecimal amount)
        implements OrderStatus {

    public Paid {
        if (paymentID == null || paymentID.isBlank()) {
            throw new IllegalArgumentException("Invalid payment ID");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
    }
}
