<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Device Management</title>
    </head>
    <body>
        <h1>Device Management</h1>
<!--    <form:form action="device.do" method="POST" commandName="student">
        <table>
            <tr>
                <td>Student ID</td>
                <td><form:input path="studentId" /></td>
            </tr>
            <tr>
                <td>First name</td>
                <td><form:input path="firstname" /></td>
            </tr>
            <tr>
                <td>Last name</td>
                <td><form:input path="lastname" /></td>
            </tr>
            <tr>
                <td>Year Level</td>
                <td><form:input path="yearLevel" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" name="action" value="Add" />
                    <input type="submit" name="action" value="Edit" />
                    <input type="submit" name="action" value="Delete" />
                    <input type="submit" name="action" value="Search" />
                </td>
            </tr>
        </table>
    </form:form>-->
    <br>
    <table border="1">
        <th>Device ID</th>
        <th>Device Type</th>
        <th>Device Status</th>
        <th>Network ID</th>
        <c:forEach items="${deviceList}" var="device">
            <tr>
                <td>${device.deviceId}</td>
                <td>${device.deviceType}</td>
                <td>${device.deviceStatus}</td>
                <td>${device.networkId}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>