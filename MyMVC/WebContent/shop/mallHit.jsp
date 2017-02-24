<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = 'c' uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = 'fmt' uri = "http://java.sun.com/jsp/jstl/fmt" %>

<div class="section">
      <div>
      
        <div class="row">
        	<div class = "col-md-12 col-xs-12 col-sm-12">
        		<h2 class = "text-success">HIT상품</h1>
        	</div>
        </div>
        
        <div class = "row">
        <c:if test = "${hitList eq null || empty hitList }">
        	 <div class="col-md-3 col-xs-6 col-sm-6">
	            <img src="http://pingendo.github.io/pingendo-bootstrap/assets/placeholder.png"
	            class="img-responsive">
	            <h2>HIT 상품 준비중</h2>
	          </div>
        </c:if>
        
        <c:if test = "${hitList ne null || not empty hitList}">
        	<c:forEach var = "pd" items = "${hitList}" varStatus="st">
        		<!-- 
        			varStatus에 지정된 변수로 반목문의 상태정보를 얻어올 수 있다.
        			st.index : 반복문의 인덱스 번호(0부터 시작)를 반환
        			st.count : 반복문의 횟수(1부터 시작)를 반환
        		 -->
		          <div class="col-md-3 col-xs-6 col-sm-6">
		            <img src="images/${pd.pimage1}"
		            class="img-responsive">
		            <h2>${pd.pname}</h2>
		            <p>
		            	<del>
			            	<fmt:formatNumber value = "${pd.price}" pattern = "###,###"></fmt:formatNumber>
		            	</del> 원 <br>
		            	<span class = "text text-danger"><b>상품판매가</b><fmt:formatNumber value = "${pd.price}" pattern = "###,###"></fmt:formatNumber></span> 원 <br>
		            	<span class = "text text-primary"><b>${pd.percent}</b> %할인</span> <br>
		            	<span class = "label label-warning">${pd.point}POINT</span> <br>
		            </p>
		          </div>
		          
		          <c:if test = "${st.count mod 4 == 0}">
		          	</div> <!-- row(행)을 끝낸고 새로운 row를 시작 -->
		          	<div claoss = "row">
		          </c:if>
		          
	         </c:forEach>
          </c:if>
          
          </div>
      </div>
    </div>