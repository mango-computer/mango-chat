-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 17-10-2018 a las 00:57:13
-- Versión del servidor: 10.1.13-MariaDB
-- Versión de PHP: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `mangochatbot`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE `articulos` (
  `id` int(11) NOT NULL,
  `articulo` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`id`, `articulo`) VALUES
(1, 'este'),
(2, 'esta'),
(3, 'estas'),
(4, 'esto'),
(5, 'estos'),
(6, 'unos'),
(7, 'unas'),
(8, 'pero'),
(9, 'la'),
(10, 'las'),
(11, 'lo'),
(12, 'los'),
(13, 'un'),
(14, 'una'),
(15, 'uno'),
(16, 'es'),
(17, 'en'),
(18, 'a'),
(19, 'de'),
(20, 'del'),
(21, 'le'),
(22, 'que'),
(23, 'para'),
(24, 'o'),
(25, 'u'),
(26, 'y'),
(27, 'e'),
(28, 'i'),
(29, 'me'),
(30, 'tengo'),
(31, 'quiero'),
(32, 'quiere'),
(33, 'queremos'),
(34, 'quieren'),
(35, 'querer'),
(36, 'tu'),
(37, 'yo'),
(38, 'el'),
(39, 'ella'),
(40, 'nosotros'),
(41, 'vosotros'),
(42, 'ellos'),
(43, 'crees'),
(44, 'puedes'),
(45, 'podrias'),
(46, 'por'),
(47, 'has'),
(48, 'han');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fabricaFrases`
--

CREATE TABLE `fabricaFrases` (
  `id` int(11) NOT NULL,
  `codFrase` tinytext NOT NULL,
  `frase` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `fabricaFrases`
--

INSERT INTO `fabricaFrases` (`id`, `codFrase`, `frase`) VALUES
(1, '1', 'edad tienes'),
(2, '1', 'edad tiene'),
(3, '1', 'cuantos años tienes'),
(4, '1', 'cuanto años tiene'),
(5, '1', 'cuanto año tienes'),
(6, '1', 'cuanto año tiene'),
(7, '1', 'edad'),
(8, '1', 'tienes edad'),
(9, '1', 'nacimiento'),
(10, '1', 'año naciste'),
(11, '1', 'año nacistes'),
(12, '1', 'fecha nacimiento'),
(13, '1', 'nacimiento fecha'),
(14, '1', 'tiempo vida'),
(15, '1', 'tiempo vivido');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `semantica_sino`
--

CREATE TABLE `semantica_sino` (
  `id` int(11) NOT NULL,
  `palabra` tinytext NOT NULL,
  `semantica` tinytext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `semantica_sino`
--

INSERT INTO `semantica_sino` (`id`, `palabra`, `semantica`) VALUES
(1, 'si', 'si'),
(2, 'positivo', 'si'),
(3, 'exacto', 'si'),
(4, 'yes', 'si'),
(5, 'ok', 'si'),
(6, 'aja', 'si'),
(7, 'perfecto', 'si'),
(8, 'dale', 'si'),
(9, 'si', 'si'),
(10, 'bien', 'si'),
(11, 'claro', 'si'),
(12, 'afirmativo', 'si'),
(13, 'supuesto', 'si'),
(14, 'creo', 'si'),
(15, 'siempre', 'si'),
(16, 'obvio', 'si'),
(17, 'correcto', 'si'),
(18, 'correcto', 'no'),
(19, 'negativo', 'no'),
(20, 'impreciso', 'no'),
(21, 'not', 'no'),
(22, 'nunca', 'no'),
(23, 'jamas', 'no'),
(24, 'correcto', 'no');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `fabricaFrases`
--
ALTER TABLE `fabricaFrases`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `semantica_sino`
--
ALTER TABLE `semantica_sino`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulos`
--
ALTER TABLE `articulos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT de la tabla `fabricaFrases`
--
ALTER TABLE `fabricaFrases`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT de la tabla `semantica_sino`
--
ALTER TABLE `semantica_sino`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
