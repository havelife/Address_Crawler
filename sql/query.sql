SELECT * from page where url = 'http://beijing.anjuke.com/sale/aolinpikegongyuan/b24-p69';
SELECT * from url where url = 'http://beijing.anjuke.com/sale/aolinpikegongyuan/b24-p69';
SELECT * from job ORDER BY id DESC;
SELECT * from log;
ORDER BY id desc;

SELECT * from url where iscompleted = 0;

SELECT * from page limit 1;

SELECT * from url where url = 'http://shanghai.anjuke.com/sale/beixinjing/b23-p42';

SELECT * from page where iscompleted = 0;

SELECT count(*) from page;

alter table url add index url_url_idx(`url`);

alter table page add index page_iscompleted_idx(`iscompleted`);

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