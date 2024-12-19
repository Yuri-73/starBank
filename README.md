# 1. Содержание проекта
Проект Банк Звезда предназначен для предоставления рекомендаций продукции пользователю в зависимости от использования ранее услуг. Стек технологий: Spring, PostgreSQL, Swagger, JDBS, JPA, Hibernate, Telegram bot.

# 2. Архитектура приложения
Диаграмма, расположенная в корне проекта, представляет собой архитектуру Проекта. 

В контроллерах мы принимаем данные для обработки. В них реализуется логика, соответствующая трём этапам получения технического задания для Проекта:

## Этап 1: 
Проверка общей функциональности Проекта, при которой проверяется валидная работа с подключенной БД Н2; 
## Этап 2: 
Работа с динамическими правилами из БД PostgreSQL для выдачи рекомендаций пользователю по применению продуктов банка.
## Этап 3: 
Telegram bot обеспечивает выдачу рекомендаций по имени пользователя. Сервис осуществляет статистику срабатывания динамических правил.
***
_Подробное описание функционирования Сервиса изложено в инструкции, размещённой в [Wiki](https://github.com/Yuri-73/starBank/wiki)._
***
## 3. Развертывание приложения
* Для развертывания Проекта необходима база данных PostgreSQL и H2, среда разработки IntelliJ IDEA, java 17.
* Необходимо клонировать приложение себе на ПК. Это можно сделать с помощью git: 
* Открываем папку, в которую хотим клонировать проект и нажимаем правой кнопкой мыши на пустое место в папку и выбираем Open Git Bach here: image
Откроется консоль в которой необходимо прописать (git clone https://github.com/Yuri-73/starBank.git): image
* Ссылку на проект можно взять в git: image
* После клонирования репозитория необходимо запустить среду разработки и открыть там файл.
* После этого необходимо создать базу данных в PostgreSQL с названием bankStar, ролью student и паролем chocolatefrog. Также все параметры по созданию базы данных можно поменять, но при этом не забыть поменять и в самом application.properties: image
## 4. Сборка проекта
* В правом верхнем углу требуется нажать на букву m. 
* В spring-bankStar требуется нажать на clean, а потом на package. Это сначала очистит папку target, а потом соберет проект в jar файл.
image
## 5. Разработчики:
1. [Владимир Ходаковский](https://github.com/Chowo);
2. [Юрий Федейкин](https://github.com/Yuri-73).