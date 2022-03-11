CREATE TABLE IF NOT EXISTS inventory
    AS SELECT uniq_id, ROUND(RAND() * 3,0) AS status FROM CSVREAD('classpath:jcpenney_com-ecommerce_sample.csv');