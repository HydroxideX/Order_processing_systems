
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
                      INDEX (TITLE),
                      PRIMARY KEY(ISBN, TITLE),
                      FOREIGN KEY (PUBLISHER_NAME) REFERENCES PUBLISHER (PUBLISHER_NAME) ON UPDATE CASCADE ON DELETE CASCADE,
                      FOREIGN KEY (AUTHOR) REFERENCES AUTHOR (AUTHOR_NAME) ON UPDATE CASCADE ON DELETE CASCADE
);

