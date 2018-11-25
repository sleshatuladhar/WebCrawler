package lucenedemo;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class SearcherController
 */
//@WebServlet("/Searcher")
public class SearcherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearcherController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String name=request.getParameter("search");
		System.out.println(name);
		
		DB database=new DB();
		
		//database.getLinks("http://www.mit.edu");
		
		database.getLinks("https://edition.cnn.com/");
		
		database.getLinks("http://www.bbc.com/news");
		
		database.getLinks("https://www.aljazeera.com/news/");
		
		Indexer indexing=new Indexer();
		try {
			indexing.indexingWeb();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		boolean searching = Searcher.searchQuery(name);
		if (searching = true) {
			response.sendRedirect("SearchResult.jsp");
		} else {
			response.sendRedirect("index.jsp?msg=NoSearchResults");
		}
		
		/*Crawler crawlingWeb=new Crawler();
		crawlingWeb.getLinks("http://www.bbc.com/news");
		crawlingWeb.getLinks("https://edition.cnn.com/");
		crawlingWeb.getLinks("https://www.aljazeera.com/news/");
		
		
		
		Searcher searching=new Searcher();
		searching.searchQuery(name);*/
		
		
		
	}

}
