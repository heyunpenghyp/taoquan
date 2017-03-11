<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="language" content="zh-CN">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta name="renderer" content="webkit">
    <title>登录护航</title>
    <script src="${springMacroRequestContext.contextPath}/js/jquery.js"></script>
    <style type="text/css">
        table{
            width:1000px;
            table-layout:fixed;
        }
    </style>
    <script>
        function manualLogin() {
            $("#loginHelpButton").attr('disabled',true);
            $.ajax({
                url: '${request.contextPath}/loginHelp/login',
                data: {},
                cache: false,
                async: false,
                type: "POST",
                dataType: 'json',
                success: function (obj) {
                    alert(obj.msg);
                    $("#loginHelpButton").attr('disabled',false);
                }
            });
        }

        function manualDownload() {
            $("#downloadHelpButton").attr('disabled',true);
            $.ajax({
                url: '${request.contextPath}/loginHelp/download',
                data: {},
                cache: false,
                async: false,
                type: "POST",
                dataType: 'json',
                success: function (obj) {
                    alert(obj.msg);
                    $("#downloadHelpButton").attr('disabled',trufalsee);
                }
            });
        }

        function refreshLoginInfo() {
            $.ajax({
                url: '${request.contextPath}/loginHelp/refreshLoginInfo',
                data: {},
                cache: false,
                async: false,
                type: "POST",
                dataType: 'json',
                success: function (obj) {
                    $("#loginRole").text(obj.loginRole);
                    $("#lastLoginTime").text(obj.lastLoginTime);
                    $("#loginSuccFlag").text(obj.loginSuccFlag);
                    $("#loginErrorMsg").text(obj.loginErrorMsg);
                    $("#cookie").text(obj.cookie);
                    $("#_tb_token_").text(obj._tb_token_);
                    $("#lastDownloadSussTime").text(obj.lastDownloadSussTime);
                    $("#xlsToDbProcessSuccNum").text(obj.xlsToDbProcessSuccNum);
                    $("#xlsToDbProcessErrorNum").text(obj.xlsToDbProcessErrorNum);
                    $("#applyDxSuccNum").text(obj.applyDxSuccNum);
                    $("#applyHdSuccNum").text(obj.applyHdSuccNum);
                    $("#downloadInfo").text(obj.downloadInfo);
                    $("#tryLoginNum").text(obj.tryLoginNum);
                }
            });
        }

        window.setInterval(refreshLoginInfo, 1000 * 5);
    </script>
</head>
<body>
    <table border="1" >
        <tr>
            <td width="200px" style="width: 200px !important;">上一次登录角色</td>
            <td id="loginRole">${loginRole}</td>
        </tr>
        <tr>
            <td>上一次登录时间</td>
            <td id="lastLoginTime">${lastLoginTime}</td>
        </tr>
        <tr>
            <td>失败后机器自动重新登录的次数</td>
            <td id="tryLoginNum">${tryLoginNum}</td>
        </tr>

        <tr>
            <td>登录状态</td>
            <td id="loginSuccFlag">${loginSuccFlag}</td>
        </tr>
        <tr>
            <td>登录异常日志</td>
            <td id="loginErrorMsg">${loginErrorMsg}</td>
        </tr>
        <tr>
            <td>cookie</td>
            <td id="cookie">${cookie}</td>
        </tr>
        <tr>
            <td>_tb_token_</td>
            <td id="_tb_token_">${_tb_token_}</td>
        </tr>
        <tr>
            <td>上一次下载阿里妈妈券商品XLS成功时间</td>
            <td id="lastDownloadSussTime">${lastDownloadSussTime}</td>
        </tr>
        <tr>
            <td>下载情况描述</td>
            <td id="downloadInfo">${downloadInfo}</td>
        </tr>
        <tr>
            <td>XLS入库成功条数</td>
            <td id="xlsToDbProcessSuccNum">${xlsToDbProcessSuccNum}</td>
        </tr>
        <tr>
            <td>XLS入库失败条数</td>
            <td id="xlsToDbProcessErrorNum">${xlsToDbProcessErrorNum}</td>
        </tr>
        <tr>
            <td>人工登录</td>
            <td><input id="loginHelpButton" type="button" value="登录护航" onclick="return manualLogin();"></td>
        </tr>
        <tr>
            <td>申请定向条数</td>
            <td id="applyDxSuccNum">${applyDxSuccNum}</td>
        </tr>
        <tr>
            <td>申请高佣条数</td>
            <td id="applyHdSuccNum">${applyHdSuccNum}</td>
        </tr>
        <tr>
            <td>人工下载 </span></td>
            <td><input id="downloadHelpButton" type="button" value="下载护航" onclick="return manualDownload();">
            <span style="color: red">人工下载必须在每日早上10:30以后确认是否护航（因为10:05会开始跑每日定时任务，可以通过入库成功/失败条数来判断定时任务是否已经开跑了,
                全部入库完毕会更新上一次下载阿里妈妈券商品XLS成功时间），如果上一次下载阿里妈妈券商品XLS成功时间仍为昨日的，可以护航
            </td>
        </tr>
    </table>
</body>
</html>