/*
 * Implementation of the Visitor Pattern
 */

package miniTWT;

public interface Visitor {
	public void atUser(User u);
	public void atGroup(Group g);
}
