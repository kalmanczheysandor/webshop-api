-- New customer
INSERT INTO t_customer SET
id=1,
identifier = 'johndoe',
password = '1234789',
email = 'john.doe@mymail.com',
phone = '00445658899',
firstname = 'John',
lastname = 'Doe',
is_active = 1;

INSERT INTO t_customer_address SET
country = 'England',
city = 'Manchester',
street = 'Castlerigg Drive 28',
postcode = 'MA4 4LW',
customer_id=1;


-- New customer
INSERT INTO t_customer SET
id=2,
identifier = 'juci28',
password = 'fsdfsdf',
email = 'jucus28@gmail.com',
phone = '00364588989',
firstname = 'Júlia',
lastname = 'Horváth',
is_active = 1;

INSERT INTO t_customer_address SET
country = 'Magyarország',
city = 'Szeged',
street = 'Petőfi útca 52 ',
postcode = '3556',
customer_id=2;

-- New customer
INSERT INTO t_customer SET
id=3,
identifier = 'bela65',
password = 'bela5555',
email = 'nagybela@gmail.com',
phone = '003645885566',
firstname = 'Béla',
lastname = 'Nagy',
is_active = 1;

INSERT INTO t_customer_address SET
country = 'Magyarország',
city = 'Sopron',
street = 'Kandó Kálmán út 35 ',
postcode = '2540',
customer_id=3;