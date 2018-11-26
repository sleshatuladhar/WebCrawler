<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="lucenedemo.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Result</title>

<style>
#lookLikeButton {
	font: bold 11px Arial;
	text-decoration: none;
	background-color: #EEEEEE;
	color: #333333;
	padding: 2px 6px 2px 6px;
	border-top: 1px solid #CCCCCC;
	border-right: 1px solid #333333;
	border-bottom: 1px solid #333333;
	border-left: 1px solid #CCCCCC;
}
</style>

</head>
<body>
	<a id="lookLikeButton" href='SearchResultMap.jsp'>Show in Map</a>

	<table cellspacing="10">
		<tr>
			<th>URLs</th>
			<th>Title</th>
			<th>Heading</th>

		</tr>
		<%
			List<URLList> urlList = Searcher.getURLList();
			for (URLList url : urlList) {
		%>

		<tr>
			<%-- <td><a href="<%=url.getUrls()%>"></a></td> --%>
			<td><a href="<%=url.getUrls()%>"><%=url.getUrls()%></a></td>
			<td><%=url.getTitle()%></td>
			<td><%=url.getHead()%></td>

		</tr>
		<%
			}
		%>

	</table>


</body>
</html>