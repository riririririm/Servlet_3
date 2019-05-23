<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setAttribute("s",5);
		request.setAttribute("e",10);
		
		String[] ar = {"a","b","c"};
		request.setAttribute("ar", ar);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		request.setAttribute("list", list);
	%>
	<c:forEach begin="${s}" end="${e}" step="1" var="i">
		${i}
	</c:forEach>
	
	<c:forEach items="${ar}" var="a">
		${a}
	</c:forEach>
	
	<c:forEach items="${list}" var="l" varStatus="t">
		<p>${l}</p>
		<h3>count: ${t.count}</h3>
		<h3>index: ${t.index}</h3>
		<h3>first: ${t.first}</h3>
		<h3>last: ${t.last}</h3>
	</c:forEach>
	
</body>
</html>