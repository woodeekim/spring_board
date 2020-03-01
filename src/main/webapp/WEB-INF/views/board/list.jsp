<%--
  Created by IntelliJ IDEA.
  User: 김근경
  Date: 2020-02-26
  Time: 오후 8:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../includes/header.jsp"%>

        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Tables</h1>
            </div>
            <!-- /.col-lg-12 -->
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        게시글 리스트<button id="regBtn" type="button" class="btn btn-xs pull-right">글등록</button>
                    </div>
                    <!-- /.panel-heading -->
                    <div class="panel-body">
                        <table width="100%" class="table table-striped table-bordered table-hover"
                               id="dataTables-example">
                            <thead>
                            <tr>
                                <th>#번호</th>
                                <th>제목</th>
                                <th>작성자</th>
                                <th>작성일</th>
                                <th>수정일</th>
                            </tr>
                            </thead>
                            <c:forEach items="${list}" var="board">
                            <tbody>
                            <tr class="odd gradeX">
                                <td><c:out value="${board.bno}"/></td>
                                <td><a class="move" href="<c:out value='${board.bno}'/>"><c:out value="${board.title}"/></a></td>
                                <td><c:out value="${board.content}"/></td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.regdate}"/></td>
                                <td><fmt:formatDate pattern="yyyy-MM-dd" value="${board.updatedate}"/></td>
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <!-- /.table-responsive -->
                        <%--a요소로 페이지가 넘어가지 않게 막고 파라미터를 넘겨주기 위함--%>

                        <div class="pull-right">
                            <ul class="pagination">
                                <c:if test="${pageMaker.prev}">
                                    <li class="paginate_button previous">
                                        <a href="${pageMaker.startPage -1}">이전</a>
                                    </li>
                                </c:if>

                                <c:forEach var="num" begin="${pageMaker.startPage}"
                                           end="${pageMaker.endPage}">
                                    <li class="paginate_button ${pageMaker.cri.pageNum == num ? "active":""}">
                                        <a href="${num}">${num}</a>
                                    </li>
                                </c:forEach>

                                <c:if test="${pageMaker.next}">
                                    <li class="paginate_button next">
                                        <a href="${pageMaker.endPage +1}">다음</a>
                                    </li>
                                </c:if>
                            </ul>
                        </div>



                        <!-- Modal 추가 -->
                        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                                    </div>
                                    <div class="modal-body">
                                        처리가 완료되었습니다.
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Save changes</button>
                                    </div>
                                </div>
                                <!-- /.modal-content -->
                            </div>
                            <!-- /.modal-dialog -->
                        </div>
                        <!-- /.modal -->


                    </div>
                    <!-- /.panel-body -->
                </div>
                <!-- /.panel -->
            </div>
            <!-- /.col-lg-12 -->
    <!-- /#page-wrapper -->
    <form id="actionForm" action="/board/list" method="get">
        <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}" >
        <input type="hidden" name="amount" value="${pageMaker.cri.amount}" >
    </form>
    <script type="text/javascript">
        $(document).ready(function(){
            var result = '<c:out value="${result}"/>';
            var actionForm =$("#actionForm");


            checkModal(result);

            history.replaceState({},null,null);

            function checkModal(result){
                if(result === '' || history.state){
                    return;
                }
                if(parseInt(result)>0){
                    $(".modal-body").html("게시글" + parseInt(result) + "번이 등록되었습니다.");
                }
                $("#myModal").modal("show");
            }//checkModal
            $("#regBtn").on("click",function(){
                self.location = "/board/register";
            });
            //페이징처리 이벤트 처리
            $(".paginate_button a").on("click", function (e) {
                e.preventDefault();
                console.log('click');
                //클릭한 this 객체의 href 속성값을 input의 value에 대입하고 submit 시킨다.
                actionForm.find("input[name='pageNum']").val($(this).attr("href"));
                actionForm.submit();
            });
            //게시글 눌렀을 때 이벤트 처리 (pageNum과 amount 파라미터를 넘기기위함)
            //why? 게시글에서 목록을 클릭하면 1페이지로 돌아가기 때문
            $(".move").on("click", function (e) {
                e.preventDefault();
                //이벤트처리를 form 태그 하나로 다룬다는거 알아두자
                actionForm.append("<input type='hidden' name='bno' value='"+$(this).attr("href")+"'>");
                actionForm.attr("action", "/board/get");
                actionForm.submit();

            })





        });
    </script>
<%@ include file="../includes/footer.jsp"%>
