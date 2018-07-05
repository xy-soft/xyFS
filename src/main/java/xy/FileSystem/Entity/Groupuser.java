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
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;
	
	private String groupId;

	private String userName;

	public Groupuser() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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