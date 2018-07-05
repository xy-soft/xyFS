package xy.FileSystem.Propert;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

	private String location = "uploadfiles";
	
	private boolean rename = true;
	
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
}
