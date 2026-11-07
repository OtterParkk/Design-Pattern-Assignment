import java.util.Stack;
import java.util.Scanner;

private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

void main() {
    DatabaseOperations.initTables();
    Scanner scanner = new Scanner(System.in);
    Stack<SendMessageCommand> msgStack = new Stack<>();

    while (true) {
        System.out.println("\n=== Test Program ===");
        System.out.println("1. Write message");
        System.out.println("2. Send message");
        System.out.println("3. Send all messages");
        System.out.println("4. Delete message");
        System.out.println("5. Delete all messages");
        System.out.println("Anything else: End program");

        String choice = scanner.nextLine().trim();
        switch (choice) {
            case "1":
                msgStack.push(makeCommand(scanner));
                break;
            case "2":
                if (!msgStack.isEmpty()) {
                    msgStack.peek().execute();
                }
                break;
            case "3":
                if (!msgStack.isEmpty()) {
                    while (!msgStack.isEmpty()) {
                        msgStack.pop().execute();
                    }
                }
                break;
            case "4":
                if (!msgStack.isEmpty()) {
                    msgStack.pop();
                }
                break;
            case "5":
                if (!msgStack.isEmpty()) {
                    while (!msgStack.isEmpty()) {
                        msgStack.pop();
                    }
                }
            default:
                return;
        }
    }
}

private static SendMessageCommand makeCommand(Scanner scanner) {
    System.out.println("\n--- Send Message ---");
    System.out.print("Recipient username: ");
    String recipient = scanner.nextLine().trim();

    if (DatabaseOperations.findUser(recipient) == null) {
        System.out.println("Error: Recipient '" + recipient + "' does not exist.");
        throw new IllegalArgumentException("Error: Recipient '" + recipient + "' does not exist.");
    }
    System.out.print("Enter your message: ");
    String content = scanner.nextLine();

    String timestamp = LocalDateTime.now().format(DATE_FORMATTER);
    Message message = new Message(recipient, "test", timestamp, content);

    return new SendMessageCommand(message);
}
