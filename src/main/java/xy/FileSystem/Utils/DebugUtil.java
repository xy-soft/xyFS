package xy.FileSystem.Utils;

import org.springframework.beans.factory.annotation.Autowired;

import xy.FileSystem.Propert.StorageProperties;


public class DebugUtil {
	
	@Autowired
	StorageProperties prop;

	public DebugUtil() {
		// TODO Auto-generated constructor stub
	}

	public static void debug(String str){
			System.out.println(str);
	}
}
