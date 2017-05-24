CREATE TABLE `reservation` (
  `Id` int(11) NOT NULL,
  `Date` date NOT NULL,
  `Day` enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY') NOT NULL,
  `Start_hour` time NOT NULL,
  `End_hour` time NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `student` (
  `id` int(11) NOT NULL,
  `last_name` varchar(200) NOT NULL,
  `first_name` varchar(200) NOT NULL,
  `email` varchar(100) NOT NULL,
  `room` int(11) NOT NULL,
  `location` varchar(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8