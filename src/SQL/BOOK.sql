
CREATE TABLE BOOK (
                      ISBN VARCHAR(30) NOT NULL UNIQUE,
                      AUTHOR VARCHAR(30),
                      TITLE VARCHAR(30) NOT NULL UNIQUE,
                      PUBLISHER_NAME VARCHAR(30),
                      PUBLICATION_YEAR YEAR,
                      CATEGORY VARCHAR(30),
                      SELLING_PRICE FLOAT,
                      THRESHOLD INT,
                      COPIES INT,
                      PRIMARY KEY(ISBN, TITLE),
                      FOREIGN KEY (PUBLISHER_NAME) REFERENCES PUBLISHER (PUBLISHER_NAME) ON UPDATE CASCADE ON DELETE CASCADE
);


delimiter $$
create trigger Modify_existing_books before update on book
    for each row
begin
    if  new.COPIES<0 then
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Cannot add or update row: only';
end if;
end;
      $$

