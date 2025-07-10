-- Generated 2025-07-10 14:09:54-0600 for database version 1

CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `oauth_key`    TEXT                              NOT NULL,
    `display_name` TEXT                              NOT NULL COLLATE NOCASE,
    `created`      INTEGER                           NOT NULL,
    `modified`     INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_oauth_key` ON `user` (`oauth_key`);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_display_name` ON `user` (`display_name`);

CREATE TABLE IF NOT EXISTS `task`
(
    `task_id`        INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title`          TEXT                              NOT NULL COLLATE NOCASE,
    `description`    TEXT,
    `created`        INTEGER                           NOT NULL,
    `modified`       INTEGER                           NOT NULL,
    `due_date`       INTEGER,
    `completed_date` INTEGER                           NOT NULL,
    `user_id`        INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_task_title` ON `task` (`title`);

CREATE INDEX IF NOT EXISTS `index_task_created` ON `task` (`created`);

CREATE INDEX IF NOT EXISTS `index_task_modified` ON `task` (`modified`);

CREATE INDEX IF NOT EXISTS `index_task_due_date` ON `task` (`due_date`);

CREATE INDEX IF NOT EXISTS `index_task_completed_date` ON `task` (`completed_date`);

CREATE INDEX IF NOT EXISTS `index_task_user_id` ON `task` (`user_id`);

CREATE TABLE IF NOT EXISTS `reminder`
(
    `reminder_id`    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title`          TEXT                              NOT NULL COLLATE NOCASE,
    `description`    TEXT,
    `created`        INTEGER                           NOT NULL,
    `modified`       INTEGER                           NOT NULL,
    `selected_date`  INTEGER,
    `completed_date` INTEGER                           NOT NULL,
    `user_id`        INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_reminder_title` ON `reminder` (`title`);

CREATE INDEX IF NOT EXISTS `index_reminder_created` ON `reminder` (`created`);

CREATE INDEX IF NOT EXISTS `index_reminder_modified` ON `reminder` (`modified`);

CREATE INDEX IF NOT EXISTS `index_reminder_selected_date` ON `reminder` (`selected_date`);

CREATE INDEX IF NOT EXISTS `index_reminder_completed_date` ON `reminder` (`completed_date`);

CREATE INDEX IF NOT EXISTS `index_reminder_user_id` ON `reminder` (`user_id`);

CREATE TABLE IF NOT EXISTS `note`
(
    `note_id`     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title`       TEXT                              NOT NULL COLLATE NOCASE,
    `description` TEXT,
    `created`     INTEGER                           NOT NULL,
    `modified`    INTEGER                           NOT NULL,
    `user_id`     INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_note_title` ON `note` (`title`);

CREATE INDEX IF NOT EXISTS `index_note_created` ON `note` (`created`);

CREATE INDEX IF NOT EXISTS `index_note_modified` ON `note` (`modified`);

CREATE INDEX IF NOT EXISTS `index_note_user_id` ON `note` (`user_id`);

CREATE TABLE IF NOT EXISTS `image`
(
    `image_id`  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `caption`   TEXT COLLATE NOCASE,
    `mime_type` TEXT,
    `uri`       TEXT                              NOT NULL,
    `created`   INTEGER                           NOT NULL,
    `note_id`   INTEGER                           NOT NULL,
    FOREIGN KEY (`note_id`) REFERENCES `note` (`note_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_image_mime_type` ON `image` (`mime_type`);

CREATE INDEX IF NOT EXISTS `index_image_created` ON `image` (`created`);

CREATE INDEX IF NOT EXISTS `index_image_note_id` ON `image` (`note_id`);