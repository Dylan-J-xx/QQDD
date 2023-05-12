package com.qqdd.web.controller.system;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.qqdd.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @auther jxy
 * @date 2023/5/9
 * @apiNote
 */
@RestController
@RequestMapping("")
public class SysSomeController {
    @GetMapping("/ai/o.php/{msg}")
    public AjaxResult chat(@PathVariable String msg) throws JsonProcessingException {
        AjaxResult ajax = AjaxResult.success();
        String jsonData = callInterface(msg);
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = mapper.readValue(jsonData, JSONObject.class);
        ajax.put("data", obj.getString("answer"));
        return ajax;
    }

    public String callInterface(String msg) {
        try {
            URL url = new URL("http://api.caonm.net/api/ai/o.php?msg=" + msg);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/lishi/jt.php")
    public AjaxResult today() throws JsonProcessingException {
        AjaxResult ajax = AjaxResult.success();
        String jsonData = getDataFromThirdApi();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = mapper.readValue(jsonData, JSONObject.class);
        ajax.put("data", obj.getJSONObject("data"));
        return ajax;
    }

    public String getDataFromThirdApi() {
        try {
            String url = "https://api.caonm.net/api/lishi/jt.php";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/holiday")
    public AjaxResult holiday() throws JsonProcessingException {
        AjaxResult ajax = AjaxResult.success();
        String jsonData = getHoliday();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = mapper.readValue(jsonData, JSONObject.class);
        ajax.put("data", obj.getString("tts"));
        return ajax;


    }

    public String getHoliday() {
        try {
            String url = "http://timor.tech/api/holiday/tts";
            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/hotlist")
    public AjaxResult hotlist() throws JsonProcessingException {
        AjaxResult ajax = AjaxResult.success();
        String jsonData = getHotList();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = mapper.readValue(jsonData, JSONObject.class);
        ajax.put("data", obj.getJSONObject("result"));
        return ajax;
    }

    public String getHotList() {
        String tianapi_data = "";
        try {
            URL url = new URL("https://apis.tianapi.com/toutiaohot/index");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            OutputStream outputStream = conn.getOutputStream();
            String content = "key=6e237d180a5fd33d4d33886468094015";
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder tianapi = new StringBuilder();
            String temp = null;
            while (null != (temp = bufferedReader.readLine())) {
                tianapi.append(temp);
            }
            tianapi_data = tianapi.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tianapi_data;
    }


    @GetMapping("/hotsearch")
    public AjaxResult hotsearch() throws JsonProcessingException {
        AjaxResult ajax = AjaxResult.success();
        String jsonData = getHotSearch();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject obj = mapper.readValue(jsonData, JSONObject.class);
        ajax.put("data2", obj.getJSONObject("result"));
        return ajax;
    }

    public String getHotSearch() {
        String tianapi_data = "";
        try {
            URL url = new URL("https://apis.tianapi.com/networkhot/index");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            OutputStream outputStream = conn.getOutputStream();
            String content = "key=6e237d180a5fd33d4d33886468094015";
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder tianapi = new StringBuilder();
            String temp = null;
            while (null != (temp = bufferedReader.readLine())) {
                tianapi.append(temp);
            }
            tianapi_data = tianapi.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tianapi_data;
    }
}
