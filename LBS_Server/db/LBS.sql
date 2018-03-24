CREATE TABLE `lbs`.`tb_tourist` (
`t_id` int(11) NOT NULL AUTO_INCREMENT,
`t_email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`t_password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`t_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`t_gender` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '男',
`t_age` int(11) NOT NULL,
`t_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`t_id`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;

CREATE TABLE `lbs`.`tb_guider` (
`g_id` int(11) NOT NULL AUTO_INCREMENT,
`g_email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`g_password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`g_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`g_gender` enum('男','女') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '男',
`g_age` int(11) NOT NULL,
`g_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`g_id`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;

CREATE TABLE `lbs`.`tb_spadmin` (
`sp_id` int(11) NOT NULL AUTO_INCREMENT,
`sp_email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`sp_password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`sp_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`sp_intro` multilinestring NOT NULL,
`sp_hotline` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`sp_id`) 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;


CREATE TABLE `lbs`.`tb_info` (
`info_id` int(11) NOT NULL AUTO_INCREMENT,
`sp_id` int(11) NOT NULL,
`g_id` int(11) NOT NULL,
`info_num` int(11) NOT NULL,
`info_detail` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`info_status` enum('1','0','-1') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '\"1\"代表通过景区审核，\"0\"代表等待景区审核，\"-1\"代表未通过景区审核',
PRIMARY KEY (`info_id`) ,
CONSTRAINT `tb_info_ibfk_1` FOREIGN KEY (`g_id`) REFERENCES `lbs`.`tb_guider` (`g_id`),
CONSTRAINT `tb_info_ibfk_2` FOREIGN KEY (`sp_id`) REFERENCES `lbs`.`tb_spadmin` (`sp_id`),
INDEX `g_id` (`g_id`),
INDEX `sp_id` (`sp_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;

CREATE TABLE `lbs`.`tb_appointment` (
`t_id` int(11) NOT NULL,
`g_id` int(11) NOT NULL,
`info_id` int(11) NOT NULL,
PRIMARY KEY (`t_id`, `g_id`) ,
CONSTRAINT `t_id` FOREIGN KEY (`t_id`) REFERENCES `lbs`.`tb_tourist` (`t_id`),
CONSTRAINT `g_id` FOREIGN KEY (`g_id`) REFERENCES `lbs`.`tb_guider` (`g_id`),
CONSTRAINT `info_id` FOREIGN KEY (`info_id`) REFERENCES `lbs`.`tb_info` (`info_id`),
INDEX `t_id` (`t_id`),
INDEX `g_id` (`g_id`),
INDEX `info_id` (`info_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;

CREATE TABLE `lbs`.`tb_emp` (
`g_id` int(11) NOT NULL,
`sp_id` int(11) NOT NULL,
`e_status` enum('1','0') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '\"1\"',
PRIMARY KEY (`g_id`, `sp_id`) ,
CONSTRAINT `tb_emp_ibfk_1` FOREIGN KEY (`g_id`) REFERENCES `lbs`.`tb_guider` (`g_id`),
CONSTRAINT `tb_emp_ibfk_2` FOREIGN KEY (`sp_id`) REFERENCES `lbs`.`tb_spadmin` (`sp_id`),
INDEX `g_id` (`g_id`),
INDEX `sp_id` (`sp_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;

CREATE TABLE `lbs`.`tb_diary` (
`diary_id` int(11) NOT NULL AUTO_INCREMENT,
`t_id` int(11) NOT NULL,
`diary_title` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
`diary_content` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
PRIMARY KEY (`diary_id`) ,
CONSTRAINT `tb_diary_ibfk_1` FOREIGN KEY (`t_id`) REFERENCES `lbs`.`tb_tourist` (`t_id`),
INDEX `t_id` (`t_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
;

insert into tb_tourist
(t_email, t_password, t_name, t_age, t_phone)
values
('hezhihaics@163.com', 'hzh', 'hezhihai', 21, '18883855634');