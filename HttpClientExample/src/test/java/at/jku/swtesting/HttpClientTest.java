package at.jku.swtesting;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class HttpClientTest {
	private static final String TEST_CONTENT = "Http mock response";

	@Mock
	private URL mockURL;
	@Mock private HttpURLConnection mockURLConnection;

	@Test
	public void testGetContentOnline() throws MalformedURLException {
		HttpClient myClient = new HttpClient();
		String page = myClient.getContent(new URL("https://www.jku.at"));
		assertTrue(page.contains("<title>JKU - Johannes Kepler UniversitÃ¤t Linz</title>"));
	}

	@Test
	public void testGetContentMocked() throws IOException {
		HttpClient myClient = new HttpClient();

		// use mock url connection to not actually connect anywhere in the internet
		when(mockURL.openConnection()).thenReturn(mockURLConnection);
		// return defined TEST_CONTENT string in InputStream
		when(mockURLConnection.getInputStream()).thenReturn(new ByteArrayInputStream(TEST_CONTENT.getBytes()));

		// get mocked content
		String page = myClient.getContent(mockURL);

		// check if HttpClient actually returns TEST_CONTENT
		assertTrue(page.contains(TEST_CONTENT));
	}

}
