# Generated by Django 2.2.6 on 2019-12-01 21:37

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('home', '0005_auto_20191201_1622'),
    ]

    operations = [
        migrations.CreateModel(
            name='Status',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('type', models.CharField(choices=[('read', 'read'), ('comment', 'comment'), ('status', 'status')], max_length=10)),
                ('createTime', models.DateTimeField(auto_now_add=True)),
                ('modifyTime', models.DateTimeField(auto_now=True)),
                ('text', models.CharField(blank=True, default='', max_length=400, verbose_name='动态内容')),
                ('book', models.ForeignKey(blank=True, on_delete=django.db.models.deletion.CASCADE, to='home.Book')),
                ('comment', models.ForeignKey(blank=True, on_delete=django.db.models.deletion.CASCADE, to='home.Comment')),
                ('read', models.ForeignKey(blank=True, on_delete=django.db.models.deletion.CASCADE, to='home.Read')),
                ('user', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
