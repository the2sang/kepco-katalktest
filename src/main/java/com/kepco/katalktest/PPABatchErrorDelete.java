package com.kepco.katalktest;


//import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

//@Slf4j
public class PPABatchErrorDelete {

    public static void main(String[] args) throws IOException {

        PPABatchErrorDelete app = new PPABatchErrorDelete();

        //String fileName = "database.properties";
        String fileName = "delete-list.txt";

        System.out.println("\ngetResource : " + fileName);

        System.out.println("getResourceAsStream : " + fileName);
        InputStream is = app.getFileFromResourceAsStream(fileName);
        String batchids = printInputStream(is);

        String ppaDeleteUrl = "http://localhost:9900/api/ppa-batch-starting/" + batchids;

        System.out.println(ppaDeleteUrl);

        app.requestDeletePPA(ppaDeleteUrl);

//        URL url = new URL(ppaDeleteUrl);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//
//        con.setDoOutput(true);
//        DataOutputStream out = new DataOutputStream(con.getOutputStream());
//        //out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
//        out.flush();
//        out.close();


//        try {
//            File file = app.getFileFromResource(fileName);
//            printFile(file);
//        } catch (URISyntaxException ex) {
//            ex.printStackTrace();
//        }


    }

    public void requestDeletePPA(String requestURL) {
        try {
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
           // getRequest.addHeader("x-api-key", RestTestCommon.API_KEY); //KEY 입력

            HttpResponse response = client.execute(getRequest);

            //Response 출력
            if (response.getStatusLine().getStatusCode() == 200) {
                ResponseHandler<String> handler = new BasicResponseHandler();
                String body = handler.handleResponse(response);
                System.out.println(body);
            } else {
                System.out.println("response is error : " + response.getStatusLine().getStatusCode());
            }

        } catch (Exception e){
            System.err.println(e.toString());
        }
    }


    // get a file from the resources folder
    // works everywhere, IDEA, unit test and JAR file.
    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    /*
        The resource URL is not working in the JAR
        If we try to access a file that is inside a JAR,
        It throws NoSuchFileException (linux), InvalidPathException (Windows)

        Resource URL Sample: file:java-io.jar!/json/file1.json
     */
    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }

    // print input stream
    private static String printInputStream(InputStream is) {

        String batchids = "";

        try (InputStreamReader streamReader =
                     new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            StringBuffer sf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                sf.append(line + ",");
            }

            batchids = sf.toString();

            batchids = Optional.ofNullable(batchids)
                    .filter(s -> s.length() != 0)
                    .map(s -> s.substring(0, s.length() - 1))
                    .orElse(batchids);
            System.out.println(batchids);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return batchids;
    }

//    private static String makeBatchid(String) {
//
//
//
//    }

    // print a file
    private static void printFile(File file) {

        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
            lines.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
