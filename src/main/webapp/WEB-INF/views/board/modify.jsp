<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp"%>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">글 수정</div>
            <div class="panel-body">
                <form role="form" action="/board/modify" method="post">
                    <div class="form-group">
                        <label>Bno</label><input class="form-control" name="bno" value="${board.bno}" readonly="readonly">
                    </div>
                    <div class="form-group">
                        <label>Title</label><input class="form-control" name="title" value="${board.title}">
                    </div>
                    <div class="form-group">
                        <label>Text area</label>
                        <textarea class="form-control" rows="3" name="content">${board.content}</textarea>
                    </div>
                    <div class="form-group">
                        <label>Writer</label><input class="form-control" name="writer" value="${board.writer}" readonly="readonly">
                    </div>

                    <div class="form-group">
                        <label>RegDate</label><input class="form-control" name="regdate" value='<fmt:formatDate value="${board.regdate}" pattern="yyyy/MM/dd"/>' readonly="readonly">
                    </div>

                    <div class="form-group">
                    <label>UpdateDate</label><input class="form-control" name="updatedate" value='<fmt:formatDate value="${board.updatedate}" pattern="yyyy/MM/dd"/>' readonly="readonly">
                        </div>
                    <button type="submit" data-oper="modify" class="btn btn-default"/>수정</button>
                    <button type="submit" data-oper="remove" class="btn btn-danger"/>삭제</button>
                    <button type="submit" data-oper="list" class="btn btn-info">목록</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    /*$(document)객체를 쓰는건 안좋다고 들었다.
    * 모든 페이지에서 공유되지 않고 modify 페이지에서만 쓸 수 있는 방법을 찾아보자.
    * */
    $(document).ready(function () {
        var formObj = $("form");
        $('button').on("click",function (e) {
            e.preventDefault();
            /*DOM 속성인 data-* 불러 올때 이렇게 사용하는구나!*/
            var operation = $(this).data("oper");
            console.log(operation);
            if(operation ==='remove'){
                formObj.attr("action", "/board/remove");
            }else if(operation === 'list'){
                formObj.attr("action", "/board/list").attr("method", "get");
                formObj.empty();

                //아 내가 이미 속성을 바꿔서 잘 동작됐구나, 위에 코드가 더 확실한 코드다. post->get 해야된다.
                /*formObj.attr("action", "/board/list");
                self.location="/board/list";
                return;*/
            }

            formObj.submit();
        });
    });
</script>

<%@ include file="../includes/footer.jsp"%>


</body>
</html>
