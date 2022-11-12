SELECT 'CREATE DATABASE eainde' WHERE NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'eainde')\gexec

DROP TABLE IF EXISTS _order;
CREATE TABLE IF NOT EXISTS _order(order_id integer PRIMARY KEY NOT NULL,
                    _level integer,
                    code varchar(10),
                    parent VARCHAR(1000),
                    description VARCHAR(10000),
                    item_includes VARCHAR(100000),
                    item_also_includes VARCHAR(100000),
                    rulings VARCHAR(1000),
                    item_excludes VARCHAR(10000),
                    ref_to_isic_rev_4 VARCHAR(1000));


