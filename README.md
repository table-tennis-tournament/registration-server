# registration-server
a table tennis online registration for mysql db

# Dockerbuild

building command: ´docker build . -t holzleube/tournamentregistration:2.0´

tagging command: ´docker tag holzleube/tournamentregistration:latest holzleube/tournamentregistration:2.0´

starting command: ´docker run -d -p 8080:8080 --name tournament-registration -e MYSQL_JDBC=jdbcstring
-e MYSQL_USER=dbuser -e MYSQL_PW=supersecret
-e EMAIL_SERVER=smtpMail -e EMAIL_USER=smtpUser
-e EMAIL_PW=smtpPassword
holzleube/tournamentregistration:latest´
