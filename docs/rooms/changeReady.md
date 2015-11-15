# changeReady

Меняет статус готовности пользователя к игре.

##HTTP METHODS
* `POST`

###Идентификатор метода
`44`

## Параметры

###Обязательные
    
* `userId` - целое беззнаковое. 
* `roomId` - целое беззнаковое. 
* `ready` - boolean.

Пример запроса:
```json
{
    "method":"join",
    "params": {
        "userId":123,
        "roomId":100,
        "ready":true
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
        "ready":true
    }
}
```