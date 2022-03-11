CREATE TABLE IF NOT EXISTS inventory 
    AS SELECT * FROM CSVREAD('classpath:jcpenney_com-ecommerce_sample.csv');