package ua.kpi.comsys.dop;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderProcessorTest {

    private final OrderProcessor processor = new OrderProcessor();

    @Test
    void testPending() {
        String expected = """
                Order Status: PENDING
                Details: Order is currently pending processing.
                """;

        assertEquals(expected, processor.process(new Pending()));
    }

    @Test
    void testPaid() {
        Paid paid = new Paid("PAY123", new BigDecimal("150.00"));

        String expected = """
                Order Status: PAID
                Details: Payment ID: PAY123, Amount: $150.00
                """;

        assertEquals(expected, processor.process(paid));
    }

    @Test
    void testShipped() {
        Shipped shipped = new Shipped(
                "TRACK123", LocalDate.of(2026, 10, 14));

        String expected = """
                Order Status: SHIPPED
                Details: Tracking Code: TRACK123, Dispatch Date: 2026-10-14
                """;

        assertEquals(expected, processor.process(shipped));
    }

    @Test
    void testCancelled() {
        Cancelled cancelled = new Cancelled("Customer request");

        String expected = """
                Order Status: CANCELLED
                Details: Reason: Customer request
                """;

        assertEquals(expected, processor.process(cancelled));
    }

    @Test
    void testNullStatus() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> processor.process(null));

        assertEquals("Status cannot be null", exception.getMessage());
    }

    @Test
    void testNullPaymentID() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid(null, new BigDecimal("100")));
    }

    @Test
    void testBlankPaymentID() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("   ", new BigDecimal("100")));
    }

    @Test
    void testNullAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("PAY123", null));
    }

    @Test
    void testZeroAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("PAY123", BigDecimal.ZERO));
    }

    @Test
    void testNegativeAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> new Paid("PAY123", new BigDecimal("-50")));
    }

    @Test
    void testNullTrackingCode() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shipped(null, LocalDate.now()));
    }

    @Test
    void testBlankTrackingCode() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shipped("   ", LocalDate.now()));
    }

    @Test
    void testNullDispatchDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new Shipped("TRACK123", null));
    }

    @Test
    void testNullReason() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cancelled(null));
    }

    @Test
    void testBlankReason() {
        assertThrows(IllegalArgumentException.class,
                () -> new Cancelled("   "));
    }
}
