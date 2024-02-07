# Effective Mobile test project

Тестовое задание - разработать простую систему управления задачами. Система обеспечивает создание, редактирование, удаление и просмотр задач.

### Запуск приложения

Чтобы запустить приложение необходимо
* Скачать или клонировать репозиторий (git clone https://github.com/shumitum/em-test-project.git)
* Собрать проект командой
```bash
mvn clean package
```
* После успешной сборки запустить контейнеры командой
```bash
docker-compose up
```
* Токен для доступа в Swagger UI будет выведен в консоль приложения (AUTHORIZATION TOKEN), <br/> 
или же токен можно запросить POST запросом по адресу http://localhost:8080/auth/auth с телом запроса <br/>
  { <br/>
  "email": "mail@mail.com", <br/>
  "password": "123456" <br/>
  } <br/>

####  Проверка работы
Swagger UI для проверки работы API будет доступен по ссылке http://localhost:8080/swagger-ui/index.html

 Отдельно OPEN-API спецификация в формате JSON доступна по [ссылке](https://github.com/shumitum/em-test-project/tree/main/docs).
