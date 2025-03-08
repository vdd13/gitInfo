1. Download and run - clone repository and import maven project to IDE
2. Start an application and test (in browser, postman/talend API) http://localhost:8080/git/allRepos/{user} where user is github user (ex: http://localhost:8080/git/allRepos/vdd13)
3. There is a github limit how many times you can call github API (per day?) - 403 Forbidden error occur.
4. Test is in same applcation so to execute it, it requires running applcation first.
5. If you download code and build application as maven project (by typing maven clean install etc.) test will execute automatically during process of building and will fail - test need to be excluded because application is not running. 
