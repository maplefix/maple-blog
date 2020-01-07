/**
 * @author Maple created on 2019/9/25
 * @description 页面加载完后加载回到顶部
 * @version v1.0
 */
$(document).ready(function(){
    //首先将#back-to-top隐藏
    $("#back-top").hide();
    //当滚动条的位置处于距顶部300像素以下时，跳转链接出现，否则消失
    $(function () {
        $(window).scroll(function(){
            if ($(window).scrollTop()>300){
                $("#back-top").fadeIn(500);
            }else{
                $("#back-top").fadeOut(500);
            }
        });
        //当点击跳转链接后，回到页面顶部位置
        $("#back-top a").click(function(){
            $('body,html').animate({
                scrollTop:0
            },800);
            return false;
        });
    });
});