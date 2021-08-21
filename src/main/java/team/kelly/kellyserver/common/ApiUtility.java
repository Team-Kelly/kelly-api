package team.kelly.kellyserver.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
public class ApiUtility {

    public static String callApi(String url) throws IOException {
        StringBuilder result = new StringBuilder();

        String urlStr = url;
        URL urladdr = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection) urladdr.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;

        br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

        String returnLine;

        while ((returnLine = br.readLine()) != null) {
            result.append(returnLine + "\n\r");
        }

        urlConnection.disconnect();

        return XmlToJsonConvert(result.toString());
    }

    public static String XmlToJsonConvert(String xml) {
        try {
            JSONObject jObject = XML.toJSONObject(xml);
            ObjectMapper mapper = new ObjectMapper();

            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            Object json = mapper.readValue(jObject.toString(), Object.class);
            String output = mapper.writeValueAsString(json);

            return output;
        } catch (Exception e) {
            return "json parsing error";
        }

    }
}
