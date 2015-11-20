package br.ufg.inf.sdd_ufg.resource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteSource;

public abstract class AbstractResource {
	public ByteSource getContent(final HttpServletRequest request) {
		final ByteSource byteSource = new ByteSource() {
	        @Override
	        public InputStream openStream() throws IOException {
	            return request.getInputStream();
	        }
	    };
	    
	    return byteSource;
	}
	
	public String getStringContent(final HttpServletRequest request) {
	    String content;
		try {
			content = getContent(request)
					.asCharSource(Charset.forName("UTF-8"))
					.read();
		} catch (IOException e) {
			content = "";
		}
		
		return content;
    }
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getJSONContent(final HttpServletRequest request) {
		String content = getStringContent(request);
		HashMap<String, Object> contentMapped;
		try {
			contentMapped = new ObjectMapper().readValue(content, HashMap.class);
		} catch (IOException e) {
			contentMapped = new HashMap<String, Object>();
		}
		
		return contentMapped;
	}
}
