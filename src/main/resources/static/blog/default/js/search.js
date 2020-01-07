/**
 * @author Maple created on 2019/9/25
 * @description 首页搜索功能
 * @version v1.0
 */
$(function () {
    $('#searchbox').keypress(function (e) {
        let key = e.which; //e.which是按键的值
        if (key == 13) {
            let q = $(this).val();
            if (q && q != '') {
                window.location.href = '/search/' + q + ".html";
            }
        }
    });
});

function search() {
    let q = $('#searchbox').val();
    if (q && q != '') {
        window.location.href = '/search/' + q + ".html";
    }
}