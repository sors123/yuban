from django.contrib import admin
from home.models import Book
from home.models import Read
from home.models import LikeRead
from home.models import Comment
from home.models import LikeComment
from home.models import Follow
from home.models import Status,UserInfo,Tag,TagToArticle,WantRead
# Register your models here.
admin.site.register(Book)
admin.site.register(Read)
admin.site.register(LikeRead)
admin.site.register(LikeComment)
admin.site.register(Comment)
admin.site.register(Follow)
admin.site.register(Status)
admin.site.register(UserInfo)
admin.site.register(Tag)
admin.site.register(TagToArticle)
admin.site.register(WantRead)
