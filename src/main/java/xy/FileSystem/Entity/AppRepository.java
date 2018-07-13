package xy.FileSystem.Entity;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AppRepository extends CrudRepository<App, Integer> {
		
	public List<App> findbyCode(String code);
	
	public List<App> findByCodeAndName(String code,String name);
	
	public List<App> deleteByCode(String code);
	
	@Query(value = "delete from app where code=?1 ", nativeQuery = true)
    @Modifying
    @Transactional
    public void deleteAppByCode(String code);
	
	@Query(value = "update app set name=?1 where id=?2 ", nativeQuery = true)
    @Modifying
    @Transactional
    public Integer updateAppName(String name,int id);

}
