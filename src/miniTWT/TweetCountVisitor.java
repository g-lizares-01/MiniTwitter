/*
 * Implementation of Visitor pattern
 * Tracks total number of tweets posted
 */

package miniTWT;

public class TweetCountVisitor implements Visitor {
	private static int tweetCount = 0;

	@Override
	public void atUser(User u) {
		tweetCount++;
	}
	
	public int getTweetCount() {
		return tweetCount;
	}

	@Override
	public void atGroup(Group g) {}

}
