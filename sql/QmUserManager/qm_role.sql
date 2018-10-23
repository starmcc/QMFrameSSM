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

 Date: 23/10/2018 17:01:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qm_role
-- ----------------------------
DROP TABLE IF EXISTS `qm_role`;
CREATE TABLE `qm_role`  (
  `roleId` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `roleName` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `createOperator` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `updateOperator` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新者',
  `powerIds` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限id数组,逗号隔开',
  PRIMARY KEY (`roleId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
