-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 02, 2020 at 07:06 AM
-- Server version: 10.3.16-MariaDB
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id13862847_seva`
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
  `image_name` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `donation_date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `donations`
--

INSERT INTO `donations` (`id`, `donation_id`, `donation_quantity`, `donation_brief`, `image_name`, `donation_date`) VALUES
(1, 1, '3', 'this is for checking purpose', '1590951630862_lobster.png', '2020-05-31 19:00:30'),
(2, 2, '15', 'Marriage food ', 'food.png', '2020-06-01 19:32:42'),
(2, 3, '15', 'Marriage food ', 'food.png', '2020-06-01 19:32:48');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(10) NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `contact` varchar(11) COLLATE utf8_unicode_ci NOT NULL,
  `username` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `user_type` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `token_key` varchar(35) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `name`, `contact`, `username`, `password`, `user_type`, `token_key`) VALUES
(1, 'Varun Sharma', '7129911090', 'vaun123', 'varun@123', 'd', 'AK$BUh8HgFEjT$3UD1591018869582i7'),
(2, 'vasant yadav', '8118836470', 'vasant12', 'vas@12', 'd', 'V8IeEtu2dBgzfOSOT1590821807137M1'),
(3, 'Nikhil Sharma', '9633858456', 'nikhil123', 'nik@123', 'c', '4eMoliLDftqqCLUcm1590995435977J3'),
(4, 'Ritu Animal Shelter', '8406699123', 'ritu@a@s', 'Rit@12', 'c', 'cKJaRr8p#gTO366j31590822441397A6'),
(5, 'Priya duggal', '9694800076', 'priya123', 'Priya123@', 'd', 'AaYpfwkWadkoOsFIA1590911336703S4'),
(6, 'Kavita karde', '9833659740', 'kavita12', 'Kavi@12', 'd', 'Wi4aMAayQxIxvUSy1159091243102641'),
(7, 'Rahul dey', '8960099420', 'Rahul12', 'Rahul12@', 'd', 'Mth6SfKARCsKNemDC1590927922816$6'),
(8, 'pooja kanojia', '8108811590', 'pooja12', 'Pooja@12', 'd', 'btTCx36nxR0S3IgiR1590991399258j1'),
(9, '', '', 'Ajay123', 'Ajay123', 'd', 'F55e1iKkthmD$kby51590995074187P5'),
(11, 'jeevan mehta', '', 'jeevan', 'Jeevan12@', 'd', '0VR1WmxJYPorGNH5P1590993194539@9');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `donations`
--
ALTER TABLE `donations`
  ADD PRIMARY KEY (`donation_id`);

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
  MODIFY `donation_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
