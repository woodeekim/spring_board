<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- /#wrapper -->

</div>

<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<!-- Bootstrap Core JavaScript -->
<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<!-- Metis Menu Plugin JavaScript -->
<script src="/resources/vendor/metisMenu/metisMenu.min.js"></script>
<!-- DataTables JavaScript -->
<!--검색과 페이징이 가상요소로 dom 에 붙어서 주석처리-->
<%--    <script src="/resources/vendor/datatables/js/jquery.dataTables.min.js"></script>--%>
<script src="/resources/vendor/datatables-plugins/dataTables.bootstrap.min.js"></script>
<script src="/resources/vendor/datatables-responsive/dataTables.responsive.js"></script>
<!-- Custom Theme JavaScript -->
<script src="/resources/dist/js/sb-admin-2.js"></script>
<script>
    $("#dataTables-example").DataTable({
        responsive: true
    });

    $(".sidebar-nav")
        .attr("class", "sidebar-nav navbar-collapse collapse")
        .attr("aria-expanded", 'false')
        .attr("style", "height:1px");

</script>




</body>

</html>