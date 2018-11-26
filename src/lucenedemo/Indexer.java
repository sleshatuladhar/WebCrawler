package lucenedemo;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.sql.Statement;

public class Indexer {

	static String indexDir = "C:\\Users\\slesh\\eclipse-workspace\\WebCrawler";

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_crawler", "root", "");
		return con;
	}

	
	public void indexingWeb() throws SQLException,ClassNotFoundException {
		Connection con = getConnection();
		String query = "Select * from record";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			IndexWriter writer = CreateWriter();
			long startTime = new Date().getTime();
			while (rs.next()) {
				Document document = new Document();
				document.add(new TextField("url", rs.getString("urls").toString(), Field.Store.YES));
				document.add(new TextField("title", rs.getString("title").toString(), Field.Store.YES));
				document.add(new TextField("head", rs.getString("head").toString(), Field.Store.YES));
				//document.add(new TextField("para", rs.getString("para").toString(), Field.Store.YES));
				//document.add(new TextField("article", rs.getString("article").toString(), Field.Store.YES));
				writer.addDocument(document);
			}
			long endtime = new Date().getTime();
			writer.commit();
			writer.close();
			con.close();
			System.out.println("time taken to index : " + (endtime - startTime));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static IndexWriter CreateWriter() {
		FSDirectory fsDirectory;
		IndexWriter writer = null;
		try {
			fsDirectory = FSDirectory.open(Paths.get(indexDir));
			IndexWriterConfig conf = new IndexWriterConfig(new StandardAnalyzer());
			writer = new IndexWriter(fsDirectory, conf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer;
	}

}
