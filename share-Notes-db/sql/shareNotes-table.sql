-- 用户表

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(63) NOT NULL COMMENT '用户名称',
  `password` varchar(63) NOT NULL DEFAULT '' COMMENT '用户密码',
  `gender` tinyint(3) NOT NULL DEFAULT '0' COMMENT '性别：0 未知， 1男， 1 女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `last_login_time` datetime DEFAULT NULL COMMENT '最近一次登录时间',
  `last_login_ip` varchar(63) NOT NULL DEFAULT '' COMMENT '最近一次登录IP地址',
  `nickname` varchar(63) NOT NULL DEFAULT '' COMMENT '用户昵称或网络名称',
  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '用户手机号码',
  `avatar` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像图片',
  `weixin_openid` varchar(63) NOT NULL DEFAULT '' COMMENT '微信登录openid',
  `session_key` varchar(100) NOT NULL DEFAULT '' COMMENT '微信登录会话KEY',
  `status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '0 可用, 1 禁用, 2 注销',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';



DROP TABLE IF EXISTS `user_formid`;
CREATE TABLE `user_formid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `formId` varchar(63) NOT NULL COMMENT '缓存的FormId',
  `isprepay` tinyint(1) NOT NULL COMMENT '是FormId还是prepayId',
  `useAmount` int(2) NOT NULL COMMENT '可用次数，fromId为1，prepay为3，用1次减1',
  `expire_time` datetime NOT NULL COMMENT '过期时间，腾讯规定为7天',
  `openId` varchar(63) NOT NULL COMMENT '微信登录openid',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;


-- 管理员，待定

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `realName` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `addDate` date DEFAULT NULL,
  `updateDate` TIMESTAMP DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT = '管理员';


-- 用户搜索，监控,待定

DROP TABLE IF EXISTS `tb_search_history`;
CREATE TABLE `tb_search_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '用户表的用户ID',
  `keyword` varchar(63) NOT NULL COMMENT '搜索关键字',
  `from` varchar(63) NOT NULL DEFAULT '' COMMENT '搜索来源',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='搜索历史表';



----- 以下是文章的

-- 目录，用户可以自己创建


CREATE TABLE IF NOT EXISTS `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(100) DEFAULT '',
  `name` varchar(50) NOT NULL,
  `parent_id` int(11) DEFAULT '0',
  `slug_name` varchar(63) NOT NULL COMMENT '用户id',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;


-- 评论表


CREATE TABLE IF NOT EXISTS `comments` (
  `type` int(11) NOT NULL DEFAULT '0',
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `author` varchar(50) NOT NULL,
  `content` varchar(1023) NOT NULL,
  `is_anonymous` int(11) NOT NULL DEFAULT 0,
  `post_id` int(11) NOT NULL,
  `status` int(11) DEFAULT '1',
  `top_priority` int(11) DEFAULT '0',
  `user_id` int(11) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;


-- 日志表

CREATE TABLE IF NOT EXISTS `logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `content` varchar(1023) NOT NULL,
  `user_id` varchar(1023) DEFAULT '' COMMENT '用户id',
  `type` int(11) NOT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4;


-- 菜单表，分为私人和公共


CREATE TABLE IF NOT EXISTS `menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `name` varchar(50) NOT NULL,
  `target` varchar(20) DEFAULT '_self',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;


-- 文章表

CREATE TABLE IF NOT EXISTS `posts` (
  `type` int(11) NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_from` int(11) DEFAULT '0' COMMENT '用户id',
  `disallow_comment` int(11) DEFAULT '0' COMMENT '是否允许评论',
  `edit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `format_content` text NOT NULL COMMENT '标题 下面的文字，控制字数',
  `original_content` text NOT NULL,
  `title` varchar(100) NOT NULL,
  `top_priority` int(11) DEFAULT '0',
  `visits` bigint(20) DEFAULT '0',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4;


-- 文章的目录归类

CREATE TABLE IF NOT EXISTS `post_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `category_id` int(11) DEFAULT NULL,
  `post_id` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;




-- 以下是群组表

CREATE TABLE IF NOT EXISTS `user_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;



-- 以下是消息表


-- 好友申请消息

CREATE TABLE IF NOT EXISTS `msg_request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `friend_id` int(11) DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- 消息日志
DROP TABLE IF EXISTS `sys_msg_log`;
CREATE TABLE `sys_msg_log` (
  `id` bigint(20) NOT NULL  COMMENT '编号',
  `rec_id` varchar(45) NOT NULL COMMENT '接受者编号，如为0，则接受者为所有人',
  `send_id` varchar(255) NOT NULL DEFAULT '0' COMMENT '发送者编号，0：默认为系统',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱，无则为空',
  `message_id` int(20)  DEFAULT NULL COMMENT '消息的编号[foreign key]',
  `status` int(5) DEFAULT NULL COMMENT '消息的查看状态，0：未读，1：已查看，2：删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT = '消息记录表';


DROP TABLE IF EXISTS `sys_msg`;
CREATE TABLE `sys_msg` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '消息的编号',
  `msg_type_id` varchar(45) NOT NULL COMMENT '文档Id',
  `title` varchar(255)  COMMENT '消息标题',
  `message_text` text DEFAULT NULL COMMENT '消息内容',
  `type` int(5) DEFAULT NULL COMMENT '消息类型， type类型有[默认]0：Private(私信)、1：Public(公共消息)、2：Global(系统消息)，-1：未知消息',
  `start_time` TIMESTAMP DEFAULT NULL COMMENT '消息生效日期',
  `post_time` TIMESTAMP DEFAULT NULL COMMENT '消息发送时间',
  `end_time` TIMESTAMP DEFAULT NULL COMMENT '消息失效日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT = '消息内容';
