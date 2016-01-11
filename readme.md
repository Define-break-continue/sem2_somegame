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
URL для методов для получения информации. Методы:

* [scoreboard](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/scoreboard.md)
     `methodId` - 10
* [userinfo](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/userinfo.md)
     `methodId` - 11


##/user
URL для методов работы с учетной записью. Методы:

* [changePass](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/user/changePass.md)
     `methodId` - 20
* [edit](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/useredit.md)
     `methodId` - 21


##/game
Url для обмена данными во время игры. Методы:

* [getField](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/game/getField.md)
     `methodId` - 30
* [move](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/game/move.md)
     `methodId` - 31

События

* [join](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/leave.md)
     `methodId` - 32
* [leave](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/join.md)
     `methodId` - 33
* [gameStarted](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/gameStarted.md)
     `methodId` - 34
* [gameOver](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/gameover.md)
     `methodId` - 35


##/rooms
URL для методов работы с комнатами. Методы:

* [create](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/create.md)
     `methodId` - 40
* [delete](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/delete.md)
     `methodId` - 41
* [info](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/info.md)
     `methodId` - 42
* [changeJoin](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/join.md)
     `methodId` - 43
* [changeStatus](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/changeStatus.md)
     `methodId` - 44
* [list](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/rooms/list.md)
     `methodId` - 45

##events
Запросы от сервера, которые получает Frontend при наступлении событий

* [bonus_generated](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/events/bonus_generated.md)

* [game_over](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/events/game_over.md)

* [gamer_leave](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/events/gamer_leave.md)

* [packmans_moved](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/events/packmans_moved.md)



##/admin
URL для админки.
Доступен только для пользователей-администраторов.
