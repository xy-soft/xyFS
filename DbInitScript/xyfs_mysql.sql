/*
Navicat MySQL Data Transfer

Source Server         : localhost3306
Source Server Version : 50550
Source Host           : localhost:3306
Source Database       : xyfs

Target Server Type    : MYSQL
Target Server Version : 50550
File Encoding         : 65001

Date: 2018-07-03 16:32:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `app`
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `appkey` varchar(255) DEFAULT NULL,
  `available` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO `app` VALUES ('1', 'password', '1', 'test1', 'test1');
INSERT INTO `app` VALUES ('2', 'password', '1', 'test2', 'test2');
INSERT INTO `app` VALUES ('3', 'password', '1', 'test3', 'test3');
INSERT INTO `app` VALUES ('4', 'password', '1', 'test4', 'test4');

-- ----------------------------
-- Table structure for `dailyreport`
-- ----------------------------
DROP TABLE IF EXISTS `dailyreport`;
CREATE TABLE `dailyreport` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `curdate` varchar(255) DEFAULT NULL,
  `files` int(11) NOT NULL,
  `groups` int(11) NOT NULL,
  `spaces` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of dailyreport
-- ----------------------------

-- ----------------------------
-- Table structure for `diskfile`
-- ----------------------------
DROP TABLE IF EXISTS `diskfile`;
CREATE TABLE `diskfile` (
  `fileid` varchar(255) NOT NULL,
  `appid` int(11) NOT NULL,
  `dfs_group_name` varchar(255) DEFAULT NULL,
  `download_num` int(11) NOT NULL,
  `download_pwd` varchar(255) DEFAULT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `extra2` varchar(255) DEFAULT NULL,
  `extra3` varchar(255) DEFAULT NULL,
  `extra4` varchar(255) DEFAULT NULL,
  `extra5` varchar(255) DEFAULT NULL,
  `file_ext` varchar(255) DEFAULT NULL,
  `file_flag` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `file_size` decimal(19,2) DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `filesource` varchar(255) DEFAULT NULL,
  `form_id` varchar(255) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `ispublic` varchar(255) DEFAULT NULL,
  `upload_date` datetime DEFAULT NULL,
  `upload_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`fileid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diskfile
-- ----------------------------

-- ----------------------------
-- Table structure for `diskgroup`
-- ----------------------------
DROP TABLE IF EXISTS `diskgroup`;
CREATE TABLE `diskgroup` (
  `group_id` varchar(50) NOT NULL,
  `administrator` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `extra2` varchar(255) DEFAULT NULL,
  `extra3` varchar(255) DEFAULT NULL,
  `extra4` varchar(255) DEFAULT NULL,
  `extra5` varchar(255) DEFAULT NULL,
  `father_group_id` varchar(255) DEFAULT NULL,
  `group_cn_name` varchar(255) DEFAULT NULL,
  `group_creator` varchar(255) DEFAULT NULL,
  `group_flag` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `group_type` varchar(255) DEFAULT NULL,
  `is_public` varchar(255) DEFAULT NULL,
  `max_num` int(11) NOT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diskgroup
-- ----------------------------

-- ----------------------------
-- Table structure for `diskuser`
-- ----------------------------
DROP TABLE IF EXISTS `diskuser`;
CREATE TABLE `diskuser` (
  `user_name` varchar(255) NOT NULL,
  `app_id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `extra2` varchar(255) DEFAULT NULL,
  `extra3` varchar(255) DEFAULT NULL,
  `extra4` varchar(255) DEFAULT NULL,
  `extra5` varchar(255) DEFAULT NULL,
  `first_date` datetime DEFAULT NULL,
  `is_admin` varchar(255) DEFAULT NULL,
  `last_date` datetime DEFAULT NULL,
  `max_space` decimal(19,2) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `used_space` decimal(19,2) DEFAULT NULL,
  `user_cn_name` varchar(255) DEFAULT NULL,
  `user_flag` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diskuser
-- ----------------------------

-- ----------------------------
-- Table structure for `groupuser`
-- ----------------------------
DROP TABLE IF EXISTS `groupuser`;
CREATE TABLE `groupuser` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupuser
-- ----------------------------

-- ----------------------------
-- Table structure for `hibernate_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('1');
INSERT INTO `hibernate_sequence` VALUES ('1');
INSERT INTO `hibernate_sequence` VALUES ('1');
INSERT INTO `hibernate_sequence` VALUES ('1');
INSERT INTO `hibernate_sequence` VALUES ('1');

-- ----------------------------
-- Table structure for `history`
-- ----------------------------
DROP TABLE IF EXISTS `history`;
CREATE TABLE `history` (
  `id` bigint(20) NOT NULL,
  `extra1` varchar(255) DEFAULT NULL,
  `extra2` varchar(255) DEFAULT NULL,
  `extra3` varchar(255) DEFAULT NULL,
  `extra4` varchar(255) DEFAULT NULL,
  `extra5` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of history
-- ----------------------------
