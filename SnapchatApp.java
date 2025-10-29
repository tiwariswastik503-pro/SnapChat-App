import java.util.*;

class Snap {
    private String sender;
    private String receiver;
    private String content;
    private boolean isViewed;
    private Date timestamp;
    private static final long EXPIRY_TIME_MS = 2 * 60 * 1000; // 2 minutes

    public Snap(String sender, String receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.isViewed = false;
        this.timestamp = new Date();
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public boolean isViewed() {
        return isViewed;
    }

    public void markViewed() {
        this.isViewed = true;
    }

    public String getFormattedTimestamp() {
        return timestamp.toString();
    }

    public boolean isExpired() {
        long now = System.currentTimeMillis();
        return now - timestamp.getTime() > EXPIRY_TIME_MS;
    }
}

class Story {
    private String username;
    private String content;
    private Date timestamp;
    private static final long EXPIRY_TIME_MS = 24 * 60 * 60 * 1000; // 24 hours

    public Story(String username, String content) {
        this.username = username;
        this.content = content;
        this.timestamp = new Date();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - timestamp.getTime() > EXPIRY_TIME_MS;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public String getFormattedTimestamp() {
        return timestamp.toString();
    }
}

class User {
    private String username;
    private String password;
    private String bio = "Hey there! I'm using MiniSnap.";
    private Set<String> friends = new HashSet<>();
    private List<Snap> receivedSnaps = new ArrayList<>();
    private Set<String> pendingRequests = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

    public void sendFriendRequest(User other) {
        other.pendingRequests.add(this.username);
    }

    public Set<String> getPendingRequests() {
        return pendingRequests;
    }

    public void acceptFriend(String friendUsername) {
        friends.add(friendUsername);
        pendingRequests.remove(friendUsername);
    }

    public void addFriendDirect(String friendUsername) {
        friends.add(friendUsername);
    }

    public void removeFriend(String friendUsername) {
        friends.remove(friendUsername);
    }

    public boolean isFriend(String friendUsername) {
        return friends.contains(friendUsername);
    }

    public Set<String> getFriends() {
        return friends;
    }

    public void receiveSnap(Snap snap) {
        receivedSnaps.add(snap);
    }

    public List<Snap> getReceivedSnaps() {
        return receivedSnaps;
    }
}

class Snapchat {
    private Map<String, User> users = new HashMap<>();
    private List<Story> stories = new ArrayList<>();
    private User currentUser = null;
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Snapchat().run();
    }

    public void run() {
        while (true) {
            System.out.println("\n===== Mini Snapchat =====");
            if (currentUser != null) System.out.println("Logged in as: " + currentUser.getUsername());
            System.out.println("1. Create Account");
            System.out.println("2. Login");
            System.out.println("3. Logout");
            System.out.println("4. Add Friend");
            System.out.println("5. Accept Friend Requests");
            System.out.println("6. Send Snap");
            System.out.println("7. View Snaps");
            System.out.println("8. Post Story");
            System.out.println("9. View Friends' Stories");
            System.out.println("10. View Friends");
            System.out.println("11. Edit Bio");
            System.out.println("12. Exit");

            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1": createAccount(); break;
                case "2": login(); break;
                case "3": logout(); break;
                case "4": sendFriendRequest(); break;
                case "5": acceptFriendRequests(); break;
                case "6": sendSnap(); break;
                case "7": viewSnaps(); break;
                case "8": postStory(); break;
                case "9": viewStories(); break;
                case "10": viewFriends(); break;
                case "11": editBio(); break;
                case "12": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    private void createAccount() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
            return;
        }
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.put(username, new User(username, password));
        System.out.println("Account created for " + username);
    }

    private void login() {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        User user = users.get(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.print("Password: ");
        String password = scanner.nextLine();
        if (user.checkPassword(password)) {
            currentUser = user;
            System.out.println("Welcome back, " + username + "!");
        } else {
            System.out.println("Wrong password.");
        }
    }

    private void logout() {
        if (currentUser == null) {
            System.out.println("No user logged in.");
            return;
        }
        currentUser = null;
        System.out.println("Logged out.");
    }

    private void sendFriendRequest() {
        if (!isLoggedIn()) return;
        System.out.print("Enter username to send request: ");
        String friendUsername = scanner.nextLine();
        if (!users.containsKey(friendUsername)) {
            System.out.println("User not found.");
            return;
        }
        if (friendUsername.equals(currentUser.getUsername())) {
            System.out.println("You can't add yourself!");
            return;
        }
        User friend = users.get(friendUsername);
        currentUser.sendFriendRequest(friend);
        System.out.println("Friend request sent to " + friendUsername);
    }

    private void acceptFriendRequests() {
        if (!isLoggedIn()) return;
        Set<String> requests = currentUser.getPendingRequests();
        if (requests.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }
        System.out.println("Pending requests:");
        for (String req : new ArrayList<>(requests)) {
            System.out.print("Accept " + req + "? (y/n): ");
            String ans = scanner.nextLine();
            if (ans.equalsIgnoreCase("y")) {
                currentUser.acceptFriend(req);
                users.get(req).addFriendDirect(currentUser.getUsername());
                System.out.println(req + " is now your friend!");
            }
        }
    }

    private void sendSnap() {
        if (!isLoggedIn()) return;
        System.out.print("Enter friend's username: ");
        String receiver = scanner.nextLine();
        if (!currentUser.isFriend(receiver)) {
            System.out.println("Not in your friend list.");
            return;
        }
        System.out.print("Enter snap content: ");
        String content = scanner.nextLine();
        Snap snap = new Snap(currentUser.getUsername(), receiver, content);
        users.get(receiver).receiveSnap(snap);
        System.out.println("Snap sent to " + receiver);
    }

    private void viewSnaps() {
        if (!isLoggedIn()) return;
        List<Snap> snaps = currentUser.getReceivedSnaps();
        if (snaps.isEmpty()) {
            System.out.println("No new snaps.");
            return;
        }
        Iterator<Snap> it = snaps.iterator();
        while (it.hasNext()) {
            Snap s = it.next();
            if (s.isExpired()) {
                it.remove();
                continue;
            }
            if (!s.isViewed()) {
                System.out.println("\nFrom: " + s.getSender());
                System.out.println("At: " + s.getFormattedTimestamp());
                System.out.println("Content: " + s.getContent());
                s.markViewed();
                it.remove(); // auto-delete after viewing
            }
        }
    }

    private void postStory() {
        if (!isLoggedIn()) return;
        System.out.print("Enter story content: ");
        String content = scanner.nextLine();
        stories.add(new Story(currentUser.getUsername(), content));
        System.out.println("Story posted!");
    }

    private void viewStories() {
        if (!isLoggedIn()) return;
        System.out.println("\n--- Your Friends' Stories ---");
        boolean found = false;
        for (Story s : new ArrayList<>(stories)) {
            if (s.isExpired()) {
                stories.remove(s);
                continue;
            }
            if (currentUser.isFriend(s.getUsername())) {
                System.out.println(s.getUsername() + ": " + s.getContent() + " (" + s.getFormattedTimestamp() + ")");
                found = true;
            }
        }
        if (!found) System.out.println("No stories available.");
    }

    private void viewFriends() {
        if (!isLoggedIn()) return;
        Set<String> friends = currentUser.getFriends();
        if (friends.isEmpty()) System.out.println("You have no friends.");
        else {
            System.out.println("Your friends:");
            for (String f : friends) System.out.println("- " + f);
        }
    }

    private void editBio() {
        if (!isLoggedIn()) return;
        System.out.print("Enter new bio: ");
        String bio = scanner.nextLine();
        currentUser.setBio(bio);
        System.out.println("Bio updated.");
    }

    private boolean isLoggedIn() {
        if (currentUser == null) {
            System.out.println("Please log in first.");
            return false;
        }
        return true;
    }
}
