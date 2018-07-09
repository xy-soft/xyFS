package xy.FileSystem.Schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 6000000)
    public void timeReport() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }
    
    //生成每日报表
    @Scheduled(fixedRate = 86400000)
    public void makeDailyReport() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

    //删除旧文件
    @Scheduled(fixedRate = 86400000)
    public void deleleOldFiles() {
        log.info("The time is now {}", dateFormat.format(new Date()));
    }

}
