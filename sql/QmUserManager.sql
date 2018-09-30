/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50529
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50529
 File Encoding         : 65001

 Date: 30/09/2018 19:41:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qm_logger
-- ----------------------------
DROP TABLE IF EXISTS `qm_logger`;
CREATE TABLE `qm_logger`  (
  `logId` int(11) NOT NULL AUTO_INCREMENT COMMENT '日志id',
  `operator` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作者',
  `text` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志',
  `requestURL` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求URL',
  `requestClassName` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求类',
  `requestMethod` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求方法',
  `requestParam` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '请求值',
  `createTime` datetime NULL DEFAULT NULL COMMENT '记录时间',
  `responseTime` bigint(20) NOT NULL COMMENT '响应时间',
  PRIMARY KEY (`logId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qm_power
-- ----------------------------
DROP TABLE IF EXISTS `qm_power`;
CREATE TABLE `qm_power`  (
  `powerId` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `powerDescription` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`powerId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for qm_role
-- ----------------------------
DROP TABLE IF EXISTS `qm_role`;
CREATE TABLE `qm_role`  (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `createTime` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `createOperator` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `updateTime` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `updateOperator` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新者',
  `powerIds` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id数组,逗号隔开',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qm_role
-- ----------------------------
INSERT INTO `qm_role` VALUES (1, '超级管理员', '2018-09-30 14:18:04', 'admin', '2018-09-30 14:18:10', 'admin', '*');

-- ----------------------------
-- Table structure for qm_user
-- ----------------------------
DROP TABLE IF EXISTS `qm_user`;
CREATE TABLE `qm_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of qm_user
-- ----------------------------
INSERT INTO `qm_user` VALUES (1, 'admin', 'admin', 1);

SET FOREIGN_KEY_CHECKS = 1;
