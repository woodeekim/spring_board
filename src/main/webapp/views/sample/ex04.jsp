<%--
  Created by IntelliJ IDEA.
  User: 김근경
  Date: 2020-02-25
  Time: 오전 3:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Model 객체 없이 Javabeans 를 통한 전달</title>
</head>
<body>
<div>
    <h3>Model 객체 없이 Javabeans 규칙에 의해서 View 전달되는데..</h3>
    <ul>
        <li>1. DTD(Document Type Definition) 없이 View로 전달시킬 수 있는가?</li>
        <li>2. DTD를 꼭 써야하는가 알아보자</li>
        <li>결론 : DTD 를 안 써도 View 전달 가능과 Javabeans 규칙에 어긋나면 View 로 전달 안됨</li>
        <li>추가 : 기본자료형을 파라미터로 지정해도 위에 말한 것처럼 View 로 전달되지 않지만
        필요한 경우 @ModelAttribute 를 통해 강제적으로 보낼 수 있다.</li>

    </ul>
</div>
<h2>SampleDTO :  ${sampleDTO} </h2>
<h2>page ${page}</h2>


</body>
</html>
