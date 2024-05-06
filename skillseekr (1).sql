-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 06 mai 2024 à 17:43
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `skillseekr`
--

-- --------------------------------------------------------

--
-- Structure de la table `doctrine_migration_versions`
--

CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `doctrine_migration_versions`
--

INSERT INTO `doctrine_migration_versions` (`version`, `executed_at`, `execution_time`) VALUES
('DoctrineMigrations\\Version20240304160508', '2024-03-04 17:05:19', 1783),
('DoctrineMigrations\\Version20240304172623', '2024-03-04 18:26:33', 226),
('DoctrineMigrations\\Version20240304183052', '2024-03-04 19:31:03', 214),
('DoctrineMigrations\\Version20240304183214', '2024-03-04 19:32:17', 182);

-- --------------------------------------------------------

--
-- Structure de la table `entretient`
--

CREATE TABLE `entretient` (
  `id` int(11) NOT NULL,
  `id_rec_id` int(11) DEFAULT NULL,
  `date` date NOT NULL,
  `type` varchar(255) NOT NULL,
  `resultat` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `location`
--

CREATE TABLE `location` (
  `location` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `location`
--

INSERT INTO `location` (`location`) VALUES
('Hybrid'),
('OnSite'),
('Remote');

-- --------------------------------------------------------

--
-- Structure de la table `messenger_messages`
--

CREATE TABLE `messenger_messages` (
  `id` bigint(20) NOT NULL,
  `body` longtext NOT NULL,
  `headers` longtext NOT NULL,
  `queue_name` varchar(190) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `available_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `delivered_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `motive`
--

CREATE TABLE `motive` (
  `motive` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `motive`
--

INSERT INTO `motive` (`motive`) VALUES
('Beta'),
('Paid');

-- --------------------------------------------------------

--
-- Structure de la table `offer`
--

CREATE TABLE `offer` (
  `id` int(11) NOT NULL,
  `motive` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `location` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `description` longtext NOT NULL,
  `author` varchar(255) NOT NULL,
  `created_at` date NOT NULL,
  `file_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `offer`
--

INSERT INTO `offer` (`id`, `motive`, `type`, `location`, `status`, `title`, `description`, `author`, `created_at`, `file_name`) VALUES
(3, 'Paid', 'Internship', 'Remote', 'Published', 'Offer Integration', '150 chars AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaa', 'sarah.henia@esprit.tn', '2024-06-05', 'listview essay.txt'),
(4, 'Paid', 'Internship', 'Onsite', 'Published', 'Offer1', 'Desc 150 chars AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', 'sarah.henia@esprit.tn', '2024-05-05', NULL),
(5, 'Paid', 'Mission', 'Hybrid', 'Published', 'Offer2', 'Desc 150 chars AAa$$ aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'sarah.henia@esprit.tn', '2024-05-05', 'image-removebg-preview (6).png'),
(6, 'Paid', 'Mission', 'Onsite', 'Published', 'offer3', 'Desc 150 chars AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA  AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaa', 'sarah.henia@esprit.tn', '2024-05-05', NULL),
(7, 'Paid', 'Internship', 'Onsite', 'Published', 'offer3', 'Desc 150 chars Aaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'sarah.henia@esprit.tn', '2024-05-05', NULL),
(9, 'Paid', 'Mission', 'Onsite', 'Published', 'Offer4', 'Desc 150 chars aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa AAAAAAAAAAAAAAAAAAAAAAaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa AAAAAAAAAAAAAAAAAAAAAAAAAAAAa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAaaaaaaaaaaaaaaaa aaaaaaaaaa', 'sarah.henia@esprit.tn', '2024-05-04', NULL),
(10, 'Paid', 'Mission', 'Hybrid', 'Published', 'Offer 6', '150 chars Desc Aaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'sarah.henia@esprit.tn', '2024-05-16', NULL),
(11, 'Paid', 'Internship', 'Onsite', 'Published', 'Offer Pag', 'Desc 150 chars Aaaaaaaaaaaaaaaaaaaaaaaa  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', 'sarah.henia@esprit.tn', '2024-05-09', NULL),
(12, 'Paid', 'Internship', 'Hybrid', 'Published', 'Integration', '150 chars AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA', 'sarah.henia@esprit.tn', '2024-05-07', 'Calendrier_S2_22 fév.pdf');

-- --------------------------------------------------------

--
-- Structure de la table `offer_skills`
--

CREATE TABLE `offer_skills` (
  `id` int(11) NOT NULL,
  `skill` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `offer_skills`
--

INSERT INTO `offer_skills` (`id`, `skill`) VALUES
(3, 'JAVA'),
(4, 'JAVA'),
(4, 'PHP'),
(5, 'PHP'),
(6, 'JAVA'),
(7, 'JAVA'),
(7, 'PHP'),
(9, 'JAVA'),
(9, 'PHP'),
(10, 'PHP'),
(11, 'JAVA'),
(12, 'Full Stack'),
(12, 'JAVA'),
(12, 'JavaFx');

-- --------------------------------------------------------

--
-- Structure de la table `projet`
--

CREATE TABLE `projet` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `competences` varchar(255) NOT NULL,
  `budget` float NOT NULL,
  `date_deb` date NOT NULL,
  `date_fin` date NOT NULL,
  `proprietaire` varchar(255) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `projet`
--

INSERT INTO `projet` (`id`, `titre`, `competences`, `budget`, `date_deb`, `date_fin`, `proprietaire`, `user_id`) VALUES
(1, 'Site web', 'php , symfony', 150.55, '2019-01-01', '2019-01-01', 'mohamed', 2),
(2, 'aeaze', 'azeaze', 12, '2019-01-01', '2019-01-01', 'freelancer1', 4),
(3, 'projet12', 'php , smf , java', 3220.55, '2019-01-01', '2019-01-01', 'mohamed', 2),
(4, 'projet13', 'php , smf , java', 7000, '2019-01-01', '2019-01-01', 'mohamed', 2),
(7, 'aeaze3', 'azeaze', 120000, '2019-01-01', '2019-01-01', 'freelancer1', 4),
(8, 'aeaze4', 'azeaze', 4000, '2019-01-01', '2019-01-01', 'freelancer1', 111),
(9, 'aeaze5', 'azeaze', 6500.5, '2019-01-01', '2019-01-01', 'freelancer1', 111),
(10, 'javafx', 'azerazer', 15.5, '2024-04-02', '2024-04-17', 'aaaaa', 111),
(11, 'projet', 'jdid ', 14, '2024-05-02', '2024-05-08', 'mohamed', 35),
(12, 'test', 'zerzr', 14, '2024-05-01', '2024-05-17', 'flen', 111);

-- --------------------------------------------------------

--
-- Structure de la table `proposition`
--

CREATE TABLE `proposition` (
  `id` int(11) NOT NULL,
  `id_projet_id` int(11) NOT NULL,
  `description` varchar(255) NOT NULL,
  `prop_budget` double NOT NULL,
  `prop_delai` date NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `proposition`
--

INSERT INTO `proposition` (`id`, `id_projet_id`, `description`, `prop_budget`, `prop_delai`, `user_id`) VALUES
(2, 1, 'prop jdidaaa', 71.44, '2021-12-31', 1),
(5, 1, 'propossmmm', 47, '2024-04-17', 1),
(6, 11, 'Develop and train machine learning models using jdid to automate tasks, improve predictions, and make data-driven decisions.', 80, '2024-05-23', 35);

-- --------------------------------------------------------

--
-- Structure de la table `rating`
--

CREATE TABLE `rating` (
  `id_user` int(11) NOT NULL,
  `id_projet` int(11) NOT NULL,
  `value` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `rating`
--

INSERT INTO `rating` (`id_user`, `id_projet`, `value`) VALUES
(1, 1, 4.59659),
(1, 2, 3.52841),
(1, 3, 1.44886),
(111, 1, 2.48295),
(111, 2, 2.09659),
(111, 3, 3.4375),
(111, 4, 3.34659),
(111, 7, 2.77841);

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

CREATE TABLE `reclamation` (
  `idrec` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `content` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `recrutement`
--

CREATE TABLE `recrutement` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `date` date NOT NULL,
  `statut` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `recrutement`
--

INSERT INTO `recrutement` (`id`, `titre`, `description`, `date`, `statut`) VALUES
(1, 'rze', 'rer', '2019-01-01', 'rzer');

-- --------------------------------------------------------

--
-- Structure de la table `reponse`
--

CREATE TABLE `reponse` (
  `idrep` int(11) NOT NULL,
  `reclamation_id` int(11) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `message` longtext NOT NULL,
  `created_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reset_password_request`
--

CREATE TABLE `reset_password_request` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `selector` varchar(20) NOT NULL,
  `hashed_token` varchar(100) NOT NULL,
  `requested_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `expires_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `skill`
--

CREATE TABLE `skill` (
  `skill` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `skill`
--

INSERT INTO `skill` (`skill`) VALUES
('DevOps'),
('Docker'),
('ERP-BI'),
('Full Stack'),
('JAVA'),
('JavaFx'),
('Linux Administration'),
('MERN Stack'),
('Mongo DB'),
('No SQL'),
('PHP'),
('Project Piloting'),
('Python'),
('SCRUM'),
('Stage PFE'),
('Symfony');

-- --------------------------------------------------------

--
-- Structure de la table `status`
--

CREATE TABLE `status` (
  `status` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `status`
--

INSERT INTO `status` (`status`) VALUES
('Archived'),
('Draft'),
('Published'),
('WIP');

-- --------------------------------------------------------

--
-- Structure de la table `type`
--

CREATE TABLE `type` (
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `type`
--

INSERT INTO `type` (`type`) VALUES
('Internship'),
('Mission');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(180) NOT NULL,
  `roles` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '(DC2Type:json)' CHECK (json_valid(`roles`)),
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `is_verified` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `roles`, `password`, `username`, `is_verified`) VALUES
(1, 'test@esprit.tn', '[\"ROLE_ADMIN\"]', '$2y$13$aL2.Yg5c6vgzIqIHSOLAn.KCbVCTuXNT.1eQKLT9kN0/XREEZn0DK', 'test', 1),
(2, 'freelancer@esprit.tn', '[\"ROLE_FREELANCER\"]', '$2y$13$F5VhHJMN4atiYxoQVyMfEeHpMG5YTT3qY09YDriW8smOBWtwi7NUS', 'freelancer', 1),
(3, 'rec@esprit.tn', '[\"ROLE_RECRUTEUR\"]', '$2y$13$O2nF6.Qhe56mCf.aTKsmXuUNsoVcgG9Tvg.WrE6EkZgii9rOXIFEa', 'recruteur', 1),
(4, 'freelancer1@esprit.tn', '[\"ROLE_FREELANCER\"]', '$2y$13$F5VhHJMN4atiYxoQVyMfEeHpMG5YTT3qY09YDriW8smOBWtwi7NUS', 'freelancer1', 1),
(5, 'client@gmail.com', '[\"ROLE_RECRUTEUR\"]', '$2a$12$rb7Vj3ZTYOrTBMZPBtMLjedV.oyL8EV.R9M4oBP7dsv24jVDQPqJq', 'client', 0),
(6, 'client@test.tn', '[\"ROLE_RECRUTEUR\"]', '$2a$10$aKdOtQnG1a8jgJt5eu56MOuBhCq6VC/7xWZTEfkN5cmGZAu4j0.YC', 'client', 0),
(35, 'test@gmail.com', '[\"ROLE_ADMIN\"]', '$2a$10$tCw/A4vY3WIpRlulg6co6OpZfJ6CfqZ0/TuRfQBbUpr3blUOy6qzS', 'test', 1),
(111, 'sarah.henia20@gmail.com', '[\"ROLE_RECRUTEUR\"]', '$2a$10$tCw/A4vY3WIpRlulg6co6OpZfJ6CfqZ0/TuRfQBbUpr3blUOy6qzS', 'SarahRec', 1),
(112, 'sarah.henia15@gmail.com', '[\"ROLE_FREELANCER\"]', '$2a$10$1yuOaNKJHXHm31mfNFXK5eEi8aVx6ne2rYYU5JOl1J15yhAkohgqu', 'SarahFreelance', 1),
(114, 'doratekaya28@gmail.com', '[\"ROLE_RECRUTEUR\"]', '$2a$10$fxrUDiA70VavRylA63M9l.iBdbc.SXtEyYKPO6J3aTaYRwSjNPvCW', 'dora', 0),
(115, 'doratekaya@gmail.com', '[\"ROLE_RECRUTEUR\"]', '$2a$10$.UFZeZyM6m8fIK2JyBNPGuYsKKjV3SLJKWPjRFuGOv5I3D9HtLouy', 'dora', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `doctrine_migration_versions`
--
ALTER TABLE `doctrine_migration_versions`
  ADD PRIMARY KEY (`version`);

--
-- Index pour la table `entretient`
--
ALTER TABLE `entretient`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_E34739B4305E0476` (`id_rec_id`);

--
-- Index pour la table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`location`);

--
-- Index pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_75EA56E0FB7336F0` (`queue_name`),
  ADD KEY `IDX_75EA56E0E3BD61CE` (`available_at`),
  ADD KEY `IDX_75EA56E016BA31DB` (`delivered_at`);

--
-- Index pour la table `motive`
--
ALTER TABLE `motive`
  ADD PRIMARY KEY (`motive`);

--
-- Index pour la table `offer`
--
ALTER TABLE `offer`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_29D6873EF92CD178` (`motive`),
  ADD KEY `IDX_29D6873E8CDE5729` (`type`),
  ADD KEY `IDX_29D6873E5E9E89CB` (`location`),
  ADD KEY `IDX_29D6873E7B00651C` (`status`);

--
-- Index pour la table `offer_skills`
--
ALTER TABLE `offer_skills`
  ADD PRIMARY KEY (`id`,`skill`),
  ADD KEY `IDX_C6461D1BF396750` (`id`),
  ADD KEY `IDX_C6461D15E3DE477` (`skill`);

--
-- Index pour la table `projet`
--
ALTER TABLE `projet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_50159CA9A76ED395` (`user_id`);

--
-- Index pour la table `proposition`
--
ALTER TABLE `proposition`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_C7CDC35380F43E55` (`id_projet_id`),
  ADD KEY `IDX_C7CDC353A76ED395` (`user_id`);

--
-- Index pour la table `rating`
--
ALTER TABLE `rating`
  ADD PRIMARY KEY (`id_user`,`id_projet`);

--
-- Index pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD PRIMARY KEY (`idrec`),
  ADD KEY `IDX_CE606404A76ED395` (`user_id`);

--
-- Index pour la table `recrutement`
--
ALTER TABLE `recrutement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD PRIMARY KEY (`idrep`),
  ADD KEY `IDX_5FB6DEC72D6BA2D9` (`reclamation_id`);

--
-- Index pour la table `reset_password_request`
--
ALTER TABLE `reset_password_request`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_7CE748AA76ED395` (`user_id`);

--
-- Index pour la table `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`skill`);

--
-- Index pour la table `status`
--
ALTER TABLE `status`
  ADD PRIMARY KEY (`status`);

--
-- Index pour la table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`type`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `entretient`
--
ALTER TABLE `entretient`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `offer`
--
ALTER TABLE `offer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `projet`
--
ALTER TABLE `projet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `proposition`
--
ALTER TABLE `proposition`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `reclamation`
--
ALTER TABLE `reclamation`
  MODIFY `idrec` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `recrutement`
--
ALTER TABLE `recrutement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `reponse`
--
ALTER TABLE `reponse`
  MODIFY `idrep` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reset_password_request`
--
ALTER TABLE `reset_password_request`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `entretient`
--
ALTER TABLE `entretient`
  ADD CONSTRAINT `FK_E34739B4305E0476` FOREIGN KEY (`id_rec_id`) REFERENCES `recrutement` (`id`);

--
-- Contraintes pour la table `offer`
--
ALTER TABLE `offer`
  ADD CONSTRAINT `FK_29D6873E5E9E89CB` FOREIGN KEY (`location`) REFERENCES `location` (`location`),
  ADD CONSTRAINT `FK_29D6873E7B00651C` FOREIGN KEY (`status`) REFERENCES `status` (`status`),
  ADD CONSTRAINT `FK_29D6873E8CDE5729` FOREIGN KEY (`type`) REFERENCES `type` (`type`),
  ADD CONSTRAINT `FK_29D6873EF92CD178` FOREIGN KEY (`motive`) REFERENCES `motive` (`motive`);

--
-- Contraintes pour la table `offer_skills`
--
ALTER TABLE `offer_skills`
  ADD CONSTRAINT `FK_C6461D15E3DE477` FOREIGN KEY (`skill`) REFERENCES `skill` (`skill`),
  ADD CONSTRAINT `FK_C6461D1BF396750` FOREIGN KEY (`id`) REFERENCES `offer` (`id`);

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `FK_CE606404A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `reponse`
--
ALTER TABLE `reponse`
  ADD CONSTRAINT `FK_5FB6DEC72D6BA2D9` FOREIGN KEY (`reclamation_id`) REFERENCES `reclamation` (`idrec`);

--
-- Contraintes pour la table `reset_password_request`
--
ALTER TABLE `reset_password_request`
  ADD CONSTRAINT `FK_7CE748AA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
