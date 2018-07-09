package xy.FileSystem.File;

//上传后回调信息
public class UploadResult {
	
    public String fileName;
	public long fsize;
    public String key;//文件系统中的关键Key,用于查询定位,Fastdsf\ali\Seaweedfs都是用key定位文件
    public String location;//一些扩展信息
    public String bucket;
    public String tag;//一些扩展信息
    public String hash;
    public int width;
    public int height;
}
