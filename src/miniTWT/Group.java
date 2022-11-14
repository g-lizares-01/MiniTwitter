/* 
 * Group Class for MiniTwitter Project
 * Contains constructor for Group, 
 * 		getters for Group components, 
 * 		and an accept method for Visitor
 */

package miniTWT;

import java.util.ArrayList;

public class Group implements TreeEntry, Visitable {
	private String groupId;
	private ArrayList<TreeEntry> entries;
	
	public Group(String gid) {
		groupId = gid;
		entries = new ArrayList<TreeEntry>();
	}
	
	public String getComponentId() {
		// TODO Auto-generated method stub
		return groupId;
	}
	
	public ArrayList<TreeEntry> getChildren() {
		return entries;
	}
	

	public void accept(Visitor v) {
		v.atGroup(this);
		
	}

}
