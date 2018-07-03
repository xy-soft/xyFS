package xy.FileSystem.Entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the groupuser database table.
 * 
 */
@Entity
public class Groupuser implements Serializable {
	private static final long serialVersionUID = 1L;

	private String groupId;

	private String userName;

	public Groupuser() {
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}