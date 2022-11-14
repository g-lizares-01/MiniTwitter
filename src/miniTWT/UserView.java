/*
 * User View UI for MiniTwitter Project.
 * Functions include:
 * 		Following users
 * 		Displaying a list of users followed
 * 		Posting tweets
 * 		Displaying a feed of a user's own tweets, as well as 
 * 			the tweets of those the user is following
 */

package miniTWT;

import java.awt.*;
import java.awt.event.*;

import java.util.HashMap;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class UserView extends JFrame {
	private JPanel panel;
	private JLabel followingLabel, feedLabel;
	private JTextField userToFollow, writeTweet;
	private JButton followUser, postTweet;
	private JScrollPane scrollFollowing, scrollFeed;
	private JList<String> currentFollowing;
	private JList<String> newsFeed;
	private DefaultListModel<String> followingLM; 
	private DefaultListModel<String> feedLM;
	private String currentUserId, targetId, tweetContent, tweetText;
	private User currentUser;//, targetUser;
	private HashMap<String, User> allUsers = AdminControlPanel.getAllUsers();
	
	Font buttonFont = new Font("monospaced", Font.BOLD, 18);
	
	Color darkGray = new Color(38, 38, 38);
	Color lightBlue = new Color(175, 215, 250);
	Color darkBlue = new Color(5, 51, 92);
	
	public UserView(User u) {
		currentUser = u.getUser();
		
		setTitle(u.getComponentId().toString() + "'s User View");
		setSize(450, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(darkGray);
		setContentPane(panel);
		
		userToFollow = new JTextField();
		userToFollow.setBounds(10, 10, 270, 40);
		userToFollow.setFont(buttonFont);
		userToFollow.setToolTipText("Enter the name of the User you want to follow.");
		panel.add(userToFollow);
		
		followUser = new JButton("Follow");
		followUser.setBounds(290, 10, 135, 40);
		followUser.setFont(buttonFont);
		followUser.setForeground(darkBlue);
		followUser.addActionListener(new followListener());
		followUser.setFocusable(false);
		panel.add(followUser);
		
		followingLabel = new JLabel("Users you are following:");
		followingLabel.setFont(buttonFont);
		followingLabel.setBounds(10, 30, 415, 60);
		followingLabel.setForeground(lightBlue);
		panel.add(followingLabel);
		
		followingLM = new DefaultListModel<String>();
		if(!currentUser.getFollowing().isEmpty()) 
			for(int i = 0; i < currentUser.getFollowing().size(); i++) 
				followingLM.add(0, currentUser.getFollowing().get(i).getComponentId());
		currentFollowing = new JList<String>(followingLM);
		currentFollowing.setBounds(10, 75, 415, 170);
		currentFollowing.setBackground(lightBlue);
		currentFollowing.setFont(buttonFont);
		
		scrollFollowing = new JScrollPane(currentFollowing, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollFollowing.setBounds(10, 75, 415, 170);
		panel.add(scrollFollowing);
		
		writeTweet = new JTextField();
		writeTweet.setBounds(10, 255, 270, 40);
		writeTweet.setFont(buttonFont);
		writeTweet.setToolTipText("Tweet something to your followers!");
		panel.add(writeTweet);
		
		postTweet = new JButton("Tweet");
		postTweet.setBounds(290, 255, 135, 40);
		postTweet.setFont(buttonFont);
		postTweet.setForeground(darkBlue);
		postTweet.addActionListener(new TweetListener());
		postTweet.setFocusable(false);
		panel.add(postTweet);
		
		feedLabel = new JLabel("Your news feed:");
		feedLabel.setFont(buttonFont);
		feedLabel.setForeground(lightBlue);
		feedLabel.setBounds(10, 275, 415, 60);
		panel.add(feedLabel);
		
		feedLM = new DefaultListModel<String>();
		if(!currentUser.getFeed().isEmpty()) 
			for(int i = 0; i < currentUser.getFeed().size(); i++) 
				feedLM.add(0, currentUser.getFeed().get(i));
		newsFeed = new JList<String>(feedLM);
		newsFeed.setBounds(10, 320, 415, 230);
		newsFeed.setBackground(lightBlue);
		newsFeed.setFont(buttonFont);
		
		scrollFeed = new JScrollPane(newsFeed, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollFeed.setBounds(10, 320, 415, 230);
		panel.add(scrollFeed);
	}
	
	//Allows easy creation of pop up messages
	private void popUp(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	private void buildFollowingList() {
		followingLM.removeAllElements();
		for(int i = 0; i < currentUser.getFollowing().size(); i++) 
			followingLM.add(0, currentUser.getFollowing().get(i).getComponentId());
		currentFollowing = new JList<String>(followingLM);
		currentFollowing.setBounds(10, 75, 415, 170);
		currentFollowing.setBackground(lightBlue);
		currentFollowing.setFont(buttonFont);
		
		scrollFollowing = new JScrollPane(currentFollowing, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollFollowing.setBounds(10, 75, 415, 170);
		panel.add(scrollFollowing);

	}
	
	//ActionListener for Follow Button
	private class followListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			currentUserId = currentUser.getComponentId();
			targetId = userToFollow.getText();
			if(targetId.isBlank())
				popUp("Please enter a User's name to follow them.");
			else if(!allUsers.containsKey(targetId))
				popUp("This user does not exist.\nPlease try again.");
			else if(targetId.equals(currentUserId))
				popUp("You can't follow yourself!\nPlease try again.");
			else if(currentUser.getFollowing().contains(allUsers.get(targetId)))
				popUp("You are already following this user!\nPlease try again.");
			else {
				currentUser.addFollowing(allUsers.get(targetId));
				buildFollowingList();
				userToFollow.setText(null);
			}
			
		}
		
	}
	
	public void buildNewsFeed() {
		feedLM.removeAllElements();

		for(int i = 0; i < currentUser.getFeed().size(); i++) 
			feedLM.add(0, currentUser.getFeed().get(i));
	
		newsFeed = new JList<String>(feedLM);
		newsFeed.setBounds(10, 320, 415, 230);
		newsFeed.setBackground(lightBlue);
		newsFeed.setFont(buttonFont);
		
		scrollFeed = new JScrollPane(newsFeed, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollFeed.setBounds(10, 320, 415, 230);
		panel.add(scrollFeed);
	}

	//ActionListener for Tweet Button
	private class TweetListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			tweetContent = writeTweet.getText();
			if(tweetContent.isBlank())
				popUp("Tweet content cannot be empty! Enter a message\n"
						+ "to share it with your followers!");
			else {
				currentUser.addMyTweets(tweetContent);
				currentUser.setLatestTweet();
				tweetText = currentUser.formatLatestTweet();
				currentUser.updateObservers(tweetText);
				
				Visitor newTweet = new TweetCountVisitor();
				currentUser.accept(newTweet);
				
				Visitor positiveTweet = new PositiveTweetPercentVisitor();
				currentUser.accept(positiveTweet);
				
				buildNewsFeed();
				
				for(User f : currentUser.getFollowers()) 
					f.buildNewsFeed();

				
				writeTweet.setText(null);
			}
			
		}
		
	}
	
}
