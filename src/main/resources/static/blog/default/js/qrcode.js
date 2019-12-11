/**
 * @author Maple created on 2019/11/20
 * @description 其他设备阅读-调用二维码接口生成二维码，手机扫描可继续阅读
 * @version v2.1
 */
$(function () {
    //点击图标时生成二维码图片，点击图片之外的屈于二维码消失
    $("#otherDeviceRead").click(function(e){
        loadQrCode();
        $("#qrCodeRead").show();
        e.stopPropagation();
        $(document).one('click',function(){
            $("#qrCodeRead").hide();
        })
    });
    $("#qrCodeRead").click(function(e){
        e.stopPropagation();
    })
});

/**
 * 加载二维码
 */
function loadQrCode() {
    let content = window.location.href;
    //加载二维码
    $.ajax({
        url: '/createBase64String',//createBufferedImage
        //type: "POST",
        data:{
            "content":content,
            "needLogo":"false"
        },
        success: function (result) {
            //点击图片时生成当前页面url的二维码
            $("#qrCodeRead").attr("src",result);

        }
    });
}