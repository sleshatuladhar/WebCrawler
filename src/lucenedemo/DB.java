package lucenedemo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DB {

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_crawler", "root", "");
		return con;
	}
	
	public static void clearingDatabaseRecord() throws ClassNotFoundException, SQLException {
		String sql="DELETE from record";
		Connection con=getConnection();
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.executeUpdate();
		
		con.close();
	}

	HashSet<String> links = new HashSet<String>();
	ArrayList<String> temporary = new ArrayList<String>();

	void getLinks(String rootUrl) {
		if (!links.contains(rootUrl)) {
			links.add(rootUrl);
			System.out.println(rootUrl);

		}

		try {
			Document document = Jsoup.connect(rootUrl).get();

			Elements elements = document.select("a[href]");
			Elements heading = document.select("head");
			//Elements article = document.select("article");
			//Elements para = document.select("p");
			System.out.println("Heading : " + heading.text());
			//System.out.println("Para : " + para.text());
			//System.out.println("Article : " + article.text());

			getTitle(rootUrl, heading.text());
			/*for(Element page:elements ) {
				//getLinks(page.attr("abs:href"));
				getLinks(page.absUrl("href"));
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getTitle(String rootUrl, String head) throws SQLException {

		Document document;
		try {
			String sql = "INSERT INTO record(urls,title,head) VALUES (?,?,?)";
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			document = Jsoup.connect(rootUrl).get();
			//Elements articleLinks = document.select("h2 a[href]");
			Elements articleLinks = document.select("a[href]");

			
			for (Element title : articleLinks) {
				if (!temporary.contains(title.text())) {
					temporary.add(title.text());
					System.out.println("Title : " + title.text());
					stmt.setString(1, rootUrl);
					stmt.setString(2, title.text().trim());
					stmt.setString(3, head.trim());
					//stmt.setString(4, para.trim());
					

					stmt.executeUpdate();
				}
			}

			con.close();
			
			System.out.println();

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	/*public static void main(String[] args) {
		new DB().getLinks("http://www.bbc.com");
	}*/
}
