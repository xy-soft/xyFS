package xy.FileSystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xy.FileSystem.Entity.App;
import xy.FileSystem.Entity.AppRepository;

@Controller    
@RequestMapping(path="/apps") 
public class AppController {
	
	@Autowired
	private  AppRepository appRepository;
	
	@GetMapping(path="/add") 
	public @ResponseBody String addNewUser (@RequestParam String name
			, @RequestParam String code, @RequestParam String appkey) {
		
		add(name, code, appkey);
		return "ok";
	}

	public void add(String name, String code, String appkey) {
		App model = new App();		
		model.setName(name);
		model.setAvailable("1");
		model.setCode(code);
		model.setAppkey(appkey);
		appRepository.save(model);
	}
	
	@GetMapping(path="/findall")
	public @ResponseBody Iterable<App> findall() {
		return appRepository.findAll();		
	}
	
	@GetMapping(path="/findbycode")
	public @ResponseBody Iterable<App> getcode(@RequestParam String code) {

		 List<App> apps = appRepository.findbyCode(code);
	     return apps;
	}
	

}
