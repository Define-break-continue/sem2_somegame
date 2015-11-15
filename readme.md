#API Documentation
Протокол взаимодействия между сервером и клиентом.

#Схема взаимодействия
Клиент отправляет POST или GET запрос на соответствующий URL с указанием ID метода (если URL имеет набор методов).<br>
POST запросы должны отправлять параметры в формате JSON.<br>
Каждый метод может иметь набор обязательных и опциональных параметров.<br>
Если URL имеет методы, то необходимо передать параметры.<br>
###Обязательные
* ```methodId``` - идентификатор метода. Целое беззнаковое число.<br>
                   Для POST запроса ```methodId``` передается в URL'e (ourhost.ru/rooms/20)
* ```params``` - в случае POST запроса набор параметров для метода в формате JSON c именем `params`. 

###Опциональные
* ```method``` - имя метода. Строка.

Пример запроса для метода POST в общем случае:

```json
{
    "methodId": 25,
    "method":"edit",
    "params": {
        "key":"value",
        ...
    }
}
```

В ответ сервер отправляет JSON объект с кодом [ошибки] (https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/errors.md) и данными.

```json
{
    "code": 0,
    "response": {
        "some_key":"some_value",
        ...
    }
}
```

##[/signup] (https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/signup.md)
URL для регистрации пользователя.


##[/signin] (https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/signin.md)
URL для авторизации пользователя.


##/info
URL для методов для получения информации.

* [scoreboard](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/scoreboard.md)
     `methodId` - 10
* [userinfo](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/userinfo.md)
     `methodId` - 11


##/user
URL для методов работы с учетной записью.

* [changePass](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/user/changePass.md)
     `methodId` - 20
* [edit](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/useredit.md)
     `methodId` - 21


##/game
Url для обмена данными во время игры.

* [getField](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/game/getField.md)
     `methodId` - 30
* [move](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/game/move.md)
     `methodId` - 31


##/rooms
URL для методов работы с комнатами.

* [create](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/create.md)
     `methodId` - 40
* [delete](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/delete.md)
     `methodId` - 41
* [info](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/info.md)
     `methodId` - 42
* [join](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/join.md)
     `methodId` - 43
* [leave](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/leave.md)
     `methodId` - 44
* [list](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/list.md)
     `methodId` - 45
* [ready](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/ready.md)
     `methodId` - 46
* [unReady](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/unReady.md)
     `methodId` - 47


##/admin
URL для админки.
Доступен только для пользователей-администраторов.
