package com.esign.service.configuration.client;

import com.esign.service.configuration.config.OauthUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Base64;


@Service
public class DmsServiceClient {

    @Autowired
    OauthUser oauthUser;

    @Value("${dms.host}")
    private String host;

    @Value("${dms.folder}")
    private String folder;

    public final OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();;

    private final String NEWLINE = "\r\n";

    public String sendToDMS(String filename, String fileType, byte[] contentByte) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n\"fileName\":\"" + filename + "\",\r\n    "
                + "\"meta\":\"" + Base64.getEncoder().encodeToString("".getBytes()) + "\",\r\n    "
                + "\"contentType\":\"" + fileType + "\",\r\n    "
                + "\"fileContent\":\"" + new String(contentByte) + "\"\r\n\r\n}");

            Request request = new Request.Builder()
                .url("http://" + host + "/dms-service/api/files/upload/folder/" + folder + "/base64")
                .method("POST", body)
                .addHeader("Authorization", "Bearer " + oauthUser.getToken())
                .build();
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBodyCopy = response.peekBody(1024 * 1024);

            if (response.code() != 200) {
                System.out.println("FAIL:HTTP:" + response.code() + ",MSG: Error On call");
                return "";
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
                String dataRes = new String(responseBodyCopy.string().getBytes());
                System.out.println("Datares -> " + dataRes);
                JSONObject jonj = new JSONObject(dataRes);
                if (200 != jonj.getInt("statusCode")) {
                    System.out.println("FAIL:HTTP:" + response.code() + ",MSG:" + jonj.getInt("statusDescription"));
                    return "";
                } else {
                    JSONObject jdoc = jonj.getJSONObject("result");
                    return jdoc.getString("sharingId");
                }
            }
        } catch (Exception e) {
            System.out.println(stackTraceToString(e));
        }
        return "";
    }

    public String stackTraceToString(Exception ex) {
        if (ex != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            return sw.toString();
        }
        return "";
    }
}