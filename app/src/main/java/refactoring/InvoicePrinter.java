package refactoring;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class InvoicePrinter {
    public String statement(Invoice invoice, Map<String, Play> plays) throws Exception {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "\nStatement for " + invoice.customer() + "\n";
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (Performance perf : invoice.performances()) {
            Play play = plays.get(perf.playID());
            int thisAmount = 0;
            switch (play.type()) {
                case "tragedy":
                    thisAmount = 40000;
                    if (perf.audience() > 30) {
                        thisAmount += 1000 * (perf.audience() - 30); }
                    break;
                case "comedy":
                    thisAmount = 30000;
                    if (perf.audience() > 20) {
                        thisAmount += 10000 + 500 * (perf.audience() - 20); }
                    thisAmount += 300 * perf.audience();
                    break;
                default:
                    throw new Exception("unknown type: " + play.type());
            }
            volumeCredits += Math.max(perf.audience() - 30, 0);
            if ("comedy" == play.type()) {
                volumeCredits += Math.floor(perf.audience() / 5);
            }
            result += "  " + play.name() + ": " + formatter.format(thisAmount/100) + " (" + perf.audience() + " seats)\n";
            totalAmount += thisAmount;
        }
        result += "Amount owed is " + formatter.format(totalAmount/100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";
        return result;
    }
}

class Invoice {
    private String customer;
    private Performance[] performances;

    public Invoice(String customer, Performance[] performances) {
        this.customer = customer;
        this.performances = performances;
    }

    public String customer() {
        return customer;
    }

    public Performance[] performances() {
        return performances;
    }
}

class Performance {
    private String playID;
    private int audience;

    public Performance(String playID, int audience) {
        this.playID = playID;
        this.audience = audience;
    }

    public String playID() {
        return playID;
    }

    public int audience() {
        return audience;
    }
}

class Play {
    private String name;
    private String type;

    public Play(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String type() {
        return type;
    }

    public String name() {
        return name;
    }
}
