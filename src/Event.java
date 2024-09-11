import java.util.ArrayList;

public class Event {
    private String name;
    private double priceRowA;
    private double priceRowB;
    private ArrayList<Integer> availableTicketsPerRow;
    private static final int MAX_TICKETS_PER_ROW = 50; // Assuming 50 tickets per row

    public Event(String name, double priceRowA, double priceRowB) {
        this.name = name;
        this.priceRowA = priceRowA;
        this.priceRowB = priceRowB;
        availableTicketsPerRow = new ArrayList<>();
        availableTicketsPerRow.add(MAX_TICKETS_PER_ROW); // Row A tickets
        availableTicketsPerRow.add(MAX_TICKETS_PER_ROW); // Row B tickets
    }

    public String getName() { return name; }
    public double getPriceRowA() { return priceRowA; }
    public double getPriceRowB() { return priceRowB; }

    public int getAvailableTickets(int rowIndex) {
        return availableTicketsPerRow.get(rowIndex);
    }

    public boolean sellTickets(int rowIndex, int quantity) {
        if (rowIndex >= 0 && rowIndex < availableTicketsPerRow.size()) {
            int availableTickets = availableTicketsPerRow.get(rowIndex);
            if (availableTickets >= quantity) {
                availableTicketsPerRow.set(rowIndex, availableTickets - quantity); // Reduce available tickets
                return true;
            } else {
                System.out.println("Not enough tickets available in this row.");
            }
        } else {
            System.out.println("Invalid row selection.");
        }
        return false;
    }

    public String getEventDetails() {
        return name + " - Row A: " + priceRowA + " IDR, Row B: " + priceRowB + " IDR (Available Row A: "
                + availableTicketsPerRow.get(0) + ", Row B: " + availableTicketsPerRow.get(1) + ")";
    }
}
