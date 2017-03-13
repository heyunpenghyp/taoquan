<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="language" content="zh-CN">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta name="renderer" content="webkit">
    <title>TOP100管理</title>
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/jquery.dataTables.min.css">
    <script src="${springMacroRequestContext.contextPath}/js/jquery.js"></script>
    <script src="${springMacroRequestContext.contextPath}/js/string.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        function del(pid) {
            $.ajax({
                url: '${request.contextPath}/top100/del',
                data: {"id" : pid},
                cache: false,
                async: false,
                type: "POST",
                dataType: 'json',
                success: function (obj) {
                    if (obj.status == 200) {
                        alert("删除成功");
                    } else {
                        alert("删除失败");
                    }
                }
            });
        }
    </script>
<body>
<div>
    <a href="/taoquan/top100/preSave" target="_blank">Top100录入</a>
</div>
<table id="top100List" class="display" cellspacing="0" width="100%">
    <thead>
        <tr>
            <th>图片</th>
            <th>标题</th>
            <th>券面额</th>
            <th>现价</th>
            <th>淘口令</th>
            <th>录入时间</th>
            <th>操作</th>
        </tr>
    </thead>
    <tbody>
        <#list productList as product>
            <tr>
                <td>${product.picUrl}</td>
                <td>${product.title}</td>
                <td>${product.quanAmount}</td>
                <td>${product.price}</td>
                <td>${product.tkl}</td>
                <td>${product.creatTimeDesc}</td>
                <td><input type="button" value="删除" onclick="del(${product.id})"></td>
            </tr>
        </#list>
    </tbody>
</table>

<script type="text/javascript">

    $(document).ready(function() {
        $('#top100List').dataTable();
    });
</script>
</body>
</html>
