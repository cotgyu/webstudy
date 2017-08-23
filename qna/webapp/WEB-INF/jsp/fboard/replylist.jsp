<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
// **댓글 수정화면 생성 함수
function showReplyModify(rnum) {
	$.ajax({
		type : "get",
		url : "${path}/freply/detail/" + rnum,
		success : function(result) {
			$("#modifyReply").html(result);
			// 태그.css("속성", "값")
			$("#modifyReply").css("visibility", "visible");
		}
	})
}
// **댓글  코멘트 생성 함수
function showReplyComment(rnum) {
	$.ajax({
		type : "get",
		url : "${path}/freply/commentwrite/" + rnum,
		success : function(result) {
			$("#ReplyComment").html(result);
			// 태그.css("속성", "값")
			$("#ReplyComment").css("visibility", "visible");
		}
	})
}

	</script>
</head>
<body>
    <table style="width:700px">
        <c:forEach var="row" items="${list}">
        
        <tr>  
        
            <td>
             <c:forEach var="i" begin="1" end="${row.reindent}" step="1">
             	&nbsp;&nbsp;
        	 </c:forEach>  
                ${row.replyer} ${row.date}
                <br>
           	 <c:forEach var="i" begin="1" end="${row.reindent}" step="1">
             	&nbsp;&nbsp;
        	 </c:forEach> 
                ${row.replytext}
                <c:if test="${sessionScope.userId != null}">
                <c:if test="${sessionScope.userId == row.replyer}">
                    <button type="button" class="btn btn-default" onClick="location.href='/freply/delete?rnum=${row.rnum}&bnum=${row.bnum}'">삭제 </button>
                    <input type="button" id="btnModify" class="btn btn-default"  value="수정" onclick="showReplyModify('${row.rnum}')"> 
                </c:if>
					<input type="button" id="btnComment" class="btn btn-default"  value="댓글" onclick="showReplyComment('${row.rnum}')">
                </c:if>
            </td>
        </tr>
        </c:forEach>
    </table>
    <!-- **댓글 수정, 작성 출력할 위치 -->
    <div id="modifyReply"></div>    
	
	<div id="ReplyComment"></div>
</body>
</html>
