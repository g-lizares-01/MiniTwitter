/* 
 * Admin Control Panel UI for MiniTwitter Project.
 * Functions include:
 * 		Adding Users and Groups to the tree
 * 		Opening User View window for selected Users
 * 		Displaying values for
 * 			-total Users created
 * 			-total Groups created
 * 			-total Tweets posted
 * 			-overall percentage of positive Tweets
 */

package miniTWT;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.tree.*;

@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame {
	private JPanel panel;
	private JTree tree;
	private JScrollPane scrollTree;
	private JTextField newUserId, newGroupId;
	private JButton addUser, addGroup, openUserView;
	private JButton showUserTotal, showGroupTotal, showMsgTotal, showPstvPercent, userGroupVerify, lastUpdated;
	private DefaultMutableTreeNode root, currentTreeNode;
	private String userId, groupId;
	private static HashMap<String, User> allUsers = new HashMap<String, User>();
	private static HashMap<String, Group> allGroups = new HashMap<String, Group>();
	
	//declare text formatting tools for easier access
	Font topFont = new Font("monospaced", Font.BOLD, 18);
	Font bottomFont = new Font("monospaced", Font.BOLD, 12);
	
	Color darkGray = new Color(38, 38, 38);
	Color lightPurple = new Color(214, 185, 250);
	Color darkPurple = new Color(103, 61, 156);
	
	
	//Singleton pattern implementation
	private static AdminControlPanel instance = null;

	public static AdminControlPanel getInstance() {
		if(instance == null) 
			instance = new AdminControlPanel();
		
		return instance;
	}
	
	//Allows easier checking for existence of User and Group IDs
	public static HashMap<String, User> getAllUsers() {
		return allUsers;
	}
	
	public static HashMap<String, Group> getAllGroups() {
		return allGroups;
	}
	
	//layout the admin control panel
	private AdminControlPanel() {
		//set up frame basics
		setTitle("Admin Control Panel");
		setSize(800, 500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//set up panel appearance 
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(darkGray);
		setContentPane(panel);
		
		//set up tree appearance
		root = new DefaultMutableTreeNode("Root");
		tree = new JTree(root);
		tree.setBounds(10, 10, 300, 440);
		tree.setBackground(lightPurple);
		tree.setFont(topFont);
		
		//allow for a scroll bar to appear if the length of the tree exceeds the window's size
		scrollTree = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollTree.setBounds(10, 10, 300, 440);
		panel.add(scrollTree);
		
		//set up text field and button to add new users
		newUserId = new JTextField();
		newUserId.setBounds(320, 10, 285, 40);
		newUserId.setFont(topFont);
		newUserId.setToolTipText("Enter a New User ID.");
		panel.add(newUserId);
		
		//set up add user button
		addUser = new JButton("Add User");
		addUser.setBounds(625, 10, 150, 40);
		addUser.setFont(topFont);
		addUser.setForeground(darkPurple);
		addUser.setFocusable(false);
		addUser.addActionListener(new addUListener());
		panel.add(addUser);
		
		//set up text field and button to add new groups
		newGroupId = new JTextField();
		newGroupId.setBounds(320, 65, 285, 40);
		newGroupId.setFont(topFont);
		newGroupId.setToolTipText("Enter a New Group ID.");
		panel.add(newGroupId);
		
		//set up add group button
		addGroup = new JButton("Add Group");
		addGroup.setBounds(625, 65, 150, 40);
		addGroup.setFont(topFont);
		addGroup.setForeground(darkPurple);
		addGroup.setFocusable(false);
		addGroup.addActionListener(new addGListener());
		panel.add(addGroup);
		
		//set up user view button
		openUserView = new JButton("Open User View");
		openUserView.setBounds(320, 120, 455, 50);
		openUserView.setFont(topFont);
		openUserView.setForeground(darkPurple);		
		openUserView.setFocusable(false);
		openUserView.addActionListener(new openUVListener());
		panel.add(openUserView);
		
		userGroupVerify = new JButton("Verify User/Group IDs");
		userGroupVerify.setBounds(320, 185, 455, 50);
		userGroupVerify.setFont(topFont);
		userGroupVerify.setForeground(darkPurple);
		userGroupVerify.setFocusable(false);
		userGroupVerify.addActionListener(new verifyListener());
		panel.add(userGroupVerify);
		
		lastUpdated = new JButton("Find Last Updated User");
		lastUpdated.setBounds(320, 250, 455, 50);
		lastUpdated.setFont(topFont);
		lastUpdated.setForeground(darkPurple);
		lastUpdated.setFocusable(false);
		lastUpdated.addActionListener(new lastUpdatedListener());
		panel.add(lastUpdated);

		//set up button to display number of users created
		showUserTotal = new JButton("Show User Total");
		showUserTotal.setBounds(320, 345, 215, 45);
		showUserTotal.setFont(bottomFont);
		showUserTotal.setForeground(darkPurple);
		showUserTotal.setFocusable(false);
		showUserTotal.addActionListener(new userTotalListener());
		panel.add(showUserTotal);
		
		//set up button to display number of groups created
		showGroupTotal = new JButton("Show Group Total");
		showGroupTotal.setBounds(560, 345, 215, 45);
		showGroupTotal.setFont(bottomFont);
		showGroupTotal.setForeground(darkPurple);
		showGroupTotal.setFocusable(false);
		showGroupTotal.addActionListener(new groupCountListener());
		panel.add(showGroupTotal);
		
		//set up button to display number of tweets posted
		showMsgTotal = new JButton("Show Message Total");
		showMsgTotal.setBounds(320, 405, 215, 45);
		showMsgTotal.setFont(bottomFont);
		showMsgTotal.setForeground(darkPurple);
		showMsgTotal.setFocusable(false);
		showMsgTotal.addActionListener(new tweetCountListener());
		panel.add(showMsgTotal);
		
		//set up button to display the percentage of tweets containing positive words
		showPstvPercent = new JButton("Show Positive Percentage");
		showPstvPercent.setBounds(560, 405, 215, 45);
		showPstvPercent.setFont(bottomFont);
		showPstvPercent.setForeground(darkPurple);
		showPstvPercent.setFocusable(false);
		showPstvPercent.addActionListener(new positiveTweetPercentListener());
		panel.add(showPstvPercent);
	}
	
	//Allows easy creation of pop up messages
	private void popUp(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}
	
	//method to create new user
	private void createUser(String id, DefaultMutableTreeNode currentNode) {
		User user = new User(id);
		allUsers.put(id, user);
		
		Visitor newUser = new UserCountVisitor();
		user.accept(newUser);
		
		currentNode.add(new DefaultMutableTreeNode(id));
		((DefaultTreeModel)tree.getModel()).reload();

	}
	
	//ActionListener for JButton addUser
	private class addUListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			userId = newUserId.getText();
			currentTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if(userId.isBlank())
				popUp("User ID Field cannot be empty!");
			else if(allUsers.containsKey(userId))
				popUp("User already exists!");
			else if(currentTreeNode == null) {
				currentTreeNode = root;
				createUser(userId, currentTreeNode);
				newUserId.setText(null);
			}
			else if(allUsers.containsKey(currentTreeNode.toString()))
				popUp("Cannot add a new user at specified position."
						+ "\nSelect either Root or a Group!");
			else {
				createUser(userId, currentTreeNode);
				newUserId.setText(null);
			}		
		}
	}

	//method to create new group
	private void createGroup(String id, DefaultMutableTreeNode currentNode) {
		Group group = new Group(id);
		allGroups.put(id, group);
		
		Visitor newGroup = new GroupCountVisitor();
		group.accept(newGroup);
		
		currentNode.add(new DefaultMutableTreeNode(id));
		((DefaultTreeModel)tree.getModel()).reload();
	}
	
	//ActionListener for JButton addGroup
	private class addGListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			groupId = newGroupId.getText();
			currentTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if(groupId.isBlank())
				popUp("Group ID Field cannot be empty!");
			else if(allGroups.containsKey(groupId))
				popUp("Group already exists!");
			else if(currentTreeNode == null) {
				currentTreeNode = root;
				createGroup(groupId, currentTreeNode);
				newGroupId.setText(null);
			}
			else if(allUsers.containsKey(currentTreeNode.toString()))
				popUp("Cannot add a new user at specified position."
						+ "\nSelect either Root or a Group!");
			else {
				createGroup(groupId, currentTreeNode);
				newGroupId.setText(null);
			}
		}	
	}

	//ActionListener for UserView Button
	private class openUVListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			currentTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			
			if(currentTreeNode == null)
				popUp("Select a User to open their User View.");
			else if(currentTreeNode == root || allGroups.containsKey(currentTreeNode.toString()))
				popUp("Cannot open User View for " + currentTreeNode.toString()
				+ "\nPlease select a User to open their User View.");
			else {
				allUsers.get(currentTreeNode.toString()).openUV();
			}
		}	
	}
	
	//ActionListener for Verification Button
	private class verifyListener implements ActionListener {
		String verification = "";
		@Override
		public void actionPerformed(ActionEvent e) {
			if(allUsers.isEmpty() && allGroups.isEmpty()) {
				popUp("There are no IDs to verify.");
				return;
			}
			else {
				for(Map.Entry<String, User> user : allUsers.entrySet()) {
					String idCheck = (String)user.getKey();
					if(idCheck.contains(" ")) {
						popUp("Invalid IDs exist.");
						verification = "false";
						break;
					}
					for(Map.Entry<String, Group> group : allGroups.entrySet()) {
						if(group.getKey().equals(idCheck)) {
							popUp("Invalid IDs exist.");
							verification = "false";
							break;
						}
					}
				}
				for(Map.Entry<String, Group> group : allGroups.entrySet()) {
					String idCheck = (String)group.getKey();
					if(idCheck.contains(" ")) {
						popUp("Invalid IDs exist.");
						verification = "false";
						break;
					}
				}
				if(!verification.equals("false"))
					popUp("All IDs are valid.");
			}
		}

		
	}
	
	//ActionListener for Find Last Updated button
	private class lastUpdatedListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			User lastUpUser = null;
			if(allUsers.isEmpty())
				popUp("No users have been created!");
			else {
				lastUpUser = allUsers.entrySet().iterator().next().getValue();
				for(Map.Entry<String, User> user : allUsers.entrySet()) {
					if(user.getValue().getUpdateTime() > lastUpUser.getUpdateTime())
						lastUpUser = user.getValue();
				}
				popUp(lastUpUser.getComponentId() + " was the last updated user!");
			}
		}
		
	}
	
	//ActionListener for UserTotal Button
	private class userTotalListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			UserCountVisitor userTotal = new UserCountVisitor();
			popUp(userTotal.getUserCount() + " users have been created!");
		}	
	}
	
	//ActionListener for GroupTotal Button
	private class groupCountListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			GroupCountVisitor groupTotal = new GroupCountVisitor();
			popUp(groupTotal.getGroupCount() + " groups have been created!");
		}	
	}
	
	//ActionListener for TweetTotal Button
	private class tweetCountListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			TweetCountVisitor tweetTotal = new TweetCountVisitor();
			popUp(tweetTotal.getTweetCount() + " tweets have been posted!");
		}
		
	}
	
	//ActionListener for PositivePercent Button
	private class positiveTweetPercentListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			PositiveTweetPercentVisitor positivePercent = new PositiveTweetPercentVisitor();
			TweetCountVisitor tweetTotal = new TweetCountVisitor();
			positivePercent.calculatePosPercent(positivePercent.getPosCount(), 
					(double)tweetTotal.getTweetCount());
			popUp(String.format("%.2f%s", positivePercent.getPosPercent(), 
					"% of tweets have been positive!"));
		}
		
	}

}
