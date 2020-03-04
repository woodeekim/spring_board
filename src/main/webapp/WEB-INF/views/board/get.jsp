<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp"%>

<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">글 등록</div>
            <div class="panel-body">
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

                    <button data-oper="modify" class="btn btn-default">수정</button>
                    <button data-oper="list" class="btn btn-info">목록</button>
                <form id="operForm" action="/board/modify" method="get">
                    <input type="hidden" id="bno" name="bno" value="<c:out value='${board.bno}'/>">
                    <input type="hidden" name="pageNum" value="<c:out value='${cri.pageNum}'/>">
                    <input type="hidden" name="amount" value="<c:out value='${cri.amount}'/>">
                    <input type="hidden" name="type" value="<c:out value='${cri.type}'/>">
                    <input type="hidden" name="keyword" value="<c:out value='${cri.keyword}'/>">
                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    //form 안에 input type을 hidden 으로 파라미터 보내는 첫번째 방법
    $(document).ready(function () {
        var operForm = $("#operForm");
        $("button[data-oper='modify']").on("click",function (e) {
            e.preventDefault();
            operForm.attr("action","/board/modify").submit();
        });

        $("button[data-oper='list']").on("click",function (e) {
            e.preventDefault();
            operForm.find("#bno").remove();
            operForm.attr("action","/board/list").submit();

        })
    });
    //data-set 속성을 이용한 파라미터 보내는 두번째 방법
        /*onclick 이벤트를 쓰지 않고(HTML과 CSS분리) script 에서 Model이 보내준 bno를 받아서 처리*/
        /*
        var bno = '
        <%--<c:out value="${board.bno}--%>"/>';
        $("button").on("click",function () {
            var operation = $(this).data("oper");
            console.log(operation);
            if(operation === 'modify'){
                self.location='/board/modify?bno='+bno;
            } else if(operation === 'list'){
                self.location='/board/list';
                return;
            }
        });
    });
    */

</script>
<%@ include file="../includes/footer.jsp"%>
</body>
</html>
