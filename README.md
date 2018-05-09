# Guest Blog
Guest blog with using such technology stack as Spring MVC, Spring Data, MongoDB and Spring Boot for autoconfiguration. You can to see the application [here][1].

The main goal of this application is studying technology stack which was specified above.


## REST API

Controller of this application serves several endpoints. These endpoints will to listed below.

Main endpoint of this API is `/api`. 

### `GET /users`

Returns full list of the all users.

### `GET /posts`

Returns full list of the all posts.

### `POST /users/login` 

In response for user's login. Receives `credentials` of that user by which we want to login
```json
{
  "username": "<>",
  "password": "<>"
}
```
 and returns information about success of this operation and user id
 ```json
{
  "success": "<>",
  "id": "<>"
}
```

### `POST /users/logout`

In response for user's logout.

### `PUT /users/`

Adding of the new user and uses for registration. Gets user's data
```json
{
  "username": "<>",
  "password": "<>",
  "email": "<>"
}
```
 and returns information about success of this operation
 ```json
{
  "success": "<true/false>"
}
```

### `DELETE /users/{id}`

Removes existing user by its id.

### `PUT /posts/`

Adds new post on board. Gets such params:
```json
{
  "title": "<>",
  "text": "<>"
}
```
and returns success of this operation and post id:
```json
{
  "success": "<true/false>",
  "id": "<>"
}
```

### `DELETE /posts/{id}`

Removes post from board by its id.




[1]: https://guestblog.herokuapp.com/
