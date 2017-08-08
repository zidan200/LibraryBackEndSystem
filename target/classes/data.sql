/**
 * CREATE Script for init of DB
 */

-- Create 3 OFFLINE readers

insert into reader (id, date_created, deleted, reader_status, password, username) values (1, now(), false, 'DEACTIVATED',
'reader01pw', 'reader01');

insert into reader (id, date_created, deleted, reader_status, password, username) values (2, now(), false, 'DEACTIVATED',
'reader02pw', 'reader02');

insert into reader (id, date_created, deleted, reader_status, password, username) values (3, now(), false, 'DEACTIVATED',
'reader03pw', 'reader03');


-- Create 3 ONLINE readers

insert into reader (id, date_created, deleted, reader_status, password, username) values (4, now(), false, 'ACTIVATED',
'reader04pw', 'reader04');

insert into reader (id, date_created, deleted, reader_status, password, username) values (5, now(), false, 'ACTIVATED',
'reader05pw', 'reader05');

insert into reader (id, date_created, deleted, reader_status, password, username) values (6, now(), false, 'ACTIVATED',
'reader06pw', 'reader06');

-- Create 1 DEACTIVATED reader with coordinate(longitude=9.5&latitude=55.954)

insert into reader (id, coordinate, date_coordinate_updated, date_created, deleted, reader_status, password, username)
values
 (7,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'DEACTIVATED',
'reader07pw', 'reader07');

-- Create 1 ACTIVATED reader with coordinate(longitude=9.5&latitude=55.954)

insert into reader (id, coordinate, date_coordinate_updated, date_created, deleted, reader_status, password, username)
values
 (8,
 'aced0005737200226f72672e737072696e676672616d65776f726b2e646174612e67656f2e506f696e7431b9e90ef11a4006020002440001784400017978704023000000000000404bfa1cac083127', now(), now(), false, 'ACTIVATED',
'reader08pw', 'reader08');

-- create 1 record for author
insert into author(id,name,password) values (1,'Chahrles Dickens','mercedesPWD');

-- Create 3 records for one book
insert into book(id, convertible, book_status, license_plate, seat_count, author_id,is_selected)
values
(1, false,'BOOKED','1234',2,1,false);
insert into book(id, convertible, book_status, license_plate, seat_count, author_id,is_selected)
values
(2, true,'UNBOOKED','12345',4,1,false);
insert into book(id, convertible, book_status, license_plate, seat_count, author_id,is_selected)
values
(3, true,'BOOKED','12345',4,1,false);

-- create 3 users
insert into users(id, username, password)
values
(1, 'reader1','reader1pw');
insert into users(id, username, password)
values
(2, 'author1','author1pw');
insert into users(id, username, password)
values
(3, 'admin','admin');

-- create roles
insert into roles(id, role, user_id)
values
(1, 'ROLE_READER', 1);
insert into roles(id, role, user_id)
values
(2, 'ROLE_AUTHOR', 2);
insert into roles(id, role, user_id)
values
(3, 'ROLE_ADMIN', 3)

