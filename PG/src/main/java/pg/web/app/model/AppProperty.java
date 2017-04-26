package pg.web.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="app_properties")
public class AppProperty implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String MAPS_API_KEY = "maps-api-key";
	
	@Id
	@Column(name="\"key\"")
	private String key;
	
	@Column(name="\"value\"")
	private String value;

	public AppProperty() {
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}