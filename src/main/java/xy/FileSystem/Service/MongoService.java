package xy.FileSystem.Service;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

import xy.FileSystem.Propert.StorageProperties;

@Service
public class MongoService {
    @Autowired
    private  StorageProperties prop;
    
    
    /**
	 * @MethodName	: getMongo
	 * @Description	: 获取数据连接
	 * @return 返回mongon
	 */
	private MongoClient getMongo(){
		MongoClient mongo=null;
		try {
			mongo = new MongoClient(new ServerAddress(prop.getGridfshost(), prop.getGridfsport()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mongo;
	}
	
	/**
	 *  @MethodName	: uploadFile
	 * @Description	: 上传文件
	 * @param file ：文件，File类型
	 * @param id	：唯一标示文件，可根据id查询到文件.必须设置
	 * @param dbName ：库名，每个系统使用一个库
	 * @param collectionName：集合名，如果传入的集合名库中没有，则会自动新建并保存
	 * @param map：放入你想要保存的属性，例如文件类型（“congtentType”".jpg"）,字符串类型，区分大小写，如果属性没有的话会自动创建并保存
	 */
   public void uploadFile(File file,String id,String dbName,String collectionName,LinkedHashMap<String, Object> map){
	   //把mongoDB的数据库地址配置在外部。
		try {
			MongoClient mongo =getMongo(); 
			//每个系统用一个库
			 
			DB db = mongo.getDB(dbName);  
			System.out.println(db.toString());
			//每个库中可以分子集
			GridFS gridFS= new GridFS(db,collectionName);
			
			// 创建gridfsfile文件
			GridFSFile gridFSFile = gridFS.createFile(file);
			//判断是否已经存在文件，如果存在则先删除
			GridFSDBFile gridFSDBFile=getFileById(id, dbName, collectionName);
			if(gridFSDBFile!=null){
				deleteFile(id, dbName, collectionName);
			}
			//将文件属性设置到
			gridFSFile.put("_id", id);
			//循环设置的参数
			if (map != null && map.size() > 0) {
				for (String key : map.keySet()) {
					gridFSFile.put(key, map.get(key));
				}
			}
			//保存上传
			gridFSFile.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
   /**  
    * @MethodName	: deleteFile
    * @Description	: 删除文件
    * @param id：文件对应的id
    * @param dbName：文件所在的库
    * @param collectionName：文件所在的集合
    */
   public void deleteFile(String id,String dbName,String collectionName){
 
		try {
			//获得mongoDB数据库连接。
			MongoClient mongo =getMongo(); 
			//获得库
			DB db= mongo.getDB(dbName);
			//获得子集
			GridFS gridFS= new GridFS(db,collectionName);
			//删除文件
			DBObject query=new BasicDBObject("_id", id);
			gridFS.remove(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
   }
   
   /**
    * 批量删除文件
    * @MethodName	: deleteFileByIds
    * @Description	: TODO
    * @param ids
    * @param dbName
    * @param collectionName
    * 
    */
   public void deleteFileByIds(String[] ids,String dbName,String collectionName){
       try {
           //获得mongoDB数据库连接。
    	   MongoClient mongo =getMongo(); 
           //获得库
           DB db= mongo.getDB(dbName);
           //获得子集
           GridFS gridFS= new GridFS(db,collectionName);
           Map<String,String> map = new HashMap<String,String>();
           for(int i=0;i<ids.length;i++){
             //删除文件
               DBObject query=new BasicDBObject("_id", ids[i]);
               gridFS.remove(query);
           }         
       } catch (Exception e) {
           e.printStackTrace();
       }
  }
   
   /**
    * @MethodName	: getFileById
    * @Description	: 根据Id获得文件
    * @param id ：文件Id
    * @param dbName: 数据库名
    * @param collectionName：集合名
    * @return GridFSDBFile
    */
   public GridFSDBFile getFileById(String id,String dbName,String collectionName){
	   GridFSDBFile gridFSDBFile=null;
	   try {
			//获得mongoDB数据库连接。
			Mongo mongo =getMongo(); 
			//获得库
			DB db= mongo.getDB(dbName);
			//获得子集
			GridFS gridFS= new GridFS(db,collectionName);
			//获得文件
			DBObject query=new BasicDBObject("_id", id);
			gridFSDBFile=gridFS.findOne(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	   //返回数据
	   return gridFSDBFile;
   }
   
   /**查询集合中所有文件
    * @MethodName	: getAllFile
    * @Description	: TODO
    * @param dbName
    * @param collectionName    *
    * @return
    */
   public List<GridFSDBFile> getAllFile(String dbName,String collectionName){
       List<GridFSDBFile> gridFSDBFileList=null;
       try {
            //获得mongoDB数据库连接。
            Mongo mongo =getMongo(); 
            //获得库
            DB db= mongo.getDB(dbName);
            //获得子集
            GridFS gridFS= new GridFS(db,collectionName);
            //获得文件
            DBObject query=new BasicDBObject();//空的构造
            gridFSDBFileList = gridFS.find(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
       //返回数据
       return gridFSDBFileList;
   }


	
	/**
	 * 据文件名返回文件，只返回第一个
	 * @param fileName
	 * @return
	 */
	public GridFSDBFile getByFileName(String fileName,String dbName,String collectionName){
		DBObject query = new BasicDBObject("filename", fileName);
        DB db= getMongo().getDB(dbName);
		GridFS gridFS= new GridFS(db,collectionName);
		GridFSDBFile gridFSDBFile = gridFS.findOne(query);
		return gridFSDBFile;
	}
   
   private void downloadFile(GridFSDBFile gridFSDBFile,HttpServletResponse res,String filename)
   {
       try
       {
           if (gridFSDBFile != null)
           {
               
               OutputStream sos = res.getOutputStream();
               
               res.setCharacterEncoding("UTF-8");
               res.setHeader("Access-Control-Allow-Origin", "*");
               res.setContentType("application/octet-stream");
               res.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
               gridFSDBFile.writeTo(sos);
               sos.flush();
               sos.close();
           }
       }
       catch (Exception e)
       {
           System.out.println("下载文件异常 ...");
           e.printStackTrace();
       }
   }
   
}
