--liquibase formatted sql

--changeset digid0c:create_employee_table logicalFilePath:db/changelog/changes/10
DROP TABLE IF EXISTS employee CASCADE;

CREATE TABLE employee
(
    id                BIGINT(19)   NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
    name              VARCHAR(75)  NOT NULL,
    surname           VARCHAR(75)  NOT NULL,
    created_at        DATETIME     NOT NULL,
    last_modified_at  DATETIME     NOT NULL
);
--rollback drop table employee cascade;
