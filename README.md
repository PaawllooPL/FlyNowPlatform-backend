# FlyNow (back-end)

FlyNow was engineering thesis project that solves problem of connecting pilots with clients. Everyone can book flight or become organizer and offer others flights.</br>
See it at: https://flynow-mhj9.onrender.com/<br>
Example photos in README of front-end project: https://github.com/PaawllooPL/FlyNowPlatform-frontend

## Overview

Main purpose of project was to create web application that specifically (and only) helps people find sightseeing flights offers.
Unlike many others it focuses clearly on aviation part of business instead of aggregating all types of market offers.

Project is based on <b>Clean Architecture</b> and anemic <b>DDD</b>. It uses Spring Security framework to provide authentication and authorization, keeping endpoints secure from unwanted actions.

## Architecture

Project consists of 5 sub-modules:

* <b>application</b> - contains Main class, registers all beans and consume  configuration files
* <b>api</b> - controllers, endpoint security, custom requestFilter to check and retrieve JWT
* <b>service</b> - UseCases and services implementing them, command objects, repository interfaces (CQRS), readmodels
* <b>infrastructure</b> - DB entities, startup dataloader, implementations of Command and Query repo interfaces (IoC), jpa interfaces with custom SQL, Db projection models
* <b>domain</b> - domain anemic models, Enums

## Technologies for back-end

* Java (21)
* Spring Boot (Web, Security, Data)
* Maven
* PostgreSQL
* JPA (Hibernate)
* JWT
* Docker

## Getting Started

### Requirements
* Java 21
* Maven 4.0.0</br>
---------------- OR -----------------
* Docker with linux container support

### Installation & running locally
Confirm config files in application layer's resources folder. Set database credentials and url, set JWT key and local disk location for storing images if needed other than default.

```bash
git clone https://github.com/PaawllooPL/FlyNowPlatform-backend.git
cd FlyNowPlatform-backend
mvn spring-boot:run
```
## Project Structure

Main source code is divided into components, dto, models, pages (utilizing components) and services. 

## Author

PaawllooPL 
* https://github.com/PaawllooPL
* https://gitlab.com/PaawllooPL