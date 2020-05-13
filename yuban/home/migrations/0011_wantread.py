# Generated by Django 2.2.6 on 2019-12-27 23:35

from django.conf import settings
from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        migrations.swappable_dependency(settings.AUTH_USER_MODEL),
        ('home', '0010_auto_20191227_1812'),
    ]

    operations = [
        migrations.CreateModel(
            name='WantRead',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('createTime', models.DateTimeField(auto_now_add=True)),
                ('modifyTime', models.DateTimeField(auto_now=True)),
                ('wantreadBook', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='home.Book')),
                ('wantreadUser', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to=settings.AUTH_USER_MODEL)),
            ],
        ),
    ]
