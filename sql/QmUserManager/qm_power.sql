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

 Date: 23/10/2018 17:01:09
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for qm_power
-- ----------------------------
DROP TABLE IF EXISTS `qm_power`;
CREATE TABLE `qm_power`  (
  `powerId` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `powerDescription` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限描述',
  PRIMARY KEY (`powerId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
