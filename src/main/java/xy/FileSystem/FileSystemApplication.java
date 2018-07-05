package xy.FileSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import xy.FileSystem.Cache.UsesCache;
import xy.FileSystem.Propert.StorageProperties;
import xy.FileSystem.Service.StorageService;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(StorageProperties.class)
public class FileSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileSystemApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
            
            initCache();
        };
    }

	public void initCache() {
		//TODO init cache
		UsesCache.files = 100;
		UsesCache.groups = 100;
		UsesCache.usedspace = 100000;
	}
	
	
}


