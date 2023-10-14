# good_news
## Hướng dẫn cài đặt hệ thống good_news backend

1. Clone project good_news về máy
```c
    git clone https://github.com/lequangtoi202/good_news.git
```
2. Sử dụng IntelliJ IDE hoặc Eclipse có cài đặt JDK để chạy máy ảo java.
3. Mở project và build. có thể sử dụng giao diện hoặc môi trường dòng lệnh terminal như sau:
```c
    mvn clean install
```
4. Nhấn vào icon run trên giao diện của IntelliJ IDE :arrow_forward: Run
5. Hoàn tất

## Hướng dẫn sử dụng database local hoặc AWS RDS
Chạy ***database local***:
1. Mở file **application.yml**
2. Tìm và command 
```c
    #  profiles:
    #     active: dev
```
3. Mở MySQL Workbench(recommended) hoặc những hệ quản trị cơ sở dữ liệu có hỗ trợ MySQL.
4. Tạo 1 database tên là **good_news**. Import file ***dump_data.spl*** vào database
5. Chạy lại server và hoàn tất.
[Link Api local](http://localhost:8083/swagger-ui/index.html)

Chạy ***database của AWS RDS***
1. Mở file **application.yml**
2. Tìm và mở command
```c
    #  profiles:
    #     active: dev
```
3. Mở MySQL Workbench(recommended) hoặc những hệ quản trị cơ sở dữ liệu có hỗ trợ MySQL.
4. Tạo 1 database tên là **good_news**. Import file ***dump_data.spl*** vào database
5. Chạy lại server và hoàn tất.
[Link Api cloud](http://good-news-env.eba-radvhidw.ap-southeast-2.elasticbeanstalk.com/swagger-ui/index.html)