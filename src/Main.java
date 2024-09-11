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
                    buyTicket(scanner);
                    break;
                case 3:
                    displayPurchasedTickets();
                    break;
                case 4:
                    System.out.println("Thank you for using the Ticketing System!");
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
        System.out.println("2. Buy Ticket");
        System.out.println("3. View Purchased Tickets");
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

private static void buyTicket(Scanner scanner) {
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

            System.out.print("How many tickets do you want to buy? ");
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
                System.out.println("Tickets purchased successfully!");
                for (int i = tickets.size() - quantity; i < tickets.size(); i++) {
                    tickets.get(i).printTicketDetails();
                }
            } else {
                System.out.println("Failed to purchase tickets. Not enough available.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void displayPurchasedTickets() {
        if (tickets.isEmpty()) {
            System.out.println("No tickets purchased yet.");
        } else {
            System.out.println("\nPurchased Tickets:");
            for (Ticket ticket : tickets) {
                ticket.printTicketDetails();
                System.out.println();
            }
        }
    }
}