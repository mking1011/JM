<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix = 'c' uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = 'fmt' uri = "http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page = "/top.jsp"/>

<script type="text/javascript" src="./js/stupidtable.min.js"></script>

<script type="text/javascript">
	$(function(){
		$('#cartTable').stupidtable();
	});
</script>



	<div align = "center" id = "shop">
    
		<h1>${loginUser.name}[${loginUser.userid}]님 장바구니</h1>	
		<form role = "form" name = "orderF" action = "order.do">
		
			<!-- 
				input text 박스가 1개이면 엔터를 쳣을 때 submit이 된다
				이걸 방지하기 위해 text박스를 하나 추가하고 보이지 않게 하자
			 -->
		<input type = "text" name = "prevent" style = "visibility : hidden">
		
			<table id = "cartTable"class = "table table-bordered table-responsive">
				<thead>
					<tr>
						<th data-sort = "int">번호</th>
						<th data-sort = "string">상품명</th>
						<th>수량</th>
						<th>단가</th>
						<th>금액</th>
						<th>삭제</th>
					</tr>
				</thead>
				
				<!-- ------------------------------------ -->
				<c:if test = "${cartList eq null || empty cartList}">
					<tr>
						<td colspan = "6"><b>장바구니에 담긴 상품이 없어요</b></td>
					</tr>
				</c:if>
				<!-- ------------------------------------ -->
				
				<c:if test = "${cartList ne null && not empty cartList}">
					<c:forEach var = "pd" items = "${cartList}" varStatus = "st">
						<tbody>
							<tr>
								<td>
									<input type = "checkbox" name = "opnum" id = "opnum" value = "${pd.pnum}">&nbsp;&nbsp;${pd.pnum}
								</td>					
								<td align = 'center'>
									<a href = "pdDetail.do?pnum=${pd.pnum}"><img src = "images/${pd.pimage1}" class = "img responsive img-thumbnail" style = "width:50%;min-width:80px"></a><br>
									${pd.pname}
								</td>
								<td>
									<!-- 수량 -->
									<a><input type = "number" name = "oqty" id = "oqty${st.index}" value = "${pd.pqty}" size = "2" style = "width:50px"
									min = '0' max = '50'></a>
									<a href = "#shop" onclick = "editCart('${pd.pnum}','${st.index}')" class = "btn btn-default">수정</a>
								</td>
								<td>
									<fmt:formatNumber value = "${pd.saleprice}" pattern = "###,###"/>원<br>
									<span class = "label label-info">${pd.point} POINT</span>
								</td>
								<td>
									<fmt:formatNumber value = "${pd.totalPrice}" pattern = "###,###"/>원<br>
									<span class = "label label-danger">${pd.totalPoint}</span>
								</td>
								<td><a href = "#shop" onclick = "delCart('${pd.pnum}')">삭제</a></td>
							</tr>
						</tbody>
					</c:forEach>
				</c:if>
			</table>
			
				<table>
					<tr>
						<td colspan = '4' style = "padding-right:10px">
							<b>장바구니 총액</b>
							<span style = "color:blue;font-weight:bold">
								<fmt:formatNumber value = "${cartMap.cartTotalPrice}" pattern = "###,###"/>
							</span>원<br>
							<b>총 포인트</b>
							<span style = "color:red"><b>${cartMap.cartTotalPoint}</b></span>POINT
						</td>
						<td colspan = '2'>
							<button type = "button" class = "btn btn-primary">주문하기</button>
							<button type = "button" onclick = "history.go(-2)" class = "btn btn-warning">계속쇼핑</button>
						</td>
					</tr>
				</table>
		</form>
		
		
		<!-- 삭제 form start -->
		
		
		
		<form name = "df" id = "df" action = "cartRemove.do" method = "POST">
			<input type = "hidden" name = "pnum" id = "delPnum">
		</form>
		
		
		<!------------------ 수정 form start ------------->
		
		<form name = "ef" id = "ef" action = "cartEdit.do" method = "POST">
			<input type = "hidden" name = "pnum" id = "editPnum">
			<input type = "hidden" name = "oqty" id = "editOqty">
		</form>
		
		<!-- -------------------- -->
		
		<script type="text/javascript">
			
			var editCart = function(num, idx)
			{
				//수정할 수량 값 얻어오기
				var qty = $('#oqty'+ idx).val();
				$('#editPnum').val(num);
				$('#editOqty').val(qty);
				$('#ef').submit();
				
			}//--------
		
			
			var delCart = function(num)
			{
				
				//delPnum 아이디 텍스트 박스에 num 값을 전달하세요
				var yn = confirm(num+"번 상품을 정말 삭제할까요?");
				
				if(yn)
				{
					$('#delPnum').val(num);
					$('#df').submit();
				}
			}
		</script>
		
		
		<!-- ------------------- -->
		
	</div>

<jsp:include page = "/foot.jsp"/>