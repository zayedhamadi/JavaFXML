-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3305
-- Generation Time: Jun 16, 2023 at 12:18 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bdgestion`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `codeCli` varchar(20) NOT NULL,
  `nomCli` varchar(20) NOT NULL,
  `adresseCli` varchar(20) NOT NULL,
  `emailCli` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`codeCli`, `nomCli`, `adresseCli`, `emailCli`) VALUES
('10', 'zayedhamadi', 'Mourouj', 'zayedh80@gmail.com'),
('11', 'oussema', 'yasminet', 'oussema@gmail.com'),
('142', 'Monkey D Luffy', 'one piece', 'LuffyM@gmail.com'),
('2', 'ahmed', 'kef', 'ahmed@gmail.com'),
('24', 'semi', 'manzah', 'semi@gmail.com'),
('25', 'aziz', 'manzah', 'aziz@gmail.com'),
('5', 'imen', 'mourouj', 'imen@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `facture`
--

CREATE TABLE `facture` (
  `numFacture` varchar(30) NOT NULL,
  `dateFacture` date NOT NULL,
  `total` float NOT NULL,
  `refCli` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `facture`
--

INSERT INTO `facture` (`numFacture`, `dateFacture`, `total`, `refCli`) VALUES
('10', '2023-06-05', 10.23, '25'),
('2', '2023-06-05', 20.3, '142');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(20) NOT NULL,
  `email` varchar(20) NOT NULL,
  `password` varchar(1500) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `email`, `password`, `id`) VALUES
('zayed', 'zayedh80@gmail.com', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 10),
('hamzaaa', 'h@gmail.com', '0ffe1abd1a08215353c233d6e009613e95eec4253832a761af28ff37ac5a150c', 33),
('ahmed', 'ahmed@gmail.com', '9af2921d3fd57fe886c9022d1fcc055d53a79e4032fa6137e397583884e1a5de', 98);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`codeCli`);

--
-- Indexes for table `facture`
--
ALTER TABLE `facture`
  ADD PRIMARY KEY (`numFacture`),
  ADD KEY `code_Client` (`refCli`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `facture`
--
ALTER TABLE `facture`
  ADD CONSTRAINT `code_Client` FOREIGN KEY (`refCli`) REFERENCES `client` (`codeCli`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
