# Отчет о проведенном тестировании
## Краткое описание
**Было проведено тестирование комплексного сервиса, взаимодействующего с СУБД и API Банка.**

Приложение представляет собой веб-сервис, который предоставляет возможность купить тур по определённой цене с помощью двух способов:
1. Обычная оплата по дебетовой карте
1. Уникальная технология: выдача кредита по данным банковской карты

**На начальном этапе** было проведено **исследовательское (ручное) тестирование** для ознакомления с проектом.  
**На следующем этапе** были созданы **авто-тесты**, согласно [Плана автоматизации тестирования](https://github.com/Viktor2491/QA-Diplom/blob/master/documents/Plan.md).

**Тестирование было проведено для двух баз данных - MySQL и PostgreSQL.**

## Перечень выявленных ошибок
[Ошибки](https://github.com/Viktor2491/QA-Diplom/issues)

## Количество тест-кейсов (успешных/не успешных)
* Количество тест-кейсов - 38(100%)
* Упешные тест-кейсы - 26(68,42%)
* Не успешных - 12(31,57%)
 
## Подготовлены отчёты:
* [Отчёт Allure CreditPurchaseTest](https://github.com/Viktor2491/QA-Diplom/issues/9)
* [Отчёт Allure DebitPurchaseTest](https://github.com/Viktor2491/QA-Diplom/issues/10)
## Общие рекомендации:
* Создать документацию для данного приложения;
* Добавить функциональность блокирования кнопки "Продолжить" до тех пор, пока все поля не будут заполнены корректными значениями;
* Добавить изменение цвета кнопок "Купить" и "Купить в кредит" при переключении между двумя вкладками для удобства пользователя;
* Заменить предупреждения "Неверный формат" на более информативные;
* Исправить выявленные ошибки.
