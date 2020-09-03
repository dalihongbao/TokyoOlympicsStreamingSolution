# TokyoOlympicsStreamingSolution

### Easiest way to clone repository to use locally on IntelliJ
1. Copy the clone URL via the green `Clone or download` button
2. Open IntelliJ and navigate to the home screen
3. Click the `Check out from Version Control` button, then choose `Git`
4. Paste in the copied URL into the field (GitHub may ask you to login)
5. Click the `Clone` button. IntelliJ will attempt to clone the current repository
6. When IntelliJ asks if you want to open the project, click `Yes`
7. If IntelliJ asks to 'Auto-import', say `Yes`

Done!

*Note: It might take a hot minute for Maven to download dependencies*

### How to run application
There are quite a few ways to run the application. Here are the simplest:
- Run `mvn spring-boot:run` in your preferred terminal after navigating into the project folder and go to `localhost:8080` on a browser
- Run the `Application.java` file within IntelliJ and go to `localhost:8080` on a browser
