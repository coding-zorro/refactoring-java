package refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class InvoicePrinterTest {
    private static Map<String, Play> plays = new HashMap<>();
    private static Invoice invoice;

    @BeforeAll
    static void buildPlaysAndInvoices() {
        plays.put("hamlet", new Play("Hamlet", "tragedy"));
        plays.put("as-like", new Play("As You Like It", "comedy"));
        plays.put("othello", new Play("Othello", "tragedy"));

        Performance[] performances = new Performance[] {
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)
        };
        invoice = new Invoice("BigCo", performances);
    }

    @Test
    void testStatement() throws Exception {
        String statement = "\nStatement for BigCo\n" +
                "  Hamlet: $650.00 (55 seats)\n" +
                "  As You Like It: $580.00 (35 seats)\n" +
                "  Othello: $500.00 (40 seats)\n" +
                "Amount owed is $1,730.00\n" +
                "You earned 47 credits\n";
        assertEquals(statement, new InvoicePrinter().statement(invoice, plays));
    }
}
