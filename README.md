# Guest Blog
Guest blog with using such technology stack as Spring MVC, Spring Data, PostgreSQL and Spring Boot for autoconfiguration. You can to see the application [here][1]. This service has the next features:
- REST-API
- Cookie based authentication
- stores images in the database

The main goal of this application is studying technology stack which was specified above. Consider it just as laboratory mouse.



## 1. Environment variables
- `GB_SERVER_PORT` - port of this service
- `GB_POSTGRES_URL` - url for PostgreSQL database
- `GB_POSTGRES_USERNAME` - username of the database
- `GB_POSTGRES_PASSWORD` - password for this username



## 2. REST API

The documentation about restpoints available by the next path: `<this service url>/swagger-ui/index.html`. You can read more [about][5].

## Credentials

The default credentials are
- username: `admin`
- password: `admin`

Its possible to change it later.



[1]: https://guestblog.herokuapp.com/swagger-ui/index.html
[2]: https://reactjs.org/
[4]: https://en.wikipedia.org/wiki/Base64
[5]: https://springfox.github.io/springfox/docs/current/#springfox-swagger-ui
