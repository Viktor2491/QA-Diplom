[![Build status](https://ci.appveyor.com/api/projects/status/8g56qmac94cu8iai?svg=true)](https://ci.appveyor.com/project/Viktor2491/qa-diplom-30uv2)
# Процедура запуска автотестов

## Предварительные требования

1. Получить доступ к удаленному серверу

1. На удаленном сервере должны быть установлены и доступны:
	- GIT
	- Docker	
	- Bash
	 
1. На компьютере пользователя должна быть установлена:
	- Git Bash
	- Intellij IDEA

## Подготовка и запуск

1. Залогиниться на удаленный сервер
 
1. Клонировать проект на удаленный сервер командой:
    ```
    git clone https://github.com/Viktor2491/QA-Diplom.git
    ```
1. Перейти в созданный каталог командой:
    ```
    cd Qa-Diplom 
    ``` 
1. Создать и запустить необходимые Docker Container'ы командой:
    ```
    docker-compose up -d
    ```
1. Клонировать проект на свой компьютер:
    - открыть терминал (командная строка) и ввести команду:	
	```
        git clone https://github.com/Viktor2491/QA-Diplom.git
	```
1. Открыть с клонированный проект в Intellij IDEA

### Для работы с базой данных MySQL
**Проект пред настроен под работу с базой данных MySQL развернутой по ip-адресу 185.119.57.176.**

**Как изменить ip-адрес описано в разделе "Для работы с базой данных PostgreSQL", для MySQL - делается аналогично.**

1. Запустить SUT во вкладке Terminal Intellij IDEA командой:
	```
	java -jar artifacts/aqa-shop.jar
	```
	**Дождаться появления строки:**  
	`ru.netology.shop.ShopApplication         : Started ShopApplication in 17.116 seconds (JVM running for 19.968)`	
	
1. Для запуска авто-тестов в Terminal Intellij IDEA открыть новую сессию и ввести команду:
	```
	./gradlew clean test allureReport -Dheadless=true
	```
	(В моем терминале запускается вот такой командой: **gradlew clean test allureReport -Dheadless=true**)
	`где:`
	- `allureReport` - подготовка данных для отчета Allure;
	- `-Dheadless=true` - запускает авто-тесты в headless-режиме (без открытия браузера).
	 
1. Для просмотра отчета Allure в терминале ввести команду:
	```
	./gradlew allureServe
	```
 (В моем терминале запускается вот такой командой: **gradlew allureServe**)
 
### Для работы с базой данных PostgreSQL

1. В находящемся в проекте файле build.gradle в разделе test закомментировать строку ниже "//Для работы с базой данных mySQL" и раскомментировать строку ниже "//Для работы с базой данных postgreSQL", выглядеть будет так:
	```
       //Для работы с базой данных mySQL (со строки ниже необходимо снять комментарий):
	//systemProperty 'datasource', System.getProperty('datasource', 'jdbc:mysql://185.119.57.164:3306/base_mysql')
	//Для работы с базой данных postgreSQL (со строки ниже необходимо снять комментарий):
	systemProperty 'datasource', System.getProperty('datasource', 'jdbc:postgresql://185.119.57.164:5432/base_postgresql')
	```
	`где:`
	- `185.119.57.164` - ip-адрес удаленной машины с развернутой PostgreSQL, в случае необходимости заменить на ip-адрес своей удаленной машины с развернутой PostgreSQL.
	- 
1. Применить изменения (Ctrl+Shift+O);

1. Далее во вкладке Terminal Intellij IDEA запустить SUT командой:
	 ```
         java -jar artifacts/aqa-shop.jar
	 ```
	 
      **Дождаться появления строки:**
      `ru.netology.shop.ShopApplication         : Started ShopApplication in 17.116 seconds (JVM running for 19.968)`
      
1. Для запуска авто-тестов в Terminal Intellij IDEA открыть новую сессию и ввести команду:
	```
	./gradlew clean test allureReport -Dheadless=true
	```
	(В моем терминале запускается вот такой командой: **gradlew clean test allureReport -Dheadless=true**)
	`где:`
	- `allureReport` - подготовка данных для отчета Allure;
	- `-Dheadless=true` - запускает авто-тесты в headless-режиме (без открытия браузера).

1. Для просмотра отчета Allure в терминале ввести команду:
	```
	./gradlew allureServe
	```
   (В моем терминале запускается вот такой командой: **gradlew allureServe**)
   
 ## Закрыть отчёт:
        
        CTRL + C -> y -> Enter
         
## Перейти в первый терминал и остановить приложение:
        
        CTRL + C
	
## Остановить контейнеры:
       
        docker-compose down
	
 
