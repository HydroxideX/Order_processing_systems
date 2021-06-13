CREATE TABLE ONLINE_USER (
     USER_NAME VARCHAR(30) NOT NULL UNIQUE,
     PASSWORD VARCHAR(30) NOT NULL,
     EMAIL_ADDRESS VARCHAR(30) NOT NULL UNIQUE,
     FNAME VARCHAR(30),
     LNAME VARCHAR(30),
     PHONE_NO VARCHAR(30),
     SHIPPING_ADDRESS VARCHAR(30),
     MANAGER BOOL DEFAULT 0,
     PRIMARY KEY(USER_NAME, EMAIL_ADDRESS)
);
