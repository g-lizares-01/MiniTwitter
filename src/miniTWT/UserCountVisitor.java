/* 
 * Implementation of Visitor pattern
 * Counts/tracks the number of users that exist for each instance
 * of the Admin Control Panel
 */

package miniTWT;

public class UserCountVisitor implements Visitor {
	private static int userCount = 0;

	@Override
	public void atUser(User u) {
		userCount++;
	}
	
	public int getUserCount() {
		return userCount;
	}

	public void atGroup(Group g) {}

	public void atTweet(String t) {}

}
