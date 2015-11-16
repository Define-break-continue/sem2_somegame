#/signin
Авторизация пользователя.

##HTTP METHODS
`POST`

## Параметры

###Обязательные
- type - ```unsigned byte ```
    тип учетки (см. [userinfo](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/userinfo.md))<br>
    значения:
    * 0 - email
         * `email` - строка длинной до 40 символов.
         * `password` - строка длинной до 30 символов.
    * 1 - VK
         * `VK_id` - идентификатор пользователя.
         * `access_token` - токен от контакта получаемый при авторизации. Строка.
         * `name` - строка длинной до 30 символов.
         * `second_name` - строка длинной до 30 символов.
    * 2 - ??
    
    * 3 - ??
