<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp"%>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">글 등록</div>
            <div class="panel-body">
                <form role="form" action="/board/register" method="post">
                    <div class="form-group">
                        <label>Bno</label><input class="form-control" name="bno" value="${board.bno}" readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label>Title</label><input class="form-control" name="title" value="${board.title}" readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label>Text area</label>
                        <textarea class="form-control" rows="3" name="content" readonly="readonly">${board.content}</textarea>
                    </div>
                    <div class="form-group">
                        <label>Writer</label><input class="form-control" name="writer" value="${board.writer}" readonly="readonly">
                    </div>
                    <button data-oper="modify" class="btn btn-default" onclick="location.href='/board/modify?bno=<c:out value="{board.bno}"/>'">수정</button>
                    <button data-oper="list" class="btn btn-default" onclick="location.href='/board/list'">목록</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp"%>
</body>
</html>
