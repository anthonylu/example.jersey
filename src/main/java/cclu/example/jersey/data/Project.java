package cclu.example.jersey.data;

import javax.validation.constraints.NotNull;

public class Project {
	@NotNull(message="{project.name}")
	private String name;
	@NotNull(message="{project.major}")
	private Integer major;
	@NotNull(message="{project.minor}")
	private Integer minor;
	private String fileName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMajor() {
		return major;
	}
	public void setMajor(Integer major) {
		this.major = major;
	}
	public Integer getMinor() {
		return minor;
	}
	public void setMinor(Integer minor) {
		this.minor = minor;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
