package xy.FileSystem.Entity;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the app database table.
 * 
 */
@Entity
@Table(name="app")
@NamedQueries({
	@NamedQuery(name="App.findAll", query="SELECT a FROM App a"),
    @NamedQuery(name="App.findbyCode",query="SELECT a FROM App a WHERE a.code = :code")
         
})
public class App implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String appkey;

	private String available;

	private String code;

	private String name;

	public App() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppkey() {
		return this.appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getAvailable() {
		return this.available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}