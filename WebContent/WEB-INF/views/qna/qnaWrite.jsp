<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<jsp:include page="../temp/bootstrap.jsp" />
<script type="text/javascript">
	$(function() {
		var count=0;
		var d1=0;
		$("#add").click(function() {
			if(count<5){
				$("#addFile").append('<div id="'+d1+'"><input type="file" class="form-control" id="" name="f1"><span title="'+d1+'" class="del">X</span></div>')
				/* $("#addFile").append('<input type="file" class="form-control" id="" name="f1"><span class="del">X</span>')*/
				count++;
				d1++;
			}else {
				alert('5개만 가능');
			}
			
			
		});
		
		$("#addFile").on("click",".del", function() {
			$(this).prev().remove();
			$(this).remove();
			count--;
		});

		/* $("#addFile").on("click",".del", function() {
			$(this).prev().remove();
			$(this).remove();
			count--;
		}); */
		
	});
</script>
<style type="text/css">
	.del{
		color: red;
		cursor:pointer;
	}
</style>
</head>
<body>

	<c:import url="../temp/header.jsp" />

	<div class="container">
		<h1>QnA Write page</h1>
		<form action="./qnaWrite" method="POST"
			enctype="multipart/form-data">
			<div class="form-group">
				<label for="title">title:</label> 
				<input type="text" class="form-control" id="title" name="title">
			</div>
			<div class="form-group">
				<label for="writer">writer:</label> 
				<input type="text" class="form-control" id="writer" name="writer">
			</div>
			<div class="form-group">
				<label for="contents">Contents:</label>
				<textarea class="form-control" rows="5" id="contents"></textarea>
			</div>
			<div class="form-group" id="addFile">
				<label for="file">file:</label> 
				
			</div>
			
			<div class ="form-group">
				<input type="button" value="Add" class="btn" onclick="f()" id="add">
			</div>
			
			<button class="btn btn-primary">Write</button>
		</form>
	</div>

	
</body>
</html>