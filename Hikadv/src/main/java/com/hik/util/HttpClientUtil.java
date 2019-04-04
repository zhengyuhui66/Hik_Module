package com.hik.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import net.sf.json.JSONObject;

@Component
public class HttpClientUtil {
	
	public HttpClientUtil(){}
	
	public static void main(String[] args) {
	 HttpClientUtil h = new HttpClientUtil();
	 String appid="wxc96cf77dc69519d4";
	 String appsecret="958eef8e45455fd0245aa71002221121";
//	String accessToken= h.requestByGetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret);
//	JSONObject json =JSONObject.fromObject(accessToken);
	
	 String accesstoken="wSMuz2ReCQVOph4ttAHhsFUFpo18BZwP-C4_x3XaeQdwPyl59Hve1y1vjkMXclLBGx0U7rLuus3c642-et8ETZGKhmLRs8GEq8X5_SQSr_ZdhM3mXrcI_x9nla1isFM9QGDhAHACSP";//json.getString("access_token");
	 System.out.println("accessToken:"+accesstoken);
//	 String accesstoken="HcqsuJ-TSKNxsHTfd56feLvoehXLUzlGV1ytyAaXRGY6ujBS479zKCuZaY-rSe6sOqdNyLVbMBFg3Z9S0F_PDhVpRqNH3oBxtlicSI8g8gALCf_qRqp3yUmIXK8y65oCUQNdAHAFBY";
	 String url="https://api.weixin.qq.com/bizwifi/device/list?access_token="+accesstoken;
//     List<NameValuePair> list = new ArrayList<NameValuePair>();
//     list.add(new BasicNameValuePair("pageindex","1"));
//     list.add(new BasicNameValuePair("pagesize", "10"));
	 JSONObject jsonParam = new JSONObject();  
//   jsonParam.put("pageindex",1);
//   jsonParam.put("pagesize",10);
     JSONObject result= h.requestByPostMethod(jsonParam, url);
     System.out.println("reuslt:"+result);
	}
    /**
     * ͨ��GET��ʽ����http����
     */
    public String requestByGetMethod(String url){
        //����Ĭ�ϵ�httpClientʵ��
        CloseableHttpClient httpClient = getHttpClient();
        String result="";
        try {
            //��get��������http����
            HttpGet get = new HttpGet(url);
            System.out.println("ִ��get����:...."+get.getURI());
            CloseableHttpResponse httpResponse = null;
            //����get����
            httpResponse = httpClient.execute(get);
            try{
                //responseʵ��
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                	  result=EntityUtils.toString(entity);
                    System.out.println("��Ӧ״̬��:"+ httpResponse.getStatusLine());
                    System.out.println("-------------------------------------------------");
                    System.out.println("��Ӧ����:" +result);
                    System.out.println("-------------------------------------------------");    
                  
                }
            }
            finally{
                httpResponse.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                closeHttpClient(httpClient);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return result;
    }
     
     
    /**
     * POST��ʽ����http����
     */
    public JSONObject requestByPostMethod(JSONObject jsonParam,String url){
        CloseableHttpClient httpClient = getHttpClient();
        String result=null;
        try {
            HttpPost post = new HttpPost(url);          //�������ϱ�����ĳ������������
            //���������б�
            StringEntity en = new StringEntity(jsonParam.toString(),"utf-8");//���������������    
            en.setContentEncoding("UTF-8");    
            en.setContentType("application/json");   
            post.setEntity(en);
            System.out.println("POST ����...." + post.getURI());
            post.setHeader("Content-type", "application/json");  
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try{
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                	 result=EntityUtils.toString(entity);
                    System.out.println("-------------------------------------------------------");
                    System.out.println(result);
                    System.out.println("-------------------------------------------------------");   
                }
            } finally{
                httpResponse.close();
            }
             
        } catch( UnsupportedEncodingException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try{
                closeHttpClient(httpClient);                
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        JSONObject json=null;
        if(StringUtils.isNotBlank(result)){
        	json=JSONObject.fromObject(result);
        }
        return json;
    }
     
    private CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }
     
    private void closeHttpClient(CloseableHttpClient client) throws IOException{
        if (client != null){
            client.close();
        }
    }
}  