-- MySQL dump 10.17  Distrib 10.3.23-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: meal_plan_db
-- ------------------------------------------------------
-- Server version	10.3.23-MariaDB-1:10.3.23+maria~bionic-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart_ingredients`
--

DROP TABLE IF EXISTS `cart_ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart_ingredients` (
  `cart_id` varchar(255) NOT NULL,
  `ingredient_id` varchar(255) NOT NULL,
  KEY `FK9ygf82aesvgqk3egvinebvemh` (`ingredient_id`),
  KEY `FKgvujpe9v5n64u2i1nyi1i1aiy` (`cart_id`),
  CONSTRAINT `FK9ygf82aesvgqk3egvinebvemh` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`id`),
  CONSTRAINT `FKgvujpe9v5n64u2i1nyi1i1aiy` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_ingredients`
--

LOCK TABLES `cart_ingredients` WRITE;
/*!40000 ALTER TABLE `cart_ingredients` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carts` (
  `id` varchar(255) NOT NULL,
  `ingredient_amount` int(11) NOT NULL,
  `ingredient_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK26w7ukbh93wfow1op29w8bkab` (`ingredient_id`),
  CONSTRAINT `FK26w7ukbh93wfow1op29w8bkab` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredient_prices`
--

DROP TABLE IF EXISTS `ingredient_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingredient_prices` (
  `id` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `supplier_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaf8691sy3r67n16rpamjqon9a` (`supplier_id`),
  CONSTRAINT `FKaf8691sy3r67n16rpamjqon9a` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredient_prices`
--

LOCK TABLES `ingredient_prices` WRITE;
/*!40000 ALTER TABLE `ingredient_prices` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingredient_prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ingredients` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
INSERT INTO `ingredients` VALUES ('03423d4a-71c0-4067-8dfe-fcfc8c10b3fc','basil'),('05ed106f-6d4b-41d8-be6b-7c99418fc6b3','puff pastry'),('064f747f-0287-462f-b87b-25cdfff139fb','string cheese'),('08bee8ee-ce8a-4b02-a0c7-ae0b2d1fd7b2','doamt'),('0bda5324-45f7-459d-a472-fb8e209a135d','farfalle pasta'),('156bfef3-c34e-4013-a905-39bbf9764873','asdas'),('1784cc82-885d-409d-8e31-cf40e1861ec0','scallions'),('184ef828-7e44-4082-b496-021ea4b17fae','onion'),('1b156e6d-65fb-4467-87a1-2ef9b85ea7c6','bread crumbs'),('209406f2-4a86-491a-ad03-a6207f2a2f8a','rolled oats'),('3722620b-2885-44d6-973c-603505d16be4','oil'),('4192e183-d7d7-4efb-9091-10b02eb85c63','egg roll wrappers'),('44d84dea-4fcb-41b9-ae2f-fc3c7205c23b','tomatoes'),('4a308ad8-8abe-41ac-adff-6215ca53a8da','chicken breasts'),('5fee8c8a-339e-4adb-8655-d73fbecb62bc','broccoli'),('6e6c49c1-5b01-4ec1-b033-215af67e00b2','eggs'),('767a0dfb-b984-4d12-8b31-342678431165','milk'),('76dc10a6-4e73-4560-a5e3-061c7d17f441','extra-virgin olive oil'),('80dc8f8a-5468-41cd-8d64-ce753bd97ea5','water'),('8f3e8a37-b1c9-41e7-9055-bd7ab9597fc5','BBQ sauce'),('956ded17-55cc-4522-8e28-ed9d5b0f9324','banana'),('a2031d13-7561-4d73-8128-ad8220158786','chicken breast'),('a6d84cdb-f0d5-48d2-8c90-4f9d59d885f9','hot dogs'),('a7dfdc80-4fd0-4de6-8471-fe2685ab2aa5','ground turkey'),('bd379984-3d98-4841-bb05-d4255d526ccf','pepper'),('bff52c0c-1e41-4673-aba6-a24226d5e249','lasagne strips'),('c033b652-4ec9-49eb-86a6-b36021a5d7b7','sugar'),('c490c054-3a74-458c-b9f6-37cbbcdd4094','parsley'),('c49d22df-86ac-422c-a256-fd079504cf52','Roma tomatoes'),('d3c1d1ae-dbba-4e14-813a-037aadde5b0e','salsa'),('dfc6dce9-35d9-404e-9873-21a3b21b790b','cheddar cheese'),('e5276692-db1d-4595-a824-60cc0314d730','zuchini'),('e70a0da4-62c4-45d8-a95f-ab77580f59b3','garlic'),('f26046fc-65ae-4c1d-87b1-d72389111fa7','sudjuk'),('f7f704fd-514a-4348-8773-d1e10e775f67','carrot'),('fcfad169-3328-4e2a-9334-bb0ab5f22742','cucumber'),('ffe41f23-0d0f-48e1-bdd8-6d3b7b8089a1','sweet potatoes');
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` varchar(255) NOT NULL,
  `delivery_date` datetime(6) NOT NULL,
  `order_date` datetime(6) NOT NULL,
  `price` decimal(19,2) NOT NULL,
  `supplier_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKg2540vs5sg5b0uov81t6p0229` (`supplier_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKg2540vs5sg5b0uov81t6p0229` FOREIGN KEY (`supplier_id`) REFERENCES `suppliers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `planned_meals`
--

DROP TABLE IF EXISTS `planned_meals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `planned_meals` (
  `id` varchar(255) NOT NULL,
  `planned_date` datetime(6) NOT NULL,
  `portions` int(11) NOT NULL,
  `recipe_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl1lyl8ppjpolade7u2gld8fv9` (`recipe_id`),
  KEY `FKjdrkluiixcmpe32stik4kuk8b` (`user_id`),
  CONSTRAINT `FKjdrkluiixcmpe32stik4kuk8b` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKl1lyl8ppjpolade7u2gld8fv9` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `planned_meals`
--

LOCK TABLES `planned_meals` WRITE;
/*!40000 ALTER TABLE `planned_meals` DISABLE KEYS */;
INSERT INTO `planned_meals` VALUES ('0d71bf87-ce25-4c12-b676-77fc6956496a','2020-08-10 09:00:00.000000',2,'1d383a6a-7d04-4c6a-9f68-e76629cdc037','ea39ecc7-5949-4565-b2c3-0f101b12d92b'),('1ea0f9fd-6b84-4286-9ca3-653010d3d3e9','2020-08-10 16:00:00.000000',2,'88c8846b-a387-4d05-aed0-6bbe975e1bf8','3a313134-e004-4851-8c95-e764f7daf977'),('3bdd9ccd-d101-44a3-9a90-b343c0d42823','2020-08-10 12:00:00.000000',2,'65d02b84-8112-488f-b13e-146fcf7c3415','3a313134-e004-4851-8c95-e764f7daf977'),('46f8aa0f-0c9e-47f9-be61-a26cd1d8185a','2020-08-10 04:00:00.000000',2,'1d383a6a-7d04-4c6a-9f68-e76629cdc037','3a313134-e004-4851-8c95-e764f7daf977'),('55fee985-91ff-433d-a86c-d6ccc5802013','2020-08-10 09:00:00.000000',2,'f3a17bfe-0323-4893-a4f5-0f926b1f112f','3a313134-e004-4851-8c95-e764f7daf977'),('57a98259-b530-49fe-b78b-34be112cc387','2020-08-11 04:00:00.000000',2,'e2e65568-e4f4-4a77-9d60-b8601e48aeff','3a313134-e004-4851-8c95-e764f7daf977'),('5c1609ea-fdfc-40f6-8e06-7140b84364f0','2020-08-12 09:00:00.000000',2,'f96e82d4-3934-4a37-85fb-9a93356be047','3a313134-e004-4851-8c95-e764f7daf977'),('600b4eae-8675-4ab5-97f3-ecaac21c2cd0','2020-08-10 09:00:00.000000',2,'5e56fbed-b789-4e20-90ee-318206b8ac08','3a313134-e004-4851-8c95-e764f7daf977'),('636e4202-c848-45c3-b656-a051dc78b939','2020-08-10 16:00:00.000000',2,'fa75f50e-1b29-4794-b68d-06b8530fad34','3a313134-e004-4851-8c95-e764f7daf977'),('cf398898-02f1-47ca-a7ad-94da90206868','2020-08-12 04:00:00.000000',2,'f0491ec1-0d1e-4427-8c26-d1c777535396','3a313134-e004-4851-8c95-e764f7daf977'),('d0568ff3-6e6a-4a93-a6e0-861795160fde','2020-08-11 04:00:00.000000',1,'1d383a6a-7d04-4c6a-9f68-e76629cdc037','ea39ecc7-5949-4565-b2c3-0f101b12d92b'),('dac785ea-cfe2-4eb9-97ca-76391504299b','2020-08-11 16:00:00.000000',2,'fa75f50e-1b29-4794-b68d-06b8530fad34','3a313134-e004-4851-8c95-e764f7daf977');
/*!40000 ALTER TABLE `planned_meals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_ingredient`
--

DROP TABLE IF EXISTS `recipe_ingredient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_ingredient` (
  `id` varchar(255) NOT NULL,
  `amount` int(11) NOT NULL,
  `ingredient_id` varchar(255) DEFAULT NULL,
  `recipe_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbult6ksqva3bk1cg64fgl0l00` (`ingredient_id`),
  KEY `FK6fyh7xtjni8tgso26h8ek5b96` (`recipe_id`),
  CONSTRAINT `FK6fyh7xtjni8tgso26h8ek5b96` FOREIGN KEY (`recipe_id`) REFERENCES `recipes` (`id`),
  CONSTRAINT `FKbult6ksqva3bk1cg64fgl0l00` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_ingredient`
--

LOCK TABLES `recipe_ingredient` WRITE;
/*!40000 ALTER TABLE `recipe_ingredient` DISABLE KEYS */;
INSERT INTO `recipe_ingredient` VALUES ('038f07e8-bb12-474e-a031-b45ed5182d63',120,'1b156e6d-65fb-4467-87a1-2ef9b85ea7c6','bd787a73-8f89-46c0-aa5c-9f097b047a15'),('04b27672-4c6a-434b-8860-eee33f425233',5,'4192e183-d7d7-4efb-9091-10b02eb85c63','88c8846b-a387-4d05-aed0-6bbe975e1bf8'),('061d7d4e-e6c4-4adf-8918-06466472e59b',6,'c49d22df-86ac-422c-a256-fd079504cf52','65d02b84-8112-488f-b13e-146fcf7c3415'),('0a4204d1-7e64-45da-b2a6-d57c7b6a6755',3,'e70a0da4-62c4-45d8-a95f-ab77580f59b3','f3a17bfe-0323-4893-a4f5-0f926b1f112f'),('31b50487-9b53-4304-a830-3e01e8248eda',175,'dfc6dce9-35d9-404e-9873-21a3b21b790b','f3a17bfe-0323-4893-a4f5-0f926b1f112f'),('3632859f-dfdb-413e-bf22-31c796dee954',1000,'a7dfdc80-4fd0-4de6-8471-fe2685ab2aa5','1d383a6a-7d04-4c6a-9f68-e76629cdc037'),('3aeaa254-39b8-4899-8530-ee9bb2d8dcde',1,'05ed106f-6d4b-41d8-be6b-7c99418fc6b3','fa75f50e-1b29-4794-b68d-06b8530fad34'),('4023c23a-1cea-45a2-9a69-5dbc59cd06ad',290,'8f3e8a37-b1c9-41e7-9055-bd7ab9597fc5','bd787a73-8f89-46c0-aa5c-9f097b047a15'),('40e26e6b-fd3a-4d6a-8983-ee63af868b5d',3,'5fee8c8a-339e-4adb-8655-d73fbecb62bc','f3a17bfe-0323-4893-a4f5-0f926b1f112f'),('43a9ba27-0aae-477e-b96a-896b25da6db4',60,'dfc6dce9-35d9-404e-9873-21a3b21b790b','fa75f50e-1b29-4794-b68d-06b8530fad34'),('55b85459-ae54-4933-8de9-4ecfb7c27638',2,'ffe41f23-0d0f-48e1-bdd8-6d3b7b8089a1','5e56fbed-b789-4e20-90ee-318206b8ac08'),('5a8839a8-57d6-42e9-940e-a5578f1f7081',240,'767a0dfb-b984-4d12-8b31-342678431165','f0491ec1-0d1e-4427-8c26-d1c777535396'),('5f434cbb-6b15-4aca-be2d-376bc8d8732f',10,'064f747f-0287-462f-b87b-25cdfff139fb','88c8846b-a387-4d05-aed0-6bbe975e1bf8'),('6b6e1f3b-0afb-4d49-9cb9-265abc4f1bb8',200,'4a308ad8-8abe-41ac-adff-6215ca53a8da','5e56fbed-b789-4e20-90ee-318206b8ac08'),('73bc18ea-fdfa-45b9-8bbe-fa1f533f2bd9',100,'3722620b-2885-44d6-973c-603505d16be4','88c8846b-a387-4d05-aed0-6bbe975e1bf8'),('7a6c7205-b7db-4b6c-b35a-382ba874ec81',20,'c490c054-3a74-458c-b9f6-37cbbcdd4094','65d02b84-8112-488f-b13e-146fcf7c3415'),('7abf5a1a-b35b-4796-8744-859a314cbfea',2,'6e6c49c1-5b01-4ec1-b033-215af67e00b2','e2e65568-e4f4-4a77-9d60-b8601e48aeff'),('87850be2-4036-45f8-a1ca-3d635bec4b48',6,'a6d84cdb-f0d5-48d2-8c90-4f9d59d885f9','fa75f50e-1b29-4794-b68d-06b8530fad34'),('893a034b-7cb4-490c-a85d-13d1cd7222e5',4,'44d84dea-4fcb-41b9-ae2f-fc3c7205c23b','f96e82d4-3934-4a37-85fb-9a93356be047'),('8b06f70f-c32e-41f4-b927-c2daa0def790',400,'0bda5324-45f7-459d-a472-fb8e209a135d','f96e82d4-3934-4a37-85fb-9a93356be047'),('9c4bc382-7006-49fe-adce-908540458e80',100,'80dc8f8a-5468-41cd-8d64-ce753bd97ea5','88c8846b-a387-4d05-aed0-6bbe975e1bf8'),('9f75a319-061a-4dc8-918e-4d0a1118516b',2,'956ded17-55cc-4522-8e28-ed9d5b0f9324','e2e65568-e4f4-4a77-9d60-b8601e48aeff'),('a1df2bbc-cf4e-4981-9ef0-c3577bb53eeb',6,'6e6c49c1-5b01-4ec1-b033-215af67e00b2','1d383a6a-7d04-4c6a-9f68-e76629cdc037'),('aaa54936-9aac-41d3-b978-2b2d80edc819',50,'8f3e8a37-b1c9-41e7-9055-bd7ab9597fc5','5e56fbed-b789-4e20-90ee-318206b8ac08'),('ad6aac85-c483-4146-a6f1-f120e44e6ca4',1,'956ded17-55cc-4522-8e28-ed9d5b0f9324','f0491ec1-0d1e-4427-8c26-d1c777535396'),('c49d6632-6084-47ae-be54-fedfbb63f5fa',50,'209406f2-4a86-491a-ad03-a6207f2a2f8a','f0491ec1-0d1e-4427-8c26-d1c777535396'),('cf812c3b-9422-46be-8b5b-5a9600279a1b',50,'76dc10a6-4e73-4560-a5e3-061c7d17f441','f96e82d4-3934-4a37-85fb-9a93356be047'),('d5aa7274-bcb5-4080-86bf-bcf8342b814f',200,'d3c1d1ae-dbba-4e14-813a-037aadde5b0e','1d383a6a-7d04-4c6a-9f68-e76629cdc037'),('e5205ca8-85b9-4805-af22-442a87078e00',1,'a2031d13-7561-4d73-8128-ad8220158786','bd787a73-8f89-46c0-aa5c-9f097b047a15'),('faf3a189-bcec-4dff-bc08-938f654e64fe',1,'fcfad169-3328-4e2a-9334-bb0ab5f22742','65d02b84-8112-488f-b13e-146fcf7c3415'),('fd0f4391-411a-4127-9f14-0e4d38c90056',14,'1784cc82-885d-409d-8e31-cf40e1861ec0','f96e82d4-3934-4a37-85fb-9a93356be047');
/*!40000 ALTER TABLE `recipe_ingredient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipes` (
  `id` varchar(255) NOT NULL,
  `image` tinyblob DEFAULT NULL,
  `instructions` text NOT NULL,
  `name` varchar(255) NOT NULL,
  `portions_count` int(11) NOT NULL,
  `author_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhcd6j9baovkdrh8gu9my8lco5` (`author_id`),
  CONSTRAINT `FKhcd6j9baovkdrh8gu9my8lco5` FOREIGN KEY (`author_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
INSERT INTO `recipes` VALUES ('1d383a6a-7d04-4c6a-9f68-e76629cdc037',NULL,'Heat oil in a large skillet over medium heat. Once hot add ground meat and cook, stirring occasionally until no pink remains, about 8 minutes.\r\nAdd in salsa and mix to combine, let cook together until heated through, about 3 minutes.\r\nCrack in 6 eggs and cover skillet until the eggs are cooked through to your desired consistency, about 5-8 minutes.','BREAKFAST SKILLET',4,'3a313134-e004-4851-8c95-e764f7daf977'),('5e56fbed-b789-4e20-90ee-318206b8ac08',NULL,'Preheat oven to 425 degrees F. Lay sweet potatoes cut side up on a large baking sheet. Roast until tender, about 35 minutes, depending on size of your potatoes.\r\nIn a saucepan set to medium-low, stir together chicken and BBQ sauce. Heat until warm, 5-10 minutes.\r\nTop each potato with scoopfuls of chicken. Spoon over additional BBQ sauce and sprinkle with chopped parsley or sliced green onions, if desired.\r\nEnjoy!','BBQ Stuffed Sweet Potatoes',2,'3a313134-e004-4851-8c95-e764f7daf977'),('65d02b84-8112-488f-b13e-146fcf7c3415',NULL,'Place the diced tomatoes, cucumbers and parsley in a large salad bowl. Add salt and set aside for 4 minutes or so.\r\nAdd salt and pepper and give the salad a gentle toss. Allow the flavors a few minutes to meld before serving. Enjoy!','Mediterranean Salad',1,'3a313134-e004-4851-8c95-e764f7daf977'),('88c8846b-a387-4d05-aed0-6bbe975e1bf8',NULL,'Slice the egg roll wrappers in half.\r\nPlace a stick of string cheese on the edge of the egg roll wrapper, then fold the sides of the wrapper over the ends of the cheese.\r\nWet the other edge of the wrapper with water, then roll it up tightly, pressing the edge to make sure it’s sealed.\r\nRepeat with the remaining wrappers and cheese.\r\nHeat oil in a pot.\r\nFry the sticks until golden brown, then drain on a paper towel.\r\nServe with marinara.\r\nEnjoy!','Egg Roll Mozzarella Sticks',10,'3a313134-e004-4851-8c95-e764f7daf977'),('bd787a73-8f89-46c0-aa5c-9f097b047a15',NULL,'Preheat oven to 375°F (190°C).\r\nSlice the chicken into even strips.\r\nDip the chicken strips into the BBQ sauce, then coat them in the bread crumbs.\r\nPlace on a baking sheet lined with parchment paper and bake for 15 minutes.\r\nBrush the remaining BBQ sauce evenly on both sides of the chicken strips and bake for another 10 minutes and serve.\r\nEnjoy!','BBQ Chicken Tenders',6,'3a313134-e004-4851-8c95-e764f7daf977'),('e2e65568-e4f4-4a77-9d60-b8601e48aeff',NULL,'Smash the bananas and mix with the eggs. Grease a pan with coconut oil and pour with a spoon in the pan. Bake from both sides until ready. Enjoy with maple syrup. ','Easy Banana Pancakes',2,'3a313134-e004-4851-8c95-e764f7daf977'),('f0491ec1-0d1e-4427-8c26-d1c777535396',NULL,'Add rolled oats to a blender and blend until the oats are the size of a fine crumb.\r\nAdd the banana and milk and blend well.\r\nPour in a glass.\r\nEnjoy!','3-Ingredient Banana Oat Smoothie',1,'3a313134-e004-4851-8c95-e764f7daf977'),('f3a17bfe-0323-4893-a4f5-0f926b1f112f',NULL,'Preheat oven to 375ºF (190ºC).\r\nSpread out broccoli in a medium rectangular casserole dish.\r\nStir the garlic and cheddar cheese together in a separate bowl.\r\nSprinkle the cheese evenly over broccoli and add salt and pepper to taste.\r\nBake for 25 minutes.\r\nEnjoy!','Cheesy Garlic Broccoli',6,'3a313134-e004-4851-8c95-e764f7daf977'),('f96e82d4-3934-4a37-85fb-9a93356be047',NULL,'In a food processor, pulse three-fourths of the tomatoes with the chopped scallions and the 1/4 cup of olive oil to a coarse puree; season with salt and pepper.\r\nLight a grill or preheat a grill pan. Drizzle the whole scallions with olive oil and season them with salt and pepper. Grill the scallions over high heat until lightly charred and tender, about 1 minute per side. Transfer the grilled scallions to a work surface and cut them into 1-inch pieces.\r\nIn a large pot of salted boiling water, cook the pasta until al dente. Drain and transfer to a large serving bowl. Add the tomato-scallion puree and the grilled scallions; toss well. Season with salt and pepper. Top the pasta with the remaining chopped tomatoes and serve.','Pasta with Fresh Tomatoes, Grilled Scallions',4,'3a313134-e004-4851-8c95-e764f7daf977'),('fa75f50e-1b29-4794-b68d-06b8530fad34',NULL,'Preheat oven to 425°F (220?C).\r\nSlice the puff pastry into 6 even rectangles. Place a slice of cheddar on top of each puff pastry piece.\r\nPlace a hot dog on the cheddar, then roll it up.\r\nSlice each roll into 3 even pieces, and arrange the pieces evenly on a baking sheet lined with parchment paper with a 1-inch (2-cm) gap between the pieces.\r\nBake for 15 minutes, until golden brown and flaky.\r\nEnjoy!','Pigs In A Blanket',1,'3a313134-e004-4851-8c95-e764f7daf977');
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` varchar(255) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('72c32289-07a2-469b-a845-ca1835663907','ROLE_USER'),('c6e51ba7-27dc-405d-9a02-a95b6725632a','ROLE_ADMIN'),('ccb98ece-c86e-4f6f-863c-34bb5186edb6','ROLE_ROOT');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suppliers` (
  `id` varchar(255) NOT NULL,
  `logo_url` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('1f481631-6b2c-40d9-9863-44b8b029da75','123','ivan@ivan','ivan','$2a$10$UwkOjq/5R7cFoj0WMieQxOyMBKAtwNj1WeudBKvOUfrlvpcw3Aa/K','ivan'),('2c5ee503-9df4-486d-beb3-cf504cfce0be','ggg','g@g','ggg','$2a$10$0e2frgP2XwwCYHbeZhaJL.PPjo4gpfyKoEf8IGdpZWOezTDQW7WMa','ggg'),('3a313134-e004-4851-8c95-e764f7daf977','123123123','123@123','Angelo Angelov Atanasov','$2a$10$ksI4xgoczi8MUVaHyYhcy.3BvdABru2STJ7zsoynGw.hlarhR7.GC','angelo'),('51018178-ac75-451d-a978-862e0aa75a03','ava','ava2@ava','ava','$2a$10$V0j5waMYKYNcbW2wF9.4qOYfISK2pxXuAnmxidhhRQ4zZOg0ZVP8u','ava2'),('53e90500-0011-41d9-88fa-c9044c5a5945','s','as@as','a','$2a$10$gxPX4dAsZeD3fR/NyAytkeC1KOmpw7vblc.BQTHvs8N9fpuM8MXJO','a'),('908ae7b2-d289-47eb-8073-7421f10913dc','eee','e@e','eee','$2a$10$fY/85ZVM0A1WK2ujsiwVseUHRox4YMOjZEpautDFx7m5fXPNTsuIi','eee'),('93462e16-e8e3-438a-8dad-4ff97270051e','fff','f@f','fff','$2a$10$EfFSBdlyc2gSIlfcbSfyWe6Suxjdl/bvQFaKdx1wsQjDHd0sGkqD6','fff'),('9891abaf-d2a9-4b1c-9046-802f3568c693','ddd','d@d','ddd','$2a$10$6qCrYTk3gFQW/uGByzFQvOLr/6Qi6MM7GfB2BQunW9kdfpN2T3F8G','ddd'),('ae018474-bfcd-4400-9721-a19e035dbc8c','ccc','ccc@ccc','ccc','$2a$10$2ql7q5C5EFXgsPrPrP6SfOp47EI1DhNzpp5rQDXMBM.1UHIL64lHu','ccc'),('c3b328b1-6aca-4e0c-8e35-1e6fe8e89a82','asdasd','sta@asdt','stamat2','$2a$10$JA8kPNH69yaT2SYayc9yrOlgSGG8MkzqKE1k90uXKFIbE.yChrbMS','stamat2'),('c778d78f-a6d9-472d-88a8-a057fe038dc6','aaa','a@a','aaa','$2a$10$m/Y2Bx530PmZlzbHSswKjeKz4dFbUI/2YrMfEucrWAPNyXNT3BfJ.','aaa'),('ca4e17d1-3e16-4f13-8932-04e799634e93','hhh','hhh@h','hhh','$2a$10$Qj.wurfU1Z7FjYcIEqH4ReNXGsL4QpSbeDUuKTnwVhOqZWZ8rfOzi','hhh'),('ea39ecc7-5949-4565-b2c3-0f101b12d92b','ava','ava@ava','ava','$2a$10$TF1GKkl2XVffK3jWkGWSeOsDnXTWrusa0gqY7NA47EYQVpQgFI6FG','ava'),('ef543aba-0d3e-4559-83b4-11d23f27d549','bbb','b@b','bbb','$2a$10$EjiHwxsOxp4wEbxkXhi6T.2Ofu432Py7TauK2ALQqUnaJOsUHpyQq','bbb');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`),
  CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES ('1f481631-6b2c-40d9-9863-44b8b029da75','72c32289-07a2-469b-a845-ca1835663907'),('1f481631-6b2c-40d9-9863-44b8b029da75','c6e51ba7-27dc-405d-9a02-a95b6725632a'),('2c5ee503-9df4-486d-beb3-cf504cfce0be','72c32289-07a2-469b-a845-ca1835663907'),('3a313134-e004-4851-8c95-e764f7daf977','72c32289-07a2-469b-a845-ca1835663907'),('3a313134-e004-4851-8c95-e764f7daf977','c6e51ba7-27dc-405d-9a02-a95b6725632a'),('3a313134-e004-4851-8c95-e764f7daf977','ccb98ece-c86e-4f6f-863c-34bb5186edb6'),('51018178-ac75-451d-a978-862e0aa75a03','72c32289-07a2-469b-a845-ca1835663907'),('53e90500-0011-41d9-88fa-c9044c5a5945','72c32289-07a2-469b-a845-ca1835663907'),('908ae7b2-d289-47eb-8073-7421f10913dc','72c32289-07a2-469b-a845-ca1835663907'),('93462e16-e8e3-438a-8dad-4ff97270051e','72c32289-07a2-469b-a845-ca1835663907'),('9891abaf-d2a9-4b1c-9046-802f3568c693','72c32289-07a2-469b-a845-ca1835663907'),('ae018474-bfcd-4400-9721-a19e035dbc8c','72c32289-07a2-469b-a845-ca1835663907'),('c3b328b1-6aca-4e0c-8e35-1e6fe8e89a82','72c32289-07a2-469b-a845-ca1835663907'),('c778d78f-a6d9-472d-88a8-a057fe038dc6','72c32289-07a2-469b-a845-ca1835663907'),('ca4e17d1-3e16-4f13-8932-04e799634e93','72c32289-07a2-469b-a845-ca1835663907'),('ea39ecc7-5949-4565-b2c3-0f101b12d92b','72c32289-07a2-469b-a845-ca1835663907'),('ef543aba-0d3e-4559-83b4-11d23f27d549','72c32289-07a2-469b-a845-ca1835663907');
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-09  8:45:11
