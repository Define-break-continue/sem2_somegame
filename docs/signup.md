#/signup
Регистрация пользователя. После регистрации пользователь считается авторизованным.

##HTTP METHODS
`POST`

## Параметры

###Обязательные
- type - ```unsigned byte ```
    тип учетки (см. [userinfo](https://github.com/Define-break-continue/sem2_somegame/tree/protocol/docs/info/userinfo.md))<br>
    значения:
    * 0 - по email
         * `email` - строка длинной до 40 символов.
         * `password1` - строка длинной до 30 символов.
         * `password2` - строка длинной до 30 символов.
         * `name` -  строка длинной до 30 символов.
         * `second_name` - строка длинной до 30 символов.
    * 1 - VK
         * `VK_id` - идентификатор пользователя.
         * `at` - access_token - токен от контакта получаемый при авторизации. Строка.
         * `name` - строка длинной до 30 символов.
         * `second_name` - строка длинной до 30 символов.
    * 2 - ??
    
    * 3 - ??
