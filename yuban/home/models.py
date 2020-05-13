from django.db import models
from django.contrib.auth.models import User
# Create your models here.
class Book(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    #书籍名称
    bookName=models.CharField('书籍名称', max_length=40,default="暂无")
    # 作者名称
    authorName = models.CharField('作者', max_length=20,default="暂无")
    # 出版社名称
    pressName = models.CharField('出版社', max_length=20,default="暂无")
    # 页数
    pageNum = models.IntegerField('页数', default=0)
    # 价钱
    pricingNum = models.IntegerField('定价', default=0)
    #简介
    introduction = models.TextField('简介',default="暂无")

    isbn=models.CharField('isbn', max_length=20,default="")
    #封面
    image =models.ImageField(upload_to='photos')



    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)
    # 修改时间 auto_now： 添加和修改都会改变时间
    modifyTime = models.DateTimeField(auto_now=True)

    class Meta:
        db_table = 'book'
        db_table = 'ImageStore'

    def __str__(self):
        return self.bookName


#想读
class WantRead(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    #想读所属的用户和书
    wantreadBook=models.ForeignKey(Book,on_delete=models.CASCADE)
    wantreadUser=models.ForeignKey(User,on_delete=models.CASCADE)


    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)
    # 修改时间 auto_now： 添加和修改都会改变时间
    modifyTime = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.wantreadUser.username+" 想读 "+self.wantreadBook.bookName


#读过
class Read(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    #读过所属的用户和书
    readBook=models.ForeignKey(Book,on_delete=models.CASCADE)
    readUser=models.ForeignKey(User,on_delete=models.CASCADE)

    #短评
    comment=models.CharField('短评', max_length=200,default="")

    #评分
    RATING_CHOICES=[
        (1,'差评'),
        (2,'一般'),
        (3,'好评')
    ]
    rating=models.IntegerField(choices=RATING_CHOICES,default=2)

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)
    # 修改时间 auto_now： 添加和修改都会改变时间
    modifyTime = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.readUser.username+" 读过 "+self.readBook.bookName


# 给读过点赞
class LikeRead(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    read = models.ForeignKey(Read,on_delete=models.CASCADE)
    user = models.ForeignKey(User,on_delete=models.CASCADE)

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.user.username+" 赞 "

#长评
class Comment(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    #长评所属的用户和书
    commentBook=models.ForeignKey(Book,on_delete=models.CASCADE)
    commentUser=models.ForeignKey(User,on_delete=models.CASCADE)

    #长评内容
    comment=models.TextField('长评',default="")

    # 长评内容
    commentTitle = models.CharField('长评标题', default="",max_length=40)



    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)
    # 修改时间 auto_now： 添加和修改都会改变时间
    modifyTime = models.DateTimeField(auto_now=True)

    def __str__(self):
        return self.commentUser.username +" 评论 "+self.commentBook.bookName


#给长评点赞
class LikeComment(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    comment=models.ForeignKey(Comment,on_delete=models.CASCADE)
    user=models.ForeignKey(User,on_delete=models.CASCADE)

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)
    def __str__(self):
        return self.user.username+" 赞 "+self.comment.commentTitle

class Follow(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    #被关注的用户
    beFollowedUser = models.ForeignKey(User,on_delete=models.CASCADE,related_name ="beFollowedUser")

    #发起关注的用户
    FollowUser = models.ForeignKey(User,on_delete=models.CASCADE,related_name ="FollowUser")

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.FollowUser.username+" 关注 "+self.beFollowedUser.username


class Status(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    TYPE_CHOICES = [
        ('read', 'read'),
        ('comment', 'comment'),
        ('status', 'status')
    ]
    type=models.CharField(choices=TYPE_CHOICES,max_length=10)
    user=models.ForeignKey(User,on_delete=models.CASCADE)
    book=models.ForeignKey(Book,on_delete=models.CASCADE, null=True,blank=True)
    comment = models.ForeignKey(Comment, on_delete=models.CASCADE, null=True,blank=True)
    read = models.ForeignKey(Read, on_delete=models.CASCADE, null=True,blank=True)

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)

    # 修改时间 auto_now： 添加和修改都会改变时间
    modifyTime = models.DateTimeField(auto_now=True)
    text = models.CharField('动态内容', max_length=400, default="", blank=True)

    def __str__(self):
        return self.user.username+" "+self.type

class UserInfo(models.Model):
    id = models.AutoField(primary_key=True)
    user = models.OneToOneField(User, on_delete=models.CASCADE)

    text = models.CharField('个性签名', max_length=100, default="", blank=True)
    sex = models.CharField('性别', max_length=10, default="", blank=True)
    location = models.CharField('位置', max_length=10, default="", blank=True)

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)
    # 修改时间 auto_now： 添加和修改都会改变时间
    modifyTime = models.DateTimeField(auto_now=True)

    #头像
    image = models.ImageField(upload_to='user',default="user/user_normal.jpg")
    class Meta:
        db_table = 'UserInfo'


    def __str__(self):
        return self.user.username


#标签
class Tag(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)
    user = models.ForeignKey(User, on_delete=models.CASCADE)

    tag = models.CharField('标签', max_length=120)

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)

    def __str__(self):
        return self.tag

# 文章的标签
class TagToArticle(models.Model):
    # 自增主键, 这里不能设置default属性，负责执行save的时候就不会新增而是修改元素
    id = models.AutoField(primary_key=True)

    book = models.ForeignKey(Book,on_delete=models.CASCADE)

    tag = models.ForeignKey(Tag,on_delete=models.CASCADE)

    # 创建时间 auto_now_add：只有在新增的时候才会生效
    createTime = models.DateTimeField(auto_now_add=True)
    def __str__(self):
        return self.tag.tag+" "+self.book.bookName








