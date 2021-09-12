# dtp

```sql
CREATE DATABASE IF NOT EXISTS dtp
    DEFAULT CHAR SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

USE dtp;

CREATE TABLE IF NOT EXISTS t_user
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    username varchar(64) NULL,
    amount   int         NULL

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户';


INSERT INTO t_user(username, amount)
VALUES ('batman', 100),
       ('superman', 0)
```

事务模拟
```sql
-- 查看全局和当前session的事务隔离级别
SELECT @@GLOBAL.tx_isolation, @@tx_isolation;

-- 设置隔离级别
SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
-- level: READ UNCOMMITTED, READ COMMITTED, REPEATABLE READ,  SERIALIZABLE

-- session 1:
START TRANSACTION;
UPDATE t_user SET amount = amount + 100 WHERE username = 'BatMan';
UPDATE t_user SET amount = amount - 100 WHERE username = 'SuperMan';
COMMIT;
-- ROLLBACK;



-- session 2:
START TRANSACTION;
SELECT * FROM t_user
COMMIT;
```
