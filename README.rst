
**1. Description.**

Application serve simple REST API for tournament management.
Every tournament can have many seasons.
In single tournament's season participate many teams.
Team play matches during the season.

**2. Used technologies.**

Application was written using:

- Spring Boot 2
- Spring Data
- Spring Security

For store data was used MySQL database. MySQL database instance
should be running on port number 3306. Database will be automatically
created during application start.

**3. Available requests.**

**3.1. Create user.**

| Path: /user
| Method: post
| Payload:

  .. code-block:: JSON

    {
    	"login": "user_login",
    	"password": "user_password",
    	"email": "user@email.com"
    }
|
**3.2. Get all users.**

| Path: /users
| Method: get
|
**3.3 Find user by login.**

| Path: /user?login=<username>
| Method: get
|
**3.4. Create tournament.**

| Path: /tournament
| Method: post
| Payload:

  .. code-block:: JSON

    {
      "name": "tournament_name",
      "decription": "tournament_description"
    }

|
**3.5. Find tournament by id.**

| Path: /tournament/{id}
| Method: get
|
**3.6. Get all tournaments.**

| Path: /tournaments
| Method: get
|
**3.7. Create new team.**

| Path: /team
| Method: post
| Payload:

  .. code-block:: JSON

    {
      "name": "team_name"
    }

|
**3.8. Create new tournament season.**

| Path: /tournament/{tournamentId}/season
| Method: post
| Payload:

  .. code-block:: JSON

    {
      "beginDate": "yyyy-MM-ddTHH:mm:ss"
    }

|
**3.9. Get all tournament seasons.**

| Path: /tournament/{tournamentId}/seasons
| Method: get
|
**3.10. Create new tournament match.**

| Path: /season/{seasonId}/match
| Method: post
| Payload:

  .. code-block:: JSON

    {
      "beginDate": "yyyy-MM-ddTHH:mm:ss",
      "homeScore": "0",
      "awayScore": "0",
      "homeTeam": {
        "id": "0",
      },
      "awayTeam": {
        "id": "1",
      }
    }

|
**3.11. Get all tournament matches.**

| Path: season/{seasonId}/matches
| Method: get
|
**3.12. Get all matches.**

| Path: /matches
| Method: get
|