#!/bin/sh
echo 'craete user'
curl \
-u superuser:superuser \
-H "Content-Type: application/json" \
-d '{"fullName":"Makar","emailAddress":"makar@yandex.ru","enable":"false","password":"qwerty","roles":[{"name":"ROLE_ADMINISTRATOR"}]}' \
-X PUT http://172.28.146.32:8080/jasperserver-pro/rest_v2/users/makar