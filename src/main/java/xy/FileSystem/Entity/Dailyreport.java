package xy.FileSystem.Entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the dailyreport database table.
 * 
 */
@Entity
public class Dailyreport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String curdate;

	private int files;

	private int groups;

	private int spaces;

	public Dailyreport() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurdate() {
		return this.curdate;
	}

	public void setCurdate(String curdate) {
		this.curdate = curdate;
	}

	public int getFiles() {
		return this.files;
	}

	public void setFiles(int files) {
		this.files = files;
	}

	public int getGroups() {
		return this.groups;
	}

	public void setGroups(int groups) {
		this.groups = groups;
	}

	public int getSpaces() {
		return this.spaces;
	}

	public void setSpaces(int spaces) {
		this.spaces = spaces;
	}

}