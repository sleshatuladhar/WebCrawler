package lucenedemo;

import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {

	HashSet<String> links=new HashSet<String>();
	
	void getLinks(String rootUrl) {
		if(!links.contains(rootUrl)) {
			links.add(rootUrl);
			System.out.println(rootUrl);
			getArticles(rootUrl);
		}
		
		try {
			//parse html contents
			Document document=Jsoup.connect(rootUrl).get();
			
			//getting links
			System.out.println("Body : "+document.body().text());
			Elements elements=document.select("a[href]");
			Elements element2=document.select("head");
			
			System.out.println("Body : "+element2.text());
			for(Element page:elements ) {
				getLinks(page.attr("abs:href"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getArticles(String x) {
		
		Document document;
		try {
			document=Jsoup.connect(x).get();
			Elements articleLinks=document.select("h2 a[href]");
			
			for(Element article:articleLinks) {
				System.out.println();
				
				ArrayList<String> temporary=new ArrayList<String>();
				temporary.add(article.text());
				System.out.println("Title : "+ article.text());
			}
			System.out.println();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/*public static void main(String[] args) {
		Crawler crawler=new Crawler();
		crawler.getLinks("http://www.bbc.com");
	}*/
}
