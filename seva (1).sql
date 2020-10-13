-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 08, 2020 at 03:58 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `seva`
--

-- --------------------------------------------------------

--
-- Table structure for table `donations`
--

CREATE TABLE `donations` (
  `id` int(10) NOT NULL,
  `donation_id` int(10) NOT NULL,
  `donation_quantity` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `donation_brief` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `image_name` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `donation_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `food_status` varchar(5) COLLATE utf8_unicode_ci NOT NULL,
  `collectorID` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`id`, `donation_id`, `donation_quantity`, `donation_brief`, `image_name`, `donation_date`, `food_status`, `collectorID`) VALUES
(2, 4, '13', 'this is for checking purpose 2', '1591091836029_lobster.png', '2020-06-02 09:57:16', 'yes', 5),
(2, 10, '2', 'food for kids', '1591107447088_choc3.jpg', '2020-06-02 14:17:27', 'yes', 6),
(1, 11, '22', 'marriage food', '1591108187541_IMG-20200602-WA0037.jpg', '2020-06-02 14:29:47', 'yes', 6),
(1, 12, '1', 'mess food', '1591109137372_IMG-20200602-WA0028.jpg', '2020-06-02 14:45:37', 'yes', 5),
(1, 13, '21', 'food from dharm charity', '1591109354021_IMG-20200602-WA0025.jpg', '2020-06-02 14:49:14', 'yes', 5),
(1, 14, '12', 'testing 4', '1591161874175_choc3.jpg', '2020-06-03 05:24:34', 'yes', 5),
(1, 17, '41', 'testing 4', '1591173880707_choc2.jpg', '2020-06-03 08:44:40', 'yes', 5),
(1, 18, '8', 'mess food for 10 people', '1591384119782_IMG-20200604-WA0005.jpg', '2020-06-05 19:08:39', 'yes', 6),
(3, 19, '70', 'mess food wasted', 'chock.png', '2020-06-05 20:29:29', 'yes', 6),
(4, 20, '25', 'food for animals', 'chock.png', '2020-06-06 06:18:41', 'yes', 5),
(1, 22, '5', 'mess waste food', '1591524713124_food3.jpg', '2020-06-07 10:11:53', 'yes', 5),
(8, 23, '1', 'maggi', '1591525939122_food1.jpg', '2020-06-07 10:32:19', 'yes', 5),
(1, 24, '17', 'Food for dogs', '1591613424290_food1.jpg', '2020-06-08 10:50:24', 'yes', 5);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  `contact` varchar(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `user_type` varchar(10) NOT NULL,
  `token_key` varchar(35) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `name`, `contact`, `username`, `password`, `user_type`, `token_key`) VALUES
(1, 'Ritu Sharma', '7893600001', 'ritu123', '123@ritu', 'd', 'QrvcvBuu9dLGXddj$159161360581024'),
(2, 'Yash sharma', '7892211456', 'yash123', 'yash123', 'd', 'HQHgTs7Mv0s6TTWfA1591519192808K6'),
(3, 'Jagdish jain', '7892219996', 'jagdish23', 'jag23', 'd', 'LwT2CphubzE7QgAPa1590669497037C6'),
(4, 'sumit khan', '9892279596', 'sumit20', 'sum@123', 'd', 'YRJ5zsVBGrunLf8GO1591519107332H5'),
(5, 'Ved Animal shelter', '8475588469', 'ved123', 'Ved@123', 'c', 'vu@syifqCEAR4@Mjw1591613453592w6'),
(6, 'Shitla OldAge Home', '8904466321', 'shitla', 'shitla123', 'c', '$lwysjHr3E3etS3u1591519890366N8'),
(8, 'vishal kanojia', '9833989435', 'vishaaaal', 'Vsk249@', 'd', 'gdXIo0PXVmkTbYC5$1591526091484s6');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `donations`
--
ALTER TABLE `donations`
  ADD PRIMARY KEY (`donation_id`),
  ADD KEY `fk_foreign_key_name` (`id`),
  ADD KEY `fk_foreign_key_name1` (`collectorID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `donations`
--
ALTER TABLE `donations`
  MODIFY `donation_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `donations`
--
ALTER TABLE `donations`
  ADD CONSTRAINT `fk_foreign_key_name` FOREIGN KEY (`id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `fk_foreign_key_name1` FOREIGN KEY (`collectorID`) REFERENCES `users` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
