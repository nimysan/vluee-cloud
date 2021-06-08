CREATE TABLE `simple_domain_event` (
	`aggregate_id` VARCHAR(128) NOT NULL COLLATE 'utf8_general_ci',
	`aggregate_status` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`version` INT(4) NULL DEFAULT NULL,
	`content` BLOB NOT NULL,
	`event_name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci',
	`event_time` DATETIME NULL DEFAULT NULL,
	`is_publish` BIT(1) NULL DEFAULT NULL,
	`retries` INT(11) NULL DEFAULT NULL,
	PRIMARY KEY (`aggregate_id`) USING BTREE
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;
