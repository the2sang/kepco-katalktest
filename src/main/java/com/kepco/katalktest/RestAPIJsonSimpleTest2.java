package com.kepco.katalktest;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RestAPIJsonSimpleTest2 {

    private final String USER_AGENT = "Mozilla/5.0";

    public void setTsmsAgentMessageDTO(TsmsAgentMessageDTO tsmsAgentMessageDTO) {
        this.tsmsAgentMessageDTO = tsmsAgentMessageDTO;
    }

    private TsmsAgentMessageDTO tsmsAgentMessageDTO;
    private static String TSMS_CREATE_CALL_URL = "http://localhost:9070/api/tsms-agent-messages";
    //private static String TSMS_CREATE_CALL_URL = "http://168.78.201.129:9070/api/tsms-agent-messages";
    public RestAPIJsonSimpleTest2() {}

    public RestAPIJsonSimpleTest2(TsmsAgentMessageDTO tsmsAgentMessageDTO) {
        this.tsmsAgentMessageDTO = tsmsAgentMessageDTO;
    }

    public static void main(String[] args) throws Exception {

        RestAPIJsonSimpleTest2 restAPITest = new RestAPIJsonSimpleTest2();

        String callUrl = "http://localhost:9070/api/tsms-edi-message?";

        StringBuffer paramSf = new StringBuffer();
        String serviceSeqno = "1810013776";
        String sendMessage = "test";
        String backupMessage = "백업메세지"; //한글 텍스트는 URLEncoder.encode 필요
        String backupProcessCode = "000";
        String messageType = "002";
        String contentsType = "004";
        String receiveMobileNo = "01073994958";
        String callbackNo = "123";
        String jobType = "R00";
        String templateCode = "[CRM]00000";
        String registerBy = "강희철"; //한글 텍스트는 URLEncoder.encode 필요
        String imgAttachFlag = "N";
        String custData1 = "EDI-PPA";
        String custData2 = "담당자-강희철"; //한글 텍스트는 URLEncoder.encode 필요
        String custData3 = "0910-7580";
        String custData4 = "073301";
        String custBackupFlag = "N";
        String custMessageType = "S";
        String sendFlag = "N";

        paramSf.append("serviceSeqno=" + serviceSeqno + "&");
        paramSf.append("sendMessage=" + sendMessage + "&");
        paramSf.append("backupMessage=" + URLEncoder.encode(backupMessage, "UTF-8") + "&");
        paramSf.append("backupProcessCode=" + backupProcessCode + "&");
        paramSf.append("messageType=" + messageType + "&");
        paramSf.append("contentsType=" + contentsType + "&");
        paramSf.append("receiveMobileNo=" + receiveMobileNo + "&");
        paramSf.append("callbackNo=" + callbackNo + "&");
        paramSf.append("jobType=" + jobType + "&");
        paramSf.append("templateCode=" + templateCode + "&");
        paramSf.append("registerBy=" + URLEncoder.encode(registerBy, "UTF-8") + "&");
        paramSf.append("imgAttachFlag=" + imgAttachFlag + "&");
        paramSf.append("custData1=" + custData1 + "&");
        paramSf.append("custData2=" + URLEncoder.encode(custData2, "UTF-8") + "&");
        paramSf.append("custData3=" + custData3 + "&");
        paramSf.append("custData4=" + custData4 + "&");
        paramSf.append("custBackupFlag=" + custBackupFlag + "&");
        paramSf.append("custMessageType=" + custMessageType + "&");
        paramSf.append("sendFlag=" + sendFlag);

        restAPITest.sendSms(callUrl + paramSf.toString());

    }

    private void sendSms(String targetUrl) throws Exception {
        URL url = new URL(targetUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET"); // optional default is GET
        con.setRequestProperty("User-Agent", USER_AGENT); // add request header

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close(); // print result
        System.out.println("HTTP 응답 코드 : " + responseCode);
        System.out.println("HTTP body : " + response.toString());
    }


}
