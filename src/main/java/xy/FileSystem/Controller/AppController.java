package xy.FileSystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import xy.FileSystem.Entity.App;
import xy.FileSystem.Entity.AppRepository;

@Controller
@RequestMapping(path = "/apps")
public class AppController {

	@Autowired
	private AppRepository appRepository;

	@GetMapping(path = "/add")
	public @ResponseBody String addNewUser(@RequestParam String name, @RequestParam String code,
			@RequestParam String appkey) {

		add(name, code, appkey);
		return "ok";
	}
	
	@PostMapping("/addApp")
	public ResponseEntity<Void> addApp(@RequestBody App app, UriComponentsBuilder builder) {
		App appSaved = appRepository.save(app);
		if (appSaved == null) {
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/apps/{id}").buildAndExpand(app.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	public void add(String name, String code, String appkey) {
		App model = new App();
		model.setName(name);
		model.setAvailable("1");
		model.setCode(code);
		model.setAppkey(appkey);
		appRepository.save(model);
	}

	@GetMapping(path = "/findall")
	public @ResponseBody Iterable<App> findall() {
		return appRepository.findAll();
	}

	@GetMapping(path = "/findbycode")
	public @ResponseBody Iterable<App> getCode(@RequestParam String code) {

		List<App> apps = appRepository.findbyCode(code);
		return apps;
	}

	@GetMapping(path = "/findbycodeandname")
	public @ResponseBody Iterable<App> getCodeAndName(@RequestParam String code, @RequestParam String name) {

		List<App> apps = appRepository.findByCodeAndName(code, name);
		return apps;
	}

	@GetMapping(path = "/deletebycode")
	public ResponseEntity<Void> deleteByCode(@RequestParam String code) {

		appRepository.deleteAppByCode(code);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/deletebyid")
	public ResponseEntity<Void> deleteById(@RequestParam int id) {

		appRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(path = "/updateapp")
	public Integer updateApp(@RequestParam int id, @RequestParam String name) {
		int result = appRepository.updateAppName(name, id);
		return result;
	}

	

}
