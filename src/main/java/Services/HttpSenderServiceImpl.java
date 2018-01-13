package Services;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.faces.bean.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ApplicationScoped
public class HttpSenderServiceImpl implements HttpSenderService {

    private static final String AUTH_TOKEN = "ODYzMDFkNDEtMWQ3OC00NmM1LWFlM2QtZTlmNzI3NzUwZmMy";

    @Override
    public void transcribeAudio() throws URISyntaxException, IOException {
        URI uri = new URIBuilder("https://api.speechmatics.com/v1.0/user/34828/jobs/")
                .addParameter("auth_token", AUTH_TOKEN)
                .build();
        try(CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setHeader("Accept", "application/json");

            File file = new File("/Users/ilesev/Downloads/test.mp3");

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("model", "en-US")
                    .addTextBody("notification", "none")
                    .addBinaryBody("data_file", file, ContentType.DEFAULT_BINARY ,file.getName())
                    .build();

            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            String[] response = EntityUtils.toString(httpResponse.getEntity()).split("\n");
            System.out.println(response);
        }
    }
}
