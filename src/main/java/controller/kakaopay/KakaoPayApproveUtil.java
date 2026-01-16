package controller.kakaopay;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class KakaoPayApproveUtil {
    public static boolean approve(String tid, String pgToken, int orderPk, int accountPk) {
        try {
            JsonObject body = new JsonObject();
            body.addProperty("cid", KakaoPayConfig.CID);
            body.addProperty("tid", tid);
            body.addProperty("partner_order_id", orderPk);
            body.addProperty("partner_user_id", accountPk);
            body.addProperty("pg_token", pgToken);

            URL url = new URL("https://open-api.kakaopay.com/online/v1/payment/approve");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "SECRET_KEY " + KakaoPayConfig.SECRET_KEY_DEV);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(body.toString().getBytes("UTF-8"));
            os.flush();
            os.close();

            BufferedReader br;
            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

            // 승인 성공 여부 판단
            return json.has("aid"); // aid 있으면 승인 성공

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
