/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.19 : Database - yixie
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`yixie` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `yixie`;

/*Table structure for table `car` */

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `c_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '购物id',
  `u_id` int NOT NULL COMMENT '购买用户id',
  `shop_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品id',
  `shop_sum` int NOT NULL COMMENT '购买数量',
  `shop_price` decimal(11,2) NOT NULL COMMENT '购买价格',
  `sto_id` int NOT NULL COMMENT '店铺id',
  `c_time` datetime NOT NULL COMMENT '购买时间',
  PRIMARY KEY (`c_id`),
  KEY `fk_car_user` (`u_id`),
  KEY `fk_car_shop` (`shop_id`),
  CONSTRAINT `fk_car_user` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `car` */

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `com_time` datetime NOT NULL COMMENT '评论时间',
  `u_id` int NOT NULL COMMENT '用户id',
  `com_mark` int NOT NULL COMMENT '评分',
  `com_content` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `com_pic1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论图路径',
  `com_pic2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论图路径',
  `com_pic3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '评论图路径',
  `shop_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品id',
  KEY `fk_com_shop` (`shop_id`),
  CONSTRAINT `fk_com_shop` FOREIGN KEY (`shop_id`) REFERENCES `shop` (`shop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `o_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `o_time` datetime NOT NULL COMMENT '生成时间',
  `o_num` int NOT NULL COMMENT '商品总数',
  `o_price` decimal(11,2) NOT NULL COMMENT '商品总价',
  `o_status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单状态',
  `u_id` int NOT NULL COMMENT '所属用户',
  PRIMARY KEY (`o_id`),
  KEY `fk_order_user` (`u_id`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`o_id`,`o_time`,`o_num`,`o_price`,`o_status`,`u_id`) values ('o-1612075107692','2021-01-31 14:38:27',12,'22.00','已处理',1),('o-1612076129142','2021-01-31 14:55:29',5,'20.00','已处理',1);

/*Table structure for table `orderitem` */

DROP TABLE IF EXISTS `orderitem`;

CREATE TABLE `orderitem` (
  `u_id` int NOT NULL COMMENT '用户id',
  `shop_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `oi_time` datetime NOT NULL COMMENT '购买时间',
  `oi_count` int NOT NULL COMMENT '购买数量',
  `oi_price` decimal(11,2) NOT NULL COMMENT '单价',
  `oi_status` varchar(14) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `o_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单id',
  `sto_id` int NOT NULL COMMENT '店铺id',
  `s_can` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'Y' COMMENT '是否显示',
  `exit_why` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '退货原因',
  `about_sto` varchar(6) DEFAULT NULL COMMENT '店铺相关',
  KEY `fk_oi_order` (`o_id`),
  KEY `fk_oi_shop` (`shop_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orderitem` */

insert  into `orderitem`(`u_id`,`shop_name`,`oi_time`,`oi_count`,`oi_price`,`oi_status`,`o_id`,`sto_id`,`s_can`,`exit_why`,`about_sto`) values (1,'签收商品','2021-01-31 13:56:58',5,'1.00','已签收','o-1612073655936',34,'N',NULL,'店铺已被删除'),(1,'签收退货商品','2021-01-31 13:57:04',4,'2.00','已退货','o-1612073655936',34,'N','退货2','店铺已被删除'),(1,'未签收退货商品','2021-01-31 13:57:10',3,'3.00','已退货','o-1612073655936',34,'N','退货','店铺已被删除'),(1,'签收商品','2021-01-31 14:37:53',5,'1.00','已签收','o-1612075107692',34,'Y',NULL,'店铺已被删除'),(1,'签收退货商品','2021-01-31 14:38:02',4,'2.00','已退货','o-1612075107692',34,'Y','签收退货','店铺已被删除'),(1,'未签收退货商品','2021-01-31 14:38:14',3,'3.00','已退货','o-1612075107692',34,'Y','未签收退货','店铺已被删除'),(1,'下架退货商品1','2021-01-31 14:55:15',5,'4.00','已退款','o-1612076129142',34,'Y',NULL,'店铺已被删除');

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `shop_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品id(店铺id+"-"+时间戳)',
  `shop_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名',
  `shop_class` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品类型',
  `shop_price` decimal(11,2) NOT NULL COMMENT '价格',
  `shop_unit` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规格(单位)',
  `shop_time` datetime NOT NULL COMMENT '添加时间',
  `shop_stock` int NOT NULL COMMENT '折扣',
  `shop_discount` int NOT NULL COMMENT '库存',
  `shop_bpicture` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '大图路径',
  `shop_spicture1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '小图路径',
  `shop_spicture2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '小图路径',
  `shop_spicture3` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '小图路径',
  `shop_allprice` decimal(11,2) NOT NULL COMMENT '总销售额',
  `sto_id` int NOT NULL COMMENT '所属店铺id',
  PRIMARY KEY (`shop_id`),
  KEY `fk_shop_sto` (`sto_id`),
  CONSTRAINT `fk_shop_sto` FOREIGN KEY (`sto_id`) REFERENCES `store` (`sto_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `shop` */

insert  into `shop`(`shop_id`,`shop_name`,`shop_class`,`shop_price`,`shop_unit`,`shop_time`,`shop_stock`,`shop_discount`,`shop_bpicture`,`shop_spicture1`,`shop_spicture2`,`shop_spicture3`,`shop_allprice`,`sto_id`) values ('10-1604669948918','优质冬瓜','蔬菜','1.00','元/斤','2020-11-06 21:39:09',10,610,'19/store/1604669948918/bpicture.JPG','19/store/1604669948918/spicture1.JPG',NULL,NULL,'0.00',10),('10-1604669991012','优质马铃薯','蔬菜','1.00','元/斤','2020-12-03 21:39:51',10,645,'19/store/1604669991012/bpicture.JPG','19/store/1604669991012/spicture1.JPG','19/store/1604669991012/spicture2.JPG',NULL,'10.00',10),('10-1604670032916','优质山药1','蔬菜','8.00','元/斤','2020-12-17 21:40:32',10,687,'19/store/1604670032916/bpicture.JPG','19/store/1604670032916/spicture1.JPG','19/store/1604670032916/spicture2.JPG',NULL,'64.00',10),('10-1604670061793','优质山药2','蔬菜','10.00','元/斤','2020-12-18 21:41:01',8,900,'19/store/1604670061793/bpicture.JPG','19/store/1604670061793/spicture1.JPG','19/store/1604670061793/spicture2.JPG',NULL,'0.00',10),('10-1604670167352','优质丝瓜','蔬菜','4.00','元/斤','2020-11-06 21:42:47',10,800,'19/store/1604670167352/bpicture.JPG','19/store/1604670167352/spicture1.JPG','19/store/1604670167352/spicture2.JPG',NULL,'0.00',10),('10-1604670224268','优质西红柿','蔬菜','5.00','元/斤','2020-11-06 21:43:44',8,597,'19/store/1604670224268/bpicture.JPG','19/store/1604670224268/spicture1.JPG','19/store/1604670224268/spicture2.JPG',NULL,'0.00',10),('11-1604711254873','草莓罐头','副产品','9.00','元/罐(500g)','2020-11-07 09:07:34',10,700,'20/store/1604711254873/bpicture.JPG','20/store/1604711254873/spicture1.JPG','20/store/1604711254873/spicture2.JPG',NULL,'0.00',11),('11-1604711338235','什锦罐头1','副产品','10.00','元/罐(500g)','2020-12-16 09:08:58',8,488,'20/store/1604711338235/bpicture.JPG','20/store/1604711338235/spicture1.JPG','20/store/1604711338235/spicture2.JPG','20/store/1604711338235/spicture3.JPG','64.00',11),('11-1604711378804','什锦罐头','副产品','10.00','元/罐(500g)','2020-11-07 09:09:38',8,600,'20/store/1604711378804/bpicture.JPG','20/store/1604711378804/spicture1.JPG',NULL,NULL,'0.00',11),('11-1604711459519','糖水菠萝罐头','副产品','9.00','元/罐(500g)','2020-11-07 09:10:59',10,700,'20/store/1604711459519/bpicture.JPG','20/store/1604711459519/spicture1.JPG',NULL,NULL,'0.00',11),('11-1604711511686','糖水黄桃','副产品','10.00','元/罐(500g)','2020-11-07 09:11:51',9,788,'20/store/1604711511686/bpicture.JPG','20/store/1604711511686/spicture1.JPG','20/store/1604711511686/spicture2.JPG',NULL,'63.00',11),('11-1604711550267','糖水荔枝罐头','副产品','8.00','元/罐(500g)','2020-11-07 09:12:30',10,900,'20/store/1604711550267/bpicture.JPG','20/store/1604711550267/spicture1.JPG',NULL,NULL,'0.00',11),('11-1604711589647','糖水樱桃','副产品','8.00','元/罐(500g)','2020-11-07 09:13:09',9,400,'20/store/1604711589647/bpicture.JPG','20/store/1604711589647/spicture1.JPG',NULL,NULL,'0.00',11),('11-1604711625087','雪梨木瓜罐头','副产品','9.00','元/罐(500g)','2020-11-07 09:13:45',8,500,'20/store/1604711625087/bpicture.JPG','20/store/1604711625087/spicture1.JPG',NULL,NULL,'0.00',11),('12-1604712911967','优质桂圆干','副产品','8.50','元/斤','2020-12-05 09:35:11',10,800,'21/store/1604712911967/bpicture.JPG','21/store/1604712911967/spicture1.JPG','21/store/1604712911967/spicture2.JPG','21/store/1604712911967/spicture3.JPG','0.00',12),('12-1604712962247','优质荔枝干','副产品','12.00','元/斤','2020-12-05 09:36:02',9,900,'21/store/1604712962247/bpicture.JPG','21/store/1604712962247/spicture1.JPG',NULL,NULL,'0.00',12),('12-1604713006883','优质木瓜干','副产品','6.30','元/斤','2020-11-07 09:36:46',10,390,'21/store/1604713006883/bpicture.JPG','21/store/1604713006883/spicture1.JPG','21/store/1604713006883/spicture2.JPG',NULL,'31.50',12),('12-1604713048818','优质葡萄干','副产品','5.00','元/斤','2020-11-07 09:37:28',10,515,'21/store/1604713048818/bpicture.JPG','21/store/1604713048818/spicture1.JPG','21/store/1604713048818/spicture2.JPG',NULL,'0.00',12),('12-1605602308484','优质枸杞','副产品','20.00','元/斤','2020-12-11 16:38:28',9,695,'21/store/1605602308484/bpicture.JPG','21/store/1605602308484/spicture1.JPG','21/store/1605602308484/spicture2.JPG',NULL,'90.00',12),('12-1606400535392','优质柿饼','副产品','8.00','元/斤','2020-12-12 22:22:15',9,600,'21/store/1606400535392/bpicture.JPG','21/store/1606400535392/spicture1.JPG','21/store/1606400535392/spicture2.JPG',NULL,'0.00',12),('12-1606400568311','优质草莓干','副产品','10.00','元/斤','2020-11-26 22:22:48',10,596,'21/store/1606400568311/bpicture.JPG','21/store/1606400568311/spicture1.JPG',NULL,NULL,'50.00',12),('3-1604452994157','优质莲藕','蔬菜','3.60','元/斤','2020-11-04 09:23:14',10,688,'13/store/1604452994157/bpicture.JPG','13/store/1604452994157/spicture1.JPG',NULL,NULL,'231.20',3),('3-1605542271986','优质红根蒜苗','蔬菜','1.20','元/斤','2020-11-16 23:57:51',10,590,'13/store/1605542271986/bpicture.JPG','13/store/1605542271986/spicture1.JPG','13/store/1605542271986/spicture2.JPG',NULL,'132.00',3),('3-1609574743637','优质白根蒜苗','瓜果','4.00','元/斤','2021-01-02 16:05:43',10,4000,'13/store/1609574743637/bpicture.JPG','13/store/1609574743637/spicture1.JPG','13/store/1609574743637/spicture2.JPG','13/store/1609574743637/spicture3.JPG','0.00',3),('3-1609574978488','优质蒜黄','蔬菜','3.50','元/斤','2021-01-02 16:09:38',10,500,'13/store/1609574978488/bpicture.JPG','13/store/1609574978488/spicture1.JPG','13/store/1609574978488/spicture2.JPG','13/store/1609574978488/spicture3.JPG','0.00',3),('3-1609575450592','优质芥蓝','瓜果','5.00','元/斤','2021-01-02 16:17:30',10,400,'13/store/1609575450592/bpicture.JPG','13/store/1609575450592/spicture1.JPG','13/store/1609575450592/spicture2.JPG',NULL,'0.00',3),('3-1609575498896','优质西兰花','蔬菜','5.50','元/斤','2021-01-02 16:18:18',9,500,'13/store/1609575498896/bpicture.JPG','13/store/1609575498896/spicture1.JPG','13/store/1609575498896/spicture2.JPG',NULL,'0.00',3),('3-1609575909248','优质白菜','瓜果','3.00','元/斤','2021-01-02 16:25:09',10,400,'13/store/1609575909248/bpicture.JPG','13/store/1609575909248/spicture1.JPG',NULL,NULL,'0.00',3),('3-1609576065518','优质冬笋','蔬菜','8.00','元/斤','2021-01-02 16:27:45',8,500,'13/store/1609576065518/bpicture.JPG','13/store/1609576065518/spicture1.JPG','13/store/1609576065518/spicture2.JPG',NULL,'0.00',3),('3-1609576164529','优质西葫芦','蔬菜','5.00','元/斤','2021-01-02 16:29:24',10,500,'13/store/1609576164529/bpicture.JPG','13/store/1609576164529/spicture1.JPG',NULL,NULL,'0.00',3),('3-1609584013645','西洋菜','蔬菜','3.50','元/斤','2021-01-02 18:40:13',10,510,'13/store/1609584013645/bpicture.JPG','13/store/1609584013645/spicture1.JPG',NULL,NULL,'0.00',3),('4-1605262436932','优质黄无花果','瓜果','3.50','元/斤','2020-11-13 18:13:56',10,1000,'15/store/1605262436932/bpicture.JPG','15/store/1605262436932/spicture1.JPG','15/store/1605262436932/spicture2.JPG','15/store/1605262436932/spicture3.JPG','0.00',4),('4-1605262458803','优质红无花果','瓜果','4.00','元/斤','2020-11-13 18:14:18',10,900,'15/store/1605262458803/bpicture.JPG','15/store/1605262458803/spicture1.JPG','15/store/1605262458803/spicture2.JPG','15/store/1605262458803/spicture3.JPG','0.00',4),('5-1604453416485','优质百香果','瓜果','3.50','元/斤','2020-11-04 09:30:16',10,800,'16/store/1604453416485/bpicture.JPG','16/store/1604453416485/spicture1.JPG','16/store/1604453416485/spicture2.JPG',NULL,'0.00',5),('5-1604453459995','优质草莓','瓜果','5.00','元/斤','2020-11-04 09:31:00',7,800,'16/store/1604453459995/bpicture.JPG','16/store/1604453459995/spicture1.JPG','16/store/1604453459995/spicture2.JPG','16/store/1604453459995/spicture3.JPG','0.00',5),('5-1604453494362','优质杨桃','瓜果','5.40','元/斤','2020-11-04 09:31:34',10,900,'16/store/1604453494362/bpicture.JPG','16/store/1604453494362/spicture1.JPG','16/store/1604453494362/spicture2.JPG',NULL,'0.00',5),('5-1604453539458','优质芒果','瓜果','4.50','元/斤','2020-11-04 09:32:19',10,800,'16/store/1604453539458/bpicture.JPG','16/store/1604453539458/spicture1.JPG','16/store/1604453539458/spicture2.JPG',NULL,'0.00',5),('5-1604453570552','优质金秋沙糖桔','瓜果','4.50','元/斤','2020-11-04 09:32:50',10,900,'16/store/1604453570552/bpicture.JPG','16/store/1604453570552/spicture1.JPG','16/store/1604453570552/spicture2.JPG',NULL,'0.00',5),('5-1604453606551','优质柚子','瓜果','1.50','元/斤','2020-11-04 09:33:26',10,1000,'16/store/1604453606551/bpicture.JPG','16/store/1604453606551/spicture1.JPG','16/store/1604453606551/spicture2.JPG',NULL,'0.00',5),('5-1604453635648','优质文旦','瓜果','2.50','元/斤','2020-11-04 09:33:55',10,900,'16/store/1604453635648/bpicture.JPG','16/store/1604453635648/spicture1.JPG','16/store/1604453635648/spicture2.JPG',NULL,'0.00',5),('5-1604453665483','优质红心火龙果','瓜果','4.00','元/斤','2020-12-04 09:34:25',10,800,'16/store/1604453665483/bpicture.JPG','16/store/1604453665483/spicture1.JPG','16/store/1604453665483/spicture2.JPG',NULL,'0.00',5),('6-1604495019800','优质巴旦木','副产品','10.00','元/斤','2020-11-04 21:03:41',9,700,'22/store/1604495019800/bpicture.JPG','22/store/1604495019800/spicture1.JPG','22/store/1604495019800/spicture2.JPG',NULL,'0.00',6),('6-1604495107535','优质炒板栗','副产品','7.00','元/斤','2020-11-04 21:05:07',8,800,'22/store/1604495107535/bpicture.JPG','22/store/1604495107535/spicture1.JPG','22/store/1604495107535/spicture2.JPG',NULL,'0.00',6),('6-1604495159691','优质核桃','副产品','3.00','元/斤','2020-12-23 21:05:59',9,700,'22/store/1604495159691/bpicture.JPG','22/store/1604495159691/spicture1.JPG',NULL,NULL,'0.00',6),('6-1604495270147','优质红枸杞','副产品','20.00','元/斤','2020-12-05 21:07:50',8,600,'22/store/1604495270147/bpicture.JPG','22/store/1604495270147/spicture1.JPG','22/store/1604495270147/spicture2.JPG',NULL,'0.00',6),('6-1604495315293','优质笋干','副产品','24.00','元/斤','2020-11-04 21:08:35',8,500,'22/store/1604495315293/bpicture.JPG','22/store/1604495315293/spicture1.JPG',NULL,NULL,'0.00',6),('7-1604630156599','优质小米2','谷物','4.00','元/斤','2020-11-06 10:35:56',9,580,'12/store/1604630156599/bpicture.JPG','12/store/1604630156599/spicture1.JPG',NULL,NULL,'61.20',7),('7-1604630175018','优质小米3','谷物','5.00','元/斤','2020-11-06 10:36:15',8,689,'12/store/1604630175018/bpicture.JPG','12/store/1604630175018/spicture1.JPG',NULL,NULL,'24.00',7),('7-1605600697718','优质大米1','谷物','4.50','元/斤','2020-11-17 16:11:37',10,561,'12/store/1605600697718/bpicture.JPG','12/store/1605600697718/spicture1.JPG',NULL,NULL,'117.00',7),('7-1605600963111','优质大米2','谷物','5.00','元/斤','2020-11-17 16:16:03',9,677,'12/store/1605600963111/bpicture.JPG','12/store/1605600963111/spicture1.JPG','12/store/1605600963111/spicture2.JPG',NULL,'58.50',7),('7-1605601256442','优质高粱','谷物','2.00','元/斤','2020-11-17 16:20:56',10,595,'12/store/1605601256442/bpicture.JPG','12/store/1605601256442/spicture1.JPG',NULL,NULL,'10.00',7),('7-1605601463145','优质小麦','谷物','4.00','元/斤','2020-11-17 16:24:23',10,389,'12/store/1605601463145/bpicture.JPG','12/store/1605601463145/spicture1.JPG',NULL,NULL,'44.00',7),('7-1609405674210','优质小米1','谷物','4.00','元/斤','2020-12-31 17:07:54',10,290,'12/store/1609405674210/bpicture.JPG','12/store/1609405674210/spicture1.JPG',NULL,NULL,'15.00',7),('8-1604631150060','优质蚕豆','谷物','5.00','元/斤','2020-11-06 10:52:30',10,800,'17/store/1604631150060/bpicture.JPG','17/store/1604631150060/spicture1.JPG',NULL,NULL,'0.00',8),('8-1604631257912','优质大豆','谷物','3.60','元/斤','2020-11-06 10:54:17',10,900,'17/store/1604631257912/bpicture.JPG','17/store/1604631257912/spicture1.JPG',NULL,NULL,'0.00',8),('8-1604631313447','优质豌豆','谷物','7.00','元/斤','2020-11-06 10:55:13',10,700,'17/store/1604631313447/bpicture.JPG','17/store/1604631313447/spicture1.JPG','17/store/1604631313447/spicture2.JPG',NULL,'0.00',8),('8-1604631372945','优质玉米1','谷物','4.00','元/斤','2020-11-06 10:56:12',9,800,'17/store/1604631372945/bpicture.JPG','17/store/1604631372945/spicture1.JPG','17/store/1604631372945/spicture2.JPG',NULL,'0.00',8),('8-1604631392528','优质玉米2','谷物','3.50','元/斤','2020-11-06 10:56:32',10,900,'17/store/1604631392528/bpicture.JPG','17/store/1604631392528/spicture1.JPG',NULL,NULL,'0.00',8),('9-1604667318791','优质哈密瓜','瓜果','5.00','元/斤','2020-11-06 20:55:18',9,700,'18/store/1604667318791/bpicture.JPG','18/store/1604667318791/spicture1.JPG','18/store/1604667318791/spicture2.JPG',NULL,'0.00',9),('9-1604667387881','优质红苹果','瓜果','2.00','元/斤','2020-11-06 20:56:27',9,800,'18/store/1604667387881/bpicture.JPG','18/store/1604667387881/spicture1.JPG','18/store/1604667387881/spicture2.JPG',NULL,'0.00',9),('9-1604667426861','优质红枣','副产品','3.00','元/斤','2020-11-06 20:57:06',10,800,'18/store/1604667426861/bpicture.JPG','18/store/1604667426861/spicture1.JPG','18/store/1604667426861/spicture2.JPG',NULL,'0.00',9),('9-1604667473482','优质橘子','瓜果','1.50','元/斤','2020-11-06 20:57:53',10,700,'18/store/1604667473482/bpicture.JPG','18/store/1604667473482/spicture1.JPG','18/store/1604667473482/spicture2.JPG',NULL,'0.00',9),('9-1604667524042','优质青苹果','瓜果','1.00','元/斤','2020-11-06 20:58:44',10,700,'18/store/1604667524042/bpicture.JPG','18/store/1604667524042/spicture1.JPG',NULL,NULL,'0.00',9),('9-1604667597050','优质青枣','瓜果','2.00','元/斤','2020-11-06 20:59:57',10,800,'18/store/1604667597050/bpicture.JPG','18/store/1604667597050/spicture1.JPG','18/store/1604667597050/spicture2.JPG',NULL,'0.00',9),('9-1604667661348','优质沙果','瓜果','2.00','元/斤','2020-11-06 21:01:01',9,599,'18/store/1604667661348/bpicture.JPG','18/store/1604667661348/spicture1.JPG',NULL,NULL,'1.80',9),('9-1605542522741','优质香蕉','瓜果','3.00','元/斤','2020-11-17 00:02:02',7,730,'18/store/1605542522741/bpicture.JPG','18/store/1605542522741/spicture1.JPG',NULL,NULL,'0.00',9);

/*Table structure for table `store` */

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `sto_id` int NOT NULL AUTO_INCREMENT COMMENT '店铺id',
  `sto_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺名称',
  `sto_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '店铺地址',
  `sto_mainShop` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主营产品',
  `sto_time` datetime NOT NULL COMMENT '创建时间',
  `sto_status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '店铺状态',
  `u_id` int DEFAULT NULL COMMENT '所属用户id',
  PRIMARY KEY (`sto_id`),
  KEY `fk_user_sto` (`u_id`),
  CONSTRAINT `fk_user_sto` FOREIGN KEY (`u_id`) REFERENCES `user` (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `store` */

insert  into `store`(`sto_id`,`sto_name`,`sto_address`,`sto_mainShop`,`sto_time`,`sto_status`,`u_id`) values (3,'test3的店铺','浙江','蔬菜','2020-10-20 16:16:36','正常',13),(4,'test4的店铺','新疆','水果','2020-10-20 20:07:59','正常',15),(5,'test5的店铺','广东','水果','2020-10-20 20:07:59','正常',16),(6,'test11的店铺','浙江','农副产品','2020-11-01 14:21:26','正常',22),(7,'test2的店铺','浙江省','五谷','2020-11-06 10:16:36','正常',12),(8,'test6的店铺','天津','五谷-豆类','2020-11-06 10:41:51','正常',17),(9,'test7的店铺','新疆','瓜果','2020-11-06 20:33:03','正常',18),(10,'test8的店铺','辽宁','蔬菜','2020-11-06 21:19:28','正常',19),(11,'test9的店铺','天津','农副产品---水果罐头','2020-11-06 22:55:58','正常',20),(12,'test10的店铺','济南','农副产品-干果糕点','2020-11-07 09:19:59','正常',21),(13,'test13的店铺','浙江','蔬菜','2021-01-01 21:52:09','正常',24),(14,'test15的店铺','浙江','蔬菜','2021-01-07 13:58:49','正常',38);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `u_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `u_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `u_password` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `u_email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `u_tel` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `u_time` datetime NOT NULL COMMENT '创建时间',
  `u_status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '正常' COMMENT '用户状态',
  `u_gender` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2' COMMENT '用户等级',
  `u_header` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'img/defHead.jpg' COMMENT '用户头像路径',
  `u_money` decimal(11,2) NOT NULL DEFAULT '0.00' COMMENT '用户的金额',
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`u_id`,`u_name`,`u_password`,`u_email`,`u_tel`,`u_time`,`u_status`,`u_gender`,`u_header`,`u_money`) values (1,'test1','test1123','test1@qq.com','13145678909','2020-10-04 22:20:44','正常','1','1/head.JPG','995.00'),(12,'test2','test2123','test2@qq.com','13145678910','2020-10-07 23:44:19','正常','2','12/head.JPG','1000.00'),(13,'test3','test3123','test3@qq.com','13145678912','2020-10-09 00:00:00','正常','2','13/head.JPG','1000.00'),(15,'test4','test4123','test4@qq.com','13145678914','2020-10-12 09:47:43','正常','2','15/head.JPG','1000.00'),(16,'test5','test5123','test5@qq.com','13145678915','2020-10-13 17:53:40','正常','2','16/head.JPG','1000.00'),(17,'test6','test6123','test6@qq.com','13145678916','2020-10-13 18:49:42','正常','2','17/head.JPG','1000.00'),(18,'test7','test7123','test7@qq.com','13145678917','2020-10-22 18:18:25','正常','2','18/head.JPG','1000.00'),(19,'test8','test8123','test8@qq.com','13145678918','2020-10-27 20:36:53','正常','2','19/head.JPG','1000.00'),(20,'test9','test9123','test9@qq.com','13145678919','2020-10-27 21:11:37','正常','2','20/head.JPG','1000.00'),(21,'test10','test10123','test10@qq.com','13145678920','2020-10-31 09:28:10','正常','2','21/head.JPG','1000.00'),(22,'test11','test11123','test11@qq.com','13145678921','2020-11-01 14:21:05','正常','2','22/head.JPG','1000.00'),(23,'test12','test12123','test12@qq.com','13145678922','2020-11-08 19:36:29','正常','2','23/head.JPG','1000.00'),(24,'test13','test13123','test13@qq.com','13145678923','2021-01-01 21:45:29','正常','2','24/head.JPG','1000.00'),(38,'test15','test15123','123456@qq.com','13145678909','2021-01-02 14:40:22','正常','2','38/head.JPG','1005.00'),(39,'test16','test16123','test16@qq.com','13145678924','2021-01-30 20:28:55','正常','2','39/head.JPG','5.00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
