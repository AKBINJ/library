CREATE DATABASE IF NOT EXISTS `library`;
DROP DATABASE `library`;
USE `library`;

CREATE TABLE members (
  member_id BIGINT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  status ENUM('ACTIVE','BLOCKED') NOT NULL
);

CREATE TABLE books (
  book_id BIGINT PRIMARY KEY,
  title VARCHAR(100) NOT NULL,
  available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE borrow_log (
  log_id BIGINT PRIMARY KEY AUTO_INCREMENT,
  member_id BIGINT NOT NULL,
  book_id BIGINT NOT NULL,
  result ENUM('SUCCESS','FAIL_MEMBER','FAIL_BOOK','FAIL_UNKNOWN') NOT NULL,
  message VARCHAR(200),
  created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO members(member_id,name,status) VALUES
(1,'Kim','ACTIVE'), (2,'Lee','BLOCKED');

INSERT INTO books(book_id,title,available) VALUES
(10,'Spring Boot Guide',TRUE),
(11,'Database Basics',FALSE);