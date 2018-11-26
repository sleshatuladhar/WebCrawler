<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Web Crawler</title>
</head>
<body>
	<h1>Search</h1>
	<form action="Searcher" method="post">
		<!-- Enter search: <br> <input type="text" name="search"><input
			type="submit" value="Submit"><br> -->
			<p>
			<input name="search" size="44" /> 
		</p>
		<p>
			<input name="maxresults" size="4" value="100" /> Results Per Page <input
				type="submit" value="Search" />
		</p>
			
	</form>

	<!--<script src="https://d3js.org/d3.v5.min.js"></script>  -->
	
	<!-- <form name="search" action="results.jsp" method="get">
		<p>
			<input name="query" size="44" /> Search Criteria
		</p>
		<p>
			<input name="maxresults" size="4" value="100" /> Results Per Page <input
				type="submit" value="Search" />
		</p>
	</form> -->
</body>
</html>