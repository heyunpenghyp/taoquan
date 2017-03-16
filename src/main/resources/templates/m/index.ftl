<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no">
    <title>猪猪淘精选-天猫内部优惠券</title>
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/m/amazeui.min.css">
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/m/style.css" />
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/m/allinone.css" />
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/m/grandeur.css" />
    <link rel="stylesheet" type="text/css" href="${springMacroRequestContext.contextPath}/css/m/index.css" />
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/js/m/jquery.min.js"></script>
    <script type="text/javascript" src="${springMacroRequestContext.contextPath}/js/m/amazeui.min.js"></script>
    <script type="text/javascript">
        $(window).load(function() {
            var lazyImages = $(".lazy");
            for (var i = 0; i < lazyImages.length; i++) {
                var lazyImage = lazyImages[i];
                $(lazyImage).attr("src", $(lazyImage).attr("xSrc"));
            }
        });
    </script>
</head>

<body>
<div class="am-modal am-modal-alert" tabindex="-1" id="fq_alert">
    <div class="am-modal-dialog">
        <div class="am-modal-hd" id="fq_alert_title"></div>
        <div class="am-modal-bd" id="fq_alert_info">
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn">确定</span>
        </div>
    </div>
</div>

<div class="am-modal am-modal-confirm" tabindex="-1" id="fq_confirm">
    <div class="am-modal-dialog">
        <div class="am-modal-hd" id="fq_confirm_title"></div>
        <div class="am-modal-bd" id="fq_confirm_info"></div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
        </div>
    </div>
</div>


<div class="main">
    <!-- 全部推荐商品开始 -->
    <ul class="am-avg-sm-2 am-margin-top-sm item_list">
    <#list productList as product>
        <li class="fq-goods am-padding-bottom-sm am-text-center" id="goods-item-content">
            <img class="lazy am-thumbnail am-margin-bottom-0 am-block tobuy" data-p="1"  xSrc="${product.picUrl}" width="430px" height="430px"/>
            <a class="am-block am-text-sm am-text-truncate am-padding-horizontal-xs fq-title am-padding-bottom-xs tobuy" data-id="276245182" data-p="1" >
                <strong class="item_details">${product.title}</strong>
            </a>

            <div class="am-inline-block am-text-xs am-fl fq-post fq-text-white am-margin-left-xs am-padding-horizontal-xs am-margin-bottom-xs tobuy" data-p="1" >
                包邮
            </div>
            <div class="am-inline-block am-text-xs am-fl fq-coupon fq-text-white am-padding-horizontal-xs am-margin-bottom-xs tobuy" data-p="1" >
                <span>${product.quanAmount}</span>元优惠券
            </div>
            <div style="margin-left: 5px" class="am-inline-block am-text-xs am-fl fq-coupon1 fq-text-white am-padding-horizontal-xs am-margin-bottom-xs tobuy" data-p="1" >
                <span>${product.sxInfo}</span>
            </div>

            <div class="am-inline-block tobuy" data-p="1"  style="width: 100%">
                <div class="am-fl fq-price am-padding-left-xs">
                    <span class="am-text-xs am-padding-right-xs">券后</span>
                    <strong class="fq-text-default">${product.price}</strong>

                </div>
                <div class="am-text-xs am-fr am-padding-right-xs fq-abstract">已售${product.soldNum}</div>
            </div>
            <ul class="am-avg-sm-2">
                <li class="am-padding-horizontal-xs">
                    <button type="button" taokl="${product.tkl}" onclick="changeTaokouling(this)" class="fq-amoy fq-text-white am-btn am-padding-horizontal-xs am-text-sm" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0}">
                        淘口令购买
                    </button>
                </li>
            </ul>
        </li>
    </#list>
    </ul>

    <div style="" class="am-text-xs am-text-center am-margin-vertical-sm">
        猪猪淘精选-天猫内部优惠券 ©版权所有
    </div>
</div>




<script>
    function changeTaokouling(obj) {
        $("#copy_key_ios").text($(obj).attr("taokl"));
        $("#copy_key_android").text($(obj).attr("taokl"));
    }
</script>

<div class="am-hide"></div>
<#--<script type="text/javascript" src="${springMacroRequestContext.contextPath}/js/m/clipboard.min.js"></script>-->
<!--底部菜单-->
<div class="fq-amoy-buy am-modal am-modal-no-btn" tabindex="-1" id="doc-modal-1">
    <div class="fq-background-white am-modal-dialog">
        <div class="am-modal-hd">
            淘口令购买
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd am-padding-0">
            <!--淘口令-->
            <!--二合一淘口令开始-->
            <div class="am-margin-vertical am-margin-horizontal-lg">
                <div class="fq-goods-border fq-background-white am-text-center am-margin-top am-padding-vertical am-padding-horizontal-sm">
                    <div class="fq-explain am-center am-text-center">
                        <span class="fq-nowrap fq-text-white am-padding-horizontal-sm">长按框内 &gt; 全选 &gt; 复制</span>
                    </div>
                    <span id="copy_key_ios" style="display: none;">￥b1scOsZfEV￥</span>
                    <textarea style="height: 20px; display: inline-block;" id="copy_key_android" type="text" class="am-form-field am-text-center am-text-sm" oninput="regain();">￥b1scOsZfEV￥</textarea>
                </div>
            </div>
            <!--二合一淘口令结束-->
            <div class="copy_taowords am-margin-bottom" style="display:none;">

                <div class="am-text-center am-margin-top-sm">
                    <div class="share_content am-margin-bottom am-badge-success am-badge"></div>
                    <a class="share am-padding-vertical-xs am-padding-horizontal-lg am-round am-inline-block" data-taowords="￥RUeWkGEPkN￥">
                        一键复制
                    </a>
                    <div class="am-margin-top-sm am-text-xs am-text-primary am-margin-horizontal-sm">点击复制后，请打开【手机淘宝】购买！</div>
                </div>
            </div>
            <div class="fq-instructions am-text-left am-text-xs am-padding-vertical-sm am-padding-horizontal-lg">
                        <span>
                            <span>使用说明：</span>
                            <span>复制淘口令后打开【手机淘宝】即可领取优惠券购买！</span>
                        </span>
                <#--<br />-->
                        <#--<span>-->
                            <#--<span>温馨提示：</span>-->
                            <#--手机无【手机淘宝】者，可选择浏览器购买方式哦~-->
                        <#--</span>-->
            </div>
        </div>
    </div>
</div>
<div class="copy_taoword_content am-margin-bottom am-badge-success am-badge" id="copy_taoword_content"></div>
<script>
    $(function () {
        //事件监听
        //------------------------------------------
//        var ua = navigator.userAgent.toLowerCase();
//        if (ua.match(/iphone os 10/i) == "iphone os 10") {
//            $('.fq-amoy-buy .copy_taowords').show();
//            var clipboard = new Clipboard('.share', {
//                target: function() {
//                    return document.querySelector('.copy_taoword_content');
//                }
//            });
//            clipboard.on('success', function(e){
//                e.trigger.innerHTML="已复制";
//                e.trigger.style.backgroundColor="#9ED29E";
//                e.trigger.style.borderColor="#9ED29E";
//                //var conpycontent=$(".share_content").html();
//                //console.log(conpycontent);
//                $(".copy_taoword_content").html('');
//                console.info('Action:', e.action);
//                console.info('Text:', e.text);
//                console.info('Trigger:', e.trigger);
//                e.clearSelection();
//            });
//            clipboard.on('error', function(e) {
//                $(".copy_taoword_content").html('');
//                $("#fq_alert_info").html("<div class=\"am-text-danger\">由于您的浏览器不兼容或当前网速较慢，复制失败，请手动复制或更换主流浏览器！</div><div class=\"am-margin\" style=\"text-align: left;\">"+$(".copy_taoword_content").html()+"</div>");
//                $('#fq_alert').modal();
//            });
//        }
        document.addEventListener("selectionchange", function (e) {
            if (window.getSelection().anchorNode.parentNode.id == 'copy_key_ios' && document.getElementById('copy_key_ios').innerText != window.getSelection()) {
                var key = document.getElementById('copy_key_ios');
                window.getSelection().selectAllChildren(key);
            }
            if (window.getSelection().anchorNode.parentNode.id == 'copy_key_ios1' && document.getElementById('copy_key_ios1').innerText != window.getSelection()) {
                var key = document.getElementById('copy_key_ios1');
                window.getSelection().selectAllChildren(key);
            }
            if (window.getSelection().anchorNode.parentNode.id == 'copy_key_ios2' && document.getElementById('copy_key_ios2').innerText != window.getSelection()) {
                var key = document.getElementById('copy_key_ios2');
                window.getSelection().selectAllChildren(key);
            }
            if (window.getSelection().anchorNode.parentNode.id == 'copy_key_ios3' && document.getElementById('copy_key_ios3').innerText != window.getSelection()) {
                var key = document.getElementById('copy_key_ios3');
                window.getSelection().selectAllChildren(key);
            }
            if (window.getSelection().anchorNode.parentNode.id == 'copy_key_ios4' && document.getElementById('copy_key_ios4').innerText != window.getSelection()) {
                var key = document.getElementById('copy_key_ios4');
                window.getSelection().selectAllChildren(key);
            }
            if (window.getSelection().anchorNode.parentNode.id == 'copy_key_ios5' && document.getElementById('copy_key_ios5').innerText != window.getSelection()) {
                var key = document.getElementById('copy_key_ios5');
                window.getSelection().selectAllChildren(key);
            }
        }, false);
    });
    var ua = navigator.userAgent.toLowerCase();
    if (ua.match(/iphone/i) == "iphone" || ua.match(/ipad/i) == "ipad") {
        $('.fq-explain span').html("长按框内 > 拷贝");
        $("#copy_key_ios").show();
        $("#copy_key_ios1").show();
        $("#copy_key_ios2").show();
        $("#copy_key_ios3").show();
        $("#copy_key_ios4").show();
        $('#copy_key_ios5').show();
        $("#copy_key_android").hide();
        $("#copy_key_android1").hide();
        $("#copy_key_android2").hide();
        $("#copy_key_android3").hide();
        $("#copy_key_android4").hide();
        $("#copy_key_android5").hide();
    } else {
        $("#copy_key_ios").hide();
        $("#copy_key_ios1").hide();
        $("#copy_key_ios2").hide();
        $("#copy_key_ios3").hide();
        $("#copy_key_ios4").hide();
        $('#copy_key_ios5').hide();
        $("#copy_key_android").show();
        $("#copy_key_android1").show();
        $("#copy_key_android2").show();
        $("#copy_key_android3").show();
        $("#copy_key_android4").show();
        $("#copy_key_android5").show();
    }
    $(".fq-amoy-buy .share").on('click', function() {
        var taowords = $(this).attr('data-taowords');
        console.log(taowords);
        $('.copy_taoword_content').html(taowords);
    })

    if(document.getElementById("copy_key_android")){
        var taokouling_value = document.getElementById("copy_key_android").value;
        function regain() {
            document.getElementById('copy_key_android').value = taokouling_value;
        }
    }
    if(document.getElementById("copy_key_android1")){
        var taokouling_value1 = document.getElementById("copy_key_android1").value;
        function regain1() {
            document.getElementById('copy_key_android1').value = taokouling_value1;
        }
    }
    if(document.getElementById("copy_key_android2")){
        var taokouling_value2 = document.getElementById("copy_key_android2").value;
        function regain2() {
            document.getElementById('copy_key_android2').value = taokouling_value2;
        }
    }
    if(document.getElementById("copy_key_android3")){
        var taokouling_value3 = document.getElementById("copy_key_android3").value;
        function regain3() {
            document.getElementById('copy_key_android3').value = taokouling_value3;
        }
    }
    if(document.getElementById("copy_key_android4")){
        var taokouling_value4 = document.getElementById("copy_key_android4").value;
        function regain4() {
            document.getElementById('copy_key_android4').value = taokouling_value4;
        }
    }
    if(document.getElementById("copy_key_android5")){
        var taokouling_value5 = document.getElementById("copy_key_android5").value;
        function regain5() {
            document.getElementById('copy_key_android5').value = taokouling_value4;
        }
    }

</script>
</body>
</html>
