import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    private static ArrayList<Event> events = new ArrayList<>();
    private static ArrayList<Ticket> tickets = new ArrayList<>();

    public static void main(String[] args) {
        initializeEvents();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    displayEvents();
                    break;
                case 2:
                    bookTicket(scanner);
                    break;
                case 3:
                    displayBookedTickets(scanner); // Pass scanner here
                    break;
                case 4:
                    System.out.println("Thank you for using the Tickets Booking!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }


    private static void initializeEvents() {
        events.add(new Event("Music Festival", 100000, 200000));
        events.add(new Event("Film Festival", 100000, 200000));
    }

    private static void displayMenu() {
        System.out.println("\n1. View Events Available");
        System.out.println("2. Book Ticket");
        System.out.println("3. View Booked Tickets");
        System.out.println("4. Exit");
        System.out.print("Choose an option: ");
    }

    private static int getUserChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static void displayEvents() {
        System.out.println("\nAvailable Events:");
        for (int i = 0; i < events.size(); i++) {
            System.out.println((i + 1) + ". " + events.get(i).getEventDetails());
        }
    }

private static void bookTicket(Scanner scanner) {
        try {
            displayEvents();
            System.out.print("Enter the event number: ");
            int eventChoice = getUserChoice(scanner) - 1;

            if (eventChoice < 0 || eventChoice >= events.size()) {
                System.out.println("Invalid event number.");
                return;
            }

            Event selectedEvent = events.get(eventChoice);

            System.out.print("Choose row (A/B): ");
            String row = scanner.next().toUpperCase();
            int rowIndex = (row.equals("A")) ? 0 : (row.equals("B") ? 1 : -1);

            if (rowIndex == -1) {
                System.out.println("Invalid row choice.");
                return;
            }

            System.out.print("How many tickets do you want to book? ");
            int quantity = scanner.nextInt();

            if (quantity <= 0) {
                System.out.println("Invalid ticket quantity. Please enter a positive number.");
                return;
            }

            if (selectedEvent.sellTickets(rowIndex, quantity)) {
                for (int i = 0; i < quantity; i++) {
                    Ticket ticket = new Ticket(selectedEvent, row);
                    tickets.add(ticket);
                }
                System.out.println("Tickets booked successfully!");
                for (int i = tickets.size() - quantity; i < tickets.size(); i++) {
                    tickets.get(i).printTicketDetails();
                }
            } else {
                System.out.println("Failed to book tickets. Not enough available.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void displayBookedTickets(Scanner scanner) {
        if (tickets.isEmpty()) {
            System.out.println("No tickets booked yet.");
            return;
        }

        System.out.println("\nBooked Tickets:");
        for (Ticket ticket : tickets) {
            ticket.printTicketDetails();
            System.out.println();
        }

        System.out.print("Do you want to cancel any ticket? (yes/no): ");
        String response = scanner.next().toLowerCase();

        if (response.equals("yes")) {
            System.out.print("Enter the ticket ID to cancel: ");
            String ticketIdToCancel = scanner.next().toUpperCase();

            Ticket ticketToCancel = findTicketById(ticketIdToCancel);

            if (ticketToCancel != null) {
                Event event = ticketToCancel.getEvent();
                String row = ticketToCancel.getRow();
                int rowIndex = row.equals("A") ? 0 : 1;

                // Restore the available tickets for the event
                event.restoreTickets(rowIndex, 1);

                // Remove the ticket from the list
                tickets.remove(ticketToCancel);
                System.out.println("Ticket with ID " + ticketIdToCancel + " has been successfully canceled.");
            } else {
                System.out.println("Ticket ID not found.");
            }
        }
    }
    private static Ticket findTicketById(String ticketId) {

        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i); // Get the ticket at index i
            if (ticket.getTicketId().equals(ticketId)) {
                return ticket; // Return the ticket if a match is found
            }
        }
        return null; // Return null if no match is found
    }

}