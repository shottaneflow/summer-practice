create schema if not exists postgres;

create table postgres.t_user(
id serial primary key,
c_username varchar not null check(length(trim(c_username))>0) unique,
c_pincode varchar not null check(length(trim(c_pincode))>0)
);

create table postgres.t_authority(
    id serial primary key,
    c_authority varchar not null check(length(trim(c_authority))>0) unique
);

create table postgres.t_user_authority(
    id serial primary key,
    id_user int not null references postgres.t_user(id),
    id_authority int not null references postgres.t_authority(id),
    constraint ukk_user_authority unique(id_user,id_authority)
);

create table postgres.t_user_bank_accounts(
    id serial primary key,
    id_user int not null references postgres.t_user(id),
    id_account int not null references postgres.t_bankAccounts(accountNumber),
    constraint ukk_user_bank unique(id_user,id_account)
);