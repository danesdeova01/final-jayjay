# Final Project - API & Web Testing Automation

## Deskripsi
Project ini berisi automasi testing untuk API dan Web yang dikembangkan menggunakan Java, Cucumber, dan Selenium WebDriver.  
Testing API menggunakan Rest-Assured dan testing Web menggunakan Selenium WebDriver untuk menguji fungsionalitas login, logout, dan interaksi web pada situs target.

## Tools dan Library yang Digunakan

- **Java 17** sebagai bahasa pemrograman utama
- **Gradle** sebagai build tool dan dependency management
- **Cucumber** untuk BDD (Behavior Driven Development) testing framework
- **Rest-Assured** untuk automasi testing REST API
- **Selenium WebDriver** dengan ChromeDriver untuk automasi browser testing
- **WebDriverManager** untuk manajemen otomatis driver browser
- **JUnit** sebagai test runner
- **GitHub Actions** untuk Continuous Integration (CI) menjalankan test secara otomatis dan manual

## Cara Menjalankan Test

### Menjalankan Test API
Jalankan perintah berikut di terminal pada direktori root project : ./gradlew api

### Menjalankan Test Web (Selenium)
Jalankan perintah berikut di terminal : ./gradlew web
