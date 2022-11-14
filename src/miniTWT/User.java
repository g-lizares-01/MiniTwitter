/* User class for MiniTwitter Project
 * Contains constructor for User,
 * 		getters for User components,
 * 		methods to add followers/following
 * 		update methods 
 */

package miniTWT;

import java.util.ArrayList;
//import java.util.HashMap;


public class User extends Subject implements TreeEntry, Observer, Visitable {
	private String userId, latestTweetText;
	private ArrayList<String> newsFeed, myTweets;
	private ArrayList<User> followers, following;
	private UserView uv;
	//private HashMap<String, User> allUsers = AdminControlPanel.getAllUsers();

	public User(String uid) {
		userId = uid;
		followers = new ArrayList<User>();
		following = new ArrayList<User>();
		newsFeed = new ArrayList<String>();
		myTweets = new ArrayList<String>();
	}
	
	public User getUser() {
		return User.this;
	}
	
	public String getComponentId() {
		return userId;
	}
	
	public void addFollowing(User u) {
		following.add(u);
		u.attach(this.getUser());
	}
	
	public ArrayList<User> getFollowing() {
		return following;
	}
	
	public ArrayList<User> getFollowers() {
		return followers;
	}
	
	public void addMyTweets(String tweet) {
		myTweets.add(tweet);
	}
	
	public ArrayList<String> getMyTweets(){
		return myTweets;
	}
	
	public void setLatestTweet() {
		latestTweetText = myTweets.get(myTweets.size() - 1);
	}
	
	public String getLatestTweet() {
		return latestTweetText;
	}
	
	public String formatLatestTweet() {
		return getComponentId() + ": " + latestTweetText;
	}
	
	public void addToFeed(String tweet) {
		newsFeed.add(tweet);
	}
	
	public ArrayList<String> getFeed() {
		return newsFeed;
	}
	
	@Override
	public void attach(User u) {
		followers.add(u);
	}

	@Override
	public void update(User u, String tweet) {
		u.addToFeed(tweet);
	}

	@Override
	public void updateObservers(String tweet) {
		
		addToFeed(tweet);
		for(User u : getFollowers()) {
			update(u, tweet);
			uv.buildNewsFeed();
		}
	}
	
	public void openUV() {
		uv = new UserView(this);
		uv.setVisible(true);
	}
	
	public void buildNewsFeed() {
		uv.buildNewsFeed();
	}
	
	@Override
	public void accept(Visitor v) {
		v.atUser(this);
	}

}
