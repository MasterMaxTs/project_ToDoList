# Job4j_todo
#### Сервис "Сайт_Список дел"
[![Build Status](https://app.travis-ci.com/MasterMaxTs/project_ToDoList.svg?branch=master)](https://app.travis-ci.com/MasterMaxTs/project_ToDoList)
[![codecov](https://codecov.io/gh/MasterMaxTs/project_ToDoList/branch/master/graph/badge.svg?token=BQCYLDCTWL)](https://codecov.io/gh/MasterMaxTs/project_ToDoList)

![](https://img.shields.io/badge/java-11-4AB197)&nbsp;&nbsp;&nbsp;<br>
![](https://img.shields.io/badge/maven-3.6.3-4AB197)&nbsp;&nbsp;&nbsp;<br>
![](https://img.shields.io/badge/maven--checkstyle--plugin-3.1.1-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/maven--javadoc--plugin-3.2.0-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/liquibase--maven--plugin-4.15.0-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/jacoco--maven--plugin-0.8.6-4AB197)&nbsp;&nbsp;&nbsp;<br>
![](https://img.shields.io/badge/spring--boot--starter--web-2.7.13-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/spring--boot--starter--thymeleaf-2.7.13-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/hibernate--core-5.6.11-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/lombok-1.18.22-4AB197)&nbsp;&nbsp;&nbsp;<br>
![](https://img.shields.io/badge/DBMS:_PostgreSQL-14.0-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/Style:_bootstrap-4.4.1-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/Style:_html-5-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/Style:_css-3-4AB197)&nbsp;&nbsp;&nbsp;<br>
![](https://img.shields.io/badge/Test:_junit-4.13.2-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/Test:_hamcrest--all-1.3-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/Test:_mockito--core-4.9.0-4AB197)&nbsp;&nbsp;&nbsp;
![](https://img.shields.io/badge/Test:_h2database-2.1.214-4AB197)&nbsp;&nbsp;&nbsp;<br><br>
![](https://img.shields.io/badge/Package:-.war-4AB197)&nbsp;&nbsp;&nbsp;

### Это проект по созданию сайта "Список Дел", доступного в браузере.

Чтобы начать использовать функционал сайта, новому пользователю необходимо
пройти регистрацию:
   > Вкладка навигационного меню: _Регистрация_
   > * Ввод имени;
   > * Ввод login;
   > * Ввод Password;
   > * Уточнение местонахождения пользователя: выбор часового пояса на территории РФ
       (_опция отвечает за корректное отображение локального времени создания задачи пользователю_)
   
#### Данный проект позволит зарегистрированным пользователям:
1. Просматривать весь список личных дел (задач)
   > Вкладка навигационного меню: _Все_

2. Публиковать свои задачи
   > Кнопка: _Добавить задание_

3. Просматривать карточку задачи
   > Нажатие ЛКМ на иконку  ![img.png](img/icon_fa-eye.JPG) в графе _Название_

4. Редактировать личные задачи
   > * Изменить содержимое: кнопка _Отредактировать_
   > * Изменить статус выполнения: кнопка _Выполнено_
   > * Удалить задачу: кнопка _Удалить_

5. Просматривать список выполненных задач
   > Вкладка навигационного меню: _Выполненные_

6. Просматривать список новых задач
   > Вкладка навигационного меню: _Новые_

7. Редактировать регистрационные данные профиля
   > Вкладка навигационного меню: _Профиль_

#### Администратору сайта доступно:
1. Просмотр сводных данных у зарегистрированных на сайте пользователей 
   > Вкладка навигационного меню: _Задачи пользователей_

2. Удалять профиль пользователя с очисткой связанных с ним данных
   > Нажатие ЛКМ на иконку  ![img.png](img/icon_fa-trash.JPG) в графе _User_login_
---
### Стек технологий

- Java 11
- Maven 3.6.3
- Liquibase-maven-plugin v.4.15.0
- Spring-boot-starter-web v.2.7.3
- Spring-boot-starter-thymeleaf v.2.7.3
- Bootstrap v.4.4.1
- Hibernate-core v.5.6.11
- Lombok v.1.18.22
- СУБД: PostgreSQL v.14.0
<br/><br/>
- Тестирование:
   - junit v.4.13.2
   - hamcrest v.1.3
   - mockito-core v.4.9.0
   - БД: h2database v.2.1.214

---
### Требования к окружению
- Java 11
- Maven v.3.6.3
- PostgreSQL v.14.0

---
### Запуск проекта
1. Установить СУБД PostgreSQL


2. Создать базу данных с именем todo:
   ```create database todo;```


3. Скачать файлы проекта с github по ссылке и разархивировать в выбранную директорию:<br>
   [https://github.com/MasterMaxTs/project_ToDoList/archive](https://github.com/MasterMaxTs/project_ToDoList/archive/refs/heads/master.zip)


4. Перейти в директорию проекта, открыть командную строку и выполнить команды:
- Для <ins>первого</ins> запуска приложения выполнить последовательно команды:
    - ```mvn package -Pproduction -Dmaven.test.skip=true```
    - ```java -jar target/todo-1.0.war```
    - внизу окна командной строки скопировать в буфер обмена url:
      <br>http://localhost:8080/index


- Для <ins>последующего</ins> запуска приложения выполнять команду:
    - ```java -jar target/todo-1.0.war```
    - внизу окна командной строки скопировать в буфер обмена url:
      <br>http://localhost:8080/index



5. Вставить из буфера обмена url в адресную строку браузера:<br>
   [http://localhost:8080/index](http://localhost:8080/index)



6. В базу данных пользователей сайта добавлена одна учётная запись пользователя в роли Администратор.

   > администратору сайта необходимо выполнить вход в систему со следующими учётными данными и сменить пароль
   > * логин: _admin_
   > * пароль: _adm123_
> По умолчанию часовой пояс установлен _Europe/Moscow +3ч._

---
### Взаимодействие с приложением

1. Вид главной страницы приложения

    ![img.png](img/index.JPG)


2. Вид страницы регистрации нового пользователя

    ![img.png](img/user_registration.JPG)


3. Вид страницы неуспешной регистрации нового пользователя

   ![img.png](img/user_registration_fail.JPG)


4. Вид страницы успешной регистрации нового пользователя

   ![img.png](img/user_registration_success.JPG)


5. Вид страницы авторизации пользователя

   ![img.png](img/user_authorization.JPG)


6. Вид страницы не успешной авторизации пользователя

   ![img.png](img/user_authorization_invalid.JPG)

   
7. Вид страницы приложения по добавлению нового задания

   ![img.png](img/item_new.JPG)


8. Вид страницы приложения с подробным описанием задания

   ![img.png](img/item_edit.JPG)


9. Вид страницы приложения для редактирования задания

    ![img.png](img/item_update.JPG)


10. Вид страницы приложения со всем списком заданий:

    ![img.png](img/item_all.JPG)


11. Вид страницы приложения с выполненным заданием:

    ![img.png](img/item_done_edit.JPG)


12. Вид страницы приложения со списком новых заданий:

    ![img.png](img/item_all_new.JPG)


13. Вид страницы приложения со списком выполненных заданий:

    ![img.png](img/item_all_done.JPG)


14. Вид страницы приложения со списком всех зарегистрированных на сайте пользователей и сводной информацией о них:

    ![img.png](img/user_all.JPG)

---
### Контакты
* Email: java.dev-maxim.tsurkanov@yandex.ru
* Skype: https://join.skype.com/invite/ODADx0IJ3BBu
* VK: https://m.vk.com/id349328153
* Telegram: matsurkanov
