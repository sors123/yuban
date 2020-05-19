# 鱼伴
鱼伴，书籍阅读打分记录应用。Android客户端+网页版，后台基于python。

## 界面
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/1.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/2.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/3.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/4.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/5.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/6.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/7.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/8.png)
![](https://raw.githubusercontent.com/qscdew/yuban/master/images/9.png)

## 环境
+ Ubuntu 5.4.0-6ubuntu1~16.04.10
+ MySQL5.7
+ Python 3.5.2【Django (2.2.6) pip (8.1.1) PyMySQL (0.9.3)】

## 部署
1、配置数据库
打开 settings.py，修改数据库信息。

2、切换到manage.py所在目录
`python manage.py makemigrations`
`python manage.py migrate`
`python manage.py createsuperuser`

3、运行
`python manage.py runserver 0:8088`
访问 http://localhost:8088

4、根据后端程序运行的地址，修改安卓工程中的接口。
dsajhdasjkdh
