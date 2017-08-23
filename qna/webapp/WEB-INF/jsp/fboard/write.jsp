<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SLiPP :: 자유게시판</title>
<script type="text/javascript" src="/resources/Editors/js/HuskyEZCreator.js" charset="utf-8"></script> 
<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.0.min.js"></script>

<script type="text/javascript"> 

	$(function() {
		var oEditors = [];
		
		nhn.husky.EZCreator.createInIFrame({
			oAppRef : oEditors,
			elPlaceHolder : "content",
			//SmartEditor2Skin.html 파일이 존재하는 경로 
			sSkinURI : "/resources/Editors/SmartEditor2Skin.html",
			htParams : {
				// 툴바 사용 여부
				bUseToolbar : true,
				// 입력창 크기 조절바 사용 여부 
				bUseVerticalResizer : true,
				// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음) 
				bUseModeChanger : true,
			}
		});
		
		$("#save").click(function(){
			var title = $("#title").val();		
						
			if(title==""){
				alert("제목을 입력하세요");
			}else{
				
			 oEditors.getById["content"].exec("UPDATE_CONTENTS_FIELD", []); 
			 $("#frm").submit(); 
			 }
			 
		});
	});




</script>

<%@ include file="../commons/_header.jspf"%>
</head>
<body>
	<%@ include file="../commons/_top.jspf"%>
	<br>
	<div class="studyboard_write">
	<h3>게시글 작성</h3>
	<form id="frm" method="post" action="/fboard/insert" enctype="multipart/form-data">
		<table width="59.8%" align="center">
			<tr>
				<td width="3%">제목</td>
				<td>
				<select name="tag">     
	            <option value="잡담">잡담</option>
	            <option value="일상">일상</option>
	            <option value="Help!">Help!</option>
	            </select><input name="title" id="title" size="136">
	            </td>
			</tr>
			
			<tr>
				<td>내용</td>
				<td style=background-color:white;><textarea name="content" id="content"  style="width: 1100px; height: 600px;"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
				<input type="button" id="save" class="btn btn-default" value="저장" /> 		
				<button type="button" class="btn btn-default" onClick="location.href='/fboard/list'">취소</button>
			</tr>
		</table>
	</form>
</div>
<br><br><br><br><br><br><br><br><br>
	<%@ include file="../commons/_foot.jspf"%>
</body>
</html>