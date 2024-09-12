import java.util.Random;

public class Ticket {
    private String ticketId;
    private Event event;
    private String row;
    private double price;

    public Ticket(Event event, String row) {
        this.event = event;
        this.row = row;
        this.price = row.equals("A") ? event.getPriceRowA() : event.getPriceRowB();
        this.ticketId = generateTicketId();
    }

    // Generate random ticket ID
    private String generateTicketId() {
        Random random = new Random();
        int randomNum = random.nextInt(9000) + 1000; // Generates a 4-digit random number (1000-9999)
        return "T" + randomNum; // Prefix 'T' to the random number
    }

    public void printTicketDetails() {
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Event: " + event.getName());
        System.out.println("Row: " + row);
        System.out.println("Price: " + price + " IDR");
    }
    public String getTicketId() {
        return ticketId;
    }
    public Event getEvent() {
        return event; // Return the associated event
    }

    // Getter for row
    public String getRow() {
        return row;
    }
}
