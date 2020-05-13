from django.shortcuts import render
from django.http import HttpResponse,HttpResponseRedirect,HttpResponsePermanentRedirect
import json
from django.contrib.auth import authenticate, login
from django.urls import reverse
from django.views.decorators.csrf import csrf_protect
from home.models import Book,Read,LikeRead,Comment,LikeComment,Follow,Status,UserInfo,Tag,TagToArticle
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
import requests
from lxml import html

def index(request):
    context ={}
    if(request.user.is_authenticated):
        userinfo = UserInfo.objects.get(user=request.user)
        context ['userinfo']=userinfo

    return render(request, 'index.html', context)

def bookdetail(request,id):
    book=Book.objects.get(id=id)
    reads = Read.objects.filter(readBook_id=id)

    comments = Comment.objects.filter(commentBook__id=id)
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
        tuijian = '0%'
    else:
        tuijian = format(positive / (positive + negative), '.0%')


    context = {'book': book,
    'positive': '{:.2f}%'.format(positive/total*100),
    'moderate': '{:.2f}%'.format(moderate/total*100),
    'negative': '{:.2f}%'.format(negative/total*100),
    'total': total,
    'tuijian': tuijian,
    'reads':reads,
    'comments':comments,

               }
    if (request.user.is_authenticated):
        userinfo = UserInfo.objects.get(user=request.user)
        context['userinfo'] = userinfo
    return render(request, 'book.html', context)

def userlogin(request):
    if (request.method == 'POST'):
        username = request.POST.get('username')
        password = request.POST.get('password')

        user = authenticate(request, username=username, password=password)
        if user is not None:
            login(request, user)
            # Redirect to a success page.
            return HttpResponseRedirect(reverse('web:index'))
        else:
            context={"error":"用户名或密码错误"}
            return render(request, 'login.html',context)
    else:

        return render(request, 'login.html')



def register(request):
    context = {'tags': 'x'
               }
    return render(request, 'register.html', context)

def userlogout(request):
    logout(request)
    return HttpResponseRedirect(reverse('web:index'))

def userpage(request,id):
    context = {'tags': 'x'
               }
    if (request.user.is_authenticated):
        userinfo = UserInfo.objects.get(user=request.user)
        context['userinfo'] = userinfo
    return render(request, 'userpage.html', context)

def userread(request,id):
    user = User.objects.get(id=id)
    reads = Read.objects.order_by('-createTime').filter(readUser=user)
    context = {'reads': reads
               }

    if (request.user.is_authenticated):
        userinfo = UserInfo.objects.get(user=request.user)
        context['userinfo'] = userinfo
    return render(request, 'userread.html', context)

def api_edituserinfo(request):
    context = {'tags': 'x'
               }
    if (request.method == 'POST'):
        qianming = request.POST.get('qianming')
        sex = request.POST.get('sex')
        location = request.POST.get('location')


        userinfo = UserInfo.objects.get(user=request.user)
        userinfo.location=location
        userinfo.sex=sex
        userinfo.text=qianming
        if ('avatar' in request.FILES):
            image = request.FILES['avatar']
            image.name = str(request.user.username) + '.jpg'
            userinfo.image=image
        userinfo.save()
        return HttpResponseRedirect(reverse('web:api_edituserinfo'))

    else:
        if (request.user.is_authenticated):
            userinfo = UserInfo.objects.get(user=request.user)

            context['userinfo'] = userinfo
    return render(request, 'edituserinfo_app.html', context)

def api_tag(request):
    tags=Tag.objects.all()

    tagss = []
    for tag in tags:
        books = TagToArticle.objects.filter(tag=tag)
        thistag = {'tag':tag,
                 'books':books
        }
        tagss.append(thistag)
    context = {'tags': tagss

               }
    print(tagss)
    return render(request, 'tag_app.html', context)