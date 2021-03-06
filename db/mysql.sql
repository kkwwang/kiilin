/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : kiilin

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-08-28 10:15:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` varchar(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('d2ae04b282264d828167de3e1cdabbcd', 'banheTask', 'banheDeal', null, '0 15 0 * * ?', '0', null, '2018-08-15 18:01:33');
INSERT INTO `schedule_job` VALUES ('f9df6747948c11e893b21831bf4e61cf', 'testTask', 'test', 'kiilin', '0 0/30 * * * ?', '1', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO `schedule_job` VALUES ('fdc9232c948c11e893b21831bf4e61cf', 'testTask', 'test2', null, '0 0/30 * * * ?', '0', '无参数测试', '2016-12-03 14:55:56');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` varchar(32) NOT NULL COMMENT '任务日志id',
  `job_id` varchar(32) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`),
  CONSTRAINT `job_log_job_id_key` FOREIGN KEY (`job_id`) REFERENCES `schedule_job` (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES ('a361712686af4b4094fa7ca61bf05e43', 'fdc9232c948c11e893b21831bf4e61cf', 'testTask', 'test2', null, '0', null, '1', '2018-08-28 10:00:00');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父节点id',
  `parent_ids` varchar(1000) DEFAULT NULL COMMENT '所有父节点id，已逗号分隔',
  `dept_name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `dept_code` varchar(255) DEFAULT NULL COMMENT '部门代号',
  `dept_level` int(2) DEFAULT NULL,
  `dept_type` varchar(32) DEFAULT NULL COMMENT '部门类型（综合、项目办/市高指、施工单位、技术单位、监理单位、技术服务单位、其他）',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dept_code_key` (`dept_code`) USING BTREE COMMENT '部门代号不能重复',
  UNIQUE KEY `dept_name_key` (`dept_name`) USING BTREE COMMENT '部门名称不能重复'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES ('36de374009314ed1833ec2b20cf0af6b', '553d3d9dc2c94990953cf1b7e063dfd3', '0,553d3d9dc2c94990953cf1b7e063dfd3,', '超级777', '777', '2', 'test2', '671973208fae11e88d081831bf4e61cf', '2018-08-20 16:45:15', null, null, '\0', '');
INSERT INTO `sys_dept` VALUES ('4562c32bb9af46cfb5feef6d95fd7ecf', '0', '0,', '凯琳公司', 'kiilin', null, 'test1', '671973208fae11e88d081831bf4e61cf', '2018-08-20 15:34:41', '671973208fae11e88d081831bf4e61cf', '2018-08-20 16:52:33', '\0', '');
INSERT INTO `sys_dept` VALUES ('553d3d9dc2c94990953cf1b7e063dfd3', '0', '0,', '超级666有限公司', 'cj666', '1', 'test2', '671973208fae11e88d081831bf4e61cf', '2018-08-20 16:33:25', null, null, '\0', '');

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `type` varchar(32) DEFAULT NULL COMMENT '类型值',
  `label` varchar(32) DEFAULT NULL COMMENT '字典名称',
  `value` varchar(32) DEFAULT NULL COMMENT '字典值',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `sys_flag` bit(1) DEFAULT b'0' COMMENT '系统数据标识, 系统数据时不允许修改',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dict_type_value_key` (`type`,`value`) USING BTREE COMMENT '类型、值组合',
  UNIQUE KEY `dict_type_label_key` (`type`,`label`) USING BTREE COMMENT '类型、描述组合',
  CONSTRAINT `sys_dict_type_key` FOREIGN KEY (`type`) REFERENCES `sys_dict_type` (`type_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('2e5944e1351b4f50a50201939743762c', 'user_type', '用户', 'user', '3', '\0', '671973208fae11e88d081831bf4e61cf', '2018-08-15 11:10:26', '671973208fae11e88d081831bf4e61cf', '2018-08-15 11:12:11', '\0', '');
INSERT INTO `sys_dict` VALUES ('2f95b6161c3049f4b80144668b9900b5', 'project_level', '标段', '2', '2', '\0', '671973208fae11e88d081831bf4e61cf', '2018-08-22 16:23:46', null, null, '\0', '');
INSERT INTO `sys_dict` VALUES ('2fb0a6772d484b86812525da99fdf8f1', 'user_type', '管理员', 'admin', '2', '\0', '671973208fae11e88d081831bf4e61cf', '2018-08-15 11:10:17', '671973208fae11e88d081831bf4e61cf', '2018-08-15 11:12:01', '\0', '');
INSERT INTO `sys_dict` VALUES ('3ad10fbef6864a6789e7c39d71cfc6a3', 'sex', '女', '0', '0', '', '671973208fae11e88d081831bf4e61cf', '2018-08-15 09:44:30', null, null, '\0', '');
INSERT INTO `sys_dict` VALUES ('4119bde2fe2c4b12b42aec8567244a66', 'sex', '男', '1', '1', '', '671973208fae11e88d081831bf4e61cf', '2018-08-15 09:44:23', null, null, '\0', '');
INSERT INTO `sys_dict` VALUES ('578e5a928f2411e88d081831bf4e61cf', 'menu_type', '菜单', 'menu', '2', '', '', '2018-07-27 10:56:39', '6b1a14e23daa4728b5d0385b17844796', '2018-08-15 19:49:21', '\0', '');
INSERT INTO `sys_dict` VALUES ('5dd0821f8f2411e88d081831bf4e61cf', 'menu_type', '按钮', 'action', '3', '', '', '2018-07-27 10:56:41', '671973208fae11e88d081831bf4e61cf', '2018-08-14 18:26:34', '\0', '');
INSERT INTO `sys_dict` VALUES ('8860f3361f944cfc811fe2e944142696', 'dept_type', 'test2', 'test2', '2', '\0', '671973208fae11e88d081831bf4e61cf', '2018-08-20 15:37:01', null, null, '\0', '');
INSERT INTO `sys_dict` VALUES ('8a0c900f0ada4399890d04985981d8a1', 'banhe_firstday', 'banhe', '2018-08-01', '1', '', '671973208fae11e88d081831bf4e61cf', '2018-08-17 13:56:53', null, null, '\0', '拌合数据最早早不过这个值');
INSERT INTO `sys_dict` VALUES ('8fa481fe64a74bd1873d2610a5198754', 'dept_type', '测试部门类型', 'test1', '1', '\0', '671973208fae11e88d081831bf4e61cf', '2018-08-20 15:36:47', null, null, '\0', '');
INSERT INTO `sys_dict` VALUES ('960b91828f2411e88d081831bf4e61cf', 'sys_code', 'pc', 'pc', '11', '', '', '2018-07-27 10:56:45', '671973208fae11e88d081831bf4e61cf', '2018-08-27 10:42:30', '\0', '');
INSERT INTO `sys_dict` VALUES ('a114bd05b85843d486e139d2a07d4358', 'user_type', '系统管理员', 'sys_admin', '1', '', '671973208fae11e88d081831bf4e61cf', '2018-08-15 11:10:09', '671973208fae11e88d081831bf4e61cf', '2018-08-15 11:11:53', '\0', '');
INSERT INTO `sys_dict` VALUES ('a49df7d88f2411e88d081831bf4e61cf', 'sys_code', 'app', 'app', '21', '', '', '2018-07-27 10:56:47', '671973208fae11e88d081831bf4e61cf', '2018-08-27 10:42:48', '\0', '');
INSERT INTO `sys_dict` VALUES ('af1232a58aa44c539311a6dc3a85841f', 'menu_type', '目录', 'menu_dir', '1', '', '671973208fae11e88d081831bf4e61cf', '2018-08-14 18:26:28', '671973208fae11e88d081831bf4e61cf', '2018-08-20 11:26:02', '\0', '');
INSERT INTO `sys_dict` VALUES ('b0341b4684bc4844ba0df7e1a45aa707', 'sys_code', 'pc_top', 'pc_top', '12', '', '671973208fae11e88d081831bf4e61cf', '2018-08-27 10:35:04', '671973208fae11e88d081831bf4e61cf', '2018-08-27 10:42:39', '\0', '');
INSERT INTO `sys_dict` VALUES ('c07855c55b7d41b6a2afa8c677098012', 'project_level', '项目', '1', '1', '\0', '671973208fae11e88d081831bf4e61cf', '2018-08-22 16:23:25', null, null, '\0', '');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `type_value` varchar(32) DEFAULT NULL COMMENT '类型值',
  `type_label` varchar(32) DEFAULT NULL COMMENT '类型名称',
  `sys_flag` bit(1) DEFAULT b'0' COMMENT '系统数据标识, 系统数据时不允许修改',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dict_type_key` (`type_value`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('3d2d05bb45094db48476abf5717d32e9', 'project_level', '项目级别', '\0', '7', '671973208fae11e88d081831bf4e61cf', '2018-08-22 16:23:06', null, null, '\0', null);
INSERT INTO `sys_dict_type` VALUES ('510c6e3d7f704c0fa512069ebc04dcf0', 'sex', '性别', '', '3', '671973208fae11e88d081831bf4e61cf', '2018-08-15 09:42:54', null, null, '\0', null);
INSERT INTO `sys_dict_type` VALUES ('aacc30aa8f2111e8b73f1831bf4e61cf', 'menu_type', '菜单类型', '', '1', '', '2018-07-27 10:38:10', '671973208fae11e88d081831bf4e61cf', '2018-08-17 16:00:53', '\0', '');
INSERT INTO `sys_dict_type` VALUES ('af5e734d384145eaab6c0b1dec002497', 'dept_type', '部门类型', '\0', '5', '671973208fae11e88d081831bf4e61cf', '2018-08-20 15:36:27', null, null, '\0', null);
INSERT INTO `sys_dict_type` VALUES ('c0ee513b6b6b4974baaa1eeb7e972651', 'user_type', '用户类型', '\0', '4', '671973208fae11e88d081831bf4e61cf', '2018-08-15 11:04:18', '6b1a14e23daa4728b5d0385b17844796', '2018-08-15 19:24:55', '\0', '');
INSERT INTO `sys_dict_type` VALUES ('e8b5fd815296424b9430c065f95b8517', 'banhe_firstday', '拌合数据最早', '\0', '5', '671973208fae11e88d081831bf4e61cf', '2018-08-17 13:52:05', null, null, '\0', null);
INSERT INTO `sys_dict_type` VALUES ('f1e4175c914511e88d081831bf4e61cf', 'sys_code', '所属系统', '', '2', null, '2018-07-27 10:38:12', null, null, '\0', null);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(20000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) DEFAULT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志表（记录新增、修改、删除操作的日志）';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名',
  `role_code` varchar(32) DEFAULT NULL COMMENT '角色 code',
  `role_type` varchar(32) DEFAULT NULL COMMENT '角色类型',
  `data_scope` int(2) DEFAULT NULL COMMENT '数据范围',
  `useable` bit(1) DEFAULT b'1' COMMENT '是否可用',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_code_key` (`role_code`) USING BTREE,
  UNIQUE KEY `role_name_key` (`role_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('9e1d30d1914911e88d081831bf4e61cf', '超级管理员', 'super_admin', '系统管理员', '1', '', '', '2018-07-27 11:03:50', '671973208fae11e88d081831bf4e61cf', '2018-08-27 14:33:04', '\0', 'hahah');
INSERT INTO `sys_role` VALUES ('a3a9e079592047648d3f4f80c0461302', '测试角色', 'test', '测试', '1', '', '671973208fae11e88d081831bf4e61cf', '2018-08-13 17:41:26', null, null, '', '');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父节点id',
  `parent_ids` varchar(1000) DEFAULT NULL COMMENT '所有父节点id，已逗号分隔',
  `menu_name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `menu_level` int(2) DEFAULT NULL COMMENT '菜单级别',
  `menu_type` varchar(32) DEFAULT NULL COMMENT '菜单类型（1-链接，2-权限） 字典表维护',
  `menu_href` varchar(255) DEFAULT NULL COMMENT '链接',
  `menu_target` varchar(255) DEFAULT NULL COMMENT '目标',
  `menu_icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `show_flag` bit(1) DEFAULT b'1' COMMENT '是否显示',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `sys_code` varchar(255) DEFAULT NULL COMMENT '归属系统（pc:主导航菜单、app:APP菜单） 字典表维护',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('01c62d29dbff4dda90a8931c883ec04d', 'pc_top', 'pc_top,', '项目主页', '1', 'menu', 'https://www.kiilin.com', 'main', '', '', '', null, 'pc_top', '671973208fae11e88d081831bf4e61cf', '2018-08-27 10:59:23', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:29:42', '\0', '');
INSERT INTO `sys_menu` VALUES ('04a59c6ba44611e8913c1831bf4e61cf', 'd2141675914911e88d081831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,', '部门管理', '2', 'menu', 'modules/sysDept.html', 'main', null, null, '', '1', 'pc', null, '2018-08-20 14:55:24', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('04bfbff5a44611e8913c1831bf4e61cf', '04a59c6ba44611e8913c1831bf4e61cf', '04a59c6ba44611e8913c1831bf4e61cf', '查看', '3', 'action', null, null, null, 'sysDept:list,sysDept:info', '', null, 'pc', null, '2018-08-20 14:55:24', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('04c58025a44611e8913c1831bf4e61cf', '04a59c6ba44611e8913c1831bf4e61cf', '04a59c6ba44611e8913c1831bf4e61cf', '修改或新增', '3', 'action', null, null, null, 'sysDept:save', '', null, 'pc', null, '2018-08-20 14:55:24', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('04cb7f7ca44611e8913c1831bf4e61cf', '04a59c6ba44611e8913c1831bf4e61cf', '04a59c6ba44611e8913c1831bf4e61cf', '删除', '3', 'action', null, null, null, 'sysDept:del', '', null, 'pc', null, '2018-08-20 14:55:24', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('290a1d639fa911e8bc2b305a3a74ee41', '435243de9fa311e8bc2b305a3a74ee41', 'pc,d2141675914911e88d081831bf4e61cf,', '数据字典数据管理', '3', 'menu', 'modules/sysDict.html', 'main', '', '', '\0', '1', 'pc', '', '2018-08-14 18:01:56', '671973208fae11e88d081831bf4e61cf', '2018-08-20 11:28:47', '\0', '');
INSERT INTO `sys_menu` VALUES ('2950fa539fa911e8bc2b305a3a74ee41', '290a1d639fa911e8bc2b305a3a74ee41', 'pc,290a1d639fa911e8bc2b305a3a74ee41,', '查看', '3', 'action', null, null, null, 'sysDict:list,sysDict:info', '', null, 'pc', null, '2018-08-14 18:01:57', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('2957820d9fa911e8bc2b305a3a74ee41', '290a1d639fa911e8bc2b305a3a74ee41', 'pc,290a1d639fa911e8bc2b305a3a74ee41,', '修改或新增', '3', 'action', null, null, null, 'sysDict:save', '', null, 'pc', null, '2018-08-14 18:01:57', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('295e9cba9fa911e8bc2b305a3a74ee41', '290a1d639fa911e8bc2b305a3a74ee41', 'pc,290a1d639fa911e8bc2b305a3a74ee41,', '删除', '3', 'action', null, null, null, 'sysDict:del', '', null, 'pc', null, '2018-08-14 18:01:57', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('2b6b6207a8504061823f63120d8ac8d6', 'fbdec5db87d24bc0911738fc9d07176d', 'pc_top,fbdec5db87d24bc0911738fc9d07176d,', '百度一哈', '2', 'menu', 'https://www.baidu.com', 'main', '', '', '', null, 'pc_top', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:34', null, null, '\0', '');
INSERT INTO `sys_menu` VALUES ('31cb94a894a311e893b21831bf4e61cf', 'd2141675914911e88d081831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,', '定时任务', '2', 'menu', 'job/schedule.html', '', '', 'test:test', '', null, 'pc', '', '2018-07-31 17:33:55', '6b1a14e23daa4728b5d0385b17844796', '2018-08-15 18:14:11', '\0', '');
INSERT INTO `sys_menu` VALUES ('435243de9fa311e8bc2b305a3a74ee41', 'd2141675914911e88d081831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,', '数据字典', '2', 'menu', 'modules/sysDictType.html', 'main', '', '', '', '1', 'pc', '', '2018-08-14 17:19:43', '671973208fae11e88d081831bf4e61cf', '2018-08-14 17:20:30', '\0', '');
INSERT INTO `sys_menu` VALUES ('439b3c3b9fa311e8bc2b305a3a74ee41', '435243de9fa311e8bc2b305a3a74ee41', 'pc,435243de9fa311e8bc2b305a3a74ee41,', '查看', '3', 'action', '', '', '', 'sysDictType:list,sysDictType:info', '', null, 'pc', '', '2018-08-14 17:19:44', '671973208fae11e88d081831bf4e61cf', '2018-08-14 17:23:16', '\0', '');
INSERT INTO `sys_menu` VALUES ('43a3cea59fa311e8bc2b305a3a74ee41', '435243de9fa311e8bc2b305a3a74ee41', 'pc,435243de9fa311e8bc2b305a3a74ee41,', '修改或新增', '3', 'action', null, null, null, 'sysDictType:save', '', null, 'pc', null, '2018-08-14 17:19:44', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('43ac45b59fa311e8bc2b305a3a74ee41', '435243de9fa311e8bc2b305a3a74ee41', 'pc,435243de9fa311e8bc2b305a3a74ee41,', '删除', '3', 'action', null, null, null, 'sysDictType:del', '', null, 'pc', null, '2018-08-14 17:19:44', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('4553e05494a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '查看', '3', 'action', null, null, null, 'sys:schedule:list,sys:schedule:info', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('48e9e1ff94a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '新增', '3', 'action', null, null, null, 'sys:schedule:save', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('4dd61a5394a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '修改', '3', 'action', null, null, null, 'sys:schedule:update', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('513f5ed794a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '删除', '3', 'action', null, null, null, 'sys:schedule:delete', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('54c29b4a94a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '暂停', '3', 'action', null, null, null, 'sys:schedule:pause', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('58194d3694a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '恢复', '3', 'action', null, null, null, 'sys:schedule:resume', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('5ae1497e5f12475e8179f561e1f29148', '290a1d639fa911e8bc2b305a3a74ee41', 'pc,0d2141675914911e88d081831bf4e61cf,', '编辑系统数据', '3', 'action', '', 'main', '', 'sysDict:sysData:edit', '', '4', 'pc', '6b1a14e23daa4728b5d0385b17844796', '2018-08-15 16:28:32', null, null, '\0', '');
INSERT INTO `sys_menu` VALUES ('5b41cd1194a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '立即执行', '3', 'action', null, null, null, 'sys:schedule:run', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('5ea78c8394a311e893b21831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,31cb94a894a311e893b21831bf4e61cf,', '日志列表', '3', 'action', null, null, null, 'sys:schedule:log', '', null, 'pc', null, '2018-07-31 17:33:37', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('8c72a6439b9d11e8a32d1831bf4e61cf', 'd2141675914911e88d081831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,', '角色管理', '2', 'menu', 'modules/sysRole.html', 'main', null, null, '', '1', 'pc', null, '2018-08-09 14:29:18', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('8c9404579b9d11e8a32d1831bf4e61cf', '8c72a6439b9d11e8a32d1831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,a323c4da9b9811e8a32d1831bf4e61cf,dfac6a329b9c11e8a32d1831bf4e61cf,f656a8629b9c11e8a32d1831bf4e61cf,0da2aff19b9d11e8a32d1831bf4e61cf,8c72a6439b9d11e8a32d1831bf4e61cf,', '查看', '3', 'action', null, null, null, 'sysRole:list,sysRole:info', '', null, 'pc', null, '2018-08-09 14:29:19', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('8c9de2dd9b9d11e8a32d1831bf4e61cf', '8c72a6439b9d11e8a32d1831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,a323c4da9b9811e8a32d1831bf4e61cf,dfac6a329b9c11e8a32d1831bf4e61cf,f656a8629b9c11e8a32d1831bf4e61cf,0da2aff19b9d11e8a32d1831bf4e61cf,8c72a6439b9d11e8a32d1831bf4e61cf,', '修改或新增', '3', 'action', null, null, null, 'sysRole:save', '', null, 'pc', null, '2018-08-09 14:29:19', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('8ca336eb9b9d11e8a32d1831bf4e61cf', '8c72a6439b9d11e8a32d1831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,a323c4da9b9811e8a32d1831bf4e61cf,dfac6a329b9c11e8a32d1831bf4e61cf,f656a8629b9c11e8a32d1831bf4e61cf,0da2aff19b9d11e8a32d1831bf4e61cf,8c72a6439b9d11e8a32d1831bf4e61cf,', '删除', '3', 'action', null, null, null, 'sysRole:del', '', null, 'pc', null, '2018-08-09 14:29:19', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('beb0b37fc9054801b83cdbaca69160de', '435243de9fa311e8bc2b305a3a74ee41', 'pc,d2141675914911e88d081831bf4e61cf,', '系统数据编辑', '3', 'action', '', 'main', '', 'sysDictType:sysData:edit', '', '4', 'pc', '6b1a14e23daa4728b5d0385b17844796', '2018-08-15 16:29:16', null, null, '\0', '');
INSERT INTO `sys_menu` VALUES ('c98bb4d6a02811e8bc2b305a3a74ee41', 'd2141675914911e88d081831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,', '用户管理', '2', 'menu', 'modules/sysUser.html', 'main', null, null, '', '1', 'pc', null, '2018-08-15 09:15:32', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('c99b1edaa02811e8bc2b305a3a74ee41', 'c98bb4d6a02811e8bc2b305a3a74ee41', 'pc,c98bb4d6a02811e8bc2b305a3a74ee41,', '查看', '3', 'action', null, null, null, 'sysUser:list,sysUser:info', '', null, 'pc', null, '2018-08-15 09:15:32', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('c9a25006a02811e8bc2b305a3a74ee41', 'c98bb4d6a02811e8bc2b305a3a74ee41', 'pc,c98bb4d6a02811e8bc2b305a3a74ee41,', '修改或新增', '3', 'action', null, null, null, 'sysUser:save', '', null, 'pc', null, '2018-08-15 09:15:32', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('c9aa63c3a02811e8bc2b305a3a74ee41', 'c98bb4d6a02811e8bc2b305a3a74ee41', 'pc,c98bb4d6a02811e8bc2b305a3a74ee41,', '删除', '3', 'action', null, null, null, 'sysUser:del', '', null, 'pc', null, '2018-08-15 09:15:32', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('d2141675914911e88d081831bf4e61cf', 'pc', 'pc,', '系统管理', '1', 'menu_dir', '', '', 'el-icon-setting', 'system:manager', '', null, 'pc', '', '2018-07-27 11:05:23', '6b1a14e23daa4728b5d0385b17844796', '2018-08-15 15:16:27', '\0', '');
INSERT INTO `sys_menu` VALUES ('f8b1865f9ede11e8a7a81831bf4e61cf', 'd2141675914911e88d081831bf4e61cf', 'pc,d2141675914911e88d081831bf4e61cf,', '菜单管理', '2', 'menu', 'modules/sysMenu.html', 'main', null, null, '', '1', 'pc', null, '2018-08-13 17:55:06', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('f8d1e7239ede11e8a7a81831bf4e61cf', 'f8b1865f9ede11e8a7a81831bf4e61cf', 'pc,f8b1865f9ede11e8a7a81831bf4e61cf,', '查看', '3', 'action', null, null, null, 'sysMenu:list,sysMenu:info', '', null, 'pc', null, '2018-08-13 17:55:06', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('f8d9d2b19ede11e8a7a81831bf4e61cf', 'f8b1865f9ede11e8a7a81831bf4e61cf', 'pc,f8b1865f9ede11e8a7a81831bf4e61cf,', '修改或新增', '3', 'action', null, null, null, 'sysMenu:save', '', null, 'pc', null, '2018-08-13 17:55:06', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('f8e178929ede11e8a7a81831bf4e61cf', 'f8b1865f9ede11e8a7a81831bf4e61cf', 'pc,f8b1865f9ede11e8a7a81831bf4e61cf,', '删除', '3', 'action', null, null, null, 'sysMenu:del', '', null, 'pc', null, '2018-08-13 17:55:06', null, null, '\0', null);
INSERT INTO `sys_menu` VALUES ('fbdec5db87d24bc0911738fc9d07176d', 'pc_top', 'pc_top,', '顶菜单2', '1', 'menu_dir', '', 'main', '', '', '', null, 'pc_top', '671973208fae11e88d081831bf4e61cf', '2018-08-27 14:32:46', null, null, '\0', '');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `menu_id` varchar(32) NOT NULL COMMENT '菜单（操作）id',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_menu_key` (`role_id`,`menu_id`) USING BTREE,
  KEY `role_menu_menu_key` (`menu_id`),
  CONSTRAINT `role_menu_menu_key` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `role_menu_role_key` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-菜单（操作）关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('01ad78c1bd174834af0a8580db5a0e05', '9e1d30d1914911e88d081831bf4e61cf', '48e9e1ff94a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('038040f6441246e48197661a86694dff', '9e1d30d1914911e88d081831bf4e61cf', '8c9404579b9d11e8a32d1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('064eb45d108045ce8bc8fa1631925b71', '9e1d30d1914911e88d081831bf4e61cf', '2b6b6207a8504061823f63120d8ac8d6', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('0ca3047111174fac82f86369fe2a7ac7', '9e1d30d1914911e88d081831bf4e61cf', '54c29b4a94a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('160c7bf3cbe0455c991ae03ddaababae', '9e1d30d1914911e88d081831bf4e61cf', '8c72a6439b9d11e8a32d1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('17500386582a4383bd0e325c76e9422f', '9e1d30d1914911e88d081831bf4e61cf', '295e9cba9fa911e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('21b689f9a08e40468e1d85968dedea53', '9e1d30d1914911e88d081831bf4e61cf', '8c9de2dd9b9d11e8a32d1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('273aba955a1c47f3b17dc61a6291e9ea', '9e1d30d1914911e88d081831bf4e61cf', 'fbdec5db87d24bc0911738fc9d07176d', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('27dffde01f8e4f8b86b0f586724c0d20', '9e1d30d1914911e88d081831bf4e61cf', '513f5ed794a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('2e8e2626c6f145d9acd3f9a223cb63cc', '9e1d30d1914911e88d081831bf4e61cf', '01c62d29dbff4dda90a8931c883ec04d', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('3446ddb8c54d49dfb04ec0c43bdc2640', '9e1d30d1914911e88d081831bf4e61cf', 'c9a25006a02811e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('3abcb836f4d9475da500e9345a039e49', '9e1d30d1914911e88d081831bf4e61cf', '8ca336eb9b9d11e8a32d1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('4031c7879d244fc8b8acd3a5c1159d50', '9e1d30d1914911e88d081831bf4e61cf', 'c99b1edaa02811e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('440d7daefa454d28a1221c445d0015f7', '9e1d30d1914911e88d081831bf4e61cf', '290a1d639fa911e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('45b137711374420d82d13af8bce97774', '9e1d30d1914911e88d081831bf4e61cf', 'c9aa63c3a02811e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('46ccbe5aa1a44238b541a91ce44cbe44', '9e1d30d1914911e88d081831bf4e61cf', 'f8e178929ede11e8a7a81831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('4826ec6d83844b15bf6bc2b2028a2a90', '9e1d30d1914911e88d081831bf4e61cf', '04c58025a44611e8913c1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('4ea01cec49e44e379538dcebb318731f', '9e1d30d1914911e88d081831bf4e61cf', '58194d3694a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('6386f3fc2f77498aaa72803ae96bc4f6', '9e1d30d1914911e88d081831bf4e61cf', '5b41cd1194a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('654cd20489334c0b940fa4bb53254cc6', '9e1d30d1914911e88d081831bf4e61cf', '439b3c3b9fa311e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('717d9dc79ef8422580b5601d3abf84a1', '9e1d30d1914911e88d081831bf4e61cf', '31cb94a894a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('724c238a97eb436089aa54bd875c8da4', '9e1d30d1914911e88d081831bf4e61cf', '04cb7f7ca44611e8913c1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('78eaf68fcfd4430c90656380fa9d0d09', '9e1d30d1914911e88d081831bf4e61cf', 'beb0b37fc9054801b83cdbaca69160de', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('7a57e679cc974e6f8577631da95bbd46', '9e1d30d1914911e88d081831bf4e61cf', '2957820d9fa911e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('88dff3b58d2542e1b281fbeb6808cc24', '9e1d30d1914911e88d081831bf4e61cf', 'c98bb4d6a02811e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('956e3b6b7290469db50716288b4e3a4f', '9e1d30d1914911e88d081831bf4e61cf', '04bfbff5a44611e8913c1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('98d1d9ac87cd4619b14e7ea1f04357a8', '9e1d30d1914911e88d081831bf4e61cf', '04a59c6ba44611e8913c1831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('9c9f6afb621e43aca591c7221ce503d7', '9e1d30d1914911e88d081831bf4e61cf', 'f8d9d2b19ede11e8a7a81831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('9edc356fa1c64129805b9a1592535ded', '9e1d30d1914911e88d081831bf4e61cf', '4dd61a5394a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('b4107b67a8664a48bc50c2d1df74a3fa', '9e1d30d1914911e88d081831bf4e61cf', '4553e05494a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('b997d472ef6542a180358ac5c37a872a', '9e1d30d1914911e88d081831bf4e61cf', 'd2141675914911e88d081831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('cacd284b93594815a4d7a08f6a08baca', '9e1d30d1914911e88d081831bf4e61cf', '2950fa539fa911e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('cbad9c2966eb43a69598c45f54a4de92', '9e1d30d1914911e88d081831bf4e61cf', '435243de9fa311e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('cefbc45e91204a6bb6525eb3306b897c', '9e1d30d1914911e88d081831bf4e61cf', 'f8b1865f9ede11e8a7a81831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('d10a21c10dde4f64964fd56d111097d5', '9e1d30d1914911e88d081831bf4e61cf', '43a3cea59fa311e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('de16129fed4e4a0f99473cd70fdd7f3a', '9e1d30d1914911e88d081831bf4e61cf', 'f8d1e7239ede11e8a7a81831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('dfcba00a5abc4e36834d0362da682a9e', '9e1d30d1914911e88d081831bf4e61cf', '5ea78c8394a311e893b21831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('f0a6b649cadb48fbb17201f910fd3778', '9e1d30d1914911e88d081831bf4e61cf', '5ae1497e5f12475e8179f561e1f29148', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);
INSERT INTO `sys_role_menu` VALUES ('f61b601115cb489b8f80f6ed8d55db35', '9e1d30d1914911e88d081831bf4e61cf', '43ac45b59fa311e8bc2b305a3a74ee41', '671973208fae11e88d081831bf4e61cf', '2018-08-30 10:30:49', null, null, '\0', null);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `login_name` varchar(64) DEFAULT NULL COMMENT '登录名',
  `salt` varchar(32) DEFAULT NULL COMMENT '加密盐',
  `password` varchar(64) DEFAULT NULL COMMENT '登录密码',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号码',
  `sex` bit(1) DEFAULT NULL COMMENT '用户性别：0-女 1-男',
  `photo` varchar(255) DEFAULT NULL COMMENT '头像路径',
  `sign` varchar(1000) DEFAULT NULL COMMENT '个性签名',
  `wx_openid` varchar(64) DEFAULT NULL COMMENT '绑定的微信号',
  `user_type` varchar(32) DEFAULT NULL COMMENT '用户类型 数据字典维护',
  `last_login_ip` varchar(255) DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `status` int(1) DEFAULT '0' COMMENT '状态（0正常 1停用 2冻结）',
  `dept_id` varchar(32) DEFAULT NULL COMMENT '所属部门',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(1000) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_login_name_key` (`login_name`) USING BTREE,
  UNIQUE KEY `user_mobile_key` (`mobile`) USING BTREE,
  UNIQUE KEY `user_email_key` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('671973208fae11e88d081831bf4e61cf', '系统管理员', 'admin', 'KFSmJuvEhHTGGWdaKJcM', '20b3c9c144626f4928a7d4314662a80b5abe31963773d8ed63f90ab0b1c83175', 'a@a.com', '13888888888', '', '', '', '', 'sys_admin', '', null, '0', '', '', null, '671973208fae11e88d081831bf4e61cf', '2018-08-20 17:43:11', '\0', '');

-- ----------------------------
-- Table structure for sys_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_dept`;
CREATE TABLE `sys_user_dept` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `dept_id` varchar(32) NOT NULL COMMENT '部门id',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_dept_key` (`user_id`,`dept_id`) USING BTREE,
  KEY `user_dept_dept_id_key` (`dept_id`),
  CONSTRAINT `user_dept_dept_id_key` FOREIGN KEY (`dept_id`) REFERENCES `sys_dept` (`id`),
  CONSTRAINT `user_dept_user_id_key` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-部门关系表';

-- ----------------------------
-- Records of sys_user_dept
-- ----------------------------
INSERT INTO `sys_user_dept` VALUES ('c0c3e5c10bc844f99b58c332ccc7991d', '671973208fae11e88d081831bf4e61cf', '4562c32bb9af46cfb5feef6d95fd7ecf', '671973208fae11e88d081831bf4e61cf', '2018-08-20 17:43:11', null, null, '\0', null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(32) NOT NULL COMMENT 'id-uuid',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '修改者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `del_flag` bit(1) DEFAULT b'0' COMMENT '删除标识：0-未删除，1-删除',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_role_key` (`user_id`,`role_id`),
  KEY `user_role_role_key` (`role_id`),
  CONSTRAINT `user_role_role_key` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `user_role_user_key` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('aa13a10afbcf4072886f70ef656a74b4', '671973208fae11e88d081831bf4e61cf', '9e1d30d1914911e88d081831bf4e61cf', '671973208fae11e88d081831bf4e61cf', '2018-08-20 17:43:11', null, null, '\0', null);
