# noteOnline
一个搓搓的个人线上笔记示例；
当前只支持上传文本文件或图片后的展示。
内部的Demo文件夹下包含一些自己学习时写的各种test样例

前台为 Html + jQuery + Vue 编写（样式有点搓，后面考虑采用Hexo）.
后台为 Spring + SpringMVC + Mybatis 框架；
数据库为 Mysql；

线上地址 http://106.14.200.149/login.html（域名申请ing...）
部署了tomcat应用服务器两台，通过Nginx实现负载均衡
Redis服务器两台实现分布式缓存，用于记录用户登录信息（有一个自己实现的简单的Redis链接池）
