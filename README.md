# Guest Blog
Guest blog with using such technology stack as Spring WebFlux, Spring Data, MongoDB and Spring Boot for autoconfiguration. You can to see the application [here][1]. This service has the next features:
- REST-API
- reactive code
- simple web-client with using of [ReactJS][2]
- stores images in the [GridFS][3]

The main goal of this application is studying technology stack which was specified above.



## REST API


### `GET /messages/`

Returns array of all messages in the next format (for example):
```json
{"messages": [
  {
    "id" : "5b20476bd8904e3cfea7c51c",
    "timestamp": "2018-06-12T07:25:54.438",
    "isEdited" : false,
    "title" : "The new message",
    "text" : "A text of the new message"
  }, 
  {
    "id" : "5b2047b0d8904e3cfea7c51d",
    "timestamp": "2018-06-13T05:21:31.798",
    "isEdited" : false,
    "title" : "The second message",
    "text" : "A text of the second message"    
  }
]}
```
If blog does not contain any message then `204` status code will have returned.


### `GET /messages/{id}`

Returns message by its id. In success case `200` status code will have returned and `204` (message not found) or `500` (hard exception) in otherwise.


### `POST /messages/`

Adds messages in this blog. Format of the `PUT` request has the next view:
```json
{
  "title": "A title of the message",
  "text": "A text of the message",
  "file": "data:image/png;base64,iVBORw0KGgoAA..."
}
```
Field `file` contains [Base64][4] representation of a loaded file.

As answer service gives id of the added message:
```json
{
  "id": "5b2047b0d8904e3cfea7c51d"
}
```


### `DELETE /messages/{id}`

Removes message by its id. In success case `200` status code will have returned and `500` in otherwise.


### `GET /files/{fileName}`

Get file by its name.




[1]: https://guestblog.herokuapp.com
[2]: https://reactjs.org/
[3]: https://docs.mongodb.com/manual/core/gridfs/
[4]: https://en.wikipedia.org/wiki/Base64
