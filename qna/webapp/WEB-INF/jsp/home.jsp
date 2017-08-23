<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Gyu's Page</title>
<script>
    // **���ϴ� �������� �̵��� �˻�����, Ű���� ���� �����ϱ� ���� 
    function list(page){
        location.href="${path}/board/list?curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}";
    }
    
</script>
<%@ include file="./commons/_header.jspf" %>
</head>
<body>
	<%@ include file="./commons/_top.jspf" %>

	 <!-- Header �Ǿ� ����,����/ ��ϵ� ��ư  -->
    <a name="about"></a>
    <div class="intro-header">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h1>WelCome</h1>
                        <h3>Gyu's Page</h3>
                        <hr class="intro-divider">
                        <ul class="list-inline intro-social-buttons">
                            <li>
                                <a hrsef="https://www.facebook.com/profile.php?id=100002282055147" class="btn btn-default btn-lg"><i class="fa fa-facebook fa-fw"></i> <span class="network-name">FaceBook</span></a>
                            </li>
                            <li>
                                <a href="https://github.com/cotgyu" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                            </li>
                            <li>
                                <a href="https://cotmulgyu.blogspot.kr" class="btn btn-default btn-lg"><i class="fa fa-linkedin fa-fw"></i> <span class="network-name">Blog</span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.container -->
    </div>
    <!-- /.intro-header -->
  <!-- Page Content -->

	<a  name="services"></a>
    <div class="content-section-a">
	<div class="container">
            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">�� ���� ������ �����ؿ�!</h2><br>
                    <p class="lead">�ڽ��� ������ ������ �÷� ������ �����غ����?</p>
                    
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                  	�α� �Խù�
					<table border="2" align="center" width="100%" style= "background-color: white">					
					<c:forEach var="row" items="${map.poplist}">
					<tr>					
					<td height="30">&nbsp;<a href="${path}/board/view?bnum=${row.bnum}">${row.title}
								<!-- ** ����� ������ �Խñ� �̸� ���� ����ϱ� -->
				                    <c:if test="${row.recnt > 0}">
				                    <span style="color: red;">(${row.recnt})</span>
				                    </c:if>	
								</a></td></tr>
					</c:forEach>
					</table>
					
                </div>
            </div>
	
        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

    <div class="content-section-b">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6" >
                                
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">������ �÷��ּ���!</h2><br>
                    <p class="lead">�ڽ��� ���ɺо� ������ �����غ����?</p>
                </div>
                <div class="col-lg-6 col-sm-pull-7 col-sm-20">
                &emsp;&emsp;&emsp;&nbsp;�α�Խù�
                <ul>
					<c:forEach items="${map.popImglist}" var="map">						
						<li>						
							<a href="/gallery/list"><img src="/resources/uploads/${map.imgfile}" width="110" height="170"></a>					
						</li>
					</c:forEach>
				</ul>               
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-b -->

    <div class="content-section-a">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">�����Ӱ� �ǰ��� ������!</h2><br>
                    <p class="lead">�����Ӱ� �ǰ��� ���������? </p>
                   
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                	�α�Խù�
                    <table border="2" align="center" style= "background-color: white" width="100%" >
					<c:forEach var="row2" items="${map.popFlist}">
					<tr>
					<td height="30">&nbsp;<a href="${path}/fboard/view?bnum=${row2.bnum}">${row2.title}
								<!-- ** ����� ������ �Խñ� �̸� ���� ����ϱ� -->
				                    <c:if test="${row2.recnt > 0}">
				                    <span style="color: red;">(${row2.recnt})
				                    </span>
				                    </c:if>	
								</a></td></tr>
					</c:forEach>
						</table>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

	<a  name="contact"></a>
    <div class="banner">

        <div class="container">

            <div class="row">
                <div class="col-lg-6">
                    <h3>�ּ���</h3>
                    <h4>1993.03.03</h4>
                    <h4>�ܱ����б� ��ǻ�Ͱ��а�</h4>
                    <h4>v123v123s@gmail.ocm</h4>
                </div>
                <div class="col-lg-6">
                    <ul class="list-inline banner-social-buttons">
                        <li>
                            <a href="https://www.facebook.co.kr" class="btn btn-default btn-lg"><i class="fa fa-facebook fa-fw"></i> <span class="network-name">facebook</span></a>
                        </li>
                        <li>
                            <a href="https://github.com/IronSummitMedia/startbootstrap" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                        </li>
                        <li>
                            <a href="https://cotmulgyu.blogspot.kr/" class="btn btn-default btn-lg"><i class="fa fa-linkedin fa-fw"></i> <span class="network-name">Blog</span></a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.banner -->

 	<%@ include file="../jsp/commons/_foot.jspf"%>


</body>
</html>