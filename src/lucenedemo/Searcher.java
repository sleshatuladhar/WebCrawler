package lucenedemo;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RegexpQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SynonymQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.QueryBuilder;


public class Searcher {

	private static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/web_crawler", "root", "");
		return con;
	}
	
	public static void clearingDatabaseResult() throws ClassNotFoundException, SQLException {
		String sql="DELETE from result";
		Connection con=getConnection();
		PreparedStatement stmt=con.prepareStatement(sql);
		stmt.executeUpdate();
		
		con.close();
	}

	public static void resultingTitle(String urls, String title, String head)
			throws ClassNotFoundException, SQLException {
		String sql = "INSERT INTO result(urls,title,head) VALUES (?,?,?)";
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, urls);
		stmt.setString(2, title);
		stmt.setString(3, head);

		stmt.executeUpdate();
		
		con.close();
	}
	
	public static List<URLList> getURLList() throws ClassNotFoundException, SQLException {
		List<URLList> urlList = null;
		try {

			Connection con = getConnection();
			String sql = "SELECT * FROM result ";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			urlList = new ArrayList<URLList>();
			while (rs.next()) {
				URLList url = new URLList();

				url.setUrls(rs.getString("urls"));
				url.setTitle(rs.getString("title"));
				url.setHead(rs.getString("head"));

				urlList.add(url);

			}
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return urlList;
	}

	public static boolean searchQuery(String textToFind) throws ClassNotFoundException, SQLException {
		String indexDir = "C:\\Users\\slesh\\eclipse-workspace\\WebCrawler";
		//clearingDatabase();
		try {
			FSDirectory fsDirectory = FSDirectory.open(Paths.get(indexDir));

			IndexReader reader = DirectoryReader.open(fsDirectory);
			IndexSearcher searcher = new IndexSearcher(reader);
			Query query = phraseQuery(textToFind);
			// Query query1 = fuzzyQuery(textToFind);

			TopDocs docs = searcher.search(query, 10);
			if (docs.totalHits == 0) {
				System.out.println("Not found");

				return false;
			} else {
				for (ScoreDoc scoreDoc : docs.scoreDocs) {
					System.out.println(scoreDoc.score);
					System.out.println("Url :" + searcher.doc(scoreDoc.doc).get("url"));
					System.out.println("Title :" + searcher.doc(scoreDoc.doc).get("title"));
					System.out.println("Heading :" + searcher.doc(scoreDoc.doc).get("head"));
					// System.out.println("Para :"+searcher.doc(scoreDoc.doc).get("para"));
					// System.out.println("Article :"+searcher.doc(scoreDoc.doc).get("article"));

					resultingTitle(searcher.doc(scoreDoc.doc).get("url"), searcher.doc(scoreDoc.doc).get("title"),
							searcher.doc(scoreDoc.doc).get("head"));

				}

			}

			//////////////////////////
			// Boostedquery(textToFind, searcher);
			// RegexQUery(textToFind, searcher);
			///////////////////////////

			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	static Query phraseQuery(String text) {
		Analyzer analyzer = new StandardAnalyzer();
		QueryBuilder builder = new QueryBuilder(analyzer);
		Query query = builder.createPhraseQuery("title", text);
		return query;
	}

	static FuzzyQuery fuzzyQuery(String text) {
		System.out.println("Using Fuzzy Query....");
		Analyzer analyzer = new StandardAnalyzer();
		FuzzyQuery fuzzyQuery = new FuzzyQuery(new Term("title", text));
		return fuzzyQuery;
	}

	static void Boostedquery(String text, IndexSearcher searcher) throws IOException {
		System.out.println("\n................Boosted Query........ ");
		SynonymQuery query = new SynonymQuery(new Term("title", text));

		BoostQuery boostQuery = new BoostQuery(query, 2.0f);
		TopDocs docs = searcher.search(boostQuery, 10);
		if (docs.totalHits == 0)
			System.out.println("Not found");
		else {
			for (ScoreDoc scoreDoc : docs.scoreDocs) {
				System.out.println(scoreDoc.score);
				System.out.println("Url :" + searcher.doc(scoreDoc.doc).get("url"));
				System.out.println("Title :" + searcher.doc(scoreDoc.doc).get("title"));
				System.out.println("Heading :" + searcher.doc(scoreDoc.doc).get("head"));
				// System.out.println("Para :"+searcher.doc(scoreDoc.doc).get("para"));
				// System.out.println("Article :"+searcher.doc(scoreDoc.doc).get("article"));
			}
		}

	}

	static void RegexQUery(String text, IndexSearcher searcher) throws IOException {
		System.out.println("\n----------------Regex query.....");
		Analyzer analyzer = new StandardAnalyzer();
		QueryBuilder builder = new QueryBuilder(analyzer);
		RegexpQuery regexpQuery = new RegexpQuery(new Term(text));
		TopDocs docs = searcher.search(regexpQuery, 10);

		System.out.println(regexpQuery.getRegexp());
		if (docs.totalHits == 0)
			System.out.println("Not found");
		else {
			for (ScoreDoc scoreDoc : docs.scoreDocs) {
				System.out.println(scoreDoc.score);
				System.out.println("Url :" + searcher.doc(scoreDoc.doc).get("url"));
				System.out.println("Title :" + searcher.doc(scoreDoc.doc).get("title"));
				System.out.println("Heading :" + searcher.doc(scoreDoc.doc).get("head"));
				// System.out.println("Para :"+searcher.doc(scoreDoc.doc).get("para"));
				// System.out.println("Article :"+searcher.doc(scoreDoc.doc).get("article"));
			}
		}
	}
}
