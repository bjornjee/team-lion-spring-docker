# team-lion-spring-docker

## Steps to setup your machine to run the spring boot program 
* clone this git repository on docker
* navigate to team-lion-spring-docker
* run command `docker-compose up`

## Endpoints

Type of request | endpoint | request body | output
--------------- | -------- | ---------- | ------
GET | /api/account | null | List of Accounts
GET	| /api/account/{username} | username | Account
POST | /api/account/register | username, password, email | Account token
PUT	| /api/account/{username} | username, password, email | null
DELETE | /api/account/{username} | username | null
GET | /api/market/?start=start,symbol=symbol,end=end,token=token | null  | List of stock info

## Description of application
* This rest service allows CRUD methods on the account entity. 
* To ensure that the user is registered, a token, an encrypted value of the username and password will be returned to the client upon registration
* The user will then have to input the token received before performing the GET request on /api/market endpoint, along with the other required parameters.

## Logic on /api/market endpoint
When the user inputs the stock symbol, start date, with format "YYYY-MM-DD", end date and token, the service will decrypt the token to get the username and password of the user and verify with the database if the user is registered. Once this is verified, the service layer will check if our database has the values of the requested stock. If so, return to the client. If not, our program will internally run the python script to scrape the missing data from yahoo finance and populate our mongoldb database. Once this completed, our service layer will then fetch the relevant information and return it to the client.

## What we managed to get working
* We manage to containerize our mongodb, python and spring application code
* We created a rest service for our python file using flask to communicate with our spring boot applciation internally
* We utilized project lombok to clean our spring code

## What we did not manage to do
* Did not have time to deploy on openshift, and AWS

## Interesting information
* Internally, our spring boot appication does an internal rest api call to our python micro service, which scrapes Yahoo Finance for the relevant stock information and stores it into our mongo database
* Our Dockerfile-app file performs multistage build. It first builds our .jar file using maven, which is then used in our second build to run the application image
* We have multiple links to our spring boot applciation, one to mongodb container and the other to python-docker container
* We utilized RestTemplate class in Spring boot to call our python microsevice endpoint

## Work split
* Bjorn Jee - Spring, flask
* Chang Jia Li - Dockerfile-app
* Gabriela Nydia Tanoko - docker-compose, Dockerfile-python, Dockerfile-mongodb, flask
* Monica Saravana - Lombok implementation


