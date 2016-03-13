<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>
<link rel="stylesheet"
	href='<c:url value="/resources/css/jquery-ui.css" />' >
<link rel="stylesheet"
	href='<c:url value="/resources/css/login.css" />' >
<script src='<c:url value="/resources/js/jquery-2.0.2.min.js" />' ></script>
<script src='<c:url value="/resources/js/jquery-ui.min.js" />' ></script>

</head>

<body>
	<div class="box">
		<img src="<c:url value="/resources/images/sensor_alert.v4.gif" />"
			alt="Pakistan Army" height="110" width="110" align="left"></img> <br>
		
		<br />
		<font size=4><center>
				<b>UGS Command And Control System</b>
			</center></font><br>
		<br>

		<form:form modelAttribute="user" action="/cnc/login/authenticate"
			method="post">
			<form:label path="userId">User Name</form:label>
			<form:input path="userId" />
			<form:label path="password">Password</form:label>
			<form:password path="password" />
			<button type="submit" id="login">Sign In</button>
		</form:form>

		<c:if test="${authentication == 'success'}">
			<!--change /display/... to /display_offline/... for turning on the offline mode-->
			<c:redirect url="/display/main/${user.userId}/${user.userRole}"></c:redirect>
		</c:if>
		<c:if test="${authentication == 'failure'}">
			<p class="error">Invalid Password</p>
		</c:if>

		<c:if test="${authentication == 'not found'}">
			<p class="error">User Not Found</p>
		</c:if>

	</div>
	<script type="text/javascript">
		$(function() {

			$("#login").click(function(event) {
				$("#login").attr("action", "/cnc/login/authenticate")
				$("#login").submit();

			});
		});
	</script>

	<%@ include file="common/footer.jsp"%>
