package at.jku.swtesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpClientTest {
	@Mock
	private URL mockURL;
	@Mock private HttpURLConnection mockURLConnection;
  	@Mock private InputStream mockInputStream;

	@Test
	public void testGetContentOnline() throws MalformedURLException {
		HttpClient myClient = new HttpClient();
		String page = myClient.getContent(new URL("https://www.jku.at"));
		assertTrue(page.contains("<title>JKU - Johannes Kepler UniversitÃ¤t Linz</title>"));
	}

	@Test
	public void testGetContentMocked() throws IOException {
		// TODO: Implement test using mocks
		HttpClient myClient = new HttpClient();

		when(mockURL.openConnection()).thenReturn(mockURLConnection);

		// mock input stream by creating a mockinputstream class which extends InputStream and returns fixed characters
		when(mockURLConnection.getInputStream()).thenReturn(mockInputStream);
		when (mockInputStream.read()).thenReturn(-1);
		when(myClient.getContent(mockURL)).thenReturn("Mock http response");

		String page = myClient.getContent(mockURL);
		assertTrue(page.contains("Mock http response"));
	}

}
