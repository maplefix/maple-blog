/**
 * @author Maple created on 2019/9/25
 * @description 页面内容加载完后加载底部的网站运行时间
 * @version v2.1
 */
$(function () {
    $('#searchbox').keypress(function (e) {
        var key = e.which; //e.which是按键的值
        if (key == 13) {
            var q = $(this).val();
            if (q && q != '') {
                window.location.href = '/search/' + q + '.html';
            }
        }
    });
});

function search() {
    var q = $('#searchbox').val();
    if (q && q != '') {
        window.location.href = '/search/' + q +'.html';
    }
}