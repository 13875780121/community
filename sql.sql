/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 16/04/2021 16:12:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `gmt_create` bigint NULL DEFAULT NULL,
  `gmt_modified` bigint NULL DEFAULT NULL,
  `like_count` bigint NULL DEFAULT 0,
  `content` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `comment_count` int NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES (4, 2, 1, 1, 1, 10, '123456', 7, 1);
INSERT INTO `t_comment` VALUES (5, 7, 1, 1617947848727, NULL, 0, 'asd ', 7, 5);
INSERT INTO `t_comment` VALUES (6, 3, 1, 1617948384925, NULL, 0, '我时一个回复', 7, 3);
INSERT INTO `t_comment` VALUES (7, 33, 1, 1617950082156, NULL, 1, '学nm', 7, 3);
INSERT INTO `t_comment` VALUES (8, 5, 2, 1617955138484, NULL, 0, '能不能行', 7, 3);
INSERT INTO `t_comment` VALUES (9, 5, 2, 1617955138484, NULL, 0, '能不能行', 7, 3);
INSERT INTO `t_comment` VALUES (10, 5, 2, 1617958144118, NULL, 0, '？？？', 7, NULL);
INSERT INTO `t_comment` VALUES (11, 5, 2, 1617958144118, NULL, 0, '？？？', 7, NULL);
INSERT INTO `t_comment` VALUES (12, 5, 2, 1617958153134, NULL, 0, '4', 7, NULL);
INSERT INTO `t_comment` VALUES (13, 5, 2, 1617958153134, NULL, 0, '4', 7, NULL);
INSERT INTO `t_comment` VALUES (15, 36, 1, 1618061822969, NULL, 14, '我回复你了', 7, NULL);
INSERT INTO `t_comment` VALUES (16, 5, 1, 1618066045248, NULL, 0, '我的回复阿阿阿阿阿', 7, NULL);
INSERT INTO `t_comment` VALUES (17, 16, 2, 1618066055624, NULL, 0, '我是回复的回复', 7, NULL);
INSERT INTO `t_comment` VALUES (18, 16, 2, 1618066055624, NULL, 0, '我是回复的回复', 7, NULL);
INSERT INTO `t_comment` VALUES (19, 16, 2, 1618066066794, NULL, 0, '？？？', 7, NULL);
INSERT INTO `t_comment` VALUES (20, 16, 2, 1618066066794, NULL, 0, '？？？', 7, NULL);
INSERT INTO `t_comment` VALUES (21, 34, 1, 1618066419885, NULL, 0, '我是回复', 7, NULL);
INSERT INTO `t_comment` VALUES (22, 21, 2, 1618066426469, NULL, 0, '我是回复的回复', 7, NULL);
INSERT INTO `t_comment` VALUES (23, 15, 2, 1618217902593, NULL, 0, '我是回复的回复', 7, NULL);

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `noticer` bigint NOT NULL,
  `outer_id` bigint NOT NULL,
  `type` int NOT NULL DEFAULT 1,
  `gmt_create` bigint NULL DEFAULT NULL,
  `status` int NULL DEFAULT 0,
  `receiver` bigint NOT NULL,
  `outer_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES (1, 7, 36, 3, 1618061822977, 1, 7, 'SpringMVC');
INSERT INTO `t_notice` VALUES (2, 7, 5, 3, 1618066045282, 1, 7, 'title');
INSERT INTO `t_notice` VALUES (3, 7, 16, 1, 1618066055625, 1, 7, 'title的评论');
INSERT INTO `t_notice` VALUES (4, 7, 16, 1, 1618066066794, 1, 7, 'title的评论');
INSERT INTO `t_notice` VALUES (5, 7, 34, 3, 1618066419898, 1, 7, 'Spring');
INSERT INTO `t_notice` VALUES (6, 7, 34, 1, 1618066426470, 1, 7, 'Spring的评论');
INSERT INTO `t_notice` VALUES (7, 7, 36, 1, 1618217902609, 1, 7, 'SpringMVC的评论');

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE `t_question`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
  `gmt_create` bigint NULL DEFAULT NULL,
  `gmt_modified` bigint NULL DEFAULT NULL,
  `creator` bigint NULL DEFAULT NULL,
  `comment_count` int NULL DEFAULT 0,
  `view_count` int NULL DEFAULT 0,
  `like_count` int NULL DEFAULT 0,
  `tag` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_question
-- ----------------------------
INSERT INTO `t_question` VALUES (2, 'title', 'content', 1617539714580, NULL, 7, 9, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (3, 'title', 'content', 1617539714580, NULL, 7, 1, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (4, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (5, 'title', 'content', 1617539714580, NULL, 7, 1, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (6, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (7, 'title', 'content', 1617539714580, NULL, 7, 4, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (8, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (9, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (10, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (11, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (12, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (13, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (14, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (15, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (16, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (17, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (18, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (19, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (20, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (21, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (22, 'title', 'content', 1617539714580, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (23, 'title2', 'content', 1617696066794, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (24, 'title2', 'content', 1617696066794, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (25, 'title2', 'content', 1617696248167, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (26, 'title2', 'content', 1617696248167, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (27, 'title123', 'content', 1617696462563, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (28, 'title123', 'content', 1617696462563, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (29, 'title1111111111111111', 'content1111111111111111111111111', 1617696700335, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (30, 'title1111111111111111', 'content1111111111111111111111111', 1617696700335, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (31, 'title title title title title ', 'content', 1617949757432, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (32, 'title title title title title ', 'content', 1617949757432, NULL, 7, 0, 0, 0, 'tag1');
INSERT INTO `t_question` VALUES (33, '如何学习', '学你嘛', NULL, NULL, 7, 1, 0, 0, 'study');
INSERT INTO `t_question` VALUES (34, 'Spring', '如何学习Spring', NULL, NULL, 7, 1, 0, 0, 'Spring,Java,SpringBoot');
INSERT INTO `t_question` VALUES (35, 'SpringBoot', 'SpringBoot', NULL, NULL, 7, 0, 0, 0, 'SpringBoot,Java,SpringMVC');
INSERT INTO `t_question` VALUES (36, 'SpringMVC', 'SpringMVC', NULL, NULL, 7, 1, 0, 0, 'SpringMVC,Spring,Java');
INSERT INTO `t_question` VALUES (37, 'Hello world', '```java\r\nSystem.out.print(\"Hello world\");\r\n```', NULL, NULL, 7, 0, 0, 0, 'Java,Html,Go');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gmt_create` bigint NULL DEFAULT NULL,
  `gmt_modified` bigint NULL DEFAULT NULL,
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (7, '61342009', 'noob', 1617538794270, NULL, NULL, 'ghu_7PYiiCCqRcVZ4eDZpl4dhW7FPnAgoq10nJfK', 'https://avatars.githubusercontent.com/u/61342009?v=4');

SET FOREIGN_KEY_CHECKS = 1;
