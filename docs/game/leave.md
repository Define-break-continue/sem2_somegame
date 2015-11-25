# leave

Удаляет пользователя из игры, фикирует его игровую статистику и ставит ему статус 0 в комнате.
Генерирует событие [gamer_leave](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/events/gamer_leave.md)

##HTTP METHODS
* `POST`

###Идентификатор метода
`31`

## Параметры

###Обязательные
    * `roomId` - целое беззнаковое. 
    * `gameId` - целое беззнаковое. 

Пример запроса:


Пример ответа:
