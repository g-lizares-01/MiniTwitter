/* 
 * Driver class for MiniTwitter Project
 * Creates Singleton instance of Admin Control Panel
 */

package miniTWT;

public class Driver {
	public static void main(String[] args) {
		AdminControlPanel.getInstance().setVisible(true);
	}
}
