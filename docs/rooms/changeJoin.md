# changeJoin

Подключает/отключает пользователя к/от комнате(ы) соответственно.

##HTTP METHODS
* `POST`

###Идентификатор метода
`43`

## Параметры

###Обязательные
    
* `userId` - целое беззнаковое. 
* `roomId` - целое беззнаковое. 
* `join` - boolean.

Пример запроса:
```json
{
    "method":"join",
    "params": {
        "userId":123,
        "roomId":100,
        "join":true
    }
}
```

Пример ответа:
```json
{
    "code":"0",
    "response": {
        "userId":123,
        "roomId":100,
        "join":true
    }
}
```