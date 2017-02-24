<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name = "viewport" content = "width = device-width, initial-scale = 1">
<title>::loginModal::</title>

 
<!--부트스트랩 참조는 탑에서 이미 했으므로 한번만 하는 것이 좋다 ----------------------------------- -->

<!-- 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> -->

<!------------------------------------->


</head>
<body>
	<div class = "container">
		
		<div id = "myModal" role = "dialog" class = "modal fade">
			<div class = "modal-dialog">		
				<div class = "modal-content">
					
					<form id = 'loginF' role = "form" action = loginEnd.do method = "post">
						
						<!-- 헤더 영역 -->
						<div class = "modal-header">
							<button data-dismiss = "modal" class = "close">&times;</button>
							<h3>Login</h3>
						</div>
						
						<!-- 바디 영역 .modal-body -->
						<div class = "modal-body">
							<div class = 'row'>
								<div class = "col-md-4 col-sm-4 text-right">
									<b>아이디</b>
								</div>
								
								<div class = "col-md-8 col-sm-8">
									<input type = "text" name = 'userid' id = 'userid' class = "form-control">
								</div>
								
								<div class = "col-md-4 col-sm-4 text-right">
									<b>비밀번호</b>
								</div>
								
								<div class = "col-md-8 col-sm-8">
									<input type = "password" name = 'pwd' id = 'pwd' class = "form-control">
								</div>
							</div>
						</div>
						
						<!-- 푸터 영역 .modal-footer -->
						<div class = "modal-body">
							<button type = "button" id = "btnLogin" class = "btn btn-success">로그인</button>
							<button type = "button" class = "btn btn-warning">CLOSE</button>
						</div>
					
					</form>
					
				</div>
			</div>
		</div>
	</div>
	
	
	
	<script type="text/javascript">
		$(function(){
			$('#btnLogin').click(function(){
				loginCheck();
			});
		});
		
		
		var loginCheck = function(){
			if(!$('#userid').val())
			{
				alert('아이디를 입력하세요');
				$('#userid').focus();
				return;		
			}
			
			if(!$('#pwd').val())
			{
				alert("비밀번호를 입력하세요");
				$('#pwd').focus();
				return;
			}
			
			$('#loginF').submit();
		}
	</script>
	
	
</body>
</html>