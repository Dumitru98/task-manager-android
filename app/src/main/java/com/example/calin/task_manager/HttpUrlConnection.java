package com.example.calin.task_manager;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }
}

public class HttpUrlConnection extends Thread{
    private static final String URL = "http://172.19.10.144:3000/api/v1/";
    private static String route = "";
    private static Map<String, String> parametre = new HashMap<String, String>();

    public HttpUrlConnection(Map<String, String> parametre, String route) {
        this.parametre = parametre;
        this.route = route;
    }

    // TODO: Read the response in the right form

    public static Thread getThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL obj = new URL(URL + route);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("POST");

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parametre));
                out.flush();
                out.close();

                int responseCode = con.getResponseCode();
                System.out.println("GET Response Code :: " + responseCode);

                Map<String, Object> responseAsHashMap = new HashMap<String, Object>();
                ObjectMapper mapper = new ObjectMapper();
                responseAsHashMap = mapper.readValue(con.getInputStream(), Map.class);

                System.out.println(responseAsHashMap.get("err"));
                if ((Integer) responseAsHashMap.get("err") == 0) {
                    System.out.println(((Map) responseAsHashMap.get("user")).get("groupNames"));
                } else {
                    System.out.println("GET request not worked");
                }
            } catch(IOException e) {
                System.out.println("ERROR: ");
                System.out.println(e);
            }
        }
    });

    public static Thread postThread = new Thread(new Runnable() {
        public void run() {
            try {
                URL url = new URL(URL + route);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");

                con.setDoOutput(true);
                DataOutputStream out = new DataOutputStream(con.getOutputStream());
                out.writeBytes(ParameterStringBuilder.getParamsString(parametre));
                out.flush();
                out.close();

                int responseCode = con.getResponseCode();
                System.out.println("POST Response Code :: " + responseCode);

                Map<String, Object> responseAsHashMap = new HashMap<String, Object>();
                ObjectMapper mapper = new ObjectMapper();
                responseAsHashMap = mapper.readValue(con.getInputStream(), Map.class);

                if ((Integer) responseAsHashMap.get("err") == 0) {
                    GeneralInfo.response = responseAsHashMap;
                } else {
                    System.out.println("GET request not worked");
                }
            } catch(IOException e) {
                System.out.println("ERROR: ");
                System.out.println(e);
            }
        }
    });

    public static void test() {
        try {
            URL obj = new URL(URL + route);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(ParameterStringBuilder.getParamsString(parametre));
            out.flush();
            out.close();

            int responseCode = con.getResponseCode();
            System.out.println("GET Response Code :: " + responseCode);

            Map<String, Object> responseAsHashMap = new HashMap<String, Object>();
            ObjectMapper mapper = new ObjectMapper();
            responseAsHashMap = mapper.readValue(con.getInputStream(), Map.class);

            System.out.println(responseAsHashMap.get("err"));
            if ((Integer) responseAsHashMap.get("err") == 0) {
                System.out.println(((Map) responseAsHashMap.get("user")).get("groupNames"));
            } else {
                System.out.println("GET request not worked");
            }
        } catch(IOException e) {
            System.out.println("ERROR: ");
            System.out.println(e);
        }
    }
}
