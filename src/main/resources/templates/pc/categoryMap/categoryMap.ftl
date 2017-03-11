<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="language" content="zh-CN">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta name="renderer" content="webkit">
    <title>类目映射配置</title>
    <script src="${springMacroRequestContext.contextPath}/js/jquery.js"></script>
    <script src="${springMacroRequestContext.contextPath}/js/string.js"></script>
    <script>
        function generateCategoryNameCollection(categoryName) {
            if (categoryName != "") {
                $.ajax({
                    url: '${request.contextPath}/categoryMap/generateCategoryNameCollection',
                    data: {"category" : categoryName},
                    cache: false,
                    async: false,
                    type: "POST",
                    dataType: 'json',
                    success: function (obj) {
                        $("#categoryNameCollection").text(obj.categoryNameCollection);
                    }
                });
            }
        }

        function updateCategoryMap() {
            var categoryNameCollection = $("#categoryNameCollection").val();
            var categoryName = $("#categoryName").val();
            if (categoryNameCollection.trimNull() == ""
             || categoryName == "") {
                alert("请先选择好类目和填写好需要映射的类目集合");
                return false;
            }
            if (contains(categoryNameCollection, " ", true) || contains(categoryNameCollection, "，", true)) {
                alert("映射的类目集合包含特殊字符（空格或中文，）");
                return false;
            }
            $.ajax({
                url: '${request.contextPath}/categoryMap/updateCategoryMap',
                data: {"category" : categoryName, "categoryNameCollection" : categoryNameCollection},
                cache: false,
                async: false,
                type: "POST",
                dataType: 'json',
                success: function (obj) {
                    alert("更新成功");
                }
            });
        }
    </script>
</head>
<body>
    <table>
        <tr>
            <td>类目</td>
            <td>
                <select id="categoryName" onchange="return generateCategoryNameCollection(this.value);">
                    <option value="">请选择类目</option>
                    <option value="服装衣帽">服装衣帽</option>
                    <option value="数码家电">数码家电</option>
                    <option value="洗护美妆">洗护美妆</option>
                    <option value="母婴用品">母婴用品</option>
                    <option value="吃货美食">吃货美食</option>
                    <option value="居家百货">居家百货</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>对应类目</td>
            <td>
                <textarea style="height: 350px;" id="categoryNameCollection">
                </textarea>
                <span style="color: red">以英文,分隔 不要出现特殊字符</span>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input id="updateCategoryButton" type="button" value="更新类目" onclick="return updateCategoryMap();"></td>
        </tr>
    </table>
</body>
</html>