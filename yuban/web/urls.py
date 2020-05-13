from django.urls import include, path
from web import views
app_name = 'web'
urlpatterns = [

    path('', views.index,name='index'),

    #书的详情
    path('books/<int:id>', views.bookdetail,name='bookdetail'),
    path('register', views.register,name='register'),
    path('login', views.userlogin,name='login'),
    path('logout', views.userlogout,name='logout'),

    #用户主页
    path('user/<int:id>', views.userpage,name='userpage'),
    #用户读过的书
    path('user/<int:id>/read', views.userread,name='userread'),

path('api/edituserinfo/', views.api_edituserinfo,name='api_edituserinfo'),
path('api/tag/', views.api_tag,name='api_tag'),


]

