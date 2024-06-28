create schema if not exists postgres;

create table postgres.t_bankAccounts(
    accountNumber serial primary key,
    c_accountName  varchar(50) not null,
    c_money int check(c_money>0)

);
