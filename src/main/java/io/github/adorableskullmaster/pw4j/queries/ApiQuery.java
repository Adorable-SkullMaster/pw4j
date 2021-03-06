package io.github.adorableskullmaster.pw4j.queries;

import io.github.adorableskullmaster.pw4j.PoliticsAndWarAPIException;
import io.github.adorableskullmaster.pw4j.core.Response;
import io.github.adorableskullmaster.pw4j.core.Utility;
import io.github.adorableskullmaster.pw4j.domains.Entity;
import io.github.adorableskullmaster.pw4j.enums.QueryURL;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiQuery<T extends Entity> {

  private final String urlPart;
  private String urlStr = "";
  private final T t;

  ApiQuery(String urlPart, T t) {
    this.urlPart = urlPart;
    this.t = t;
  }

  private static String convertStreamToString(java.io.InputStream is) {
    java.util.Scanner s = new java.util.Scanner(is, StandardCharsets.UTF_8).useDelimiter("\\A");
    return s.hasNext() ? s.next() : "";
  }

  public void buildUrlStr(boolean testServerMode) {
    String baseUrl = testServerMode ? QueryURL.TEST_URL.getUrl() : QueryURL.LIVE_URL.getUrl();
    urlStr = baseUrl.concat(urlPart);
  }

  public Response fetchAPI() throws IOException {
    HttpURLConnection conn = null;
    try {
      URL url = new URL(urlStr);
      conn = (HttpURLConnection) url.openConnection();
      conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36");
      conn.setRequestMethod("GET");
      int respCode = conn.getResponseCode();
      String respMessage = String.format("Politics and War API returned '%s' from url: %s", respCode + " " + conn.getResponseMessage(), urlPart);

      InputStream stream = conn.getErrorStream();

      if (stream == null && (respCode >= 200 && respCode < 300)) {
        stream = conn.getInputStream();
        return new Response<>(convertStreamToString(stream), t, urlStr);
      } else {
        throw new PoliticsAndWarAPIException(Utility.obfuscateApiKey(respMessage));
      }
    } finally {
      if(conn != null) {
        conn.disconnect();
      }
    }
  }

  public String getUrlStr() {
    return urlStr;
  }
}
