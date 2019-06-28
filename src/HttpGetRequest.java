package com.nicchagil.httprequestdemo;

import java.io.UnsupportedEncodingException;//MD5加密异常抛出用到
import java.security.MessageDigest;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;  //UTF-8转码用到

import java.io.BufferedReader;
import java.io.InputStream;  //用于InputStream
import java.io.InputStreamReader;  //用于InputStreamReader
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;//MD5加密异常抛出用到

public class HttpGetRequest {

    /**
     * Main 请求接口1信息，返回结果-查看
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        System.out.println(doGet());
    }

    /**
     * Get Request
     * @return
     * @throws Exception
     */
    public static String StringToMd5(String psw) {
        {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(psw.getBytes("UTF-8"));
                byte[] encryption = md5.digest();

                StringBuffer strBuf = new StringBuffer();
                for (int i = 0; i < encryption.length; i++) {
                    if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                        strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                    } else {
                        strBuf.append(Integer.toHexString(0xff & encryption[i]));
                    }
                }

                return strBuf.toString();
            } catch (NoSuchAlgorithmException e) {
                return "";
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }
    }

    public static String doGet() throws Exception {
        String plateNum="辽A0US36";
        String stringToMd5 = StringToMd5("LicenseNo=" + plateNum+ "&Group=1&CityCode=37&ShowXiuLiChangType=1&Agent=126787&CustKey=yeqiao2018ec81dbc9247");
        String changeCode = URLEncoder.encode(plateNum, "utf-8");
        URL localURL = new URL("http://iu.91bihu.com/api/carinsurance/getreinfo?LicenseNo="+ changeCode +"&Group=1&CityCode=37&ShowXiuLiChangType=1&Agent=126787&CustKey=yeqiao2018&SecCode="+ stringToMd5);
        URLConnection connection = localURL.openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection)connection;

        httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        if (httpURLConnection.getResponseCode() >= 300) {
            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());//get请求失败抛出异常，可以改为《return"";》请求失败时返回空值。
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        } finally {

            if (reader != null) {
                reader.close();
            }

            if (inputStreamReader != null) {
                inputStreamReader.close();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return resultBuffer.toString();
    }

}