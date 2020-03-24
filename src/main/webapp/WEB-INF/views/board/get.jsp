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
                <i class="fa fa-comments fa-fw"></i>댓글
                <button id="addReplyBtn" class="btn btn-primary btn-xs pull-right">댓글작성</button>
            </div>
            <div class="panel-body">
                <ul class="chat">
                    <%--data 속성은 HTML5부터 생긴 데이터 속성이다.--%>
                    <%-- <li class="left clearfix" data-rno="12">
                         <div>
                             <div class="header">
                                 <strong class="primary-font">user00</strong>
                                 <small class="pull-right text-muted">2018-01-01 13:13</small>
                             </div>
                             <p>굿잡!!!</p>
                         </div>
                     </li>--%>
                </ul>
            </div>
            <%--페이지 출력--%>
            <div class="panel-footer">
            </div>
        </div>
    </div>
</div><%--//댓글--%>

<%--
    모달창은 html 태그 안에만 설정하면 정중앙에 위치함으로
    어디든 상관없다. (기본적으로 css 속성이 정중앙으로 설정되어 있나보다.)
--%>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
            </div>

            <div class="modal-body">
                <div class="form-group">
                    <label>Reply</label>
                    <input class="form-control" name="reply" value="새 댓글">
                </div>
                <div class="form-group">
                    <label>Replyer</label>
                    <input class="form-control" name="replyer" value="replyer">
                </div>
                <div class="form-group">
                    <label>Reply Date</label>
                    <input class="form-control" name="replyDate" value="">
                </div>
            </div>

            <div class="modal-footer">
                <button id="modalModBtn" type="button" class="btn btn-warning">수정</button>
                <button id="modalRemoveBtn" type="button" class="btn btn-warning">삭제</button>
                <button id="modalRegisterBtn" type="button" class="btn btn-warning">등록</button>
                <button id="modalCloseBtn" type="button" class="btn btn-warning">닫기</button>
            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->

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

        //댓글 모달창 변수들
        var modal = $(".modal");
        var modalInputReply = modal.find("input[name='reply']");
        var modalInputReplyer = modal.find("input[name='replyer']");
        var modalInputReplyDate = modal.find("input[name='replyDate']");
        var modalModBtn = $("#modalModBtn");
        var modalRemoveBtn = $("#modalRemoveBtn");
        var modalRegisterBtn = $("#modalRegisterBtn");

        //댓글 페이지를 위한 변수들
        var pageNum = 1;
        var replyPageFooter = $(".panel-footer");

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
            console.log("show list " + page);
            replyService.getList(
                {
                    bno: bnoValue,
                    page: page || 1
                },
                //페이징된 댓글을 불러오기 위해 댓글의 수인 replyCnt 매개변수 추가
                function (replyCnt, list) {
                    console.log("replyCnt: " + replyCnt);
                    console.log("list:" + list);
                    console.log(list);

                    //page번호가 -1 이면 마지막 페이지를 찾아서 다시 호출
                    if (page == -1) {
                        pageNum = Math.ceil(replyCnt / 10.0);
                        showList(pageNum);
                        return;
                    }//end if
                    var str = "";
                    if (list == null || list.length == 0) {
                        //replyUL.html("");
                        return;
                    }//if
                    for (var i = 0, len = list.length || 0; i < len; i++) {
                        str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
                        str += "    <div><div class='header'><strong class='primary-font'>" + list[i].replyer + "</strong>";
                        str += "    <small class='pull-right text-muted'>" + replyService.displayTime(list[i].replyDate) + "</small></div>";
                        str += "    <p>" + list[i].reply + "</p></div></li>";
                    }//for
                    replyUL.html(str);
                    showReplyPage(replyCnt);
                }); //end function
        }//end showList

        //id가 addReplyBtn인 댓글작성 버튼을 누르면 모달창 show
        /*
        모달창이 켜지기기 까지
        1.값들이 비어있어야 한다.
            - 어떤값? : reply, replyer
        2.요소들 숨기기
            1. 수정, 삭제 버튼
            2. replyDate를 가진 div 요소
        */

        $("#addReplyBtn").on("click", function () {
            modal.find("input").val("");
            /*
                closest() JS함수
                - 자신을 시작으로 부모요소 단위로 출발하여
                  해당 메소드의 해당 요소를 찾아 반환한다.
                  없으면 NULL 값 반환
            */
            modalInputReplyDate.closest("div").hide();
            modal.find("button[id !='modalCloseBtn']").hide();
            modalRegisterBtn.show();
            $(".modal").modal("show");
        });

        //모달창 댓글 등록
        /*
            인자와 매개변수
            - 항상 헷갈렸는데 기초적인 개념 알아두자.
            - 인자는 함수를 호출 할 때 전달되는 값이다.
            - 반대로 매개변수는 그 전달되는 값을 받아들이는 변수다.
                - 매개변수가 내가 잘 아는 파라미터다.
        */
        modalRegisterBtn.on("click", function () {
            var reply = {
                reply: modalInputReply.val(),
                replyer: modalInputReplyer.val(),
                bno: bnoValue
            };
            replyService.add(reply, function (result) {
                alert(result);
                modal.find("input").val("");
                modal.modal("hide");

                //댓글 갱신
                //showList(1);
                //사용자가 새로운 댓글을 차가하면 showList(-1); 을 호출하여 댓글의
                //숫자를 파악 후 마지막 페이지를 호출해서 이동시키는 방식으로 동작
                showList(-1);
            });
        });//end modalRegisterBtn

        //특정 댓글의 클릭 이벤트 처리
        $(".chat").on("click", "li", function () {
            var rno = $(this).data("rno");

            replyService.get(rno, function (reply) {
                modalInputReply.val(reply.reply);
                modalInputReplyer.val(reply.replyer);
                modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
                modal.data("rno", reply.rno);
                /*
                    (이상하게 이해하는데 한참걸렸다.)
                    modal에서 modalClose가 아닌 button 의 속성들을 숨겨라는 뜻이다.
                */
                modal.find("button[id != 'modalCloseBtn']").hide();
                modalModBtn.show();
                modalRemoveBtn.show();

                $(".modal").modal("show");
            });
        });

        //댓글 수정
        modalModBtn.on("click", function () {
            var reply = {
                rno: modal.data("rno"),
                reply: modalInputReply.val()
            };
            replyService.update(reply, function (result) {
                alert(result);
                modal.modal("hide");
                //showList(1);
                //수정시 현재 댓글이 포함된 페이지로 이동할 수 있게
                showList(pageNum);
            });
        });

        //댓글 삭제
        modalRemoveBtn.on("click", function () {
            /*
                rno는 어떻게 불러오는가?
                - data함수의 이름(키)을 불러오면 해당하는 값을 반환한다.
                - 해당 댓글을 클릭할 때 콜백함수(확실x)의 결과값에 담긴 rno를
                  data("rno",값) 에 담고 이걸 불러 쓰는 것이다
            */
            var rno = modal.data("rno");
            replyService.remove(rno, function (result) {
                alert(result);
                modal.modal("hide");
                //showList(1);
                //삭제시 현재 댓글이 포함되 페이지로 이동할 수 있게
                showList(pageNum);
            });
        });

        //페이지 번호를 출력하는 함수
        function showReplyPage(replyCnt) {
            var endNum = Math.ceil(pageNum / 10.0) * 10;
            var startNum = endNum - 9;
            var prev = startNum != 1;
            var next = false;

            if(endNum * 10 >= replyCnt) {
                endNum = Math.ceil(replyCnt/10.0);
            }//end if

            if(endNum * 10 < replyCnt) {
                next = true;
            }//end if

            var str = "<ul class='pagination pull-right'>";

            if(prev) {
                str += "<li class='page-item'><a class='page-link' href='"+(startNum-1)+"'>Previous</a></li>";
            }//end if001

            for(var i = startNum; i <= endNum; i++){

                var active = pageNum == i ? "active" : "";

                str += "<li class='page-item "+active+"'><a class='page-link' href='"+i+"'>"+i+"</a></li>";
            }//end for


            if(next) {
                str += "<li class='page-item'><a class='page-link' href='"+(endNum+1)+"'>Next</a></li>";
            }//end if

            str += "</ul></div>";
            console.log(str);
            replyPageFooter.html(str);
        }

        //페이지의 번호를 클릭했을 때 새로운 댓글을 가져오기
        /*
            on함수의 인자3개짜리
            - 이벤트위임을 하는 것이다. 즉 이벤트는 .panel-footer 에 이벤트를 걸고
              실제 이벤트는 li a 요소로 실행시킨다. 아마도 동적으로 사용하기 위해서는
              이렇게 on()함수를 써야된다.
        */
        replyPageFooter.on("click", "li a", function (e) {
            e.preventDefault();
            //attr() 함수를 통해 href 의 속성 값을 반환한다.
            var targetPageNum = $(this).attr("href");
            pageNum = targetPageNum;
            showList(pageNum);
        });

    });//ready()

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

<%@ include file="../includes/footer.jsp" %>
</body>
</html>
