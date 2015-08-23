package cclu.example.jersey.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cclu.example.jersey.ObjectMapperUtil;
import cclu.example.jersey.data.Project;
import jersey.repackaged.com.google.common.collect.Lists;

@Path("upload/projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class FileUploadResource {
	private static final Logger log = LoggerFactory.getLogger(FileUploadResource.class);
	private static final ConcurrentHashMap<String, Project> map = new ConcurrentHashMap<>();
	private static final String BASE_DIR = "projects/";
	
	@POST
	public Response createProject(@NotEmpty(message="{list.project.meta.empty}")@FormDataParam("project") String json,
			@NotEmpty(message="{list.project.file.empty}")@FormDataParam("file") List<FormDataBodyPart> fileParts) throws IOException {
		List<Project> projects = toProjectList(json);
		if (projects.isEmpty()) return Response.status(400).entity("No project meta data").build();
		HashMap<String, FormDataBodyPart> fileMap = new HashMap<>();
		for (FormDataBodyPart part:fileParts) {
			fileMap.put(part.getContentDisposition().getFileName(), part);
		}
		java.nio.file.Path path;
		List<Project> stored = new ArrayList<>();
		for (Project p:projects) {
			FormDataBodyPart part = fileMap.get(p.getFileName());
			if (part != null && !map.containsKey(p.getName())) {
				map.put(p.getName(), p);
				path = Paths.get(BASE_DIR, p.getName());
				if (createProjectDirectory(path)) {
					// store file
					path = path.resolve(p.getFileName());
					Files.copy(part.getEntityAs(InputStream.class), path);
					stored.add(p);
				}
			}
		}
		return stored.isEmpty() ? Response.notModified("").build() : Response.status(201).entity(stored).build();
	}
	
	private List<Project> toProjectList(String json) {
		try {
			return ObjectMapperUtil.toObjectCollection(json, List.class, Project.class);
		} catch (IOException e) {
			log.debug("Fail to parse json string", e);
			return Lists.newArrayList();
		}
	}
	
	private boolean createProjectDirectory(java.nio.file.Path dir) {
		if (Files.exists(dir) == false) {
			try {
				Files.createDirectories(dir);
			} catch (IOException e) {
				log.debug("Fail to create directory {}", dir);
				return false;
			}
		}
		return true;
	}
}
