TODO LIST

#### 技术选型
> 最近在学习vue，为了巩固实践性下打算重构maple-blog项目。
- 前端采用vue重构，因该会采用viewui或者elementui
- 后端接口模块采用springboot+shiro重构，引入权限管理，显示界面使用vue

#### 设计具体变更
- 1 后端整体技术选型采用springboot+mysql/redis提供数据驱动;
- 2 后端表结构重新设计，引入经典的`用户-角色-权限`模式。即新增五张表：
```
    用户表:t_user,
    角色表:t_role,
    菜单表:t_menu,
    用户角色表:t_user_role,
    角色菜单表:t_role_menu;
```
- 3 新增文件管理模块，对七牛云和本地文件进行管理，定时备份等;
- 4 系统新增统计功能，对阅读，访问等详细统计并可视化展示;
- 5 设计评论系统，不在使用第三方评论，使用系统自建评论;
- 6 接入第三方登陆，如微信扫码登陆,微博登陆,qq登陆等;

#### 表结构调整
- 1.标签表明由t_lable改为t_tag
- 2.操作日志表新加返回字段
#### 调整细节
    1.摒弃tk.mapper的大多数方法，只保留通用的select和insert，使用原始mybatis的SQL语句，查询及维护更灵活；
    2.单点逻辑：
思路
Spring集成Shiro
Shiro托管项目session，使用Redis保存Shiro缓存信息
SpringBoot集成kisso
kisso单点登录同步shiro授权信息

