package Services;

import Entities.Words;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.faces.bean.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@ApplicationScoped
public class HttpSenderServiceImpl implements HttpSenderService {

    private static final String AUTH_TOKEN = "ODYzMDFkNDEtMWQ3OC00NmM1LWFlM2QtZTlmNzI3NzUwZmMy";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final String JOB_REQUEST = "https://api.speechmatics.com/v1.0/user/34828/jobs/%d/transcript?format=json&auth_token=%s";
    private static final String STATUS_REQUEST = "https://api.speechmatics.com/v1.0/user/34828/jobs/%s/?auth_token=%s";

    @Inject
    private SubtitleParser subtitleParser;

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
            int id = Integer.parseInt(StringUtils.substringAfterLast(response[4], " "));

            List<Words> words = getResponseAsList(id);
            subtitleParser.parse(words);
        }
    }

    private List<Words> getResponseAsList(int id) throws URISyntaxException, IOException {
        URI getUri = new URI(String.format(JOB_REQUEST, id, AUTH_TOKEN));
        HttpGet httpGet = new HttpGet(getUri);

        URI statusURI = new URI(String.format(STATUS_REQUEST, id, AUTH_TOKEN));
        HttpGet statusRequest = new HttpGet(statusURI);

        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String status = null;
            do{
                HttpResponse statusResponse = httpClient.execute(statusRequest);
                String jsonStatus = EntityUtils.toString(statusResponse.getEntity());
                JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonStatus);
                JsonNode statusNode = jsonNode.get("job").get("job_status");
                status = statusNode.textValue();
            } while (status == null || !status.equalsIgnoreCase("done"));

            HttpResponse response = httpClient.execute(httpGet);
            String jsonResponse = EntityUtils.toString(response.getEntity());
            JsonNode transcriptionNode = OBJECT_MAPPER.readTree(jsonResponse);
            JsonNode wordsNode = transcriptionNode.get("words");
            return Arrays.asList(OBJECT_MAPPER.treeToValue(wordsNode, Words[].class));
        }
    }
}
