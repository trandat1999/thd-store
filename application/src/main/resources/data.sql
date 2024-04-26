INSERT INTO `railway`.`tbl_role` (`created_by`, `last_modified_by`,`role_name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'ADMIN' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `store`.`tbl_role` WHERE role_name = 'ADMIN'
    ) LIMIT 1;
INSERT INTO `railway`.`tbl_role` (`created_by`, `last_modified_by`,`role_name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'USER' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `store`.`tbl_role` WHERE role_name = 'USER'
    ) LIMIT 1;