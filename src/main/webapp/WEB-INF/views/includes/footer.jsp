<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!-- /#wrapper -->
</div>

<!-- Page-Level Demo Scripts - Tables - Use for reference -->
<script type="text/javascript">
    $(document).ready(function () {
        $('#dataTables-example').DataTable({
            responsive: true
        });

        $(".sidebar-nav")
            .attr("class", "sidebar-nav navbar-collapse collapse")
            .attr("aria-expanded", 'false')
            .attr("style", "height:1px");
    });
</script>

</body>

</html>