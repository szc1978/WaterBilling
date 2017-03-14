mysql> show variables like 'character%';
+--------------------------+----------------------------+
| Variable_name            | Value                      |
+--------------------------+----------------------------+
| character_set_client     | utf8                       |
| character_set_connection | utf8                       |
| character_set_database   | utf8                       |
| character_set_filesystem | binary                     |
| character_set_results    | utf8                       |
| character_set_server     | utf8                       |
| character_set_system     | utf8                       |
| character_sets_dir       | /opt/mysql/share/charsets/ |
+--------------------------+----------------------------+

CREATE TABLE `login_log` (

  `id` int(11) NOT NULL AUTO_INCREMENT,

  `user_name` varchar(20) DEFAULT NULL,
  
  `ip` varchar(64) DEFAULT NULL,

  `login_date` varchar(20) DEFAULT NULL,

  PRIMARY KEY (`id`)

)

CREATE TABLE `biz_log` (



)

CREATE TABLE `persistent_logins` (

  `username` varchar(64) NOT NULL,

  `series` varchar(64) NOT NULL,

  `token` varchar(64) NOT NULL,

  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  PRIMARY KEY (`series`)

)