package xy.FileSystem;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import xy.FileSystem.Propert.StorageProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@ConfigurationProperties("storage")
public class PropertiesTest {
	
	@Autowired
	private StorageProperties storageProperties;
	
	@Test
	public void getHello() throws Exception {
		Assert.assertEquals(storageProperties.isRename(), true);
		Assert.assertEquals(storageProperties.getQiniuprefix(), "http://pbby0yzdu.bkt.clouddn.com/");
	}

}
