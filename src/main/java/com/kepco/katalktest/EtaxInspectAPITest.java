package com.kepco.katalktest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class EtaxInspectAPITest {

    public void setSapuParam(SaupParam sapuParam) {
        this.sapuParam = sapuParam;
    }

    private SaupParam sapuParam;
    //private static String TSMS_CREATE_CALL_URL = "http://localhost:9070/api/tsms-agent-messages";
    private static String ETAX_CALL_URL = "http://api.odcloud.kr/api/nts-businessman/v1/validate";
    private static String API_KEY = "";

    private static String API_ENCODING_KEY =
            "Hp%2FQS2VGiw1J%2FmoPr2LV6%2Bvs0%2Fm1tiDZbP5BtqtsdRBjXEFbEouNh2nDVVfIDF0UX2mZLLQEsHglWVVbpMqG8A%3D%3D";
    //       "Hp%2FQS2VGiw1J%2FmoPr2LV6%2Bvs0%2Fm1tiDZbP5BtqtsdRBjXEFbEouNh2nDVVfIDF0UX2mZLLQEsHglWVVbpMqG8A%3D%3D"
    private static String API_DECODING_KEY =
            "Hp/QS2VGiw1J/moPr2LV6+vs0/m1tiDZbP5BtqtsdRBjXEFbEouNh2nDVVfIDF0UX2mZLLQEsHglWVVbpMqG8A==";

    private static String RETURN_TYPE = "JSON"; // or "XML



    public EtaxInspectAPITest() {}

    public EtaxInspectAPITest(SaupParam sapuParam) {
        this.sapuParam = sapuParam;
    }

    public static void main(String[] args) throws JsonParseException {

        EtaxInspectAPITest restAPITest = new EtaxInspectAPITest();

        restAPITest.post(EtaxInspectAPITest.ETAX_CALL_URL);

    }


    public void post(String strUrl)  {

        try {

            String serviceUrl = strUrl + "?serviceKey=" + EtaxInspectAPITest.API_ENCODING_KEY + "&returnType=JSON";


            URL url = new URL(serviceUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(10000); // InputStream 읽어 오는 Timeout 시간 설정
//            con.addRequestProperty("serviceKey", EtaxInspectAPITest.API_KEY); //key값 설정
//            con.addRequestProperty("returnType", EtaxInspectAPITest.RETURN_TYPE);

            con.setRequestMethod("POST");

            //json으로 message를 전달하고자 할 때
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);


            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            ObjectMapper objectMapper = new ObjectMapper();

            JSONObject obj1 = new JSONObject();
            obj1.put("b_no", "1081921785");
            obj1.put("p_nm", "김찬희");
            obj1.put("start_dt", "20110701");
            obj1.put("p_nm2", "");
            obj1.put("b_nm", "");
            obj1.put("corp_no", "");
            obj1.put("b_sector", "");
            obj1.put("b_type", "");


            JSONArray ja = new JSONArray();
            ja.add(obj1);
        //    ja.add(obj2);

            JSONObject obj3 = new JSONObject();
            obj3.put("businesses", ja);

            String jsonString = objectMapper.writeValueAsString(obj3);

            wr.write(jsonString); //json 형식의 message 전달
            wr.flush();

            StringBuilder sb = new StringBuilder();
            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //Stream을 처리해줘야 하는 귀찮음이 있음.
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"));
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                br.close();
                System.out.println("" + sb.toString());
            } else {
                System.out.println(con.getResponseMessage());
            }
        } catch (Exception e){
            System.err.println(e.toString());
        }
    }


}
