ALTER TABLE postgres.t_bankAccounts
DROP CONSTRAINT t_bankaccounts_c_money_check,
ADD CONSTRAINT t_bankaccounts_c_money_check CHECK (c_money >= 0);