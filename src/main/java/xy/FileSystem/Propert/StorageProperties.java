package xy.FileSystem.Propert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	private String location = "uploadfiles";
	private String downloadto ="";
	private boolean rename = true;	
	private String downloadfrom = "";
	private String template = "thymeleaf";
	
	private boolean debug = true;
	private boolean todisk = true;
	private boolean toqiniu = true;
	private boolean tofastdfs = false;
	private boolean tomongodb = false;
	private boolean toseaweedfs = false;
	private boolean toalioss = false;
	
	private String diskprefix = "";
	private String qiniuprefix = "";
	private String qiniuak = "";
	private String qiniusk = "";
	private String qiniubucket = "";
	
	private String gridfshost = "";
	private int gridfsport = 27017;
	private String gridfsdbname = "";
	private String gridfscollectionname = "";
	
	private String seaweedfshost = "";
	private int seaweedfsport = 9333;
	private int seaweedfstimeout = 5000;
	
	private String aliendpoint = "";
	private String aliaccesskeyid = "";
	private String aliaccesskeysecret = "";
	private String alibucketname = "";
	private String alidownloadkey = "";
	
	public String getDownloadto() {
		return downloadto;
	}

	public void setDownloadto(String downloadto) {
		this.downloadto = downloadto;
	}
	
	public String getAliendpoint() {
		return aliendpoint;
	}

	public void setAliendpoint(String aliendpoint) {
		this.aliendpoint = aliendpoint;
	}

	public String getAliaccesskeyid() {
		return aliaccesskeyid;
	}

	public void setAliaccesskeyid(String aliaccesskeyid) {
		this.aliaccesskeyid = aliaccesskeyid;
	}

	public String getAliaccesskeysecret() {
		return aliaccesskeysecret;
	}

	public void setAliaccesskeysecret(String aliaccesskeysecret) {
		this.aliaccesskeysecret = aliaccesskeysecret;
	}

	public String getAlibucketname() {
		return alibucketname;
	}

	public void setAlibucketname(String alibucketname) {
		this.alibucketname = alibucketname;
	}

	public String getAlidownloadkey() {
		return alidownloadkey;
	}

	public void setAlidownloadkey(String alidownloadkey) {
		this.alidownloadkey = alidownloadkey;
	}

	public String getSeaweedfshost() {
		return seaweedfshost;
	}

	public void setSeaweedfshost(String seaweedfshost) {
		this.seaweedfshost = seaweedfshost;
	}

	public int getSeaweedfsport() {
		return seaweedfsport;
	}

	public void setSeaweedfsport(int seaweedfsport) {
		this.seaweedfsport = seaweedfsport;
	}

	public int getSeaweedfstimeout() {
		return seaweedfstimeout;
	}

	public void setSeaweedfstimeout(int seaweedfstimeout) {
		this.seaweedfstimeout = seaweedfstimeout;
	}
	
	public String getQiniuprefix() {
		return qiniuprefix;
	}

	public void setQiniuprefix(String qiniuprefix) {
		this.qiniuprefix = qiniuprefix;
	}

	public String getQiniuak() {
		return qiniuak;
	}

	public void setQiniuak(String qiniuak) {
		this.qiniuak = qiniuak;
	}

	public String getQiniusk() {
		return qiniusk;
	}

	public void setQiniusk(String qiniusk) {
		this.qiniusk = qiniusk;
	}

	public String getQiniubucket() {
		return qiniubucket;
	}

	public void setQiniubucket(String qiniubucket) {
		this.qiniubucket = qiniubucket;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isTodisk() {
		return todisk;
	}

	public void setTodisk(boolean todisk) {
		this.todisk = todisk;
	}

	public boolean isToqiniu() {
		return toqiniu;
	}

	public void setToqiniu(boolean toqiniu) {
		this.toqiniu = toqiniu;
	}

	public boolean isTofastdfs() {
		return tofastdfs;
	}

	public void setTofastdfs(boolean tofastdfs) {
		this.tofastdfs = tofastdfs;
	}

	public boolean isTomongodb() {
		return tomongodb;
	}

	public void setTomongodb(boolean tomongodb) {
		this.tomongodb = tomongodb;
	}	

	public boolean isToalioss() {
		return toalioss;
	}

	public void setToalioss(boolean toalioss) {
		this.toalioss = toalioss;
	}

	public String getDiskprefix() {
		return diskprefix;
	}

	public void setDiskprefix(String diskprefix) {
		this.diskprefix = diskprefix;
	}

	public boolean isRename() {
		return rename;
	}

	public void setRename(boolean rename) {
		this.rename = rename;
	}
	
	public String getGridfshost() {
		return gridfshost;
	}

	public void setGridfshost(String gridfshost) {
		this.gridfshost = gridfshost;
	}

	public int getGridfsport() {
		return gridfsport;
	}

	public void setGridfsport(int gridfsport) {
		this.gridfsport = gridfsport;
	}

	public String getGridfsdbname() {
		return gridfsdbname;
	}

	public void setGridfsdbname(String gridfsdbname) {
		this.gridfsdbname = gridfsdbname;
	}

	public String getGridfscollectionname() {
		return gridfscollectionname;
	}

	public void setGridfscollectionname(String gridfscollectionname) {
		this.gridfscollectionname = gridfscollectionname;
	}

	public boolean isToseaweedfs() {
		return toseaweedfs;
	}

	public void setToseaweedfs(boolean toseaweedfs) {
		this.toseaweedfs = toseaweedfs;
	}

	public String getDownloadfrom() {
		return downloadfrom;
	}

	public void setDownloadfrom(String downloadfrom) {
		this.downloadfrom = downloadfrom;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
