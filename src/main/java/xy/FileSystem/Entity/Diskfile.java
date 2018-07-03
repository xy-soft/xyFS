package xy.FileSystem.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the diskfile database table.
 * 
 */
@Entity
public class Diskfile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private String fileid;

	private int appid;

	private String dfsGroupName;

	private int downloadNum;

	private String downloadPwd;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;

	private String extra1;

	private String extra2;

	private String extra3;

	private String extra4;

	private String extra5;

	private String fileExt;

	private String fileFlag;

	private String fileName;

	private BigInteger fileSize;

	private String filesource;

	private String fileUrl;

	private String formId;

	private String groupId;

	private String groupName;

	private String ispublic;

	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;

	private String uploadUser;

	public Diskfile() {
	}

	public String getFileid() {
		return this.fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public int getAppid() {
		return this.appid;
	}

	public void setAppid(int appid) {
		this.appid = appid;
	}

	public String getDfsGroupName() {
		return this.dfsGroupName;
	}

	public void setDfsGroupName(String dfsGroupName) {
		this.dfsGroupName = dfsGroupName;
	}

	public int getDownloadNum() {
		return this.downloadNum;
	}

	public void setDownloadNum(int downloadNum) {
		this.downloadNum = downloadNum;
	}

	public String getDownloadPwd() {
		return this.downloadPwd;
	}

	public void setDownloadPwd(String downloadPwd) {
		this.downloadPwd = downloadPwd;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
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

	public String getFileExt() {
		return this.fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileFlag() {
		return this.fileFlag;
	}

	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigInteger getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(BigInteger fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilesource() {
		return this.filesource;
	}

	public void setFilesource(String filesource) {
		this.filesource = filesource;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFormId() {
		return this.formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIspublic() {
		return this.ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public Date getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadUser() {
		return this.uploadUser;
	}

	public void setUploadUser(String uploadUser) {
		this.uploadUser = uploadUser;
	}

}