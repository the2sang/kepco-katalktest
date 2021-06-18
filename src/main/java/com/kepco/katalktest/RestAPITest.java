package com.kepco.katalktest;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;


public class RestAPITest {

    public void setTsmsAgentMessageDTO(TsmsAgentMessageDTO tsmsAgentMessageDTO) {
        this.tsmsAgentMessageDTO = tsmsAgentMessageDTO;
    }

    private TsmsAgentMessageDTO tsmsAgentMessageDTO;
    private static String TSMS_CREATE_CALL_URL = "http://localhost:9070/api/tsms-agent-messages";
    public RestAPITest() {}

    public RestAPITest(TsmsAgentMessageDTO tsmsAgentMessageDTO) {
        this.tsmsAgentMessageDTO = tsmsAgentMessageDTO;
    }

    public static void main(String[] args) throws JsonParseException {

        RestAPITest restAPITest = new RestAPITest();
        TsmsAgentMessageDTO testDto = new TsmsAgentMessageDTO();
        testDto.setServiceSeqno(1810013776L);
        testDto.setSendMessage("<한국전력공사> 전자세금계산서 발행 시 종사업장번호 필수 입력 알림\n" +
                "\n" +
                "▧ 종사업장번호 (★필수입력)\n" +
                " ☞ 임실지사:140\n" +
                "※ 미입력 시 전자세금계산서 미수신되어 발전대금 지급 불가\n" +
                "\n" +
                "[종사업장번호 입력방법] 거래처 관리 → 건별 등록 → 사업자등록번호 입력(120-82-00052) → 확인 → 종사업자 140 선택\n" +
                "[종사업장번호 입력문의] 국세청 홈택스 : 국번없이 ☎126 → 1번 → 2번 → 1번 선택 후 상담사 연결\n" +
                "\n" +
                "관련 문의사항은 한국전력공사 임실지사 (☎063-640-5212)로 연락부탁드립니다.\n" +
                "감사합니다.");
        testDto.setBackupMessage("백업메세지");
        testDto.setBackupProcessCode("000");
        testDto.setMessageType("002");
        testDto.setContentsType("004");
        testDto.setReceiveMobileNo("01073994958");
        testDto.setCallbackNo("123");
        testDto.setJobType("R00");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());


        testDto.setSendReserveDate(today);
        testDto.setTemplateCode("[CRM]000001");
        testDto.setRegisterDate(today);
        testDto.setRegisterBy("강희철");
        testDto.setImgAttachFlag("N");
        testDto.setCustData1("EDI-PPA");
        testDto.setCustData2("담당자-강희철");
        testDto.setCustData3("0910-7580");
        testDto.setCustData4("073301");
        testDto.setCustBackupFlag("N");
        testDto.setCustMessageType("S");
        testDto.setSendFlag("N");

        restAPITest.setTsmsAgentMessageDTO(testDto);
        restAPITest.post(RestAPITest.TSMS_CREATE_CALL_URL, testDto);

    }


    public void post(String strUrl, TsmsAgentMessageDTO dto) throws JsonParseException {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(10000); //서버에 연결되는 Timeout 시간 설정
            con.setReadTimeout(10000); // InputStream 읽어 오는 Timeout 시간 설정
            //con.addRequestProperty("x-api-key", RestTestCommon.API_KEY); //key값 설정

            con.setRequestMethod("POST");

            //json으로 message를 전달하고자 할 때
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            ObjectMapper objectMapper = new ObjectMapper();

            String dtoJsonString = objectMapper.writeValueAsString(dto);

            wr.write(dtoJsonString); //json 형식의 message 전달
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
