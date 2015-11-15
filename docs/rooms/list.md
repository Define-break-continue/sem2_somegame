# list
Возвращает информацию список комнат.<br>
По умолчанию возвращает
- количество комнат,
- количество готовых к игре пользователей в комнатах,
- количество мест в каждой комнате,
- создателя комнаты.
- время игры комнатах.

##HTTP METHODS
* `GET`

###Идентификатор метода
`45`

## Параметры
    
###Опциональные
* users - boolean.

    Если `true` возвращается массив с пользователями, находящимися в комнате.

Пример запроса:
```json
{
    "method":"info",
    "params": {
        "users":true
    }
}
```

Пример ответа:
```json
{
    "code":"0",    
    "response": {
        "rooms_count":1,
        "max_count":5,
                "rooms":[{
                         "roomId":100,
                         "max_count":6,
                         "users_count":3,
                         "ready_users":2,
                         "users":[{
                                "name":"Dmitry", 
                                "last_name":"Volishin",
                                "ready":true
                            }, 
                                 {   
                             "name":"Vasya", 
                             "last_name":"Pupkin",
                             "ready":true
                            }, 
                                {   
                             "name":"Peter",
                             "last_name":"Zaitsev",
                            }
                         ]                
                }]
    }
}
```