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

public class EtaxSaupStateAPITest {

    public void setSapuParam(SaupParam sapuParam) {
        this.sapuParam = sapuParam;
    }

    private SaupParam sapuParam;
    //private static String TSMS_CREATE_CALL_URL = "http://localhost:9070/api/tsms-agent-messages";
    private static String ETAX_CALL_URL = "http://api.odcloud.kr/api/nts-businessman/v1/state";
    private static String API_KEY = "Hp%2FQS2VGiw1J%2FmoPr2LV6%2Bvs0%2Fm1tiDZbP5BtqtsdRBjXEFbEouNh2nDVVfIDF0UX2mZLLQEsHglWVVbpMqG8A%3D%3D";

    private static String API_ENCODING_KEY =
            "Hp%2FQS2VGiw1J%2FmoPr2LV6%2Bvs0%2Fm1tiDZbP5BtqtsdRBjXEFbEouNh2nDVVfIDF0UX2mZLLQEsHglWVVbpMqG8A%3D%3D";
    private static String API_DECODING_KEY =
            "Hp/QS2VGiw1J/moPr2LV6+vs0/m1tiDZbP5BtqtsdRBjXEFbEouNh2nDVVfIDF0UX2mZLLQEsHglWVVbpMqG8A==";

    private static String RETURN_TYPE = "JSON"; // or "XML



    public EtaxSaupStateAPITest() {}

    public EtaxSaupStateAPITest(SaupParam sapuParam) {
        this.sapuParam = sapuParam;
    }

    public static void main(String[] args) throws JsonParseException {

        EtaxSaupStateAPITest restAPITest = new EtaxSaupStateAPITest();

        SaupParam param = new SaupParam();
        param.setB_no("7851700813"); //코릴 태양광-사업자번호 (필수)
        param.setP_nm("오은채");  // 대표자명 (필수)
        param.setStart_dt("20200101"); //개업일자 (필수) - 임의(개업일자 알 수 없음)

        restAPITest.setSapuParam(param);
        restAPITest.post(EtaxSaupStateAPITest.ETAX_CALL_URL, param);

    }


    public void post(String strUrl, SaupParam param) throws JsonParseException {
        try {




            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(10000); // InputStream 읽어 오는 Timeout 시간 설정
            con.addRequestProperty("serviceKey", EtaxSaupStateAPITest.API_DECODING_KEY); //key값 설정
            con.addRequestProperty("returnType", EtaxSaupStateAPITest.RETURN_TYPE);

            con.setRequestMethod("POST");

            //json으로 message를 전달하고자 할 때
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);


            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            ObjectMapper objectMapper = new ObjectMapper();

            //List<SaupParam> businesses = new ArrayList<SaupParam>();
            //businesses.add(param);
            //ValidateRequestBody requestBody = new ValidateRequestBody();
            //requestBody.addParam(param);

            JSONObject obj1 = new JSONObject();
            obj1.put("b_no", "7851700813");
//            obj1.put("p_nm", "오은채");
//            obj1.put("start_dt", "20200101");
//            obj1.put("p_nm2", "");
//            obj1.put("b_nm", "");
//            obj1.put("corp_no", "");
//            obj1.put("b_sector", "");
//            obj1.put("b_type", "");



//            JSONArray ja = new JSONArray();
//            ja.add(obj1);

//            JSONObject obj3 = new JSONObject();
//            obj3.put("businesses", ja);

            String jsonString = objectMapper.writeValueAsString(obj1);

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
