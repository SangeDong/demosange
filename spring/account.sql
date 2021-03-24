CREATE TABLE `bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cardNo` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `money` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `account`(`id`, `cardNo`, `name`, `money`) VALUES (1, '6029621011001', '韩梅梅', 10000);
INSERT INTO `account`(`id`, `cardNo`, `name`, `money`) VALUES (2, '6029621011000', '李大雷', 10000);
