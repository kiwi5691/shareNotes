# 规范
数据库mapper 交互的service写在 **share-Notes-db** 中
如果需要用到core的工具类的service写在此

nohup java -jar share-Notes-wx-api-0.0.1-exec.jar >logs/shareNotes.log &

# ElasticSearch
如果索引库建立出现问题

```
刷删除url->restful
DELETE http://ip:9200/_all
Header  Content-Type->application/json
```