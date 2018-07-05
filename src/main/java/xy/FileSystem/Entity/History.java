package xy.FileSystem.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the history database table.
 * 
 */
@Entity
public class History implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private Long id;

	private String fileName;

	private String groupName;

	private String operation;
	
	private String extra1;

	private String extra2;

	private String extra3;

	private String extra4;

	private String extra5;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	private String userName;

	public History() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExtra1() {
		return this.extra1;
	}

	public void setExtra1(String extra1) {
		this.extra1 = extra1;
	}

	public String getExtra2() {
		return this.extra2;
	}

	public void setExtra2(String extra2) {
		this.extra2 = extra2;
	}

	public String getExtra3() {
		return this.extra3;
	}

	public void setExtra3(String extra3) {
		this.extra3 = extra3;
	}

	public String getExtra4() {
		return this.extra4;
	}

	public void setExtra4(String extra4) {
		this.extra4 = extra4;
	}

	public String getExtra5() {
		return this.extra5;
	}

	public void setExtra5(String extra5) {
		this.extra5 = extra5;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getOperation() {
		return this.operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}