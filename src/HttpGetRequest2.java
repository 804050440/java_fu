//package com.nicchagil.httprequestdemo;

import net.sf.json.JSONObject;//用于json字符串转换，JSONObject方法

import java.io.UnsupportedEncodingException;//MD5加密异常抛出用到
import java.security.MessageDigest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;  //UTF-8转码用到

import java.io.BufferedReader;
import java.io.InputStream;  //用于InputStream
import java.io.InputStreamReader;  //用于InputStreamReader
import java.security.NoSuchAlgorithmException;//MD5加密异常抛出用到
import java.sql.*;
import java.util.Map;

public class HttpGetRequest2 {

*
     * Main 循环请求数据表中车辆的接口1信息，返回结果自动解析输出
     * （弊端，查询失败自动停止"通过1.修改请求失败抛出异常改为返回空值和2.空值转JSON报错，已将问题解决"）
     * 这个带解析哦（jx_ins方法）！！！
     * @param args
     * @throws Exception


    public static void main(String[] args) throws Exception {
        Connection con;
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mixcall?user=root&password=1&useUnicode=true&characterEncoding=UTF8&serverTimezone=GMT%2B8");
        Statement statement = con.createStatement();
        //要执行的SQL语句
        String sql = "select NO,name,name2 from student where NO in (6,7,8) order by NO";
        //3.ResultSet类，用来存放获取的结果集！！
        ResultSet rs = statement.executeQuery(sql);
        while(rs.next()) {
            String aaa = doGet(rs.getString("name"));
            System.out.println(aaa);
            String bbb = rs.getString("NO");
            System.out.println(bbb);
            jx_ins(aaa);
            String sql1 = "update student set name2 = '"+ aaa +"' where NO="+ bbb +"";
            PreparedStatement ps = con.prepareStatement(sql1);
            ps.executeUpdate(sql1);

            ps.close();

        }
        rs.close();
        con.close();

        //System.out.println(doGet());
    }

*
     * Get Request
     * @return
     * @throws Exception


    //MD5加密方法
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
//发送GET请求
    public static String doGet(String pltn) throws Exception {
        String plateNum = pltn;
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
//            throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
                return "";
        }

        try {
            inputStream = httpURLConnection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);

            while ((tempLine = reader.readLine()) != null) {
                resultBuffer.append(tempLine);
            }

        }catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            //return plateNum;
        }
        finally {

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
//解析返回结果并输出
    public static void jx_ins (String result)
    {

        if(result == null || (result != null && result.equalsIgnoreCase("")))
            return;
        JSONObject json = JSONObject.fromObject(result);
        if(!(json.get("BusinessStatus").toString().equalsIgnoreCase("1") || json.get("BusinessStatus").toString().equalsIgnoreCase("3"))){
            return;
        }

        Object userInfoObject = json.get("UserInfo");
        Map<String,Object> userInfoMap = (Map)userInfoObject;

        String carUsedType = userInfoMap.get("CarUsedType").toString();//使用性质
        String qjLicenseNo = (String) userInfoMap.get("LicenseNo");//车牌号
        String licenseOwner = (String)userInfoMap.get("LicenseOwner");//车主姓名

        System.out.println(carUsedType);
        System.out.println(qjLicenseNo);
        System.out.println(licenseOwner);
    }

}
