from django.shortcuts import render
from django.http import HttpResponse
import json
from django.contrib.auth import authenticate, login
from django.views.decorators.csrf import csrf_protect
from .models import Book,Read,LikeRead,Comment,LikeComment,Follow,UserInfo,WantRead
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
import requests
from lxml import html

# Create your views here.

def pa(request):

    url = 'https://movie.douban.com/'  # 需要爬数据的网址
    page = requests.Session().get(url)
    tree = html.fromstring(page.text)
    result = tree.xpath('//td[@class="title"]//a/text()')  # 获取需要的数据
    return HttpResponse(json.dumps(result), content_type="application/json")




def index(request):
        return HttpResponse("success")


def test(request):

    if(request.user.is_authenticated):
        response={'id':1,
            'data':'logined',
                  'userid':request.user.id,
                  'username':request.user.username}
    else:
        response={'id':2,
            'data':'guest',
                  'userid': None,
                  'username': None
                  }

    return HttpResponse(json.dumps(response), content_type="application/json")


#api books 显示所有的书
def api_books(request):
    """

    :param request:
    :return:
    """
    books=Book.objects.order_by('createTime')
    response_books = []
    for i in books:
        book={'id':i.id,'bookName':i.bookName,
              'authorName':i.authorName,
              'pressName':i.pressName,
              'pageNum':i.pageNum,
              'pricingNum':i.pricingNum,
              'introduction':i.introduction,
              'imgurl':i.image.url}
        response_books.append(book)
    return HttpResponse(json.dumps(response_books), content_type="application/json")

#显示五本新书
def api_newbooks(request):

    books=Book.objects.order_by('-createTime')
    response_books = []
    for i in books[0:5]:
        book={'id':i.id,'bookName':i.bookName,
              'authorName':i.authorName,
              'pressName':i.pressName,
              'pageNum':i.pageNum,
              'pricingNum':i.pricingNum,
              'introduction':i.introduction,
              'imgurl':i.image.url}
        response_books.append(book)
    return HttpResponse(json.dumps(response_books), content_type="application/json")
#显示五本最热的书
def api_hotbooks(request):

    books=Book.objects.order_by('-createTime')
    response_books = []
    for i in books:
        reads = Read.objects.filter(readBook=i)
        book={'id':i.id,'bookName':i.bookName,
              'authorName':i.authorName,
              'pressName':i.pressName,
              'pageNum':i.pageNum,
              'pricingNum':i.pricingNum,
              'introduction':i.introduction,
              'imgurl':i.image.url,
              'num':len(reads)}
        response_books.append(book)
    response_books.sort(key=lambda x: x['num'], reverse=True)
    return HttpResponse(json.dumps(response_books[0:5]), content_type="application/json")
#显示最受好评的五书
def api_bestbooks(request):

    books=Book.objects.order_by('-createTime')
    response_books = []
    for i in books:
        reads = Read.objects.filter(readBook=i)
        positive = 0
        moderate = 0
        negative = 0
        for read in reads:
            if (read.rating == 1):
                negative += 1
            elif (read.rating == 2):
                moderate += 1
            else:
                positive += 1
        total = len(reads)
        if (positive + negative == 0):
            tuijian = 0
        else:
            tuijian = positive / (positive + negative)
        book={'id':i.id,
              'bookName':i.bookName,
              'authorName':i.authorName,
              'pressName':i.pressName,
              'pageNum':i.pageNum,
              'pricingNum':i.pricingNum,
              'introduction':i.introduction,
              'imgurl':i.image.url,
              'tuijian':tuijian}
        response_books.append(book)
    response_books.sort(key=lambda x: x['tuijian'], reverse=True)
    return HttpResponse(json.dumps(response_books[0:5]), content_type="application/json")

#api books_id 某个id的书
def api_books_id(request,book_id):
    book=Book.objects.get(id=book_id)#需要补充空集的情况

    response_book={'id':book.id,
                   'bookName':book.bookName,
                   'authorName':book.authorName,
                   'pressName':book.pressName,
                   'pageNum':book.pageNum,
                   'pricingNum':book.pricingNum,
                   'introduction':book.introduction,
                   'imgurl': book.image.url}
    return HttpResponse(json.dumps(response_book), content_type="application/json")

#某个id的书的评论
def api_book_read(request,book_id):
    reads = Read.objects.filter(readBook_id=book_id)


    response=[]
    for read in reads:
        read_send={'id':read.id,
                   'rating':read.rating,
                   'text':read.comment,
                   'time':read.createTime,
                   'username':read.readUser.username,
                   'userid':read.readUser.id}
        response.append(read_send)
    response.sort(key=lambda x: x['time'], reverse=True)
    return HttpResponse(json.dumps(response,cls=DateEncoder), content_type="application/json")


#某个id的书的相似推荐
def api_book_youwilllike(request,book_id):
    pass

#某个id的书的comment
def api_book_comments(request,book_id):
    comments = Comment.objects.filter(commentBook__id=book_id)

    response=[]
    for comment in comments:
        send={'id':comment.id,
                   'bookid':comment.commentBook.id,
                   'bookName':comment.commentBook.bookName,
                   'authorName': comment.commentBook.authorName,
                   'introduction':comment.commentBook.introduction,
                   'imgurl': comment.commentBook.image.url,
                   'title':comment.commentTitle,
                   'text':comment.comment,
                   'time':comment.createTime,
                   'username':comment.commentUser.username,
                   'userid':comment.commentUser.id

                      }
        response.append(send)
    response.sort(key=lambda x: x['time'], reverse=True)
    return HttpResponse(json.dumps(response,cls=DateEncoder), content_type="application/json")



#某个id的书的评分
def api_book_rating(request,book_id):
    book=Book.objects.get(id=book_id)
    reads = Read.objects.filter(readBook_id=book_id)
    positive=0
    moderate=0
    negative=0
    for read in reads:
        if(read.rating==1):
            negative+=1
        elif (read.rating==2):
            moderate+=1
        else:
            positive+=1
    total = len(reads)
    if(positive+negative==0):
        tuijian='0%'
    else:
        tuijian=format(positive/(positive+negative),'.0%')

    response_book={'id':book_id,
                   'bookName':book.bookName,
                   'positive':positive,
                   'moderate':moderate,
                   'negative':negative,
                   'total':total,
                   'tuijian':tuijian}
    return HttpResponse(json.dumps(response_book), content_type="application/json")


@login_required
@csrf_exempt
def api_newcomment_book(request,book_id):
    """
    用户写评论
    """
    if (request.method == 'POST'):
        book = Book.objects.get(id=book_id)  # 需要补充空集的情况
        user=request.user
        title = request.POST.get('title')
        text=request.POST.get('text')

        newcomment=Comment(commentBook=book,commentUser=user,commentTitle=title,comment=text)
        newcomment.save()
        response_info = {'id': 1, 'data': "success"}
        return HttpResponse(json.dumps(response_info), content_type="application/json")


    else:
        response_info = {'id':3, 'data': "error. Is not post"}
        return HttpResponse(json.dumps(response_info), content_type="application/json")


@login_required
@csrf_exempt
def api_doread_book(request,book_id):
    """
    用户标记读书
    """
    if (request.method == 'POST'):
        book = Book.objects.get(id=book_id)  # 需要补充空集的情况
        user=request.user
        rating = request.POST.get('rating')
        comment=request.POST.get('comment')
        x=0
        for i in Read.objects.filter(readBook=book):
            if(i.readUser==user):
                response_info = {'id': 1, 'data': "error"}
                x=1
                break

        if(x!=1):
            newread=Read(readBook=book,readUser=user,rating=rating,comment=comment)
            newread.save()
            response_info = {'id': 1, 'data': "success"}
        return HttpResponse(json.dumps(response_info), content_type="application/json")


    else:
        response_info = {'id':3, 'data': "error. Is not post"}
        return HttpResponse(json.dumps(response_info), content_type="application/json")


@login_required
@csrf_exempt
def api_wantread_book(request,book_id):
    """
    用户标记想读
    """

    book = Book.objects.get(id=book_id)  # 需要补充空集的情况
    user=request.user

    x=0
    for i in WantRead.objects.filter(wantreadBook=book):
        if(i.wantreadUser==user):
            response_info = {'id': 1, 'data': "取消想读成功"}
            i.delete()
            x=1
            break

    if(x!=1):
        newread=WantRead(wantreadBook=book,wantreadUser=user)
        newread.save()
        response_info = {'id': 1, 'data': "已经添加至您的想读"}
    return HttpResponse(json.dumps(response_info), content_type="application/json")


#api comment_id 某个id的comment
def api_comment_id(request,comment_id):
    comment=Comment.objects.get(id=comment_id)

    response_comment={'id':comment.id,
                   'bookid':comment.commentBook.id,
                   'bookName':comment.commentBook.bookName,
                   'authorName': comment.commentBook.authorName,
                   'introduction':comment.commentBook.introduction,
                   'imgurl': comment.commentBook.image.url,
                   'title':comment.commentTitle,
                   'text':comment.comment,
                   'time':comment.createTime,
                   'username':comment.commentUser.username,
                   'userid':comment.commentUser.id

                      }
    return HttpResponse(json.dumps(response_comment,cls=DateEncoder), content_type="application/json")




#需要完善
#搜索书
@csrf_exempt
def api_books_search(request):
    #print(search_content)
    content = request.POST.get('content')

    books1 = Book.objects.filter(bookName__icontains=content)
    books2 = Book.objects.filter(authorName__icontains=content)
    books3 = Book.objects.filter(pressName__icontains=content)
    response_books = []
    for i in books1:
        book = {'id': i.id, 'bookName': i.bookName,
                'authorName': i.authorName,
                'pressName': i.pressName,
                'pageNum': i.pageNum,
                'pricingNum': i.pricingNum,
                'introduction': i.introduction[:20],
                'imgurl': i.image.url}
        response_books.append(book)
    for i in books2:
        book = {'id': i.id, 'bookName': i.bookName,
                'authorName': i.authorName,
                'pressName': i.pressName,
                'pageNum': i.pageNum,
                'pricingNum': i.pricingNum,
                'introduction': i.introduction[:20],
                'imgurl': i.image.url}
        response_books.append(book)
    for i in books3:
        book = {'id': i.id, 'bookName': i.bookName,
                'authorName': i.authorName,
                'pressName': i.pressName,
                'pageNum': i.pageNum,
                'pricingNum': i.pricingNum,
                'introduction': i.introduction[:20],
                'imgurl': i.image.url}
        response_books.append(book)
    return HttpResponse(json.dumps(response_books), content_type="application/json")


def api_user_read(request,user_id):
    """
    用户读过的书
    """
    user=User.objects.get(id=user_id)
    reads = Read.objects.order_by('createTime').filter(readUser=user)
    response_messages = []
    for i in reads:
        message = {'id': i.id,
                   'userid': i.readUser.id,
                   'username': i.readUser.username,
                   'bookid': i.readBook.id,
                   'bookimgurl': i.readBook.image.url,
                   'bookname': i.readBook.bookName,
                   'rating': i.rating,
                   'text': i.comment,
                   'time': i.createTime}
        response_messages.append(message)
    response_messages.sort(key=lambda x: x['time'], reverse=True)
    return HttpResponse(json.dumps(response_messages, cls=DateEncoder), content_type="application/json")


def api_user_wantread(request,user_id):
    """
    用户想读的书
    """
    user=User.objects.get(id=user_id)
    reads = WantRead.objects.order_by('createTime').filter(wantreadUser=user)
    response_messages = []
    for i in reads:
        message = {'id': i.id,
                   'userid': i.wantreadUser.id,
                   'username': i.wantreadUser.username,
                   'bookid': i.wantreadBook.id,
                   'bookimgurl': i.wantreadBook.image.url,
                   'bookname': i.wantreadBook.bookName,
                   'time': i.createTime}
        response_messages.append(message)
    response_messages.sort(key=lambda x: x['time'], reverse=True)
    return HttpResponse(json.dumps(response_messages, cls=DateEncoder), content_type="application/json")


#用户个人主页
def api_userhomepage(request,user_id):
    user = User.objects.get(id=user_id)
    userinfo = UserInfo.objects.get(user=user)
    fensiusers = Follow.objects.filter(beFollowedUser_id=user_id)
    guanzhuusers = Follow.objects.filter(FollowUser_id=user_id)
    reads = Read.objects.order_by('-createTime').filter(readUser=user)
    wantreads = WantRead.objects.order_by('-createTime').filter(wantreadUser=user)
    readnum=len(reads)
    fensi=len(fensiusers)
    guanzhu=len(guanzhuusers)

    if(len(wantreads)==0):
        wimg=""
    else:
        wimg=wantreads[0].wantreadBook.image.url

    if (len(reads) == 0):
        rimg = ""
    else:
        rimg = reads[0].readBook.image.url
    message={'id':user.id,
             'username':user.username,
             'fensi':fensi,
             'guanzhu':guanzhu,
             'readnum':readnum,
             'qianming':userinfo.text,
             'image':userinfo.image.url,
             'location':userinfo.location,
             'sex':userinfo.sex,
             'readimg':rimg,
             'wantimg':wimg
             }
    return HttpResponse(json.dumps(message), content_type="application/json")



#获取粉丝列表
def api_user_followed(request,user_id):

    users=Follow.objects.filter(beFollowedUser_id=user_id)
    response_users = []
    for i in users:
        user={'id':i.FollowUser.id,
              'username':i.FollowUser.username}
        response_users.append(user)
    return HttpResponse(json.dumps(response_users), content_type="application/json")

#获取关注的人
def api_user_follow(request,user_id):

    users=Follow.objects.filter(FollowUser_id=user_id)
    response_users = []
    for i in users:
        user = {'id': i.beFollowedUser.id,
                'username': i.beFollowedUser.username}
        response_users.append(user)
    return HttpResponse(json.dumps(response_users), content_type="application/json")

import datetime
class DateEncoder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj,datetime.datetime):
            return obj.strftime("%Y-%m-%d %H:%M:%S")
        else:
            return json.JSONEncoder.default(self,obj)


@login_required
def api_likeread(request,read_id):
    user=request.user
    read=Read.objects.get(id=read_id)

    x=0
    for i in LikeRead.objects.filter(read=read):
        if(i.user==user):
            response_info = {'id': 2, 'data': "error"}
            x=1
            break

    if(x!=1):
        newLikeRead=LikeRead(user=user,read=read)
        newLikeRead.save()
        response_info = {'id': 1, 'data': "success"}
    return HttpResponse(json.dumps(response_info), content_type="application/json")


@login_required
def api_likecomment(request, comment_id):
    user = request.user
    comment = Comment.objects.get(id=comment_id)

    x = 0
    for i in LikeComment.objects.filter(comment=comment):
        if (i.user == user):
            response_info = {'id': 2, 'data': "error"}
            x = 1
            break

    if (x != 1):
        newLikeComment = LikeComment(user=user, comment=comment)
        newLikeComment.save()
        response_info = {'id': 1, 'data': "success"}
    return HttpResponse(json.dumps(response_info), content_type="application/json")


def api_timeline(request):
    """
    时间线
    :param request:
    :return:
    """
    user=request.user
    if (request.user.is_authenticated):
        response_messages = []
        for f in Follow.objects.filter(FollowUser=user):
            beuser=f.beFollowedUser
            for i in Comment.objects.filter(commentUser=beuser):
                likenum = len(LikeComment.objects.filter(comment=i))
                userinfo = UserInfo.objects.get(user=i.commentUser)
                message = {'id': i.id,
                           'type': 'comment',
                           'userid': i.commentUser.id,
                           'username': i.commentUser.username,
                           'bookid': i.commentBook.id,
                           'bookimgurl': i.commentBook.image.url,
                           'bookname': i.commentBook.bookName,
                           'author': i.commentBook.authorName,
                           'press': i.commentBook.pressName,
                           'commenttitle': i.commentTitle,
                           'comment': i.comment[:50],
                           'commentid': i.id,
                           'likenum': likenum,
                           'text': None,
                           'rating': None,
                           'time': i.createTime,
                           'image':userinfo.image.url}
                response_messages.append(message)
            for i in Read.objects.filter(readUser=beuser):
                likenum = len(LikeRead.objects.filter(read=i))
                userinfo = UserInfo.objects.get(user=i.readUser)
                message = {'id': i.id,
                           'type': 'read',
                           'userid': i.readUser.id,
                           'username': i.readUser.username,
                           'bookid': i.readBook.id,
                           'bookimgurl': i.readBook.image.url,
                           'bookname': i.readBook.bookName,
                           'author': i.readBook.authorName,
                           'press': i.readBook.pressName,
                           'commenttitle': None,
                           'comment': None,
                           'commentid': None,
                           'likenum': likenum,
                           'rating': i.rating,
                           'text': i.comment,
                           'time': i.createTime,
                           'image':userinfo.image.url}
                response_messages.append(message)

        response_messages.sort(key=lambda x:x['time'], reverse=True)
        return HttpResponse(json.dumps(response_messages,cls=DateEncoder), content_type="application/json")
    else:
        return HttpResponse(json.dumps("[]"), content_type="application/json")


@login_required
def api_followuser(request,user_id):
    user=request.user
    beuser=User.objects.get(id=user_id)
    x=0
    for i in Follow.objects.filter(FollowUser=user):
        if(i.beFollowedUser==beuser):
            response={'id':1,'data':'取关成功'}
            i.delete()
            x=1
            break
    if(x!=1):
        newFollow=Follow(beFollowedUser=beuser,FollowUser=user)
        newFollow.save()
        response = {'id': 1, 'data': '关注成功'}

    return HttpResponse(json.dumps(response), content_type="application/json")

#用post进行登录
@csrf_exempt
def user_login(request):
    if (request.method == 'POST'):
        username = request.POST.get('username')
        password = request.POST.get('password')

        user = authenticate(request, username=username, password=password)
        if user is not None:
            login(request, user)
            # Redirect to a success page.
            response_info = {'id':1,'data':"login success","userid":user.id,"username":username}
            return HttpResponse(json.dumps(response_info), content_type="application/json")
        else:
            # Return an 'invalid login' error message.
            response_info = {'id': 2, 'data': "login error"}
            return HttpResponse(json.dumps(response_info), content_type="application/json")
    else:
        response_info = {'id':3, 'data': "error. Is not post"}
        return HttpResponse(json.dumps(response_info), content_type="application/json")

@csrf_exempt
def user_register(request):
    """
    用户注册
    """
    if (request.method == 'POST'):
        username = request.POST.get('username')
        password = request.POST.get('password')

        for i in User.objects.all():
            if (i.username==username):
                response_info = {'id': 2, 'data': "Error.This username is not available."}
                return HttpResponse(json.dumps(response_info), content_type="application/json")

        newuser=User.objects.create_user(username=username,password=password)
        newuser.save()

        response_info = {'id': 1, 'data': "success"}
        return HttpResponse(json.dumps(response_info), content_type="application/json")

    else:
        response_info = {'id':3, 'data': "Error. Is not post"}
        return HttpResponse(json.dumps(response_info), content_type="application/json")


def user_logout(request):
    logout(request)
    response_info = {'id': 4, 'data': "logout success"}
    return HttpResponse(json.dumps(response_info), content_type="application/json")

@csrf_exempt
def api_edituserinfo(request):

        if (request.method == 'POST'):
            username = request.POST.get('username')
            password = request.POST.get('password')

            for i in User.objects.all():
                if (i.username==username):
                    response_info = {'id': 2, 'data': "Error.This username is not available."}
                    return HttpResponse(json.dumps(response_info), content_type="application/json")

            newuser=User.objects.create_user(username=username,password=password)
            newuser.save()

            response_info = {'id': 1, 'data': "success"}
            return HttpResponse(json.dumps(response_info), content_type="application/json")

        else:
            userinfo=UserInfo.objects.get(user=request.user)
            response_info = {'image':userinfo.image.url,
                             'qianming':userinfo.text,
                             'sex':userinfo.sex,
                             'location':userinfo.location}
            return HttpResponse(json.dumps(response_info), content_type="application/json")