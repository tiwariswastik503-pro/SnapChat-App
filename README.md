🧰 1. Prerequisites


Before running the project, make sure you have:

Java Development Kit (JDK 8 or higher) installed
→ You can check by running:

java -version


A code editor or IDE (e.g., IntelliJ IDEA, Eclipse, or VS Code with Java Extension)

Basic knowledge of Java and using the command line.

📂 2. Project Setup


Create a new Java project

Open your IDE → Create a new Java project named MiniSnap.

Create a single file (or multiple files if you prefer):

Create a new file named Snapchat.java

Copy and paste the entire provided code into this file.

(Optional) If using multiple files, split into these classes:

Snap.java

Story.java

User.java

Snapchat.java (contains main())

Make sure all files are in the same package or folder.

▶️ 3. How to Run the Program


Option 1: Using an IDE

Open the project in your IDE.

Locate the class Snapchat that contains the main method.

Click Run ▶️ on the main class.

Option 2: Using Command Line

Save the code in a file named Snapchat.java.

Open a terminal and navigate to the folder where it’s saved.

Compile the code:

javac Snapchat.java


Run the program:

java Snapchat

💡 4. How to Use MiniSnap


Once the program starts, you’ll see a menu like this:

===== Mini Snapchat =====
1. Create Account
2. Login
3. Logout
4. Add Friend
5. Accept Friend Requests
6. Send Snap
7. View Snaps
8. Post Story
9. View Friends' Stories
10. View Friends
11. Edit Bio
12. Exit
Choose:


Here’s what each option does:

Option	Description
1	Create Account – Register a new user with a username and password.
2	Login – Log in using your existing account.
3	Logout – Log out of the current account.
4	Add Friend – Send a friend request to another user.
5	Accept Friend Requests – View and accept incoming requests.
6	Send Snap – Send a message (snap) to a friend.
7	View Snaps – Open received snaps (auto-deletes after viewing).
8	Post Story – Share a story visible to your friends for 24 hours.
9	View Friends' Stories – View stories from your friends.
10	View Friends – See your list of friends.
11	Edit Bio – Update your personal bio.
12	Exit – Quit the program.


⏱️ 5. Expiry System


Snaps: Auto-delete after 2 minutes or immediately after viewing.

Stories: Automatically expire after 24 hours.

🧪 6. Example Run


===== Mini Snapchat =====
1. Create Account
Choose: 1
Enter username: alice
Enter password: 123
Account created for alice

===== Mini Snapchat =====
2. Login
Choose: 2
Username: alice
Password: 123
Welcome back, alice!

Logged in as: alice
4. Add Friend
Choose: 4
Enter username to send request: bob
Friend request sent to bob
