
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `app`
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) DEFAULT NULL,
  `code` varchar(30) DEFAULT NULL,
  `key` varchar(50) DEFAULT NULL,
  `available` char(1) DEFAULT 'y' COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO `app` VALUES ('1', 'xyTalk', 'xyTalk', null, null);
INSERT INTO `app` VALUES ('2', 'xyPortal', 'xyPortal', null, null);

-- ----------------------------
-- Table structure for `dailyreport`
-- ----------------------------
DROP TABLE IF EXISTS `dailyreport`;
CREATE TABLE `dailyreport` (
  `id` int(11) NOT NULL DEFAULT '0',
  `files` int(11) DEFAULT NULL,
  `groups` int(11) DEFAULT NULL,
  `spaces` int(11) DEFAULT NULL,
  `curdate` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dailyreport
-- ----------------------------

-- ----------------------------
-- Table structure for `diskfile`
-- ----------------------------
DROP TABLE IF EXISTS `diskfile`;
CREATE TABLE `diskfile` (
  `fileid` varchar(36) NOT NULL COMMENT '文件ID',
  `appid` int(11) DEFAULT NULL COMMENT '所属app应用ID',
  `formId` varchar(50) DEFAULT NULL COMMENT '用于集成第三方系统，比如表单ID',
  `filesource` varchar(10) DEFAULT NULL COMMENT '来源：xyFS\\Seafile\\FastDFS\\MongoDB\\BFS\\PaxosStore\\aliOSS\\qiniu\\CFS',
  `fileName` varchar(100) NOT NULL,
  `fileSize` bigint(20) DEFAULT NULL,
  `fileExt` varchar(20) DEFAULT NULL,
  `fileUrl` varchar(100) DEFAULT NULL,
  `fileFlag` char(1) DEFAULT NULL COMMENT '可用：1；已删除：0',
  `dfsGroupName` varchar(100) DEFAULT NULL,
  `uploadDate` datetime DEFAULT NULL,
  `uploadUser` varchar(50) DEFAULT NULL,
  `expirationDate` datetime DEFAULT NULL,
  `ispublic` char(1) DEFAULT NULL,
  `groupId` varchar(50) DEFAULT NULL,
  `groupName` varchar(100) DEFAULT NULL,
  `downloadNum` int(11) DEFAULT '0',
  `downloadPwd` varchar(64) DEFAULT NULL,
  `extra1` varchar(100) DEFAULT NULL,
  `extra2` varchar(100) DEFAULT NULL,
  `extra3` varchar(100) DEFAULT NULL,
  `extra4` varchar(100) DEFAULT NULL,
  `extra5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`fileid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diskfile
-- ----------------------------

-- ----------------------------
-- Table structure for `diskgroup`
-- ----------------------------
DROP TABLE IF EXISTS `diskgroup`;
CREATE TABLE `diskgroup` (
  `groupId` varchar(50) NOT NULL,
  `groupName` varchar(50) DEFAULT NULL,
  `fatherGroupId` varchar(50) DEFAULT NULL COMMENT '上级父亲组ID',
  `groupCnName` varchar(100) DEFAULT NULL,
  `groupFlag` char(1) DEFAULT NULL,
  `groupCreator` varchar(50) DEFAULT NULL,
  `groupType` varchar(10) DEFAULT NULL COMMENT '组类型：MUC、文档库、图片、私有组',
  `administrator` varchar(50) DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `maxNum` int(11) DEFAULT NULL,
  `extra1` varchar(100) DEFAULT NULL,
  `extra2` varchar(100) DEFAULT NULL,
  `extra3` varchar(100) DEFAULT NULL,
  `extra4` varchar(100) DEFAULT NULL,
  `extra5` varchar(100) DEFAULT '',
  `isPublic` char(255) DEFAULT 'y' COMMENT '是否是公开组',
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diskgroup
-- ----------------------------

-- ----------------------------
-- Table structure for `diskuser`
-- ----------------------------
DROP TABLE IF EXISTS `diskuser`;
CREATE TABLE `diskuser` (
  `userName` varchar(50) NOT NULL,
  `userCnName` varchar(50) DEFAULT NULL,
  `userFlag` char(1) DEFAULT NULL COMMENT '可用：1；已删除：0',
  `password` varchar(64) DEFAULT NULL,
  `appId` int(11) DEFAULT NULL COMMENT 'app应用ID',
  `isAdmin` char(1) DEFAULT NULL COMMENT '是否超级管理员',
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `firstDate` datetime DEFAULT NULL,
  `lastDate` datetime DEFAULT NULL,
  `maxSpace` bigint(20) DEFAULT NULL,
  `usedSpace` bigint(20) DEFAULT '0',
  `extra1` varchar(100) DEFAULT NULL,
  `extra2` varchar(100) DEFAULT NULL,
  `extra3` varchar(100) DEFAULT NULL,
  `extra4` varchar(100) DEFAULT NULL,
  `extra5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`userName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diskuser
-- ----------------------------
INSERT INTO `diskuser` VALUES ('admin', '系统管理员', '1', 'admin', null, 'y', null, null, null, null, null, '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for `groupuser`
-- ----------------------------
DROP TABLE IF EXISTS `groupuser`;
CREATE TABLE `groupuser` (
  `groupId` varchar(50) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupuser
-- ----------------------------

-- ----------------------------
-- Table structure for `history`
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `id` int(11) NOT NULL,
  `userName` varchar(50) NOT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `operation` varchar(50) NOT NULL,
  `groupName` varchar(100) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `extra1` varchar(100) DEFAULT NULL,
  `extra2` varchar(100) DEFAULT NULL,
  `extra3` varchar(100) DEFAULT NULL,
  `extra4` varchar(100) DEFAULT NULL,
  `extra5` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history
-- ----------------------------
