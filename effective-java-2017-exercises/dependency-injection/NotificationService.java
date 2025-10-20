


interface MessageSender {
    void send(String message);
}

class EmailSender implements MessageSender {
    private String emailServer;

    public EmailSender(String emailServer) {
        this.emailServer = emailServer;
        System.out.println("EmailSender created with server: " + emailServer);
    }

    @Override
    public void send(String message) {
        System.out.print("Sending email via");
        System.out.print(this.emailServer);
        System.out.print(": " + message);
    }
}

class SmsSender implements MessageSender {
    private String phoneProvider;

    public SmsSender(String phoneProvider) {
        this.phoneProvider = phoneProvider;
        System.out.println("SmsSender created with provider: " + phoneProvider);
    }

    @Override
    public void send(String message) {
        System.out.print("Sending SMS via");
        System.out.print(this.phoneProvider);
        System.out.print(": " + message);
    }
}


class PushNotificationSender implements MessageSender {
    private String appName;

    public PushNotificationSender(String appName) {
        this.appName = appName;
        System.out.println("PushNotificationSender created for app: " + appName);
    }

    @Override
    public void send(String message) {
        System.out.print("Sending push notification via");
        System.out.print(this.appName);
        System.out.print(": " + message);
    }
}


class NotificationService {
    private final MessageSender messageSender;  // Dependency

    // Constructor injection - the dependency is injected!
    public NotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
        System.out.println("Notification Service created");
    }

    public void notifyUser(String username, String message) {
        var messageToSend = String.format("Hello %s: %s", username,message);
        this.messageSender.send(messageToSend);
    }

    public void notifyAllUsers(String message) {
        this.notifyUser("user1", message);
        this.notifyUser("user2", message);
        this.notifyUser("user3", message);
    }
}

class NotificationDemo {
    public static void main(String[] args) {
        System.out.println("=== NOTIFICATION SYSTEM - DEPENDENCY INJECTION ===\n");

        // TEST 1: Email notifications
        System.out.println(">>> TEST 1: Email Notifications <<<");
        EmailSender gmail = new EmailSender("smtp.gmail.com");
        NotificationService notifs = new NotificationService(gmail);
        notifs.notifyUser("Alice", "Your order has shipped!");
        // TODO: Create an EmailSender with server "smtp.gmail.com"


        // TEST 2: SMS notifications (same service, different dependency!)
        System.out.println("\n>>> TEST 2: SMS Notifications <<<");
        SmsSender sms = new SmsSender("Verizon");
        NotificationService smsNotifs = new NotificationService(sms);
        smsNotifs.notifyUser("Bob", "Your verification code is 123456");



        // TEST 3: Push notifications
        System.out.println("\n>>> TEST 3: Push Notifications <<<");
        PushNotificationSender app = new PushNotificationSender("MyApp");
        NotificationService appNotif = new NotificationService(app);
        appNotif.notifyAllUsers("New feature available!");


        // TEST 4: Demonstrate flexibility
        System.out.println("\n>>> TEST 4: Switching Dependencies <<<");
        System.out.println("Notice: We changed the message sender WITHOUT changing NotificationService code!");
        System.out.println("This is the power of dependency injection!");

        System.out.println("\n=== TEST COMPLETE ===");
    }
}