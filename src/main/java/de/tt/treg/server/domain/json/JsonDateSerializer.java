package de.tt.treg.server.domain.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonDateSerializer extends JsonSerializer<Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy");

	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		String formattedDate = dateFormat.format(date);

		gen.writeString(formattedDate);

	}

}
