--liquibase formatted sql

--changeset digid0c:create_wage_record_table logicalFilePath:db/changelog/changes/20
DROP TABLE IF EXISTS wage_record CASCADE;

CREATE TABLE wage_record
(
    id                BIGINT(19)    NOT NULL  AUTO_INCREMENT  PRIMARY KEY,
    employee_id       BIGINT(19)    NOT NULL,
    base_wage         DECIMAL(7,2)  NOT NULL,
    tax_percent       INTEGER       NOT NULL,
    tax_added_wage    DECIMAL(7,2)  NOT NULL,
    total_wage        DECIMAL(8,2)  NOT NULL,
    active            BIT(1)        NOT NULL,
    created_at        DATETIME      NOT NULL,
    last_modified_at  DATETIME      NOT NULL
);
--rollback drop table wage_record cascade;

--changeset digid0c:create_wage_record_table_employee_table_fk logicalFilePath:db/changelog/changes/20
ALTER TABLE wage_record
ADD CONSTRAINT fk_wage_record_employee FOREIGN KEY (employee_id) REFERENCES employee (id) ON DELETE CASCADE ON UPDATE CASCADE;
--rollback alter table wage_record drop foreign key if exists fk_wage_record_employee;
