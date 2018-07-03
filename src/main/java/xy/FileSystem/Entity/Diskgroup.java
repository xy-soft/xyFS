package xy.FileSystem.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the diskgroup database table.
 * 
 */
@Entity
public class Diskgroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private String groupId;

	private String administrator;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	private String extra1;

	private String extra2;

	private String extra3;

	private String extra4;

	private String extra5;

	private String fatherGroupId;

	private String groupCnName;

	private String groupCreator;

	private String groupFlag;

	private String groupName;

	private String groupType;

	private String isPublic;

	private int maxNum;

	public Diskgroup() {
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getAdministrator() {
		return this.administrator;
	}

	public void setAdministrator(String administrator) {
		this.administrator = administrator;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getFatherGroupId() {
		return this.fatherGroupId;
	}

	public void setFatherGroupId(String fatherGroupId) {
		this.fatherGroupId = fatherGroupId;
	}

	public String getGroupCnName() {
		return this.groupCnName;
	}

	public void setGroupCnName(String groupCnName) {
		this.groupCnName = groupCnName;
	}

	public String getGroupCreator() {
		return this.groupCreator;
	}

	public void setGroupCreator(String groupCreator) {
		this.groupCreator = groupCreator;
	}

	public String getGroupFlag() {
		return this.groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getIsPublic() {
		return this.isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	public int getMaxNum() {
		return this.maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

}