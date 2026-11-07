public class SendMessageCommand implements Command {
    private final Message message;

    @Override
    public void execute() {
        DatabaseOperations.saveMessage(message);
    }

    SendMessageCommand(Message message) {
        this.message = message;
    }
}
