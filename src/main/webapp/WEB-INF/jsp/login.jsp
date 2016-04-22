<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <link rel="stylesheet"
          href='<c:url value="/resources/css/jquery-ui.css" />' >
    <link rel="stylesheet"
          href='<c:url value="/resources/css/login.css" />' >
    <script src='<c:url value="/resources/js/jquery-2.0.2.min.js" />' ></script>
    <script src='<c:url value="/resources/js/jquery-ui.min.js" />' ></script>

</head>

<body style="background-color: silver;">

    <div style="border-radius: 15px; padding: 10px 10px 10px 10px; background-color: 
         #202020; border: 1px solid #ccc; width: 600px; margin: 0 auto; margin-top: 90px; 
         margin-bottom: 40px; color: white; font-size: 14px;">

        <img src="<c:url value="/resources/images/mcs_logo.png" />" alt="Military College of Signals Rawalpindi" height="110" width="110" align="left"></img> <br>

        <font size=5><center>UGS Command And Control System</center></font><br />
        <font size=3><center>Military College of Signals Rawalpindi</center></font>
        <br /><br />

        <div align='center' style="width: 100%;">
            <div align='left' style="width: 51%;">
                <form:form modelAttribute="user" action="/cnc/login/authenticate" method="post">
                    <form:label path="userId">User Name</form:label>
                    <form:input path="userId" />
                    <form:label path="password">Password</form:label>
                    <form:password path="password" />
                    <div align='center'>
                        <button type="submit" id="login" style="border-radius: 10px; font-weight: bold; width: 120px; height: 32px; cursor:pointer; background-color: silver;">Sign In</button>
                    </div>
                </form:form>
            </div>

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

    </div>
    <script type="text/javascript">
        $(function () {

            $("#login").click(function (event) {
                $("#login").attr("action", "/cnc/login/authenticate")
                $("#login").submit();

            });
        });
    </script>

    <%@ include file="common/footer.jsp"%>

    </body>
</html>
