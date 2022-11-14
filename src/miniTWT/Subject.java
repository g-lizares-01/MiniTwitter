/*
 * Implementation of the Observer pattern
 */

package miniTWT;

public abstract class Subject {
	public abstract void attach(User u);
	public abstract void updateObservers(String tweet);
}
