package cn.dsx.util;

import java.util.Vector;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetProxy {

	/**
	 * 从西刺代理网站抓取代理IP
	 * @param page 第几页
	 * @return
	 * @throws Exception
	 */
	public static Vector<HttpHost> getProxy(int page) throws Exception{
		Vector<HttpHost> proxys = new Vector<HttpHost>();
		String ip,port;
		MyHttpClient client = new MyHttpClient();
		client.addHeader("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
		String result = client.get("http://www.xicidaili.com/nt/"+page,"UTF-8");
		Document parse = Jsoup.parse(result);
		Elements elementsTr = parse.getElementsByTag("tr");
		int size = elementsTr.size();
		for (int i=1; i<size; i++) {
			Element element = elementsTr.get(i);
			Elements elementsTd = element.getElementsByTag("td");
			ip = elementsTd.get(1).text();
			port = elementsTd.get(2).text();
			proxys.add(new HttpHost(ip,Integer.parseInt(port)));
		}
		return proxys;
	}
}
