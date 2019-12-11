SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog`  (
  `blogId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '博客标题',
  `coverImg` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '封面相对路径',
  `content` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '博客内容',
  `summary` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '博客摘要',
  `categoryId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '分类ID',
  `label` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '博客标签，冗余字段',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '博文状态，1表示已经发表，2表示在草稿箱，3表示在垃圾箱',
  `hits` int(11) NULL DEFAULT NULL COMMENT '点击量',
  `height` int(11) NULL DEFAULT NULL COMMENT '权重',
  `originalFlag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '原创标识,0:转载，1:原创',
  `support` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '是否推荐，1表示推荐，0表示不推荐',
  `delFlag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标识(1:删除,0:正常)',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  `updateDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`blogId`) USING BTREE,
  INDEX `IDX_categoryId`(`categoryId`) USING BTREE COMMENT '分类id索引',
  INDEX `IDX_status`(`status`) USING BTREE COMMENT '博客状态索引',
  INDEX `IDX_createDate`(`createDate`) USING BTREE COMMENT '博客创建时间索引',
  INDEX `IDX_updateDate`(`updateDate`) USING BTREE COMMENT '博客修改时间索引',
  INDEX `IDX_delFlag`(`delFlag`) USING BTREE COMMENT '博客删除状态索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '博客表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_blog_label
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_label`;
CREATE TABLE `t_blog_label`  (
  `blId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `blogId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '博客id',
  `labelId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标签id',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`blId`) USING BTREE,
  INDEX `IDX_blogId`(`blogId`) USING BTREE COMMENT '博客id索引',
  INDEX `IDX_labelId`(`labelId`) USING BTREE COMMENT '标签id索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '博客标签关联表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_book_list
-- ----------------------------
DROP TABLE IF EXISTS `t_book_list`;
CREATE TABLE `t_book_list`  (
  `bookListId` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ' 书单主键',
  `bookName` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '书名',
  `bookAuthor` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '作者',
  `readBeginDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阅读开始时间',
  `readEndDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阅读结束时间',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `updateDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新时间',
  `reviews` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '书评',
  `readStatus` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '阅读状态，0:未开始，1:阅读中,2:已结束',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注说明',
  PRIMARY KEY (`bookListId`) USING BTREE,
  UNIQUE INDEX `IDX_NAME_AUTHOR`(`bookName`, `bookAuthor`) USING BTREE COMMENT '书名和作者名联合索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '书单表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `categoryId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `categoryName` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类型名称',
  `categoryIcon` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '类型图标相对路径',
  `height` int(11) NULL DEFAULT NULL COMMENT '权重',
  `delFlag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标识(1:删除,0:正常)',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  `updateDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '描述信息',
  PRIMARY KEY (`categoryId`) USING BTREE,
  INDEX `IDX_categoryName`(`categoryName`) USING BTREE COMMENT '分类名称索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '博客类型表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `dictId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `keyWord` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典关键字',
  `dictName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典名',
  `dictValue` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '字典值',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `delFlag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标识(1:删除,0:正常)',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  `updateDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dictId`) USING BTREE,
  INDEX `IDX_keyWord`(`keyWord`) USING BTREE COMMENT '字典关键字索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '字典表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_file_item
-- ----------------------------
DROP TABLE IF EXISTS `t_file_item`;
CREATE TABLE `t_file_item`  (
  `fileId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `fileName` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件名',
  `fileHash` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '文件hash值',
  `fileSize` int(10) NOT NULL COMMENT '文件大小',
  `uploadDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '上传时间',
  `serverType` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '图床保存的文件的类型（1表示在本地存储，2表示存储在七牛云）',
  `filePath` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问路径',
  PRIMARY KEY (`fileId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_label
-- ----------------------------
DROP TABLE IF EXISTS `t_label`;
CREATE TABLE `t_label`  (
  `labelId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `labelName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '标签名',
  `delFlag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标识(1:删除,0:正常)',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '创建时间',
  `updateDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`labelId`) USING BTREE,
  INDEX `IDX_labelName`(`labelName`) USING BTREE COMMENT '标签名称索引'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '博客标签表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_links
-- ----------------------------
DROP TABLE IF EXISTS `t_links`;
CREATE TABLE `t_links`  (
  `linksId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `linksName` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '友链名称',
  `linksType` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '0' COMMENT '友链类型(友链类别 0-友链 1-推荐网站)',
  `description` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '友链描述',
  `headerImg` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '图标地址',
  `linksUrl` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '友链地址',
  `linksEmail` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮件',
  `display` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '1' COMMENT '是否显示(1:显示,0:不显示)',
  `delFlag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标识(1:删除,0:正常)',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  `updateDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `linksRank` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '排序值',
  PRIMARY KEY (`linksId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '友链表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log`  (
  `loginLogId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `loginName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `loginIp` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录IP地址',
  `loginLocation` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录地区',
  `loginBrowser` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录浏览器',
  `loginOs` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录操作系统',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录状态(0:成功,1:失败)',
  `loginMsg` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '登录描述',
  `loginDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录时间',
  PRIMARY KEY (`loginLogId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '登录日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `t_operation_log`;
CREATE TABLE `t_operation_log`  (
  `operId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `module` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '模块名',
  `businessType` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '业务类型',
  `method` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '方法名',
  `operIp` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求IP地址',
  `operLocation` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作地区',
  `operName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作人名称',
  `operUrl` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求url',
  `operBrowser` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作浏览器',
  `operOs` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '操作系统',
  `operParam` varchar(2000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求参数',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '请求状态(0:成功,1:失败)',
  `errorMsg` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '错误信息',
  `operDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '操作时间',
  PRIMARY KEY (`operId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '访问时间' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `userId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户ID',
  `loginName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '登录名',
  `userName` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `userType` varchar(2) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户类型',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '邮件',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '电话',
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '头像相对路径',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '密码',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '帐号状态(0正常 1停用)',
  `delFlag` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '0' COMMENT '删除标识(1:删除,0:正常)',
  `loginIp` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后登录IP',
  `loginDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '最后登录时间',
  `createDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '创建时间',
  `updateDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`userId`) USING BTREE,
  INDEX `IDX_loginName`(`loginName`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for t_visit_log
-- ----------------------------
DROP TABLE IF EXISTS `t_visit_log`;
CREATE TABLE `t_visit_log`  (
  `visitLogId` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `visitIp` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问者IP',
  `visitLocation` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问者地区',
  `visitBrowser` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问者浏览器',
  `visitOs` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问者操作系统',
  `requestUrl` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问者请求地址',
  `errorMsg` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '访问错误信息',
  `module` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问模块',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问状态(0:成功,1:失败)',
  `visitDate` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '访问时间',
  PRIMARY KEY (`visitLogId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '登录时间' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
