#!/bin/sh
echo 'get data'
curl \
-u superuser:superuser \
-H "Accept: application/json" \
-X GET http://172.28.146.32:8080/jasperserver-pro/rest_v2/resources \
> result.json

