--Local H2 database data import

--PRODUCTCATEGORY
INSERT INTO PRODUCTCATEGORY VALUES (1, '식품');
INSERT INTO PRODUCTCATEGORY VALUES (2, '의류');
INSERT INTO PRODUCTCATEGORY VALUES (3, '음료');

--PROFILE IMAGE
INSERT INTO PROFILEIMAGE VALUES (1, NOW(), 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png');
INSERT INTO PROFILEIMAGE VALUES (2, NOW(), 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png');
INSERT INTO PROFILEIMAGE VALUES (3, NOW(), 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png');
INSERT INTO PROFILEIMAGE VALUES (4, NOW(), 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/user/default.png');

--USER
INSERT INTO USERS VALUES (1, NOW(), 'donghar@naver.com', 'drkoko', '{bcrypt}$2a$10$4vqaqfeWWepTBMftJPo3E.42uESauw2Nl9SEMGyurm5erSkKD1fI2', '010-1234-1234', 1);
INSERT INTO USERS VALUES (2, NOW(), 'deepred.kim@gmail.com', 'deepred', '{bcrypt}$2a$10$4vqaqfeWWepTBMftJPo3E.42uESauw2Nl9SEMGyurm5erSkKD1fI2', '010-1234-1234', 2);
INSERT INTO USERS VALUES (3, NOW(), 'wwscan3@gmail.com', 'wonbin', '{bcrypt}$2a$10$4vqaqfeWWepTBMftJPo3E.42uESauw2Nl9SEMGyurm5erSkKD1fI2', '010-1234-1234', 3);
INSERT INTO USERS VALUES (4, NOW(), 'whdgns5059@gmail.com', 'jonghoon', '{bcrypt}$2a$10$4vqaqfeWWepTBMftJPo3E.42uESauw2Nl9SEMGyurm5erSkKD1fI2', '010-1234-1234', 4);

--PRODUCT
INSERT INTO PRODUCT VALUES (1, '테스트프로덕트콘텐츠1', NOW(), NULL, 0, 0, 1000, 1200, 1, '프로덕트1', 1, 1);
INSERT INTO PRODUCT VALUES (2, '테스트프로덕트콘텐츠2', NOW(), NULL, 0, 0, 2000, 2200, 2, '프로덕트2', 1, 1);
INSERT INTO PRODUCT VALUES (3, '테스트프로덕트콘텐츠3', NOW(), NOW(), 1, 0, 3000, 3200, 3, '프로덕트3', 2, 1);
INSERT INTO PRODUCT VALUES (4, '테스트프로덕트콘텐츠4', NOW(), NULL, 0, 0, 4000, 4200, 1, '프로덕트4', 3, 2);
INSERT INTO PRODUCT VALUES (5, '테스트프로덕트콘텐츠5', NOW(), NULL, 0, 0, 5000, 5200, 1, '프로덕트5', 1, 1);
INSERT INTO PRODUCT VALUES (6, '테스트프로덕트콘텐츠6', NOW(), NULL, 0, 0, 5000, 5200, 1, '프로덕트6', 2, 3);
INSERT INTO PRODUCT VALUES (7, '테스트프로덕트콘텐츠7', NOW(), NULL, 0, 0, 5000, 5200, 2, '프로덕트7', 3, 4);
INSERT INTO PRODUCT VALUES (8, '테스트프로덕트콘텐츠8', NOW(), NULL, 0, 0, 5000, 5200, 3, '프로덕트8', 3, 2);
INSERT INTO PRODUCT VALUES (9, '테스트프로덕트콘텐츠9', NOW(), NULL, 0, 0, 5000, 5200, 1, '프로덕트9', 2, 2);
INSERT INTO PRODUCT VALUES (10, '테스트프로덕트콘텐츠10', NOW(), NULL, 0, 0, 1000, 1200, 2, '프로덕트10', 1, 1);
INSERT INTO PRODUCT VALUES (11, '테스트프로덕트콘텐츠11', NOW(), NULL, 0, 0, 2000, 2200, 3, '프로덕트11', 1, 2);
INSERT INTO PRODUCT VALUES (12, '테스트프로덕트콘텐츠12', NOW(), NULL, 0, 0, 3000, 3200, 1, '프로덕트12', 2, 3);
INSERT INTO PRODUCT VALUES (13, '테스트프로덕트콘텐츠13', NOW(), NULL, 0, 0, 4000, 4200, 1, '프로덕트13', 2, 4);
INSERT INTO PRODUCT VALUES (14, '테스트프로덕트콘텐츠14', NOW(), NULL, 0, 0, 5000, 5200, 2, '프로덕트14', 3, 2);
INSERT INTO PRODUCT VALUES (15, '테스트프로덕트콘텐츠15', NOW(), NULL, 0, 0, 6000, 6200, 3, '프로덕트15', 3, 3);
INSERT INTO PRODUCT VALUES (16, '테스트프로덕트콘텐츠16', NOW(), NULL, 0, 0, 7000, 7200, 1, '프로덕트16', 2, 2);
INSERT INTO PRODUCT VALUES (17, '테스트프로덕트콘텐츠17', NOW(), NOW(), 1, 0, 8000, 8200, 2, '프로덕트17', 1, 1);
INSERT INTO PRODUCT VALUES (18, '테스트프로덕트콘텐츠18', NOW(), NULL, 0, 0, 9000, 9200, 2, '프로덕트18', 2, 1);
INSERT INTO PRODUCT VALUES (19, '테스트프로덕트콘텐츠19', NOW(), NULL, 0, 0, 10000, 10200, 1, '프로덕트19', 1, 3);
INSERT INTO PRODUCT VALUES (20, '테스트프로덕트콘텐츠20', NOW(), NULL, 0, 0, 10000, 10200, 3, '프로덕트20', 2, 2);

--PRODUCTIMAGE
INSERT INTO PRODUCTIMAGE VALUES (1, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 1);
INSERT INTO PRODUCTIMAGE VALUES (2, NOW(), 2, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 1);
INSERT INTO PRODUCTIMAGE VALUES (3, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 2);
INSERT INTO PRODUCTIMAGE VALUES (4, NOW(), 2, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 2);
INSERT INTO PRODUCTIMAGE VALUES (5, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 3);
INSERT INTO PRODUCTIMAGE VALUES (6, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 4);
INSERT INTO PRODUCTIMAGE VALUES (7, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 5);
INSERT INTO PRODUCTIMAGE VALUES (8, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 6);
INSERT INTO PRODUCTIMAGE VALUES (9, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 7);
INSERT INTO PRODUCTIMAGE VALUES (10, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 8);
INSERT INTO PRODUCTIMAGE VALUES (11, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 9);
INSERT INTO PRODUCTIMAGE VALUES (12, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 10);
INSERT INTO PRODUCTIMAGE VALUES (13, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 11);
INSERT INTO PRODUCTIMAGE VALUES (14, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 12);
INSERT INTO PRODUCTIMAGE VALUES (15, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 13);
INSERT INTO PRODUCTIMAGE VALUES (16, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 14);
INSERT INTO PRODUCTIMAGE VALUES (17, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 15);
INSERT INTO PRODUCTIMAGE VALUES (18, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 16);
INSERT INTO PRODUCTIMAGE VALUES (19, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 17);
INSERT INTO PRODUCTIMAGE VALUES (21, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 18);
INSERT INTO PRODUCTIMAGE VALUES (22, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 19);
INSERT INTO PRODUCTIMAGE VALUES (23, NOW(), 1, 'https://mwtmarketbucket.s3.ap-northeast-2.amazonaws.com/product/product_default.png', 20);

--WISH
INSERT INTO WISH VALUES (1, NOW(), 1, 1);
INSERT INTO WISH VALUES (2, NOW(), 1, 2);
INSERT INTO WISH VALUES (3, NOW(), 4, 3);
INSERT INTO WISH VALUES (4, NOW(), 5, 4);
INSERT INTO WISH VALUES (5, NOW(), 6, 1);
INSERT INTO WISH VALUES (6, NOW(), 7, 2);
INSERT INTO WISH VALUES (7, NOW(), 8, 3);
INSERT INTO WISH VALUES (8, NOW(), 9, 4);
INSERT INTO WISH VALUES (9, NOW(), 10, 1);
INSERT INTO WISH VALUES (10, NOW(), 10, 2);
INSERT INTO WISH VALUES (11, NOW(), 11, 3);
INSERT INTO WISH VALUES (12, NOW(), 11, 4);

--CHATROOM
INSERT INTO CHATROOM VALUES (1, NOW(), 1, 1);
INSERT INTO CHATROOM VALUES (2, NOW(), 2, 2);
INSERT INTO CHATROOM VALUES (3, NOW(), 3, 3);
INSERT INTO CHATROOM VALUES (4, NOW(), 4, 4);
INSERT INTO CHATROOM VALUES (5, NOW(), 2, 1);
INSERT INTO CHATROOM VALUES (6, NOW(), 3, 2);
