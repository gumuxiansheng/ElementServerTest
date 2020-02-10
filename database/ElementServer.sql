/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : ElementServer

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 10/02/2020 23:12:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `serial_num` int(11) DEFAULT NULL COMMENT '条目文档内序号',
  `time` datetime DEFAULT NULL COMMENT '提交时间',
  `qa_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '问卷类型',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来源',
  `institution_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '涉及大厅类型',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属省',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属市',
  `district` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属区',
  `content_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内容类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '反馈内容',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系方式',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '状态',
  `distributed` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否已下发',
  `distribute_time` datetime DEFAULT NULL COMMENT '下发时间',
  `version` int(5) DEFAULT '0' COMMENT '版本',
  `deleted` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否已标注删除',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据来源文件名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=620 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for feedback_history
-- ----------------------------
DROP TABLE IF EXISTS `feedback_history`;
CREATE TABLE `feedback_history` (
  `id` int(11) NOT NULL COMMENT '主键',
  `serial_num` int(11) DEFAULT NULL COMMENT '条目文档内序号',
  `time` datetime DEFAULT NULL COMMENT '提交时间',
  `qa_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '问卷类型',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '来源',
  `institution_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '涉及大厅类型',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属省',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属市',
  `district` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '所属区',
  `content_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '内容类型',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '反馈内容',
  `contact` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '联系方式',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '状态',
  `distributed` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否已下发',
  `distribute_time` datetime DEFAULT NULL COMMENT '下发时间',
  `version` int(5) NOT NULL DEFAULT '0' COMMENT '版本',
  `deleted` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '是否已标注删除',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '数据来源文件名',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
  PRIMARY KEY (`id`,`version`,`file_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for feedback_treatment
-- ----------------------------
DROP TABLE IF EXISTS `feedback_treatment`;
CREATE TABLE `feedback_treatment` (
  `id` int(11) NOT NULL COMMENT 'treatment记录在feedback表主键',
  `file_name` varchar(255) DEFAULT NULL COMMENT '数据来源文件名',
  `serial_num` int(11) DEFAULT NULL COMMENT '条目文档内序号',
  `version` varchar(255) DEFAULT NULL COMMENT '版本号',
  `treatment_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '处理状态',
  `treatment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '处理意见',
  `treat_time` datetime DEFAULT NULL COMMENT '处理时间',
  `treat_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '经手人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for feedback_treatment_history
-- ----------------------------
DROP TABLE IF EXISTS `feedback_treatment_history`;
CREATE TABLE `feedback_treatment_history` (
  `id` int(11) DEFAULT NULL COMMENT 'treatment记录在feedback表主键',
  `file_name` varchar(255) DEFAULT NULL COMMENT '数据来源文件名',
  `serial_num` int(11) DEFAULT NULL COMMENT '条目文档内序号',
  `version` varchar(255) DEFAULT NULL COMMENT '版本号',
  `treatment_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '处理状态',
  `treatment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '处理意见',
  `treat_time` datetime DEFAULT NULL COMMENT '处理时间',
  `treat_person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '经手人'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Procedure structure for OverrideFeedbackFile
-- ----------------------------
DROP PROCEDURE IF EXISTS `OverrideFeedbackFile`;
delimiter ;;
CREATE PROCEDURE `ElementServer`.`OverrideFeedbackFile`(IN fileName VARCHAR(255), OUT oriVersion INT)
BEGIN
  #Routine body goes here...
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
		SELECT version INTO oriVersion FROM feedback WHERE feedback.file_name=fileName ORDER BY id DESC LIMIT 1;
		INSERT INTO feedback_history SELECT * FROM feedback WHERE feedback.file_name=fileName;
		DELETE FROM feedback WHERE feedback.file_name=fileName;
	COMMIT;
END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for TreatFeedbackById
-- ----------------------------
DROP PROCEDURE IF EXISTS `TreatFeedbackById`;
delimiter ;;
CREATE PROCEDURE `ElementServer`.`TreatFeedbackById`(IN feedbackId LONG, IN treatmentStatus VARCHAR(255), IN treatment VARCHAR(255), IN treatPerson VARCHAR(255))
BEGIN
  #Routine body goes here...
	DECLARE EXIT HANDLER FOR SQLEXCEPTION ROLLBACK;
	START TRANSACTION;
		INSERT INTO feedback_treatment_history SELECT * FROM feedback_treatment WHERE feedback_treatment.id=feedbackId;
		DELETE FROM feedback_treatment WHERE feedback_treatment.id=feedbackId;
		INSERT INTO feedback_treatment(id, file_name, serial_num, version) SELECT id, file_name, serial_num, version FROM feedback WHERE id = feedbackId;
		UPDATE feedback_treatment 
			SET treatment_status=treatmentStatus,
					treatment=treatment,
					treat_person=treatPerson,
					treat_time=NOW()
			WHERE id = feedbackId;
	COMMIT;
END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
