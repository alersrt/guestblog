# Guest Blog
Guest blog with using such technology stack as Spring MVC, Spring Data, MongoDB and Spring Boot for autoconfiguration. You can to see the application [here][1].

The main goal of this application is studying technology stack which was specified above.


## REST API

Controller of this application serves several endpoints. These endpoints will to listed below.

Main endpoint of this API is `/api`. 

### `POST /user/login` 

In response for user's login. Receives `credentials` of that user by which we want to login and returns information about success of this operation

```js
credentials = {
  username: "<specify username>",
  password: "<specify password>"
}
```

### `POST /user/logout`

In response for user's logout.

### `POST /user/add`

Adding of the new user and uses for registration. Gets user's data and returns information about success of this operation.

```js
user = {
  username: "<>",
  password: "<>",
  email: "<>"
}
```

### `POST /user/del`

Removes existing user. Gets user id and returns success result.

```js
user = {
  id: "<>"
}
```

### `POST /post/add`

Adds new post on board. Gets such params:
```js
post = {
  title: "<>",
  test: "<>"
}
```

### `POST /post/del`

Removes post from board. It has the next parameters:
```js
post = {
  id: "<>"
}
```




[1]: https://guestblog.herokuapp.com/