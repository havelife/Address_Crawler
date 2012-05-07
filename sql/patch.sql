CREATE TABLE `fenye` 
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(100) DEFAULT NULL,
  `page` int(10) DEFAULT NULL,
  `content` mediumtext,
  `url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `page` (
`id`  int(10) NOT NULL AUTO_INCREMENT COMMENT '主键' ,
`url`  char(255) NOT NULL ,
`content`  mediumtext NULL COMMENT '页面内容' ,
`category`  char(100) NULL COMMENT '页面类别' ,
`domain`  char(200) NULL COMMENT '所属网站' ,
`iscompleted`  int(1) NULL DEFAULT 0 COMMENT '是否下载完成,0为未完成，1为已完成' ,
`type`  varchar(100) NULL COMMENT '备用的一列，做类别区分' ,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `url` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `isinit` int(2) DEFAULT '0' COMMENT '是否下载过, 0:未下载过，1:下载过',
  `iscompleted` int(2) DEFAULT '0' COMMENT '是否下载到页面内容, 0:没有页面内容，1:已经抓取到页面内容',
  `jobid` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `job` (
  `id` int(10) NOT NULL,
  `prefix` varchar(255) NOT NULL,
  `suffix` varchar(255) DEFAULT '''''',
  `step` int(5) DEFAULT '1',
  `startidx` int(5) DEFAULT '1',
  `endidx` int(5) NOT NULL,
  `count` int(5) DEFAULT '0' COMMENT '这个job总共有多少个页面',
  `completecount` int(5) DEFAULT '0' COMMENT '完成了下载的页面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


CREATE TABLE `log` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `comment` varchar(255) DEFAULT NULL,
  `operator` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8