# Guest Blog
Guest blog with using such technology stack as Spring MVC, Spring Data, MongoDB and Spring Boot for autoconfiguration. You can to see the application [here][1]. This service has the next features:
- REST-API
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
    "timestamp" : {
      "date" : {
        "year" : 2018,
        "month" : 6,
        "day" : 13
      },
      "time" : {
        "hour" : 5,
        "minute" : 21,
        "second" : 31,
        "nano" : 798000000
      }
    },
    "isEdited" : false,
    "title" : "The new message",
    "text" : "A text of the new message"
  }, 
  {
    "id" : "5b2047b0d8904e3cfea7c51d",
    "timestamp" : {
      "date" : {
        "year" : 2018,
        "month" : 6,
        "day" : 13
      },
      "time" : {
        "hour" : 6,
        "minute" : 12,
        "second" : 54,
        "nano" : 867000000
      }
    },
    "isEdited" : false,
    "title" : "The second message",
    "text" : "A text of the second message"    
  }
]}
```
If blog does not contain any message then `204` status code will have returned.


### `PUT /messages/`

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


### `DELETE /messages/`

Removes message by it's id:
```json
{
  "id": "5b2047b0d8904e3cfea7c51d"
}
```

In success case `200` status code will have returned and `500` in otherwise.





[1]: https://guestblog.herokuapp.com/
[2]: https://reactjs.org/
[3]: https://docs.mongodb.com/manual/core/gridfs/
[4]: https://en.wikipedia.org/wiki/Base64
