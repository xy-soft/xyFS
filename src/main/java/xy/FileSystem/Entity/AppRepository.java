package xy.FileSystem.Entity;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AppRepository extends CrudRepository<App, Integer> {
		
	public List<App> findbyCode(String code);

}
