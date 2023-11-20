insert into role(name) values ('ROLE_ADMIN');

insert into role(name) values ('ROLE_USER');

insert into users(email, password, is_verified, is_deleted, role_id, phone_number)
values ('pasha16.ua@gmail.com', '$2a$12$Yii6uCkb.NTj9v84OaInceOCQYLtgLPrs4xnv13CgF05Jg.VlUL/.', true, false, 1,
        '0974232829');
