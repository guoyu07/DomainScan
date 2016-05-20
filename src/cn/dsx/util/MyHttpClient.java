package cn.dsx.util;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class MyHttpClient {

	private CloseableHttpClient httpClient;						//模拟客户端
	private HttpGet httpGet;									//Get请求
	private HttpPost httpPost;									//Post请求
	private Builder builder;									//设置参数
	private CloseableHttpResponse response;						//返回的Response
	private String result;										//返回的字符串
	
	public MyHttpClient() {
		httpClient = HttpClients.createDefault();
		httpGet = new HttpGet();
		httpPost = new HttpPost();
		builder = RequestConfig.custom();
	}
	
	public Builder setProxy(HttpHost host){
		return builder.setProxy(host);
	}
	
	public Builder setSocketTimeout(int socketTimeout){
		return builder.setSocketTimeout(socketTimeout);
	}
	
	public Builder setConnectTimeout(int connectTimeout){
		return builder.setConnectTimeout(connectTimeout);
	}
	
	public void addHeader(String name,String value){
		httpGet.addHeader(name, value);
		httpPost.addHeader(name, value);
	}
	
	public String get(String url,String charset) throws Exception{
		httpGet.setURI(URI.create(url));
		RequestConfig config = builder.build();
		httpGet.setConfig(config);
		response = httpClient.execute(httpGet);
		result = EntityUtils.toString(response.getEntity(), charset);
		httpGet.releaseConnection();
		return result;
	}
	
	public String post(String url,Map<String,String> param,String charset) throws Exception{
		httpPost.setURI(URI.create(url));
		List<BasicNameValuePair> formparams = new ArrayList<BasicNameValuePair>();
		for (Entry<String, String> entry : param.entrySet()) {
			formparams.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		httpPost.setEntity(new UrlEncodedFormEntity(formparams, charset));
		response = httpClient.execute(httpPost);
		result = EntityUtils.toString(response.getEntity(),charset);
		httpPost.releaseConnection();
		return result;
	}
}
