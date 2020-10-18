# Photo Tag - BackEnd Repository

![image](https://user-images.githubusercontent.com/58318786/96359589-80726000-114f-11eb-9e7a-c17f51b32cb1.png)

<br/>

## 📸 **Photo Tag**
~~description~~
[Team Repository]()

![release](https://img.shields.io/github/v/release/SimLeeTag/photo-tag-backend?color=5833C1)
![last commit](https://img.shields.io/github/last-commit/SimLeeTag/photo-tag-backend)
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


<br/>

## 📝ERD
![Screenshot from 2020-10-18 22-26-03](https://user-images.githubusercontent.com/58318786/96368802-18446e00-1191-11eb-9760-804c95eed7f2.png)

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