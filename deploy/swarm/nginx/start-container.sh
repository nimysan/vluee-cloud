docker run -it -d --name mynginx -p 9999:80 -v ./conf.d:/etc/nginx/conf.d -v ./log:/var/log/nginx -v ./www:/var/www -v /etc/letsencrypt:/etc/letsencrypt nginx
