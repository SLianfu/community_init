## 码匠社区
1,创建导航栏



## 资料
[Bootstrap 文档](https://v3.bootcss.com/getting-started/#download)
[](https://v3.bootcss.com/components/#navbar-default)
[srping 文档](https://spring.io/guides)
[spring Web文档](https://spring.io/guides/gs/serving-web-content/)
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/)



## 工具
[Git](https://git-scm.com/download)   
[Visual Paradigm](https://www.visual-paradigm.com)   

##第16讲中连接H2数据库,测试连接的时候提示The specified database user/password combination is rejected: 【28000】【28000】 Wrong user name or password 【28000-196】
##,网上查了H2的默认账号密码输入还是不正确,一直弹这个窗口,请问怎么解决?百度了半天都没有答案
##后来 输入的用户：sa;密码：123 就可以了,后来还执行了：create user if not exists sa password '123456';alter user sa admin true ;
                
##脚本
'''sql
CREATE TABLE USER
(
    ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN VARCHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
);
'''                
'''bash
mvn flyway:migrate
'''