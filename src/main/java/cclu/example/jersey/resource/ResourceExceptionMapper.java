package cclu.example.jersey.resource;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException e) {
		StringBuilder sb = new StringBuilder();
		for (ConstraintViolation<?> c : e.getConstraintViolations()) {
			sb.append(c.getMessage());
			sb.append(";");
		}
		return Response.status(400).entity("{\"message\": \""+sb.toString()+"\"}").build();
	}

}
