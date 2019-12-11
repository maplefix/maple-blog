/**
 * @author Maple created on 2019/9/25
 * @description 页面内容加载完后加载底部的网站运行时间
 * @version v2.1
 */
window.onload = function(){
    // 开始时间
    let start = new Date("2019/9/16 16:39:00").getTime();
    setInterval(function(){
        // 现在
        let now = new Date().getTime();
        // 运行总时间
        let run = now -  start;
        // 总秒
        let sumSeconds = parseInt(run / 1000);
        // 天数
        let d = parseInt(sumSeconds / 86400);
        // 小时
        let h = parseInt(sumSeconds % 86400 / 3600);
        // 分钟
        let min = size(parseInt(sumSeconds / 60 % 60));
        // 秒
        let m = size(parseInt(sumSeconds % 60));
        // 插入
        document.querySelector('#runtime').innerHTML = "本站运行时间: " + d + "天 " + h + "小时 " + min + "分" + m + "秒";
    }, 1000)
    // 小于10的数，前边增加一个0
    function size(d){
        return d < 10 ? '0' + d : d;
    }
}