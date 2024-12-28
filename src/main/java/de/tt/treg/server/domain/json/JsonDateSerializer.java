package de.tt.treg.server.domain.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonDateSerializer extends StdSerializer<Date> {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy");

	public JsonDateSerializer() {
		this(null);
	}

	protected JsonDateSerializer(Class<Date> t) {
		super(t);
	}


	@Override
	public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		String formattedDate = dateFormat.format(date);

		jsonGenerator.writeString(formattedDate);
	}
}
