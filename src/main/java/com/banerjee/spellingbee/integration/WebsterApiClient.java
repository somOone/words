package com.banerjee.spellingbee.integration;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * Created by somobanerjee on 3/1/17.
 */
@Component

public class WebsterApiClient {
    @Value(value = "${webster.api.url}")
    String websterApiUrl;
    @Value(value = "${webster.api.key}")
    String websterApiKey;

    public String getWordDataFromWebster(String word)
        throws IOException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = websterApiUrl.concat(URLEncoder.encode("sd3", "UTF-8")).concat("/xml/")
                .concat(URLEncoder.encode(word, "UTF-8"))
                .concat("?key=").concat(URLEncoder.encode(websterApiKey, "UTF-8"));
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "e37b4e5d-c114-e329-b21b-cb0b523051d4")
                .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
