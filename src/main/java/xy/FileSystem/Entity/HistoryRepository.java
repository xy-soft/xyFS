package xy.FileSystem.Entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface HistoryRepository extends PagingAndSortingRepository<History, Long> {

}