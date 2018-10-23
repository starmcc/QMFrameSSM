/*
 Navicat Premium Data Transfer

 Source Server         : 浅梦
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : QMFrame

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 23/10/2018 17:01:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qm_logger
-- ----------------------------
DROP TABLE IF EXISTS `qm_logger`;
CREATE TABLE `qm_logger`  (
  `logId` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `operator` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者',
  `text` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志',
  `requestURL` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求URL',
  `requestClassName` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求类',
  `requestMethod` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方法',
  `requestParam` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求值',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '记录时间',
  `responseTime` bigint(20) NOT NULL COMMENT '响应时间',
  PRIMARY KEY (`logId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 485 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
