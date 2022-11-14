/*
 * Implementation of the Visitor pattern
 */

package miniTWT;

public interface Visitable {
	public void accept(Visitor v);
}
