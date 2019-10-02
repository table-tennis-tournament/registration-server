package de.tt.treg.server.integration.testtools;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.http.MediaType;

public class IntegrationTestUtil {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	public static byte[] convertObjectToJsonBytes(Object object)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-01-01"));

		mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}
}