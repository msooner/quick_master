/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50635
 Source Host           : localhost
 Source Database       : quick_master

 Target Server Type    : MySQL
 Target Server Version : 50635
 File Encoding         : utf-8

 Date: 11/08/2019 19:17:08 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `r_system_user`
-- ----------------------------
DROP TABLE IF EXISTS `r_system_user`;
CREATE TABLE `r_system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_modified_by` varchar(20) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `org_code` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `credential_expired_date` datetime DEFAULT NULL,
  `is_locked` bit(1) DEFAULT NULL,
  `role_names` varchar(255) DEFAULT NULL,
  `role_ids` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `r_user_login_log`
-- ----------------------------
DROP TABLE IF EXISTS `r_user_login_log`;
CREATE TABLE `r_user_login_log` (
  `rec_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键,唯一索引',
  `user_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户id',
  `login_time` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '登陆时间',
  `user_name` varchar(60) NOT NULL DEFAULT '' COMMENT '登陆用户名',
  PRIMARY KEY (`rec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户登陆日志';

-- ----------------------------
--  Table structure for `r_users`
-- ----------------------------
DROP TABLE IF EXISTS `r_users`;
CREATE TABLE `r_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员资料自增id',
  `email` varchar(60) NOT NULL DEFAULT '' COMMENT '会员Email',
  `user_name` varchar(60) NOT NULL DEFAULT '' COMMENT '用户名',
  `nick_name` varchar(60) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `avatar` varchar(60) DEFAULT '' COMMENT '用户头像',
  `sex` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '性别：0保密; 1男; 2女',
  `birthday` date NOT NULL DEFAULT '0000-00-00' COMMENT '出生日期',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '推荐人会员id',
  `alias` varchar(60) DEFAULT NULL COMMENT '昵称',
  `mobile_phone` varchar(20) DEFAULT NULL COMMENT '移动电话',
  `consumption_count` decimal(10,2) unsigned DEFAULT NULL COMMENT '累计消费金额',
  `goods_comment` smallint(5) DEFAULT '0' COMMENT '已购买过的商品评论数，预留字段，后期营销为用户分组时使用',
  `pay_time_last` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '用户最后付款的时间',
  `address_id` int(10) unsigned NOT NULL DEFAULT '0',
  `group_id` int(11) DEFAULT '0' COMMENT '组id，预留字段，为期营销为用户分组时使用',
  `update_time` int(11) NOT NULL DEFAULT '0' COMMENT '更新时间',
  `rebate_code` varchar(16) NOT NULL DEFAULT '' COMMENT '客户返利邀请码',
  `vip_level` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'VIP用户等级，预留字段，后期营销为用户分组时使用',
  `is_first_order` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否首单用户（0否，1是）',
  `balance` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '用户余额，后期做营销活动时可以用金币或积分转换成余额',
  `integral` int(11) DEFAULT NULL COMMENT '用户积分，预留字段，后期营销时使用。根据支付金额按1：100的比例发放积分，10000积分=1元',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
--  Records of `r_users`
-- ----------------------------
BEGIN;
INSERT INTO `r_users` VALUES ('1', '1@qq.com', 'hao123', 'ron', '', '1', '0000-00-00', '0', null, null, null, '0', '0', '0', '0', '0', '', '0', '1', '0.00', null), ('2', '2@qq.com', 'hao234', 'ron1', '', '2', '0000-00-00', '0', null, null, null, '0', '0', '0', '0', '0', '', '0', '1', '0.00', null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
