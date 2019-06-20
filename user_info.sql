/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 127.0.0.1:3306
 Source Schema         : mybatis

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 19/06/2019 17:34:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `birth` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生日',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别',
  `mobile` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '手机号',
  `mail` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '小明', '19940104', '男', '18978985623', '123456@qq.com');
INSERT INTO `user_info` VALUES (2, '小红', '19940105', '女', '18978985624', '123457@qq.com');
INSERT INTO `user_info` VALUES (3, '小胡', '19940106', '男', '18978985625', '123458@qq.com');
INSERT INTO `user_info` VALUES (4, '小华', '19940107', '女', '18978985626', '123459@qq.com');
INSERT INTO `user_info` VALUES (5, '小王', '19940108', '男', '18978985627', '123460@qq.com');
INSERT INTO `user_info` VALUES (6, '小刚', '19940109', '女', '18978985628', '123461@qq.com');
INSERT INTO `user_info` VALUES (7, '小李', '19940110', '男', '18978985629', '123462@qq.com');
INSERT INTO `user_info` VALUES (8, '大明', '19940111', '女', '18978985630', '123463@qq.com');
INSERT INTO `user_info` VALUES (9, '小花', '19940112', '男', '18978985631', '123464@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
