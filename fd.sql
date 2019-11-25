/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 80017
Source Host           : localhost:3306
Source Database       : fd

Target Server Type    : MYSQL
Target Server Version : 80017
File Encoding         : 65001

Date: 2019-11-25 18:21:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for club
-- ----------------------------
DROP TABLE IF EXISTS `club`;
CREATE TABLE `club` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '俱乐部名称',
  `manager_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店长名字',
  `manager_phone` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店长手机号',
  `site_num` int(11) NOT NULL DEFAULT '0' COMMENT '场地数量',
  `colonel_num` int(11) NOT NULL DEFAULT '0' COMMENT '团长数量',
  `area` varchar(255) NOT NULL COMMENT '所在地区',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '详细地址',
  `time_id` int(11) NOT NULL DEFAULT '0' COMMENT '时间段，值为0则代表不在使用中',
  `motion_type` int(11) NOT NULL COMMENT '运动类型',
  `capacity` int(11) NOT NULL COMMENT '容量',
  `create_time` datetime NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '是否冻结',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for clubsite
-- ----------------------------
DROP TABLE IF EXISTS `clubsite`;
CREATE TABLE `clubsite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `club_id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `extra` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for group
-- ----------------------------
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caption` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '团长姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机',
  `num` int(11) NOT NULL DEFAULT '1' COMMENT '团员数量',
  `club_id` int(11) NOT NULL COMMENT '所属俱乐部',
  `avtivities_num` int(11) NOT NULL DEFAULT '0' COMMENT '团队发起活动数',
  `consume` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '消费总额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for manager
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `job_number` varchar(255) NOT NULL COMMENT '工作号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  `phone` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `login_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地址',
  `club_id` int(255) NOT NULL COMMENT '所属俱乐部',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `token` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for motiontype
-- ----------------------------
DROP TABLE IF EXISTS `motiontype`;
CREATE TABLE `motiontype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `permission_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pk
-- ----------------------------
DROP TABLE IF EXISTS `pk`;
CREATE TABLE `pk` (
  `id` varchar(255) NOT NULL,
  `initiator_name` varchar(255) DEFAULT NULL,
  `initiator_id` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `motion_type` int(11) DEFAULT NULL,
  `timer` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `initiator_captain` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `victory_team` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pk_audit
-- ----------------------------
DROP TABLE IF EXISTS `pk_audit`;
CREATE TABLE `pk_audit` (
  `id` varchar(255) NOT NULL,
  `initiator_name` varchar(255) DEFAULT NULL,
  `initiator_id` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `group_id` varchar(255) DEFAULT NULL,
  `motion_type` int(11) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `timer` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `initiator_captain` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for pk_reject
-- ----------------------------
DROP TABLE IF EXISTS `pk_reject`;
CREATE TABLE `pk_reject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audit_id` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for reject
-- ----------------------------
DROP TABLE IF EXISTS `reject`;
CREATE TABLE `reject` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audit_id` int(11) DEFAULT NULL COMMENT '申请表id',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for service_log
-- ----------------------------
DROP TABLE IF EXISTS `service_log`;
CREATE TABLE `service_log` (
  `name` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
  `id` varchar(255) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `captain` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `motion_type` varchar(255) DEFAULT NULL,
  `captain_phone` varchar(255) DEFAULT NULL,
  `player_num` int(11) DEFAULT '1' COMMENT '1',
  `victory` int(11) DEFAULT '0' COMMENT '0',
  `lose` int(11) DEFAULT '0' COMMENT '0',
  `group_num` int(11) DEFAULT '0' COMMENT '0',
  `pk_num` int(11) DEFAULT '0' COMMENT '0',
  `create_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for team_audit
-- ----------------------------
DROP TABLE IF EXISTS `team_audit`;
CREATE TABLE `team_audit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `captain` varchar(255) DEFAULT NULL,
  `captain_phone` varchar(255) DEFAULT NULL,
  `description` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci,
  `address` varchar(255) DEFAULT NULL,
  `motion_type` int(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT '0' COMMENT '0为未处理，1为通过，2为驳回',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for time
-- ----------------------------
DROP TABLE IF EXISTS `time`;
CREATE TABLE `time` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) NOT NULL DEFAULT '' COMMENT '昵称',
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(12) NOT NULL COMMENT '手机号',
  `user_id` int(11) NOT NULL COMMENT '邀请人',
  `superior` int(11) NOT NULL COMMENT '所属团长',
  `integral` int(255) NOT NULL DEFAULT '0' COMMENT '积分',
  `growth_value` int(255) NOT NULL DEFAULT '0' COMMENT '成长值',
  `push_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '提成收益',
  `available_income` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '可用收益',
  `number_team` int(11) NOT NULL DEFAULT '0' COMMENT '参与多少战队',
  `captain` int(11) NOT NULL DEFAULT '0' COMMENT '1为是，0为不是',
  `create_team` int(11) NOT NULL DEFAULT '0' COMMENT '创建战队的数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_club
-- ----------------------------
DROP TABLE IF EXISTS `user_club`;
CREATE TABLE `user_club` (
  `user_id` int(11) NOT NULL,
  `club_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `user_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
