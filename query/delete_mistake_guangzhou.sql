SELECT * from job ORDER BY id DESC limit 1000;

SELECT * from log ORDER BY id desc limit 100;

SELECT * from url ORDER BY id desc limit 1000;

SELECT * from page order by id desc limit 100;

SELECT count(*) from url where iscompleted = 0;


#delete mistake data of Guangzhou

DELETE from url where jobid = 3357 or jobid = 3358

DELETE from log where operator = 3357 or operator = 3358

DELETE from job where id = 3357 or id = 3358


