DROP SCHEMA IF EXISTS BOOK_STORE;
CREATE SCHEMA BOOK_STORE;
USE BOOK_STORE;
SET SQL_SAFE_UPDATES = 0;

CREATE TABLE PUBLISHER (
                           PUBLISHER_NAME VARCHAR (30) PRIMARY KEY NOT NULL UNIQUE,
                           ADDRESS VARCHAR(30),
                           PHONE VARCHAR(30)
);

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

CREATE TABLE AUTHOR (
                        NAME VARCHAR(30),
                        AUTHOR_ID VARCHAR(30) PRIMARY KEY UNIQUE
);

CREATE TABLE BOOK (
                      ISBN VARCHAR(30) NOT NULL UNIQUE,
                      AUTHOR VARCHAR(30),
                      TITLE VARCHAR(30) NOT NULL UNIQUE,
                      PUBLISHER_NAME VARCHAR(30),
                      PUBLICATION_YEAR YEAR,
                      CATEGORY VARCHAR(30),
                      SELLING_PRICE FLOAT,
                      THRESHOLD INT NOT NULL,
                      COPIES INT NOT NULL,
                      PRIMARY KEY(ISBN, TITLE),
                      FOREIGN KEY (PUBLISHER_NAME) REFERENCES PUBLISHER (PUBLISHER_NAME) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE BOOK_ORDER (
                            ISBN VARCHAR(30) NOT NULL,
                            TITLE VARCHAR(30) NOT NULL,
                            DATE_ORDERED DATE,
                            USER_NAME VARCHAR(30),
                            COPIES INT,
                            FOREIGN KEY (ISBN) REFERENCES BOOK (ISBN) ON UPDATE CASCADE ON DELETE CASCADE,
                            FOREIGN KEY (TITLE) REFERENCES BOOK (TITLE) ON UPDATE CASCADE ON DELETE CASCADE,
                            FOREIGN KEY (USER_NAME) REFERENCES ONLINE_USER(USER_NAME) ON UPDATE CASCADE ON DELETE CASCADE
);

delimiter $$
create trigger Modify_existing_books before update  on book
    for each row
begin
    if  new.COPIES<0 then
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot add or update row: only';
end if;
end;
      $$

create trigger Place_orders_books after update on book
    for each row
begin

    if new.copies<new.THRESHOLD then
        if old.copies<new.THRESHOLD then
			insert into BOOK_ORDER(ISBN,TITLE,DATE_ORDERED,COPIES) values(new.ISBN,new.title, CURDATE(),old.copies  - new.copies);
    else
			insert into BOOK_ORDER(ISBN,TITLE,DATE_ORDERED,COPIES) values(new.ISBN,new.title, CURDATE(),new.THRESHOLD  - new.copies);
end if;
end if;
end;

create trigger Confirm_orders before  delete on BOOK_ORDER
    for each row
begin
    update   book set copies = copies + old.COPIES where ISBN = old.ISBN ;
end;

insert into AUTHOR values("hamza" , 10);
insert into book (ISBN ,TITLE,COPIES,THRESHOLD) values(1 , "how to kill myslif?" ,15,10);
update book set COPIES = 7 where ISBN = 1 ;
update book set COPIES = 5 where ISBN = 1 ;

select * from book ;
select * from BOOK_ORDER;
