Tournament management simple restful API.
=========================================

1. Description.

Application serve simple REST API for tournament management.
Every tournament can have many seasons.
In single tournament's season participate many teams.
Team play matches during the season.

2. Used technologies.

Application was written using:

- Spring Boot 2
- Spring Data
- Spring Security

For store data was used MySQL database. MySQL database instance
should be running on port number 3306. Database will be automatically
created during application start.

3. Valid requests.

Below listed available application requests.

3.1. Create user.

Path: /user
Method: post
Payload:

{
	"login": "user_login",
	"password": "user_password",
	"email": "user@email.com"
}
