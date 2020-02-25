<%--
  Created by IntelliJ IDEA.
  User: 김근경
  Date: 2020-02-25
  Time: 오후 5:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false" import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <h3>에러를 잡아서 Model 로 전달해주는 페이지</h3>
    <ul>
        <li>1.@ControllerAdvice : 컨트롤러에서 발생하는 예외를 처리하는 존재임을 명시</li>
        <li>2.@ExceptionHandler : 속성에 예외 클래스 타입을 지정해서 처리할 수 있다.</li>
        <li>3.예외를 Model에 담아 View 로 전달</li>
    </ul>
</div>
    <h4><c:out value="${exception.getMessage()}"></c:out></h4>

    <div>
        <ul>
            <c:forEach items="${exception.getStackTrace()}" var="stack">
                <li><c:out value="${stack}"></c:out></li>
            </c:forEach>
        </ul>
    </div>
</body>
</html>
