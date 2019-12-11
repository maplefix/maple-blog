[TOCM]

[TOC]
## 1.项目说明
>maple-blog是利用闲暇时两个多月时间搭建的一套个人博客系统，由于之前搭建的博客体量有点大，图片文件都是存在本地。部署服务器带宽根本吃不消。于是才有了现在的重构。博客前端是基于[Amaze](https://github.com/spiritree/typecho-theme-amaze "Amaze")主题，自己略作修改而来。后端采用[springboot2](https://spring.io/projects/spring-boot/ "springboot2")+[Themeleaf](https://www.thymeleaf.org/)+[AdminLTE3](https://adminlte.io/themes/dev/AdminLTE/index.html "AdminLTE3")搭建而成。

## 2.项目展示
前端
![](https://qny.maplefix.top/index-20191209063343.png)


![](https://qny.maplefix.top/archives-20191211105351.png)


![](https://qny.maplefix.top/links-20191211105422.png)


![](https://qny.maplefix.top/about-20191211105453.png)


![](https://qny.maplefix.top/reading-20191211105523.png)


后端
![](https://qny.maplefix.top/首页-20190925102328.png)


![](https://qny.maplefix.top/发布博客-20190925102413.png)


![](https://qny.maplefix.top/博客管理-20190925103056.png)


![](https://qny.maplefix.top/分类管理-20190925103109.png)


![](https://qny.maplefix.top/标签管理-20190925103120.png)


![](https://qny.maplefix.top/友链管理-20190925103131.png)


![](https://qny.maplefix.top/登录日志-20190925103213.png)


![](https://qny.maplefix.top/访问日志-20190925103223.png)


![](https://qny.maplefix.top/操作 日志-20190925103231.png)


![](https://qny.maplefix.top/服务器监控-20190925103241.png)


![](https://qny.maplefix.top/字典配置-20190925103258.png)


![](https://qny.maplefix.top/个人信息-20190925103307.png)



## 3.项目技术依赖说明
>前端技术

| 序号  | 技术名称  | 说明  | 地址  |
| ------------ | ------------ | ------------ | ------------ |
| 1  | AdminLTE3  | Bootstartp主体框架  |  [AdminLTE3](https://adminlte.io/themes/dev/AdminLTE/index.html "AdminLTE3") |
| 2  |  bootstrap | 项目主体样式  | [bootstrap](https://www.bootcss.com/ "bootstrap")  |
| 3  | Echarts  | 图表  | [echarts](https://www.echartsjs.com/zh/index.html "echarts")  |
| 4  | font-awesome  | 字体库  | [font-awesome](http://fontawesome.dashgame.com/ "font-awesome")  |
| 5  | jquery  |  前端神器 |  [jquery](https://jquery.com/ "jquery") |
| 6  | sweetalert  |  优秀的弹出层插件 |  [sweetalert](https://sweetalert.js.org/ "sweetalert") |
| 7  | cropbox  | 图片剪裁插件  |  [cropbox](https://github.com/hongkhanh/cropbox "cropbox") |
| 8  | Editor.md  | markdown编辑器  |  [Editor.md](https://pandao.github.io/editor.md/examples/ "Editor.md") |
| 9  | Tagsinput  | 标签插件  | [tagsinput](http://bootstrap-tagsinput.github.io/bootstrap-tagsinput/examples/ "tagsinput")  |
| 10 | Ajaxupload  | 文件上传插件  |   |
| 11 | Valine  |  优秀的评论插件 | [Valine](https://valine.js.org "Valine")  |
| 12 | busuanzi  | 站点访问统计  | [busuanzi](http://busuanzi.ibruce.info/ "busuanzi")  |
| 13 | thymeleaf  | 页面模板引擎  | [thymeleaf](https://www.thymeleaf.org/ "thymeleaf") |

>后端技术

| 序号  | 技术名称  | 说明  | 地址  |
| ------------ | ------------ | ------------ | ------------ |
| 1 | SpringBoot2  | 项目主体框架  | [springboot2](https://spring.io/projects/spring-boot/ "springboot2")  |
| 2 | tk.mybatis  |  持久层框架 | [tk.mybatis](https://mapperhelper.github.io/docs/ "tk.mybatis")  |
| 3 | Redis  |  缓存 |  [redis](https://redis.io/ "redis") |
| 4 | MySQL  |  数据库 | [MySQL](https://www.mysql.com/ "MySQL")  |
| 5 | Lombok  | 注解神器  |  [Lombok](https://projectlombok.org/ "Lombok") |
| 6 | 七牛云  | 图床  | [七牛云](https://www.qiniu.com/ "七牛云")  |
| 7 |  fastjson |  Json转换工具 | [fastjson](https://github.com/alibaba/fastjson "fastjson")  |
| 8 |  UserAgentUtils | 系统信息获取工具  | [bitwalker](https://www.bitwalker.eu/software/user-agent-utils "bitwalker")  |
| 9 |  kaptcha | 验证码插件  | [kaptcha](http://code.google.com/p/kaptcha/ "kaptcha")  |
| 10 |  oshi-core | 系统监控工具  | [oshi-core](https://github.com/oshi/oshi "oshi-core")  |
| 11 |  durid |  连接池工具 | [druid](https://druid.apache.org/ "druid")  |
| 12 |  spring-session | 统一session管理  |  [spring-session](https://spring.io/projects/spring-session "spring-session") |
| 13 |  pagehelper | 分页插件  |  [pagehelper](https://pagehelper.github.io/ "pagehelper") |
| 14 |  pagehelper | Swagger  |  [API接口工具](https://swagger.io/ "API接口工具") |
