
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