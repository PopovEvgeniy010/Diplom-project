# План автоматизации
## 1. Перечень автоматизируемых сценариев
   #### Ввод валидных значений
   - Обычная покупка картой А (валидные данные)
   - Покупка в кредит картой А (валидные данные)
   - Обычная покупка картой B (валидные данные)
   - Покупка в кредит картой B (валидные данные)
   #### Ввод не валидных значений
   - Обычная покупка картой А (невалидные данные:пустые поля,ввод цифр в буквенные поля,ввод букв в поля только с цифрами)
   - Покупка в кредит картой А (невалидные данные:пустые поля,ввод цифр в буквенные поля,ввод букв в поля только с цифрами)
   - Обычная покупка картой B (невалидные данные:пустые поля,ввод цифр в буквенные поля,ввод букв в поля только с цифрами)
   - Покупка в кредит картой B (невалидные данные:пустые поля,ввод цифр в буквенные поля,ввод букв в поля только с цифрами) 
   #### Tесты
   - Проверка поддержки MySQL
   - Проверка поддержки Postgres
   - Проверка корректности данных записанных в БД
## 2. Перечень используемых инструментов  

   * IntelliJ IDEA.
   * JUnit. 
   * Gradle.
   * Selenide. 
   * Allure. 
   * Docker
   * Git и Github.
   * Windows
 
## 3. Перечень и описание возможных рисков при автоматизации
 
* Риск появления проблем с настройкой приложения и необходимых БД.
* Риск появления проблем с правильной идентификацией полей ввода. 
* Риск неработающего заявленного функционала приложения.

## 4. Интервальная оценка с учётом рисков (в часах)
* Подготовка окружения, БД, настройка проекта - 12 часов.
* Написание автотестов, тестирование и отладка автотестов -  30 часов.
* Формирование и подготовка отчетов - 6 часов. 
 
## 5. План сдачи работ (когда будут авто-тесты, результаты их прогона и отчёт по автоматизации)
* с 10.07.2023 по 13.07.2023 - планирование автоматизации тестирования.
* С 13.07.2023 по 30.07.2023 - настройка окружения, написание и отладка автотестов, тестирование.
* C 30.07.2023 по 06.08.2023 - подготовка отчетных документов.
