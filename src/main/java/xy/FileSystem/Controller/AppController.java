package xy.FileSystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import xy.FileSystem.Entity.App;
import xy.FileSystem.Entity.AppRepository;

@Api("APP应用接口")
@Controller
@RequestMapping(path = "/apps")
public class AppController {

	@Autowired
	private AppRepository appRepository;

	@ApiOperation(value="添加App应用", notes="根据应用名、应用代码、应用秘钥添加")
	@GetMapping(path = "/add")
	public @ResponseBody String addNew(@RequestParam String name, @RequestParam String code,
			@RequestParam String appkey) {

		add(name, code, appkey);
		return "ok";
	}

	@ApiOperation(value="添加App应用", notes="根据App实体对象添加")
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

	
	@ApiOperation(value="获取全部App应用")
	@GetMapping(path = "/findAll")
	public @ResponseBody Iterable<App> findall() {
		return appRepository.findAll();
	}
	
	@ApiOperation(value="更新App应用名称")
	@GetMapping(path = "/updateApp")
	public Integer updateApp(@RequestParam int id, @RequestParam String name) {
		int result = appRepository.updateAppName(name, id);
		return result;
	}
	
	@ApiOperation(value="通过代码获取App")
	@GetMapping(path = "/findByCode")
	public @ResponseBody Iterable<App> getCode(@RequestParam String code) {

		List<App> apps = appRepository.findbyCode(code);
		return apps;
	}
	
	@ApiOperation(value="通过代码或名称获取App应用列表")
	@GetMapping(path = "/findByCodeAndName")
	public @ResponseBody Iterable<App> getCodeAndName(@RequestParam String code, @RequestParam String name) {

		List<App> apps = appRepository.findByCodeAndName(code, name);
		return apps;
	}
	
	@ApiOperation(value="通过代码删除App应用")
	@GetMapping(path = "/deleteByCode")
	public ResponseEntity<Void> deleteByCode(@RequestParam String code) {

		appRepository.deleteAppByCode(code);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value="通过id删除App应用")
	@GetMapping(path = "/deleteById")
	public ResponseEntity<Void> deleteById(@RequestParam int id) {

		appRepository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	
	public void add(String name, String code, String appkey) {
		App model = new App();
		model.setName(name);
		model.setAvailable("1");
		model.setCode(code);
		model.setAppkey(appkey);
		appRepository.save(model);
	}
}
