[![Build status](https://ci.appveyor.com/api/projects/status/y38ghoafycg7uby9?svg=true)](https://ci.appveyor.com/project/Viktor2491/qa-diplom)
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
	git clone https://github.com/Viktor2491/QA-Diplom.git
1. Перейти в созданный каталог командой:
cd Qa-Diplom -> cd QA-Diplom (Первая папка "Qa-Diplom" была создана в процесе "ДОЛГОЙ" настройки, поэтому оставил ее)
1. Создать и запустить необходимые Docker Container'ы командой:
	docker-compose -p qa-diplom up -d --force-recreate
	где:
	- -p qa-diplom - присваивает указанный префикс контейнерам (имя проекта);
	- -d - включает автономный режим (режим демона);
	- --force-recreate - принудительно пересоздает все контейнеры.
1. Клонировать проект на свой компьютер:
	- открыть терминал (командная строка) и ввести команду:	
		git clone https://github.com/Viktor2491/QA-Diplom.git
1. Открыть с клонированный проект в Intellij IDEA
