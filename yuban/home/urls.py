from django.urls import include, path
from home import views
urlpatterns = [

    path('', views.index,name='index'),
    path('test/', views.test,name='test'),
    path('login', views.user_login,name='user_login'),
    path('logout', views.user_logout,name='user_logout'),
    path('register', views.user_register,name='user_register'),



    #开放给android客户端的接口

    #显示所有书
    path('api/books/', views.api_books,name='api_books'),
    #显示五本新书
    path('api/newbooks/', views.api_newbooks,name='api_newbooks'),


    #显示最受好评的五本
    path('api/bestbooks/', views.api_bestbooks,name='api_bestbooks'),
    #显示最多人读的五本书
    path('api/hotbooks/', views.api_hotbooks,name='api_hotbooks'),


    path('api/edituserinfo/', views.api_edituserinfo,name='api_edituserinfo'),
    #时间线
    path('api/timeline/', views.api_timeline,name='api_timeline'),

    #根据id检索书
    path('api/books/<int:book_id>/', views.api_books_id,name='api_books_id'),
    #根据id检索书的评分
    path('api/books/rating/<int:book_id>/', views.api_book_rating,name='api_book_rating'),
    # 根据id检索书的读过
    path('api/books/read/<int:book_id>/', views.api_book_read, name='api_book_read'),
    # 根据id检索书的评论
    path('api/books/comment/<int:book_id>/', views.api_book_comments, name='api_book_comments'),
    # 根据bookid查看书的相似推荐
    path('api/books/youwilllike/<int:book_id>/', views.api_book_youwilllike, name='api_book_youwilllike'),

    # 根据id查看comment
    path('api/comment/<int:comment_id>/', views.api_comment_id, name='api_comment_id'),

    #给read点赞
    path('api/likeread/<int:read_id>/', views.api_likeread,name='api_likeread'),

    #给comment点赞
    path('api/likecomment/<int:comment_id>/', views.api_likecomment,name='api_likecomment'),

    #搜索书
    path('api/books/search/',
         views.api_books_search,
         name='api_books_search'),
    #用户主页
    path('api/user/<int:user_id>',
         views.api_userhomepage,
         name='api_userhomepage'),

    #用户关注某人
    path('api/followuser/<int:user_id>',
         views.api_followuser,
         name='api_followuser'),

    #用户读过的书
    path('api/user/read/<user_id>',
         views.api_user_read,
         name='api_user_read'),
    #用户想读的书
    path('api/user/wantread/<user_id>',
         views.api_user_wantread,
         name='api_user_wantread'),
    #用户标记书
    path('api/user/doread/<int:book_id>',
         views.api_doread_book,
         name='api_doread_book'),
    # 用户标记想读
    path('api/user/dowantread/<int:book_id>',
         views.api_wantread_book,
         name='api_wantread_book'),
    #用户发表书的评论
    path('api/user/newcomment/<int:book_id>',
         views.api_newcomment_book,
         name='api_newcomment_book'),
    #粉丝列表
    path('api/user/followed/<user_id>',
         views.api_user_followed,
         name='api_user_followed'),

    #关注的人
    path('api/user/follow/<user_id>',
         views.api_user_follow,
         name='api_user_follow'),
#关注的人
    path('pa',
         views.pa,
         name='pa'),

]

