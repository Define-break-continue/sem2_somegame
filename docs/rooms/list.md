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
         "rooms":[{
             "roomId":100,
             "max_users":6,
             "count_users":3,
             "ready_users":2,
             "users":[{
                        "name":"Helbert", 
                        "last_name":"Shield",
                        "ready":true
                      }, 
                      {   
                         "name":"Vasya", 
                         "last_name":"Pupkin",
                         "ready":true
                      }, 
                      {   
                         "name":"Harry",
                         "last_name":"Potter",
                      }
             ]                
         }]
    }
}
```