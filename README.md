# Credit
## About The Project

A **Spring Boot** application which is used to serve **Rest APIs** and perform database operations. Used **JPA** to interact with **H2 database**. This project using **Swagger**, **Lombok** and **Java 17**.

## API DOCS

Type | API Request | Info |
|--|--|--|
| GET | customers |List of all customers|
| GET | customers/{customerId} |Show customer by id|
| POST | customers |Create customer with name, surname, tckn, phoneNumber, birthDate, salary and guaranteeAmount|
| PUT | customers/{customerId} |Update the customer's data|
| DELETE | customers/{customerId} |Remove customer (makes the status is passive)|
| GET | loan/{tckn}/{birthDate} |Show customer's loan info by customer's tckn and birthdate|
| POST | loan/apply/{tckn} |Apply loan by customer's tckn|
| POST | login |Take token by customer's tckn and birthdate|

### Clone The Repo:
``` $ git clone https://github.com/sezakocadere/credit.git ```

## Docker Images
Run ```docker build --tag=credit:latest .```  to build the application.

If you have already generated and reload it.

``` docker run -p 8080:8080 credit:latest``` 


## Request Examples
### Create Customer
Firstly customer need to be created, this step not required the token.
```
localhost:8080/customers [POST]
Request Body

{
    "name": "seza",
    "surname": "kocadere",
    "tckn": "20220220020",
    "phoneNumber": "5346571454",
    "birthDate": "1996-07-02",
    "salary": 12500,
    "guaranteeAmount": 2000
}


Response Body

{
    "id": 1,
    "name": "seza",
    "surname": "kocadere",
    "tckn": "20220220020",
    "phoneNumber": "5346571454",
    "birthDate": "1996-07-02",
    "salary": 12500,
    "status": "ACTIVE",
    "guarantee": true,
    "guaranteeAmount": 2000
}
```

### Login Customer (Authentication Step)
Login of customer then returned bearer token.
```
localhost:8080/login [POST]
Request Body

{
    "tckn": "20220220020",
    "password": "1996-07-02"
}

Response Body
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDIxMzYxNTU5NCIsImV4cCI6MTY3NzQ3NDYzOCwiaWF0IjoxNjc3NDU2NjM4fQ.EEwMcplj5VE2ncDPJYvrLw4mXsfdlri5RHnA-WijpLo"
}
```

### Loan Info of Customer (after apply loan)
Show loan info of customer, required authenticate with bearer token.

```
localhost:8080/loan/20220220020/1996-07-02 [GET]
Request Header
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDIxMzYxNTU5NCIsImV4cCI6MTY3NzQ3NDYzOCwiaWF0IjoxNjc3NDU2NjM4fQ.EEwMcplj5VE2ncDPJYvrLw4mXsfdlri5RHnA-WijpLo

Response Body

{
    "id": 2,
    "loanStatus": "APPROVED",
    "loanScore": 806,
    "loanLimit": 25500.00,
    "applyDate": "2023-02-27T03:12:20.687463+03:00",
    "customer": {
        "id": 1,
        "name": "seza",
        "surname": "kocadere",
        "tckn": "20220220020",
        "phoneNumber": "5346571454",
        "birthDate": "1996-07-02",
        "salary": 12500.00,
        "status": "ACTIVE",
        "guarantee": true,
        "guaranteeAmount": 2000.00
    }
}
```

## H2 Memory Database
http://localhost:8080/h2-console/
```
username: sa

password: 1234567
```

## Swagger Integration
http://localhost:8080/swagger-ui/

## Scenario
Writing a restful application for a loan application system, which will take the loan application requests and return the loan result to the customer according to the relevant criteria, using the Spring Boot framework and optionally writing the frontend
- [x] With the ID number, name-surname, salary, phone information, date of birth and ID number, the loan score service is sent to the default loan score service and the loan score of the relevant person is obtained and the loan result is shown to the user according to the following rules. (There may be two options as Approved or Denied.)
- [x] New users can be defined in the system, existing customers can be updated or deleted.
- [x] If the loan score is below 500, the user is rejected. (Loan result: Denied)
- [x] If the loan score is between 500 points and 1000 points and salary is below 5000 TL, the user's loan application is approved and a limit of 10.000 TL is assigned to the user. (Loan Result: Approved).If he has given a guarantee, 10 percent of the amount of the guarantee is added to the loan limit.
- [x] If the loan score is between 500 points and 1000 points and the salary is between 5000 TL and 10,000 TL, the user's loan application is approved and 20,000 TL limit is assigned to the user. (Loan Result: Approved) If a guarantee has been given, 20 percent of the guarantee amount is added to the loan limit.
- [x] If the loan score is between 500 points and 1000 points and salary is above 10.000 TL, the loan application of the user is approved and the user is assigned a limit of SALARY * LOAN LIMIT MULTIPLIER/2. (Loan Result: Approved) If a guarantee is given, 25 percent of the guarantee amount is added to the loan limit.
- [x] If the loan score is equal to or above 1000 points, the user is assigned a limit equal to SALARY * LOAN LIMIT MULTIPLIER. (Loan Result: Approved) If the collateral is given, 50 percent of the collateral amount is added to the loan limit. As a result of the conclusion of the loan, the relevant application is recorded in the database. Afterwards, an informative SMS is sent to the relevant phone number and the approval status information (denied or approved), limit information is returned from the endpoint. A completed loan application can only be queried with the ID number and date of birth. If the date of birth and identity information do not match, it should not be questioned.
- [x] The backend project works correctly according to the specified rules (adding the readme file or writing the Dockerfile)
- [x] Paying attention to the quality (Clean Code), structuring (Structure) of the code, being open to development for new features and being understandable. (SOLID principles).
- [x] Write Tests
- [x] Using Design Patterns
- [x] Documentation (Swagger, OpenApi etc.)
- [x] Use of technologies such as NoSQL, RDBMS(ORM) and Hibernate (JPA)
- [x] Establishment of logging mechanism.

---
## Screenshot From Swagger UI
![Screenshot 2023-02-27 at 03 26 30](https://user-images.githubusercontent.com/38151013/221447007-35cfda76-74f5-4a60-8d3c-429953df704a.png)
