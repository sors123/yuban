# Generated by Django 2.2.6 on 2019-12-26 22:58

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('home', '0008_tag_tagtoarticle_userinfo'),
    ]

    operations = [
        migrations.AddField(
            model_name='book',
            name='isbn',
            field=models.CharField(default='', max_length=20, verbose_name='isbn'),
        ),
    ]
