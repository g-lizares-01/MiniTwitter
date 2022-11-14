/* 
 * Implementation of Visitor pattern
 * Counts/tracks the number of groups that exist for each instance
 * of the Admin Control Panel
 */

package miniTWT;

public class GroupCountVisitor implements Visitor {
	private static int groupCount = 0;

	@Override
	public void atGroup(Group g) {
		groupCount++;
	}
	
	public int getGroupCount() {
		return groupCount;
	}

	public void atUser(User u) {}

	public void atTweet(String t) {}

}
