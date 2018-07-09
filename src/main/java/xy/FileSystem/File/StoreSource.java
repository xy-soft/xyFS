package xy.FileSystem.File;

import java.util.ArrayList;
import java.util.List;

public class StoreSource {
	static List<FileListener> listensers = new ArrayList<FileListener>();
	
    public static List<FileListener> getListensers() {
		return listensers;
	}

	public static void RegisterListensers(FileListener fl){
    	listensers.add(fl);
    }

}
