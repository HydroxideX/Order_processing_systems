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
                             PRIMARY KEY(USER_NAME, EMAIL_ADDRESS),
                             INDEX (USER_NAME)
);

CREATE TABLE AUTHOR (
				AUTHOR_NAME VARCHAR(30),
				AUTHOR_ID VARCHAR(30)UNIQUE,
			    PRIMARY KEY(Author_name, author_id)
);


CREATE TABLE BOOK (
                      ISBN VARCHAR(30) NOT NULL UNIQUE,
                      AUTHOR VARCHAR(30),
                      TITLE VARCHAR(50) NOT NULL UNIQUE,
                      PUBLISHER_NAME VARCHAR(30),
                      PUBLICATION_YEAR YEAR,
                      CATEGORY VARCHAR(30) NOT NULL,
                      SELLING_PRICE FLOAT NOT NULL,
                      THRESHOLD INT NOT NULL,
                      COPIES INT NOT NULL,
                      PRIMARY KEY(ISBN, TITLE),
                      INDEX (TITLE),
                      FOREIGN KEY (PUBLISHER_NAME) REFERENCES PUBLISHER (PUBLISHER_NAME) ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (AUTHOR) REFERENCES AUTHOR (AUTHOR_NAME) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE BOOK_ORDER (
                            ISBN VARCHAR(30) NOT NULL,
                            TITLE VARCHAR(50) NOT NULL,
                            DATE_ORDERED DATE NOT NULL,
                            USER_NAME VARCHAR(30) NOT NULL,
                            COPIES INT NOT NULL,
                            FOREIGN KEY (ISBN) REFERENCES BOOK (ISBN) ON UPDATE CASCADE ON DELETE CASCADE,
                            FOREIGN KEY (TITLE) REFERENCES BOOK (TITLE) ON UPDATE CASCADE ON DELETE CASCADE,
                            FOREIGN KEY (USER_NAME) REFERENCES ONLINE_USER(USER_NAME) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE SALES (
	ISBN VARCHAR(30) NOT NULL,
	TITLE VARCHAR(50) NOT NULL,
	DATE_ORDERED DATE NOT NULL,
	USER_NAME VARCHAR(30) NOT NULL,
	COPIES INT NOT NULL,
	FOREIGN KEY (ISBN) REFERENCES BOOK (ISBN) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (TITLE) REFERENCES BOOK (TITLE) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (USER_NAME) REFERENCES ONLINE_USER(USER_NAME) ON UPDATE CASCADE ON DELETE CASCADE
);

insert into ONLINE_USER (USER_NAME,PASSWORD,EMAIL_ADDRESS,MANAGER)values("default","----","----",true);
insert into online_user values("sh3ra", "123", "sh3ra@gmail.com", "sh3ra", "shaarawy", "011543665589", "gnby", 1);
insert into online_user values("yahia", "123", "yahia@gmail.com", "yahia", "elsaadawy", "011125112589", "bety", 1);
insert into online_user values("zayady", "123", "zayady@gmail.com", "ahmed", "zayady", "0115445455589", "msh gnby", 1);
insert into online_user values("hamo", "123", "hamo@gmail.com", "hamo", "hamo", "011543665589", "gnby", 0);

insert into publisher values("yahia corp", "bety", "01256632211");
insert into publisher values("sh3ra corp", "gnb bety", "012561532211");
insert into publisher values("zayady corp", "msh gnb bety", "01455632211");

insert into author values("amazing yahia", "1");
insert into author values("sh3ra man", "2");
insert into author values("hamo zayady", "3");

insert into book values ("1", "amazing yahia", "annoying orange", "yahia corp", 1996, "art", 196.5, 12, 18);
insert into book values ("2", "amazing yahia", "let me dribble rocket league", "yahia corp", 1996, "geography", 120, 10, 15);
insert into book values ("3", "amazing yahia", "best guide on afk gameplay", "yahia corp", 1997, "science", 1000, 20, 25);
insert into book values ("4", "amazing yahia", "hakuna matata", "yahia corp", 1997, "history", 25, 100, 150);
insert into book values ("5", "amazing yahia", "wandavision", "yahia corp", 1998, "science", 10, 7, 15);
insert into book values ("6", "sh3ra man", "deep thinking", "sh3ra corp", 1998, "religion", 200, 9, 14);
insert into book values ("7", "sh3ra man", "cool", "sh3ra corp", 1999, "art", 500, 10, 15);
insert into book values ("8", "sh3ra man", "how to become a great sh3ra man", "sh3ra corp", 1999, "science", 220, 18, 35);
insert into book values ("9", "sh3ra man", "spider", "sh3ra corp", 2000, "geography", 16.21, 12, 40);
insert into book values ("10", "sh3ra man", "thba7o", "sh3ra corp", 2000, "art", 800, 3, 5);
insert into book values ("11", "hamo zayady", "how to kill myself? 1st edition", "zayady corp", 1990, "science", 19, 1, 2);
insert into book values ("12", "hamo zayady", "how to kill myself? 2nd edition", "zayady corp", 1990, "art", 196, 1, 1);
insert into book values ("13", "hamo zayady", "how to kill myself? 3th edition", "zayady corp", 1991, "geography", 1000, 7, 13);
insert into book values ("14", "hamo zayady", "how to kill myself? 4th edition", "zayady corp", 1991, "history", 1999.99, 63, 85);
insert into book values ("15", "hamo zayady", "how to kill myself? ultimate edition guaranteed", "zayady corp", 1992, "history", 5000, 126, 200);



insert into book_order values ("1", "annoying orange", "2021-06-13", "yahia", 2);
insert into book_order values ("2", "let me dribble rocket league", "2021-06-10", "sh3ra", 3);
insert into book_order values ("3", "best guide on afk gameplay", "2021-06-09", "yahia", 7);
insert into book_order values ("3", "best guide on afk gameplay", "2021-06-09", "sh3ra", -70);
insert into book_order values ("3", "best guide on afk gameplay", "2021-06-09", "zayady", -70);
insert into book_order values ("4", "hakuna matata", "2021-06-08", "zayady", -5);
insert into book_order values ("5", "wandavision", "2021-06-13", "sh3ra", 4);
insert into book_order values ("6", "deep thinking", "2021-06-07", "hamo", -3);
insert into book_order values ("7", "cool", "2021-06-06", "hamo", -10);
insert into book_order values ("8", "how to become a great sh3ra man", "2021-05-29", "yahia", -8);
insert into book_order values ("9", "spider", "2021-05-29", "sh3ra", -2);
insert into book_order values ("10", "thba7o", "2021-05-27", "zayady", -4);
insert into book_order values ("11", "how to kill myself? 1st edition", "2021-06-02", "sh3ra", -9);
insert into book_order values ("12", "how to kill myself? 2nd edition", "2021-06-03", "zayady", -2);
insert into book_order values ("13", "how to kill myself? 3th edition",  "2021-06-10", "sh3ra",3);
insert into book_order values ("14", "how to kill myself? 4th edition", "2021-06-11", "yahia", 15);
insert into book_order values ("15", "how to kill myself? ultimate edition guaranteed", "2021-06-13", "hamo", -20);
insert into book_order values ("1", "annoying orange", "2021-06-12", "yahia", -7);
insert into book_order values ("2", "let me dribble rocket league", "2021-06-01", "sh3ra", -3);
insert into book_order values ("3", "best guide on afk gameplay", "2021-05-02", "yahia", -100);
insert into book_order values ("4", "hakuna matata", "2001-01-06", "sh3ra", -1000);
insert into book_order values ("5", "wandavision", "2001-01-06", "hamo", -500);
insert into book_order values ("6", "deep thinking", "2001-01-06", "zayady", -100);

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
    if old.copies>new.THRESHOLD and new.copies<new.THRESHOLD then
            insert into BOOK_ORDER(ISBN,TITLE,DATE_ORDERED,COPIES,USER_NAME) values(new.ISBN,new.title, CURDATE(),new.THRESHOLD*2,"default");
    end if;
end;

create trigger Confirm_orders before  delete on BOOK_ORDER
    for each row
begin
    update   book set copies = copies + old.COPIES where ISBN = old.ISBN ;
end;

