package com.shanxin.utils.httpClientUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public class HttpClientUtils {

	// httpclient 连接时间 请求时间 等待时间
	private static Integer CONNECTION_REQUEST_TIMEOUT = Integer
			.valueOf(PropertyUtil.getPropertyByName("CONNECTION_REQUEST_TIMEOUT"));
	private static Integer CONNECT_TIMEOUT = Integer.valueOf(PropertyUtil.getPropertyByName("CONNECT_TIMEOUT"));
	private static Integer SOCKET_TIMEOUT = Integer.valueOf(PropertyUtil.getPropertyByName("SOCKET_TIMEOUT"));

	
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT)
			.setSocketTimeout(SOCKET_TIMEOUT).build();

	/**
	 * GET PUT POST PUT DELETE
	 * @param requestMethod 
	 * @param url
	 * @param params
	 * @param header
	 * @return
	 */
	public static String sendHttp(HttpRequestMethedEnum requestMethod, String url, Map<String, Object> params,
			Map<String, String> header) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		String responseContent = null;

		HttpRequestBase request = requestMethod.createMethod(url);
		request.setConfig(requestConfig);

		if (null != header) {
			for (Map.Entry<String, String> entry : header.entrySet()) {
				request.setHeader(entry.getKey(), entry.getValue());
			}
		}

		try {

			if (null != params) {
				
				((HttpEntityEnclosingRequest) request).setEntity(
						new StringEntity(JSON.toJSONString(params), ContentType.create("application/json", "UTF-8")));
				/*((HttpEntityEnclosingRequest) request).setEntity(
						new StringEntity(JSON.toJSONString(params), Charset.forName("UTF-8")));*/
			}

			httpResponse = httpClient.execute(request);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					responseContent = EntityUtils.toString(entity, "UTF-8");
				}
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;

	}
	
	/**
	 * GET
	 * @param url
	 * @param header
	 * @return
	 * @throws Exception 
	 */
	public static String httpGet(String url){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		String responseContent = null;
		
		HttpGet request = new HttpGet(url);
		request.setConfig(requestConfig);
        try {
    		httpResponse = httpClient.execute(request);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					responseContent = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
		
	}
	
	/**
	 * POST
	 * Content-Type : application/x-www-form-urlencoded
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpPost(String url,Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		String responseContent = null;
		
		HttpPost request = new HttpPost(url);
		request.setConfig(requestConfig);
		//request.setHeader("Accept", "application/json"); 
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
        try {
        	if(null != params) {
    			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
    			for(Map.Entry<String, Object> entry : params.entrySet()) {
    				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
    			}
    			request.setEntity(new UrlEncodedFormEntity(nvps,Charset.forName("UTF-8")));
    		}
        	
    		httpResponse = httpClient.execute(request);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					responseContent = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
		
	}
	
	/**
	 * POST
	 * Content-Type : application/json
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpPostJson(String url,Map<String, Object> params) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse httpResponse = null;
		String responseContent = null;
		
		HttpPost request = new HttpPost(url);
		request.setConfig(requestConfig);
		//request.setHeader("Accept", "application/json"); 
		request.setHeader("Content-Type", "application/json");
		
        try {
        	
           if (null != params) {
				((HttpEntityEnclosingRequest) request).setEntity(
						new StringEntity(JSON.toJSONString(params), ContentType.create("application/json", "UTF-8")));	
			}
    		httpResponse = httpClient.execute(request);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					responseContent = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
		
	}
	
	
	/**
	 * GET NO SSL
	 * @param url
	 * @param header
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 * 
	 */
	public static String httpGetNoSSL(String url) throws KeyManagementException, NoSuchAlgorithmException{
		
        CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
		
		CloseableHttpResponse httpResponse = null;
		String responseContent = null;
		
		HttpGet request = new HttpGet(url);
		request.setConfig(requestConfig);
        try {
    		httpResponse = httpClient.execute(request);
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = httpResponse.getEntity();
				if (null != entity) {
					responseContent = EntityUtils.toString(entity, "UTF-8");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpResponse != null) {
					httpResponse.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseContent;
		
	}
	
	public static void main(String[] args) throws Exception {
		//String url = "http://localhost:8080/cslp/testGet?name=赵铁柱&password=123456";
		String url = "http://localhost:8080/cslp/testPostTwo?name=赵铁柱&password=123456";
		String url1 = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=17611258096";
		String url2 = "https://192.168.9.145:8081/cslp/testGet?name=123&password=456";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", "铁柱");
		params.put("password", "456123");
		Map<String, String> header = new HashMap<String,String>();
		header.put("Content-Type", "application/json");
		//header.put("Content-Type", "application/x-www-form-urlencoded");
		
		///String result = HttpClientUtils.sendHttp(HttpRequestMethedEnum.POST, url, params, header);
		//String result = HttpClientUtils.httpPost(url, params);
		String result = HttpClientUtils.httpGetNoSSL(url2);

		System.out.println(result);
	}

}
