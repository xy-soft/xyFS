package xy.FileSystem.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the diskuser database table.
 * 
 */
@Entity
public class Diskuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String userName;

	private int appId;

	private String email;

	private String extra1;

	private String extra2;

	private String extra3;

	private String extra4;

	private String extra5;

	@Temporal(TemporalType.TIMESTAMP)
	private Date firstDate;

	private String isAdmin;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastDate;

	private BigInteger maxSpace;

	private String password;

	private String phone;

	private BigInteger usedSpace;

	private String userCnName;

	private String userFlag;

	public Diskuser() {
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getFirstDate() {
		return this.firstDate;
	}

	public void setFirstDate(Date firstDate) {
		this.firstDate = firstDate;
	}

	public String getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Date getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public BigInteger getMaxSpace() {
		return this.maxSpace;
	}

	public void setMaxSpace(BigInteger maxSpace) {
		this.maxSpace = maxSpace;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigInteger getUsedSpace() {
		return this.usedSpace;
	}

	public void setUsedSpace(BigInteger usedSpace) {
		this.usedSpace = usedSpace;
	}

	public String getUserCnName() {
		return this.userCnName;
	}

	public void setUserCnName(String userCnName) {
		this.userCnName = userCnName;
	}

	public String getUserFlag() {
		return this.userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

}