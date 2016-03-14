package Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class App {
    private static URL link;
    private String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://webapps-qa.homedepot.com/PacManWS/rs/request/excel/exportNew?versionNumber=1&langCode=en_US&requestID=10003041&timestamp=792";

    static {
        //for localhost testing only
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier() {
                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        if (hostname.equals("webapps-qa.homedepot.com")) {
                            return true;
                        }
                        return false;
                    }
                });
    }

    public static void main(String[] args) throws IOException {
        String fileName = "file.xls"; //The file that will be saved on your computer
        URL link = new URL(GET_URL); //The file that you want to download.
        System.out.println("Downloading file - Latest ");
        downLoadFile(fileName , link );
        sendGET();
        System.out.println("GET DONE");
        System.out.println("Download Done");
    }

    private static void sendGET() throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
    }

    private static void downLoadFile(String filename, URL testurl) throws IOException {
        //Code to download
        InputStream in = new BufferedInputStream(testurl.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fos.write(response);
        fos.close();
        //End download code
        System.out.println("Download Finished");

    }
}