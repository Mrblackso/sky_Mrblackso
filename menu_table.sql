-- 菜品表
CREATE TABLE IF NOT EXISTS `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜品ID',
  `menu_name` varchar(100) NOT NULL COMMENT '菜品名称',
  `menu_pic` varchar(255) DEFAULT NULL COMMENT '菜品图片URL',
  `menu_description` text COMMENT '菜品描述',
  `menu_price` decimal(10,2) NOT NULL COMMENT '菜品价格',
  `merchant_id` int(11) NOT NULL COMMENT '关联的商家ID',
  `create_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`menu_id`),
  KEY `idx_merchant_id` (`merchant_id`),
  KEY `idx_menu_name` (`menu_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜品表';

-- 插入测试数据
INSERT INTO `menu` (`menu_name`, `menu_pic`, `menu_description`, `menu_price`, `merchant_id`) VALUES
('宫保鸡丁', '/images/kungpao-chicken.jpg', '经典川菜，鸡肉鲜嫩，花生香脆，口感丰富', 28.00, 1),
('麻婆豆腐', '/images/mapo-tofu.jpg', '麻辣鲜香，豆腐嫩滑，肉末香浓', 22.00, 1),
('水煮鱼', '/images/boiled-fish.jpg', '鲜嫩鱼片，麻辣鲜香，配菜丰富', 45.00, 1),
('回锅肉', '/images/twice-cooked-pork.jpg', '肥而不腻，香辣可口，经典川菜', 32.00, 1),
('鱼香肉丝', '/images/yuxiang-pork.jpg', '酸甜微辣，肉丝嫩滑，配菜爽脆', 26.00, 1); 