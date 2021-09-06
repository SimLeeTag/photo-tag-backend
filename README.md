# Photo Tag - BackEnd Repository

![image](https://user-images.githubusercontent.com/58318786/96359589-80726000-114f-11eb-9e7a-c17f51b32cb1.png)

<br/>

## 📸 **Photo Tag**
* 쇼핑 리스트를 사진 한 장으로 정리하고 싶을 때, 매일의 운동 기록을 태그로 관리하고 싶을 때, 나만의 태그로 맛집 리스트를 정리하고 싶을 때, 포토태그 앱으로 기록하세요!
* 기본적인 CRUD 기능을 포함한 API를 서비스하는 어플리케이션 구현, 클라이언트와 협업하며 신뢰감 있는 서버가 되기 위해 필요한 요소들(테스트 코드를 기반으로 하는 API 문서 등)에 대해 학습하고 이후 수강한 스터디에서 새롭게 학습한 내용(Spring KeyHolder, Validation, TDD, OOP, Clean Code)을 반영하기 위해 전반적인 리팩토링 진행중


![](https://i.imgur.com/fSofvJD.jpg)
![](https://i.imgur.com/RT1h6N9.jpg)
![](https://i.imgur.com/LX6x5IJ.png)

[Team Repository](https://github.com/SimLeeTag/Team)

[![Build Status](https://travis-ci.com/SimLeeTag/photo-tag-backend.svg?branch=deploy)](https://travis-ci.com/SimLeeTag/photo-tag-backend)
![last commit](https://img.shields.io/github/last-commit/SimLeeTag/photo-tag-backend?color=5833C1)
![most language](https://img.shields.io/github/languages/top/SimLeeTag/photo-tag-backend)

<br/>

## 🛠 Technologies Used | 기술 스택
### BackEnd
* Java 8
* Spring Boot
* Spring Data JPA
* OAuth2.0
* LogBack
* JUnit5

### Data
* MySQL

### Infra
* NGINX
* AWS EC2
* AWS S3
* AWS RDS
* Travis CI

<br/>

## 🚀CI/CD
![CI/CD](https://user-images.githubusercontent.com/58318786/103171592-dc184280-4890-11eb-9a5d-cf062ef18742.jpg)

<br/>

## 📝ERD
![erd2](https://user-images.githubusercontent.com/58318786/99186112-5aad9a80-2791-11eb-8b8a-a7cba72f1531.png)


<br/>

## 📌 Convention
### Commit
>  Reference: http://karma-runner.github.io/1.0/dev/git-commit-msg.html

| Type | Contents |
|--|--|
|feat| new feature for the user, not a new feature for build script
|fix| bug fix for the user, not a fix to a build script
|docs| changes to the documentation
|refactor| refactoring production code, eg. renaming a variable
|style| formatting, missing semi colons, etc; no production code change
|test| adding missing tests, refactoring tests; no production code change)
|chore| updating grunt tasks etc; no production code change

- Example

    ```
    refactor: Refactor subsystem X for readability 

    {body...}

    Issue #1 or Resolves #1 // reference issues
    ```

### Branch - Git Flow
- default branch : `dev`
- `main`: production-ready state
- `dev`: latest delivered development changes for the next release
- `feat`: develop new features for the upcoming or a distant future release
- `deploy`: support preparation of a new production release
- `hotfix`: act immediately upon an undesired state of a live production version
- `{feat}/{feature}`
- Example

    ```
    feat/create-note
    ```
