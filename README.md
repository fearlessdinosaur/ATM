# ATM
An ATM simulator built with spring and accessed via APIs. The current functionality consists of:
- Pin validation
- Cash withdrawals
- Balance checks, with max cash withdrawal amount
- Cash Parsing

## To Run - locally
To run locally simply navigate to the base directory and run `gradlew bootRun`
## To Run - Docker
To run this file as a docker image, go to the programs main directory via the terminal and enter the following command:  
`docker build --build-arg JAR_FILE=build/libs/*.jar -t atm-application .`  
**Note:** `gradlw Bootjar` must be run to create the jar prior to running this script
## Available APIs
- (GET) `/account/{id}`
- (POST) `/account/{id}/withdraw`
## Using Postman
The Docs folder *(ATM/docs/)* contains the necessary postman payloads.
