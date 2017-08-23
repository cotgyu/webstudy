<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SLiPP :: 회원가입</title>
<%@ include file="../commons/_header.jspf" %>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>

<script type="text/javascript"> 
function checkId(){
	  var prmId = $('#userId').val();
	  
	  if($("#userId").val() == ''){
		  alert('ID를 입력해주세요.'); 
	  	  return;
	  	  }
	  
	  $.ajax({
	     type : 'POST',  
	     data:"prmId="+ prmId,
	     dataType : 'text',
	     url : '/users/checkId.do',  
	     success : function(rData, textStatus, xhr) {
	      var chkRst = rData;
	      if(chkRst == 0){
	       alert("사용 가능 합니다.");
	       $("#idChk").val('Y');
	      }
	      else{
	       alert("아이디중복 입니다.");
	     
		   $("#userId").val('');
	       $("#idChk").val('N');
	      }
	     },
	     error : function(xhr, status, e) {  
	      alert(e);
	     }
	  });  
	 }
	
function join(){
	  
	  var frm = document.companyForm; 
	  
	  if(!chkVal('userId','아이디'))
		  return false;
	  if($("#idChk").val() == 'N')
		  {alert('ID체크를 해주세요.');
	  return;
	  }else{
			 $("#form1").submit(); 
		  }
	  
}



</script>


</head>
<body>
	<%@ include file="../commons/_top.jspf" %>
	
	<div class="container">
		<div class="row">
			<div class="span12">
				<section id="typography">
				<div class="page-header">
					<h3>회원가입</h3>
				</div>
				
				<c:choose>
				<c:when test="${empty user.userId}">
				<c:set var="method" value="post" />
				</c:when>
				<c:otherwise>
				<c:set var="method" value="post" />
				</c:otherwise>
				</c:choose>
				
				<form:form  modelAttribute="user" cssClass="form-horizontal" action="/users/create" method="${method}">
					<div class="control-group">
						<label class="control-label" for="userId">사용자 아이디</label>
						
						<input type="hidden" id="idChk" value="N" />	
						<div class="controls">				
							<form:input path="userId" id="userId"/><input type="button" class="btn btn-default" value="중복 체크" onclick="javascript:checkId();" />
							<br>
							<form:errors path="userId" cssClass="error" style="color:red"/>							
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="password">비밀번호</label>
						<div class="controls">
							<form:password path="password" />
							<br>
							<form:errors path="password" cssClass="error" style="color:red"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="name">이름</label>
						<div class="controls">
							<form:input path="name"/>
							<br>
							<form:errors path="name" cssClass="error" style="color:red"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="email">이메일</label>
						<div class="controls">
							<form:input path="email"/>
							<br>
							<form:errors path="email" cssClass="error" style="color:red"/>
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-default">회원가입</button>
						
						</div>
					</div>				
				</form:form> 
			</div>
		</div>
	</div>
		<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
	<%@ include file="../commons/_foot.jspf"%>
</body>
</html>