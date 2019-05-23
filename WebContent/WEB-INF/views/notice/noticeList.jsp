<%@page import="com.rim.notice.NoticeDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%-- <jsp:include page="../temp/bootstrap.jsp" /> --%>
<c:import url="../temp/bootstrap.jsp"></c:import>
</head>
<body>
	<jsp:include page="../temp/header.jsp"></jsp:include>

	<div class="container">
		<h1>Notice list</h1>
		<table class="table table-hover">
			<tr>
				<td>NUM</td>
				<td>TITLE</td>
				<td>WRITER</td>
				<td>DATE</td>
				<td>HIT</td>
			</tr>

			<c:forEach items="${list}" var="noticeDTO">
				<tr>
					<td>${noticeDTO.num}</td>
					<td><a href="./noticeSelect?num=${noticeDTO.num}">${noticeDTO.title}
					</a></td>
					<td>${noticeDTO.writer}</td>
					<td>${noticeDTO.reg_date}</td>
					<td>${noticeDTO.hit}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="container">
		<ul class="pager">
			<c:if test="${pager.curBlock gt 1}">
				<li class="previous"><a href="./noticeList?curPage=${pager.startNum-1}&kind=${pager.search.kind}&search=${pager.search.search}">Previous</a></li>
			</c:if>
			
			<li>
				 <ul class="pagination">
				 	<c:forEach begin="${pager.startNum}" end="${pager.lastNum}" var="i" step="1">
				 		<li><a href="./noticeList?curPage=${i}&kind=${pager.search.kind}&search=${pager.search.search}">${i}</a></li>
				 	</c:forEach>
 				 </ul>
			</li>
			
			<c:if test="${pager.curBlock lt pager.totalBlock}">
				<li class="next"><a href="./noticeList?curPage=${pager.lastNum+1}&kind=${pager.search.kind}&search=${pager.search.search}">Next</a></li>
			</c:if>
		</ul>
	</div>
	<!-- 주소창에 적힐 주소 -->
	<a href="./noticeWrite">Go Write</a>
</body>
</html>