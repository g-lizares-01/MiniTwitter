# MiniTwitter
CS 3560 Project 2: Mini Twitter
A desktop, Java-Based Mini Twitter program with GUI created using Java Swing.

This program implements 4 different design patterns: Singleton, Composite, Observer, and Visitor.

The Admin Control Panel allows you to create new Users and User Groups. There is one button that, when clicked after selecting a User from the tree, will open the specified User's "User View," or their profile page. There are 4 buttons to display 1.) The total number of Users created, 2.) The total number of Groups created, 3.) The total number of Tweets (messages) posted by users, and 4.) The percentage of Tweets that contain positive words.

Each User's User View allows them to follow other existing Users, as well as post a Tweet that will be visible to all their current followers. Tweets from followed Users are only displayed if posted AFTER you have followed them.
