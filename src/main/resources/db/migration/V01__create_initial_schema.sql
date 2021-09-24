CREATE TABLE en_user (
 user_id INTEGER,
 first_name ${datatype_nvarchar_2}(50) NOT NULL,
 last_name ${datatype_nvarchar_2}(50) NOT NULL,
 email ${datatype_nvarchar_2}(255),
 password ${datatype_nvarchar_2}(255),
 type ${datatype_nvarchar_2}(50) NOT NULL,
 credits ${datatype_numeric}(20,2) NULL,
 CONSTRAINT pk_en_user PRIMARY KEY (user_id),
 CONSTRAINT un_en_user_email UNIQUE(email)
);

CREATE SEQUENCE se_user_id
    START WITH     10
    INCREMENT BY   1;