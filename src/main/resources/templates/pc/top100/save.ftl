<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="language" content="zh-CN">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta name="renderer" content="webkit">
    <title>TOP100录入</title>
    <script src="${springMacroRequestContext.contextPath}/js/jquery.js"></script>
    <script src="${springMacroRequestContext.contextPath}/js/string.js"></script>

    <script>
        function upload() {
            var picUrl = $("#picUrl").val();
            var title = $("#title").val();
            var quanAmount = $("#quanAmount").val();
            var price = $("#price").val();
            var soldNum = $("#soldNum").val();
            var tkl = $("#tkl").val();
            var quanUrl =$("#quanUrl").val();
            if (picUrl.isNull() || title.isNull() || quanAmount.isNull() || price.isNull()  || soldNum.isNull() || quanUrl.isNull() || tkl.isNull()) {
                alert("所有输入框都不能为空或空字符串");
                return false;
            }
            if (!quanAmount.isPositiveInteger()) {
                alert("券面额必须为正整数");
                return false;
            }
            if (!soldNum.isPositiveInteger()) {
                alert("销量必须为正整数");
                return false;
            }
            if (!price.isPrice()) {
                alert("价格格式不正确");
                return false;
            }
            $.ajax({
                url: '${request.contextPath}/top100/upload',
                data: {"picUrl" : picUrl,"title" : title,"quanAmount" : quanAmount,"price" : price,"soldNum" : soldNum,"quanUrl" : quanUrl,"tkl" : tkl},
                cache: false,
                async: false,
                type: "POST",
                dataType: 'json',
                success: function (obj) {
                    if (obj.status == 200) {
                        alert("录入成功");
                    } else {
                        alert("录入失败");
                    }
                }
            });
        }

    </script>
    <style type="text/css">
        body{ text-align:center}
        div{
            margin: 0 auto;
            margin-left: 40%;
            margin-top: 10%;
        }
    </style>
</head>
<body>

<div>
    <table>
        <tr>
            <td>图片</td>
            <td>
                <input type="text" id="picUrl" style="width:400px">
            </td>
        </tr>
        <tr>
            <td>标题</td>
            <td>
                <input type="text" id="title" style="width:400px">
            </td>
        </tr>
        <tr>
            <td>券面额</td>
            <td>
                <input type="text" id="quanAmount">
            </td>
        </tr>
        <tr>
            <td>领券地址</td>
            <td>
                <input type="text" id="quanUrl" style="width:400px">
            </td>
        </tr>
        <tr>
            <td>现价</td>
            <td>
                <input type="text" id="price">
            </td>
        </tr>
        <tr>
            <td>销量</td>
            <td>
                <input type="text" id="soldNum">
            </td>
        </tr>
        <tr>
            <td>淘口令</td>
            <td>
                <input type="text" id="tkl">
            </td>
        </tr>
        <tr>
            <td><input type="button" value="收录" onclick="upload();"></td>
        </tr>
    </table>
</div>
</body>
</html>