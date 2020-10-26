package com.info.application;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class VoteTask extends AsyncTask<String, Void, String> {

    private String doVote(String voteType) {
        String response_result = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("r=" + URLEncoder.encode(voteType, "utf-8"));

            byte[] data = stringBuffer.toString().getBytes();
            String path = "http://localhost:8080/vote/GetVote";
            URL url = new URL(path);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置请求体的长度
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(data);
            //获得服务器的响应码
            int response = connection.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                //处理服务器的响应结果
                response_result = inputStream.toString();
                Log.i("Vote", "Response Result : " + response_result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response_result;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.i("---TAG---","Enter doInBackground()");
        String result = doVote(strings[0]);
        return result;
    }
}
