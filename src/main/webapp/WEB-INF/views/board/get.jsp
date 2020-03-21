<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp" %>

<%--게시물--%>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">글 등록</div>
            <div class="panel-body">
                <div class="form-group">
                    <label>Bno</label><input class="form-control" name="bno" value="${board.bno}" readonly="readonly">
                </div>
                <div class="form-group">
                    <label>Title</label><input class="form-control" name="title" value="${board.title}"
                                               readonly="readonly">
                </div>
                <div class="form-group">
                    <label>Text area</label>
                    <textarea class="form-control" rows="3" name="content"
                              readonly="readonly">${board.content}</textarea>
                </div>
                <div class="form-group">
                    <label>Writer</label><input class="form-control" name="writer" value="${board.writer}"
                                                readonly="readonly">
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
</div><%--//게시물--%>
<%--댓글--%>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-comments fa-fw"></i> 댓글
            </div>
            <div class="panel-body">
                <ul class="chat">
                    <%--data 속성은 HTML5부터 생긴 데이터 속성이다.--%>
                    <li class="left clearfix" data-rno="12">
                        <div>
                            <div class="header">
                                <strong class="primary-font">user00</strong>
                                <small class="pull-right text-muted">2018-01-01 13:13</small>
                            </div>
                            <p>굿잡!!!</p>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div><%--//댓글--%>


<%--
    HTML5 부터는 <script> 라고 쓰면 디폴트로
    <script type="text/javascript"> 으로 설정하기 때문에 안써도 된다.
--%>
<script type="text/javascript" src="/resources/js/reply.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        //요즘에는 var 을 안쓰고 let 과 const 써야할텐데 정확한 이유를 찾아보자.
        var bnoValue = '<c:out value="${board.bno}"/>';
        var replyUL = $(".chat");
        var operForm = $("#operForm");

        showList(1);
        //form 안에 input type을 hidden 으로 파라미터 보내는 첫번째 방법
        $("button[data-oper='modify']").on("click", function (e) {
            e.preventDefault();
            operForm.attr("action", "/board/modify").submit();
        });

        $("button[data-oper='list']").on("click", function (e) {
            e.preventDefault();
            operForm.find("#bno").remove();
            operForm.attr("action", "/board/list").submit();
        });

        function showList(page) {
            replyService.getList(
                {
                    bno : bnoValue,
                    page : page || 1
                },
                function (list) {
                    var str = "";
                    if(list == null || list.length == 0){
                        replyUL.html("");
                        return;
                    }//if
                    for(var i = 0, len = list.length || 0; i < len; i++){
                        str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>";
                        str += "    <div><div class='header'><strong class='primary-font'>"+list[i].replyer+"</strong>";
                        str += "    <small class='pull-right text-muted'>"+list[i].replyDate+"</small></div>";
                        str += "    <p>"+list[i].reply+"</p></div></li>";
                    }//for
                    replyUL.html(str);
                }); //end function
        }//end showList



    });//ready()
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
<%--reply test 를 위한 script 이다.--%>
<script type="text/javascript">
    var bnoValue = '<c:out value="${board.bno}"/>';
    console.log("bnoValue : " + bnoValue);
    console.log("Test reply for javascript");
    console.log("==========add()==========")
    replyService.add(
        {
            reply: "JS test",
            replyer: "tester",
            bno: bnoValue
        },
        /*result의 결과값으로 success 를 받는데..*/
        function (result) {
            alert("RESULT : " + result);
        }
    );

    console.log("==========getList()==========")
    replyService.getList({bno: bnoValue, page: 1}, function (list) {
        for (let i = 0, len = list.length || 0; i < len; i++) {
            console.log(list[i]);
        }//for
    });

    console.log("==========remove()==========")
    //count 가 도대체 어디서 보내길래 값을 remove에 값으로 넣어서 호출 하는걸까?
    /*
        count 의 정체 (*콜백함수 이슈에 대한 최종 정리!)
        => remove() 함수를 호출 할 때 인자값으로 count 를 넣어주는게
           아니라 ajax 를 통해 정상적으로 통신이 된 후, 결과값을 인자로
           갖는 success:function(deleteResult){} 함수를 호출한다.
           (여기가 중요) 이때 callback(deleteResult)인 콜백함수를 호출 할 때
           get.jsp 에서 remove() 함수의 콜백함수가 실행되는 것이다.
     */

    replyService.remove(49, function (count) {
        if (count === "test") {
            alert("삭제 완료");
        }//if
    }, function (err) {
        alert("에러");
    });

    replyService.update(
        {
            rno: 22,
            bno: bnoValue,
            reply: "수정하는 테스트입니다."
        },
        function (result) {
            alert("콜백함수를 호출 했을때 이창이 나옵니다. 수정 완료!");
        }
    )

    replyService.get(10, function (result) {
        alert("get");
        console.log(result);
    })
</script>
<%@ include file="../includes/footer.jsp" %>
</body>
</html>
