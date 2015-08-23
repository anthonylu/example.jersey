package cclu.example.jersey;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class ObjectMapperUtil {
	private static final ObjectWriter writer;
	private static final ObjectReader reader;
	static {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		writer = mapper.writer();
		reader = mapper.reader();
	}
	
	public static String toJson(Object obj) throws IOException {
		return writer.writeValueAsString(obj);
	}
	
	public static <T> T toObject(String json, Class<T> clazz) throws IOException {
		return reader.withType(clazz).readValue(json);
	}
	
	@SuppressWarnings("rawtypes")
	public static <T extends Collection<E>, E> T toObjectCollection(String json, Class<? extends Collection> collection, Class<E> clazz) throws IOException {
		CollectionType type = TypeFactory.defaultInstance().constructCollectionType(collection, clazz);
		return reader.withType(type).readValue(json);
	}
}
