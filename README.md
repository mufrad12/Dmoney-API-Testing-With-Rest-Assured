# Dmoney-API-Testing-With-Rest-Assured
Dmoney API Testing With Rest Assured is a process of testing API endpoints of Dmoney using the Rest-Assured library in Java. Rest-Assured is a popular Java-based library for testing RESTful web services, and it provides a fluent interface for creating HTTP requests and asserting responses. 

## Scenerio
1. Call login API
2. Create  a new customer and an agent
3. Search by the customer phone number
4. Deposit 5000 tk to the Agent from system
5. Deposit 2000 tk by agent to customer 
6. Check balance of customer
7. Check statement by trnxId 
8. Withdraw 1000 tk by customer and assert expected balance
9. Send 500 tk to another customer and assert expected balance
10. Check customer statement

## Technology and Tool Used
- Rest Assured
- Java
- Gradle
- TestNG
- Intellij Idea 
- Allure

## Test Case Report:

https://docs.google.com/spreadsheets/d/1e-oaXUjn-YjhY2QHUapt4Ul0q2JQ96zsMtitKIh-x8U/edit?usp=sharing

## How to run this project
- Clone this project
- Hit the following command into the terminal:
 ```gradle clean test```
 

 
## The following report is generated:

![Screenshot 2023-03-15 042826](https://user-images.githubusercontent.com/58912515/225158093-98f6267c-9c01-4be7-b05f-650e789dd880.png)

- For generating Allure Report use these commands:
```allure generate allure-results --clean -o allure-report``` and
```allure serve allure-results```  

## Allure Report:

![Screenshot 2023-03-15 042235](https://user-images.githubusercontent.com/58912515/225158105-acf192d8-e2c1-4c6b-a787-4fa91fe31058.png)

![Screenshot 2023-03-15 042248](https://user-images.githubusercontent.com/58912515/225158113-43bdcd91-4d38-43cd-bb0a-57df7b4fb017.png)


## Video Output:

https://user-images.githubusercontent.com/58912515/225158179-887ee20e-b9a1-4ff7-b0ca-6ccdbb7adb00.mp4



