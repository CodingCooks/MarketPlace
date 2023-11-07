insert into role(name) values ('ROLE_ADMIN');

insert into role(name) values ('ROLE_USER');

insert into users(email, password, is_verified, role_id, phone_number)
values ('pasha16.ua@gmail.com', '$2a$12$o4ReDd9EQOmhndxc551DQ.h.KaRC3IpCOBHcJmkFStZOUUoTXTv2u', true, 1, '0974232829');
