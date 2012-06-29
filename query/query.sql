SELECT * from page where url = 'http://beijing.anjuke.com/sale/aolinpikegongyuan/b24-p69';
SELECT * from url where url = 'http://beijing.anjuke.com/sale/aolinpikegongyuan/b24-p69';
SELECT * from job ORDER BY id DESC limit 10;
SELECT * from log ORDER BY id desc;

SELECT DISTINCT category, type from job;

SELECT * from url where iscompleted = 0;

SELECT count(*) from page limit 1;

SELECT * from url where url = 'http://shanghai.anjuke.com/sale/beixinjing/b23-p42';

SELECT * from page where iscompleted = 0;

SELECT count(*) from page;

alter table url add index url_url_idx(`url`);

alter table page add index page_iscompleted_idx(`iscompleted`);

alter table page add index page_jobid_idx(`jobid`);

alter table url add index url_iscompleted_idx(`iscompleted`);

SELECT sum(count)
from job
where category = '北京' and type like "%安居客-小区%";

SELECT sum(count)
from job
where category = '北京' and type like "%安居客-租房%";

SELECT sum(count)
from job
where category = '北京' and type like "%安居客-二手房%";

SELECT sum(count)
from job
where category = '上海' and type like "%安居客-小区%";

SELECT sum(count)
from job
where category = '上海' and type like "%安居客-二手房%";

#用以删除错误的上海二手房所有数据，log中不删除好了。删除page中的数据，url中的数据

#获取所有上海二手房 job id 集合
SELECT  id #sum(count)
from job
where category = '上海' and type like "安居客-二手房%";

#获取所有上海二手房 的url 集合
SELECT * 
from
(SELECT id
from job
where category = '上海' and type like "安居客-二手房%") as tmp, url
where tmp.id = url.jobid


#获取所有上海二手房 的page 集合
SELECT *
from 
(SELECT id
from job
where category = '上海' and type like "安居客-二手房%") as tmp, page
where tmp.id = page.jobid





SELECT count(*) from url;


#before 42840, after 34696


#删除所有上海二手房 的url 集合
DELETE 
from url
where url.jobid in
(SELECT id
from job
where category = '上海' and type like "安居客-二手房%")

#删除所有上海二手房 的page 集合
DELETE 
from page
where page.jobid in
(SELECT id
from job
where category = '上海' and type like "安居客-二手房%")


#删除所有上海二手房 的log 集合
DELETE 
from log
where operator in
(SELECT id
from job
where category = '上海' and type like "安居客-二手房%")

DELETE from job
where id <= 2065 and id >= 1787


SELECT count(*) from page;

SELECT * from page ORDER BY id desc limit 1;

SELECT * from job ORDER BY id desc limit 1000;

SELECT * from log ORDER BY id desc LIMIT 1000;

SELECT * from 
(SELECT DISTINCT category from job 
where type like "安居客-租房%" ) as tmp1 left join 
(SELECT DISTINCT category from job 
where type like "安居客-二手房%" ) as tmp2
on tmp1.category = tmp2.category

SELECT DISTINCT category from job 
where type like "安居客-二手房%"


SELECT * from job 
where type like "安居客-二手房%" and category = '佛山'


SELECT * from job ORDER BY id DESC limit 1000;
SELECT * from log ORDER BY id desc limit 1000;

SELECT * from page where url = 'http://qd.anjuke.com/community/W0QQpZ1QQp1Z3641QQp2Z7090';

SELECT count(*) from address;

SELECT count(*) from page;

desc address;

SELECT DISTINCT city from address;

SELECT DISTINCT city, type from address;

SELECT * from address ORDER by id desc limit 100;

DELETE from address where type='二手房' and city='北京'

SELECT * from address where type='二手房' and city='北京'

SELECT DISTINCT city from address where type = '二手房';

SELECT * from page where url = 'http://beijing.anjuke.com/sale/yayuncun/a16-b17-p17-t9';

SELECT * from page where url = 'http://beijing.anjuke.com/sale/yayuncun/a16-b17-p18-t9';

SELECT * from job 
where category = '北京' #and type = '安居客-租房'
ORDER BY id DESC limit 1000;

SELECT * from job where domain = 'http://beijing.anjuke.com/sale/yayuncun/a16-b17-t9'
SELECT * from job where id > 600 and id < 700

SELECT * from job
where category = '北京' and type like '安居客-二手房#%'

SELECT * from address ORDER by id desc limit 100;

SELECT * from address 
where district = '崇文' and city = '北京' and type = '二手房'
ORDER by id desc;

#把崇文的数据都删除了，重新下载
delete from address 
where district = '崇文' and city = '北京' and type = '二手房'

SELECT DISTINCT district from address where city = '北京' and type = '二手房'


SELECT DISTINCT city from address where type = '二手房';

SELECT * from job limit 10;

SELECT distinct category
from job 
where type like  '安居客-二手房%';


SELECT *
from address
where city = '南京' and type = '二手房'
ORDER BY id desc
limit 1000;

SELECT *
from job
where type like '安居客-二手房#浦口#%' and category = '南京'

SELECT *
from job
where type like '安居客-二手房#%' and category = '南京'

SELECT * from job limit 10;

#这个是由于程序到了南京，浦口这块就跑挂了，所以我要把浦口的数据删干净了，重新从浦口开始跑。删除了86条。
#其实这个南京，浦口，顶山的数据，当时就没有跑干净
DELETE from address where city = '南京' and type = '二手房' and website = '安居客' and district = '浦口'

select DISTINCT city, type from address where type = '二手房';


SELECT DISTINCT category
from job
where type like '安居客-二手房#%';

SELECT tmp1.city, tmp2.category from 
(select DISTINCT city, type from address where type = '二手房') as tmp1 right join 
(SELECT DISTINCT category
from job
where type like '安居客-二手房#%') as tmp2
on tmp1.city = tmp2.category;


SELECT * from address ORDER BY id desc limit 1000;

SELECT COUNT(*) from address where city = '上海' and type = '小区';
SELECT COUNT(*) from address where city = '上海' and type = '二手房';
SELECT COUNT(*) from address where city = '上海' and type = '租房';

SELECT COUNT(*) from address where city = '北京' and type = '小区';
SELECT COUNT(*) from address where city = '北京' and type = '二手房';
SELECT COUNT(*) from address where city = '北京' and type = '租房';

SELECT count(*) from address where type = '小区';
SELECT count(*) from address where type = '二手房';
SELECT count(*) from address where type = '租房';

SELECT city, count(*) from address GROUP BY city;

SELECT COUNT(*) from address;

SELECT distinct city from address where type = '小区';


SELECT * from address order by id desc limit 1000;


SELECT count(*) from address; #3010529
SELECT count(*) from page; #141208
SELECT count(*) from job; #12889
SELECT count(*) from url;#141208
SELECT count(*) from log;#39507