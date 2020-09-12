# team-lion-spring-docker

## Steps to setup your machine to run the spring boot program 
1. Download mongoldb on your machine
2. Create a Collection named teamlion
3. Change the following fields in application.properties file
    1. pythonExeFilePath = *The absolute file path to python.exe in the venv folder of the python folder of this project*
	2. pythonFilename = *The absolute file path to Scrapper.py in the python folder of this project*

## Endpoints

Type of request | endpoint | Parameters | output
--- | --- | --- | --- | ---
GET | /api/account | null | List of Accounts
GET	| /api/account/{username} | username | Account
POST | /api/account/register | username | Account
- |  | password | token
- |  | email | 
PUT	| /api/account/{username} | username | null
- |  | password
- |  | email
DELETE | /api/account/{username} | username | null
GET | /api/market/ | symbol | List of stock info
- |  | start
- |  | end
- |  | token		

## Description of application
1. This rest service allows CRUD methods on the account entity. 
2. To ensure that the user is registered, a token, an encrypted value of the username and password will be returned to the client upon registration
3. The user will then have to input the token received before performing the GET request on /api/market endpoint, along with the other required parameters.

## Logic on /api/market endpoint
When the user inputs the stock symbol, start date, with format "YYYY-MM-DD", end date and token, the service will decrypt the token to get the username and password of the user and verify with the database if the user is registered. Once this is verified, the service layer will check if our database has the values of the requested stock. If so, return to the client. If not, our program will internally run the python script to scrape the missing data from yahoo finance and populate our mongoldb database. Once this completed, our service layer will then fetch the relevant information and return it to the client.

## Work split
Task 1: Jia Li
Task 2: Monica
Task 3: Bjorn