package cclu.example.jersey.resource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cclu.example.jersey.ObjectMapperUtil;
import cclu.example.jersey.data.Project;

@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProjectResource {
	private static final Logger log = LoggerFactory.getLogger(ProjectResource.class);
	private static final ConcurrentHashMap<String, Project> map = new ConcurrentHashMap<>();
	
	@POST
	public Response createProject(@Valid@NotNull Project p) throws IOException {
		map.put(p.getName(), p);
		log.debug(ObjectMapperUtil.toJson(p));
		return Response.status(201).build();
	}
	
	@GET
	@Path("{name}")
	public Response getProject(@PathParam("name") String projectName) {
		try {
			String json = "[{\"name\":\"test1\"},{\"name\":\"test2\"}]";
			List<Project> projects = ObjectMapperUtil.toObjectCollection(json, List.class, Project.class);
			log.debug(ObjectMapperUtil.toJson(projects));
		} catch (IOException e) {
			log.debug("Fail to parse project object", e);
		}
		Project p = map.get(projectName);
		if (p != null) {
			return Response.ok(p).build();
		} else 
			return Response.status(404).entity("").build();
	}
	
	@PUT
	@Path("{name}")
	public Response updateProject(@PathParam("name") String projectName, @NotNull Project update) {
		Project p = map.get(projectName);
		if (p != null) {
			if (update.getMajor() != null) p.setMajor(update.getMajor());
			if (update.getMinor() != null) p.setMinor(update.getMinor());
			return Response.ok(p).build();
		} else 
			return Response.status(404).entity("").build();
	}
	
	@DELETE
	@Path("{name}")
	public Response deleteProject(@PathParam("name") String projectName) {
		Project p = map.remove(projectName);
		if (p != null) return Response.ok("").build();
		else return Response.status(304).entity("").build();
	}
	
}
