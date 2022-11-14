/*
 * Implementation of Visitor pattern
 * Tracks number of tweets containing a positive word
 * Contains method to calculate positive percentage when given the total tweet count
 */

package miniTWT;

public class PositiveTweetPercentVisitor implements Visitor {
	private static double positiveCount = 0;
	private double positivePercent;
    private final String[] positiveWords = {"good", "nice", "cool", "awesome", "yay",
            "fun", "happy", "great"}; 

	@Override
	public void atUser(User u) {
		u.setLatestTweet();
		String checker = u.getLatestTweet();
		for(int i = 0; i < positiveWords.length; i++) {
			if(checker.contains(positiveWords[i])) {
				positiveCount++;
				return;
			}
		}
	}
	
	public double getPosCount() {
		return positiveCount;
	}
	
	public void calculatePosPercent(double positive, double num) {
		positivePercent = 100 * (double)(positive / num);
	}
	
	public double getPosPercent() {
		return positivePercent;
	}

	@Override
	public void atGroup(Group g) {}

}
