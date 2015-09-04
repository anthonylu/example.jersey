package cclu.example.jersey.resource;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;

import cclu.example.jersey.Configuration;

public class CorsFilter implements ContainerResponseFilter {
	private final String origin;
	private final String methods;
	private final String headers;
	
	
	@Inject
	public CorsFilter(Configuration config) {
		origin = config.getString("cors.origin", "*");
		methods = config.getString("cors.methods", "GET, POST, PUT, DELETE");
		headers = config.getString("cors.headers", "Content-Type, Authorization");
	}

	@Override
	public void filter(ContainerRequestContext reqContext, ContainerResponseContext respContext) throws IOException {
		MultivaluedMap<String, Object> headerMap = respContext.getHeaders();
		headerMap.add("Access-Control-Allow-Origin", origin);
		headerMap.add("Access-Control-Allow-Methods", methods);
		headerMap.add("Access-Control-Allow-Headers", headers);
	}

}
