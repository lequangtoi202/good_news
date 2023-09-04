-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: good_news
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `article`
--

DROP TABLE IF EXISTS `article`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(150) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` longtext COLLATE utf8mb4_unicode_ci,
  `image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `active` tinyint DEFAULT NULL,
  `source` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `category_id` bigint NOT NULL,
  `authors_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_news_category1_idx` (`category_id`),
  KEY `fk_news_authors1_idx` (`authors_id`),
  CONSTRAINT `fk_news_authors1` FOREIGN KEY (`authors_id`) REFERENCES `authors` (`id`),
  CONSTRAINT `fk_news_category1` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article`
--

LOCK TABLES `article` WRITE;
/*!40000 ALTER TABLE `article` DISABLE KEYS */;
INSERT INTO `article` VALUES (1,'Sầu riêng Thái Lan ồ ạt vào Trung Quốc nhờ tàu cao tốc update','Xuất khẩu sầu riêng Thái Lan sang Trung Quốc tăng mạnh từ khi tuyến đường sắt cao tốc nối Lào và nền kinh tế số 2 thế giới vận hành. Theo Bộ Thương mại Thái Lan, xuất khẩu sầu riêng của nước này sang Trung Quốc đạt 446.152 tấn trong 5 tháng đầu năm nay, tăng 58% so với cùng kỳ năm ngoái (281.528 tấn). Một trong các lý do được người trong ngành chỉ ra là nhờ tuyến đường sắt cao tốc Lào - Trung Quốc giúp rút ngắn thời gian vận chuyển. \"Người tiêu dùng Trung Quốc ưa thích sầu riêng Thái từ lâu, nhưng xuất khẩu chỉ tăng vọt kể từ năm ngoái khi tàu cao tốc bắt đầu hoạt động\", bà Arada Fuangtong, Phó cục trưởng Cục xúc tiến thương mại quốc tế của Bộ Thương mại Thái Lan nói. Năm 2022, nước này xuất khẩu kỷ lục 700.000 tấn sầu riêng, với 90% là đến Trung Quốc. Tàu cao tốc Trung Quốc - Lào bắt đầu chạy vào tháng 12/2021, tạo cơ hội cho hàng hóa Thái Lan đi qua biên giới vào thủ đô Viêng Chăn của Lào để vận chuyển đường sắt đến thành phố Côn Minh phía nam Trung Quốc. Tuyến đường sắt này dài 1.000 km, giúp thương nhân Thái Lan giảm thời gian giao hàng đến Trung Quốc xuống còn 15 giờ thay vì hai ngày khi vận chuyển bằng xe tải như trước đây. Cửa khẩu Nong Khai, nơi tuyến đường sắt cao tốc Trung Quốc - Lào, đi qua đã chứng kiến nông sản Thái Lan thông quan tăng vọt, theo Rachada Dhnadirek, Phó phát ngôn viên Văn phòng Thủ tướng Thái Lan. Trong 5 tháng đầu năm, sầu riêng tươi của nước này đi qua Nong Khai có kim ngạch cao nhất trong các mặt hàng, đạt hơn 2 tỷ baht (hơn 57 triệu USD), tăng 364% so với cùng kỳ năm 2022. Bà Arada cho biết việc rút ngắn thời gian vận chuyển đã thúc đẩy xuất khẩu sầu riêng Thái Lan tăng mạnh và cũng mang lại cơ hội lớn cho các loại trái cây, rau quả và các sản phẩm dễ hư hỏng khác của Thái Lan cần đến tay người tiêu dùng Trung Quốc mà vẫn đảm bảo độ tươi, chất lượng tốt. Auramon Supthaweethum, Tổng giám đốc Cục Đàm phán Thương mại Thái Lan, nhấn mạnh tác động mang tính chuyển đổi khi các chuyến hàng qua cảng khô phía đông bắc Nong Khai đã tăng từ 90,41 triệu baht thời điểm tuyến đường sắt này ra mắt tháng 12/2021 lên 1,96 tỷ baht vào năm ngoái. Bộ Thương mại Thái Lan đang khuyến khích các nhà xuất khẩu, đặc biệt là trái cây và rau quả, chuyển sang tận dụng tuyến đường sắt Trung Quốc - Lào, đặc biệt khi giá dầu tương đối cao đang đẩy chi phí vận tải đường bộ leo thang. Thunkanon Tiewsuwan, một thương nhân xuất khẩu sầu riêng cho hay thường phải mất 10 đến 12 ngày để chất sầu riêng lên xe tải, chở đến cảng biển, chuyển vào container vận chuyển và chất lên tàu. Các tàu này thường mất thêm một tuần nữa để đến các cảng quan trọng của Trung Quốc. \"Tôi đang nghĩ đến việc chuyển sang sử dụng tàu cao tốc\", người này nói. Sầu riêng ngày càng trở thành nông sản xuất khẩu quan trọng của Thái Lan. Năm ngoái, kim ngạch của trái cây này là 110 tỷ baht (3,1 tỷ USD), gần bằng mức 130 tỷ baht mà Thái Lan thu được từ xuất khẩu gạo. Xuất khẩu sầu riêng của Thái Lan có thể còn nhanh hơn nữa trong tương lai khi nước này có kế hoạch xây dựng tuyến đường sắt cao tốc của riêng mình nối Bangkok đến thành phố Nong Khai gần biên giới với Lào. Đoạn đầu tiên dài 253 km từ Bangkok đến thành phố Nakhon Ratchasima đã bắt đầu thi công năm ngoái và dự kiến khai thác thương mại năm 2026. Đoạn hai dài 355 km từ Nakhon Ratchasima đến Nong Khai sẽ hoàn thành vào năm 2028, nghĩa là các loại nông sản từ miền trung Thái Lan có thể đến tay người tiêu dùng ở Trung Quốc bằng tàu cao tốc trong vài ngày. Tuy nhiên, Bộ Thương mại Thái Lan vẫn chưa điều chỉnh tăng dự báo xuất khẩu sầu riêng sang Trung Quốc vì đang lo ngại về suy thoái ở nền kinh tế số hai thế giới và khả năng canh tác bất lợi từ hiện tượng El Nino. Các nhà sản xuất và kinh do sầu riêng lo ngại thời tiết khô hạn do El Nino gây ra có thể hạn chế sản lượng và ảnh hưởng đến xuất khẩu. \"Tôi lo lắng cả về cung và cầu, vì sản lượng sầu riêng đang giảm và người mua ở Trung Quốc bắt đầu hạn chế đặt hàng\", Somchai Chongsri, một người trồng và kinh doanh sầu riêng ở tỉnh Chanthaburi, Thái Lan cho biết.Một rủi ro khác là chất lượng. Tuần trước, cơ quan hải quan Trung Quốc đã từ chối 29 container chứa 300 tấn sầu riêng Thái Lan sau khi phát hiện quả đã bị thối do sâu bướm vàng, một loại sâu bệnh phổ biến. Vụ việc khiến ngành sầu riêng Thái Lan lo ngại khả năng Trung Quốc suy giảm niềm tin và chuyển sang đặt hàng nhiều hơn từ các nước khác.','http://res.cloudinary.com/dbkikuoyy/image/upload/v1693732390/i3ij33ej4hejmswkehge.jpg','REJECT',1,'https://vnexpress.net/sau-rieng-thai-lan-o-at-vao-trung-quoc-nho-tau-cao-toc-4648985.html','2023-09-03 09:13:08','2023-09-03 16:02:08',2,2),(2,'Sầu riêng Thái Lan ồ ạt vào Trung Quốc nhờ tàu cao tốc','Xuất khẩu sầu riêng Thái Lan sang Trung Quốc tăng mạnh từ khi tuyến đường sắt cao tốc nối Lào và nền kinh tế số 2 thế giới vận hành. Theo Bộ Thương mại Thái Lan, xuất khẩu sầu riêng của nước này sang Trung Quốc đạt 446.152 tấn trong 5 tháng đầu năm nay, tăng 58% so với cùng kỳ năm ngoái (281.528 tấn). Một trong các lý do được người trong ngành chỉ ra là nhờ tuyến đường sắt cao tốc Lào - Trung Quốc giúp rút ngắn thời gian vận chuyển. \"Người tiêu dùng Trung Quốc ưa thích sầu riêng Thái từ lâu, nhưng xuất khẩu chỉ tăng vọt kể từ năm ngoái khi tàu cao tốc bắt đầu hoạt động\", bà Arada Fuangtong, Phó cục trưởng Cục xúc tiến thương mại quốc tế của Bộ Thương mại Thái Lan nói. Năm 2022, nước này xuất khẩu kỷ lục 700.000 tấn sầu riêng, với 90% là đến Trung Quốc. Tàu cao tốc Trung Quốc - Lào bắt đầu chạy vào tháng 12/2021, tạo cơ hội cho hàng hóa Thái Lan đi qua biên giới vào thủ đô Viêng Chăn của Lào để vận chuyển đường sắt đến thành phố Côn Minh phía nam Trung Quốc. Tuyến đường sắt này dài 1.000 km, giúp thương nhân Thái Lan giảm thời gian giao hàng đến Trung Quốc xuống còn 15 giờ thay vì hai ngày khi vận chuyển bằng xe tải như trước đây. Cửa khẩu Nong Khai, nơi tuyến đường sắt cao tốc Trung Quốc - Lào, đi qua đã chứng kiến nông sản Thái Lan thông quan tăng vọt, theo Rachada Dhnadirek, Phó phát ngôn viên Văn phòng Thủ tướng Thái Lan. Trong 5 tháng đầu năm, sầu riêng tươi của nước này đi qua Nong Khai có kim ngạch cao nhất trong các mặt hàng, đạt hơn 2 tỷ baht (hơn 57 triệu USD), tăng 364% so với cùng kỳ năm 2022. Bà Arada cho biết việc rút ngắn thời gian vận chuyển đã thúc đẩy xuất khẩu sầu riêng Thái Lan tăng mạnh và cũng mang lại cơ hội lớn cho các loại trái cây, rau quả và các sản phẩm dễ hư hỏng khác của Thái Lan cần đến tay người tiêu dùng Trung Quốc mà vẫn đảm bảo độ tươi, chất lượng tốt. Auramon Supthaweethum, Tổng giám đốc Cục Đàm phán Thương mại Thái Lan, nhấn mạnh tác động mang tính chuyển đổi khi các chuyến hàng qua cảng khô phía đông bắc Nong Khai đã tăng từ 90,41 triệu baht thời điểm tuyến đường sắt này ra mắt tháng 12/2021 lên 1,96 tỷ baht vào năm ngoái. Bộ Thương mại Thái Lan đang khuyến khích các nhà xuất khẩu, đặc biệt là trái cây và rau quả, chuyển sang tận dụng tuyến đường sắt Trung Quốc - Lào, đặc biệt khi giá dầu tương đối cao đang đẩy chi phí vận tải đường bộ leo thang. Thunkanon Tiewsuwan, một thương nhân xuất khẩu sầu riêng cho hay thường phải mất 10 đến 12 ngày để chất sầu riêng lên xe tải, chở đến cảng biển, chuyển vào container vận chuyển và chất lên tàu. Các tàu này thường mất thêm một tuần nữa để đến các cảng quan trọng của Trung Quốc. \"Tôi đang nghĩ đến việc chuyển sang sử dụng tàu cao tốc\", người này nói. Sầu riêng ngày càng trở thành nông sản xuất khẩu quan trọng của Thái Lan. Năm ngoái, kim ngạch của trái cây này là 110 tỷ baht (3,1 tỷ USD), gần bằng mức 130 tỷ baht mà Thái Lan thu được từ xuất khẩu gạo. Xuất khẩu sầu riêng của Thái Lan có thể còn nhanh hơn nữa trong tương lai khi nước này có kế hoạch xây dựng tuyến đường sắt cao tốc của riêng mình nối Bangkok đến thành phố Nong Khai gần biên giới với Lào. Đoạn đầu tiên dài 253 km từ Bangkok đến thành phố Nakhon Ratchasima đã bắt đầu thi công năm ngoái và dự kiến khai thác thương mại năm 2026. Đoạn hai dài 355 km từ Nakhon Ratchasima đến Nong Khai sẽ hoàn thành vào năm 2028, nghĩa là các loại nông sản từ miền trung Thái Lan có thể đến tay người tiêu dùng ở Trung Quốc bằng tàu cao tốc trong vài ngày. Tuy nhiên, Bộ Thương mại Thái Lan vẫn chưa điều chỉnh tăng dự báo xuất khẩu sầu riêng sang Trung Quốc vì đang lo ngại về suy thoái ở nền kinh tế số hai thế giới và khả năng canh tác bất lợi từ hiện tượng El Nino. Các nhà sản xuất và kinh do sầu riêng lo ngại thời tiết khô hạn do El Nino gây ra có thể hạn chế sản lượng và ảnh hưởng đến xuất khẩu. \"Tôi lo lắng cả về cung và cầu, vì sản lượng sầu riêng đang giảm và người mua ở Trung Quốc bắt đầu hạn chế đặt hàng\", Somchai Chongsri, một người trồng và kinh doanh sầu riêng ở tỉnh Chanthaburi, Thái Lan cho biết.Một rủi ro khác là chất lượng. Tuần trước, cơ quan hải quan Trung Quốc đã từ chối 29 container chứa 300 tấn sầu riêng Thái Lan sau khi phát hiện quả đã bị thối do sâu bướm vàng, một loại sâu bệnh phổ biến. Vụ việc khiến ngành sầu riêng Thái Lan lo ngại khả năng Trung Quốc suy giảm niềm tin và chuyển sang đặt hàng nhiều hơn từ các nước khác.','http://res.cloudinary.com/dbkikuoyy/image/upload/v1693748111/qeyyobpkmczwokqs9ool.jpg','DRAFT',1,'https://vnexpress.net/sau-rieng-thai-lan-o-at-vao-trung-quoc-nho-tau-cao-toc-4648985.html','2023-09-03 13:35:08','2023-09-03 13:35:08',2,2);
/*!40000 ALTER TABLE `article` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `article_tag`
--

DROP TABLE IF EXISTS `article_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `tag_id` bigint NOT NULL,
  `article_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_news_tag_tag1_idx` (`tag_id`),
  KEY `fk_news_tag_artical1_idx` (`article_id`),
  CONSTRAINT `fk_news_tag_artical1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `fk_news_tag_tag1` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `article_tag`
--

LOCK TABLES `article_tag` WRITE;
/*!40000 ALTER TABLE `article_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `article_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_confirmed` tinyint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  KEY `fk_authors_user1_idx` (`user_id`),
  CONSTRAINT `fk_authors_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authors`
--

LOCK TABLES `authors` WRITE;
/*!40000 ALTER TABLE `authors` DISABLE KEYS */;
INSERT INTO `authors` VALUES (2,'Sơn Nguyễn',0,'2023-09-03 09:12:32','2023-09-03 09:12:32',2);
/*!40000 ALTER TABLE `authors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookmark` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` timestamp NULL DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `article_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_bookmark_user1_idx` (`user_id`),
  KEY `fk_bookmark_artical1_idx` (`article_id`),
  CONSTRAINT `fk_bookmark_artical1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `fk_bookmark_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark`
--

LOCK TABLES `bookmark` WRITE;
/*!40000 ALTER TABLE `bookmark` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `is_active` tinyint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (2,'Kinh tế','Tổng hợp những tin tức về kinh tế mới nhất dành cho người đọc',0,'2023-09-02 13:53:40','2023-09-02 13:53:40');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_unicode_ci,
  `active` tinyint DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `parent_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `article_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_comment_comment1_idx` (`parent_id`),
  KEY `fk_comment_user1_idx` (`user_id`),
  KEY `fk_comment_artical1_idx` (`article_id`),
  CONSTRAINT `fk_comment_artical1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `fk_comment_comment1` FOREIGN KEY (`parent_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `fk_comment_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `databasechangeloglock`
--

DROP TABLE IF EXISTS `databasechangeloglock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `databasechangeloglock` (
  `ID` int NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `databasechangeloglock`
--

LOCK TABLES `databasechangeloglock` WRITE;
/*!40000 ALTER TABLE `databasechangeloglock` DISABLE KEYS */;
INSERT INTO `databasechangeloglock` VALUES (1,_binary '\0',NULL,NULL);
/*!40000 ALTER TABLE `databasechangeloglock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_unicode_ci,
  `type` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_sent` tinyint DEFAULT NULL,
  `sent_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN'),(4,'ROLE_APPROVER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (2,'Xã hội');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `is_active` tinyint DEFAULT NULL,
  `full_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_of_birth` timestamp NULL DEFAULT NULL,
  `address` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password_reset_token` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'quangtoi','$2a$10$1B.ItNMwVXmkhU4N2h1HNejzErdRZA.Q4AxGnJ3gzSnZTsmLj7X.6','http://res.cloudinary.com/dbkikuoyy/image/upload/v1693661379/g4iohbhmkkpju5cujxfj.jpg',1,'Lê Quang Tới','2002-09-25 00:00:00','54, Dương Cát Lợi, Nhà Bè','toiquangle@gmail.com','1KjADeSob7Ab9x9RwkfA56DRYtBcMp9m9CoOVs0OPyUXf','2023-09-02 13:29:35','2023-09-03 14:32:43');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_notification`
--

DROP TABLE IF EXISTS `user_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `notification_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_notification_notification1_idx` (`notification_id`),
  KEY `fk_user_notification_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_notification_notification1` FOREIGN KEY (`notification_id`) REFERENCES `notification` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_notification_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_notification`
--

LOCK TABLES `user_notification` WRITE;
/*!40000 ALTER TABLE `user_notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_role_user_idx` (`user_id`),
  KEY `fk_user_role_role1_idx` (`role_id`),
  CONSTRAINT `fk_user_role_role1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,2,1),(2,2,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-04 14:48:23
