<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page = "/top.jsp"/>

	<div align = "center" id = "bbs">
    
		<h1>MVC Board</h1>
   		<p>
  		<a href="${pageContext.request.contextPath}/board-write.do#bbs">글쓰기</a>|
   	    <a href="${pageContext.request.contextPath}/board-list.do#bbs">글목록</a>
  		<p>
  		
  		<c:if test = "${board eq null }"> 
  			<h2 class = "text-danger">${param.idx}번 글은 존재하지 않아요</h2>
  		</c:if>
  		
  		
  		<c:if test = "${board ne null }">
	  		<table class = "table table-bordered">
	  			<tr>
	  				<td width = "20%"><b>글번호</b></td>
	  				<td width = "30%">${board.idx}</td>
	  				<td width = "20%"><b>작성일</b></td>
	  				<td width = "30%"><fmt:formatDate value = "${board.wdate}" pattern = "yyyy-MM-dd hh:mm:ss"/></td>
	  			</tr>
	  			<tr>
	  				<td width = "20%"><b>글쓴이</b></td>
	  				<td width = "30%">${board.name}</td>
	  				<td width = "20%"><b>조회수</b></td>
	  				<td width = "30%">${board.readnum}</td>
	  			</tr>
	  			<tr>
	  				<td width = "20%"><b>홈페이지</b></td>
	  				<td width = "30%">${board.homepage}</td>
	  				<td width = "20%"><b>첨부파일</b></td>
	  				<td width = "30%"><a href = "FileDown?filename=${board.filename}">${board.filename}</a></td>
	  			</tr>
	  			<tr>
	  				<td width = "20%"><b>제목</b></td>
	  				<td colspan = '3'>${board.subject}</td>
	  			</tr>
	  			<tr>
	  				<td width = "20%"><b>글내용</b></td>
	  				<pre><td colspan = '3'>${board.content}</td></pre> <!-- pre 테그 : 엔터가 먹게한다. -->
	  			</tr>
	  			
	  			<tr>
	  				<td colspan = '4' class = "text-center">
	  					<a href = "board-list.do#bbs"><i class="fa fa-lg fa-align-justify fa-fw"></i></a>|
	  					<a href = "board-pwdUI.do?mode=edit&idx=${board.idx}#bbs"><i class="fa fa-edit fa-fw fa-lg"></i></a>|
	  					<a href = "board-pwdUI.do?mode=del&idx=${board.idx}#bbs"><i class="fa fa-cut fa-fw fa-lg"></i></a>|
	  					<a><i class="fa fa-fw fa-lg fa-reply"></i></a>
	  				</td>
	  			</tr>
	  		</table>
		</c:if>
	</div>

<jsp:include page = "/foot.jsp"/>