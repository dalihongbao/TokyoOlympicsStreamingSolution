package olympix;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import olympix.cognito.CognitoHelper;

import static olympix.Application.*;

/**
 * REST API that acts as an adapter/facade to communicate with Cognito
 *
 * @author Kenley, Yifeng
 */
@RestController
@RequestMapping("/api")
public class AuthRestController {

    /**
     * Create a set of user credentials using Cognito using inputted values
     *
     * @param username the user inputted username
     * @return 200 if successful, 400 otherwise
     */
    @RequestMapping(value = "/reset_password", method = RequestMethod.POST)
    int ResetPassword(
        @RequestParam(value = "username") String username
    ) {
        System.out.println("username = " + username);
        CognitoHelper cognitoHelper = new CognitoHelper();
        String validator = cognitoHelper.ResetPassword(username);
        if (validator != null) {
            return HttpStatus.SC_OK;
        } else {
            return HttpStatus.SC_BAD_REQUEST;
        }
    }

    /**
     * Create a set of user credentials using Cognito using inputted values
     *
     * @param username the user inputted username
     * @param newpw    the user inputted new password
     * @param code     the user inputted code
     * @return 200 if successful, 400 otherwise
     */
    @RequestMapping(value = "/update_password", method = RequestMethod.POST)
    int UpdatePassword(
        @RequestParam(value = "username") String username,
        @RequestParam(value = "newpw") String newpw,
        @RequestParam(value = "code") String code
    ) {
        System.out.println("username = " + username);
        System.out.println("newpw = " + newpw);
        System.out.println("code = " + code);
        CognitoHelper cognitoHelper = new CognitoHelper();
        String validator = cognitoHelper.UpdatePassword(username, newpw, code);
        if (validator != null) {
            return HttpStatus.SC_OK;
        } else {
            return HttpStatus.SC_BAD_REQUEST;
        }
    }

    /**
     * Create a set of user credentials using Cognito using inputted values
     *
     * @param username the user inputted username
     * @param code     the user inputted code
     * @return 200 if successful, 400 otherwise
     */
    @RequestMapping(value = "/verification", method = RequestMethod.POST)
    int VerifyAccessCode(
        @RequestParam(value = "username") String username,
        @RequestParam(value = "code") String code
    ) {
        System.out.println("username = " + username);
        System.out.println("code = " + code);
        CognitoHelper cognitoHelper = new CognitoHelper();
        boolean validator = cognitoHelper.VerifyAccessCode(username, code);
        if (validator) {
            return HttpStatus.SC_OK;
        } else {
            return HttpStatus.SC_BAD_REQUEST;
        }
    }

    /**
     * Validates user credentials through Cognito
     *
     * @param username the user inputted username
     * @param password the user inputted password
     * @return 200 if successful, 400 otherwise
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    int loginRequest(
        @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password
    ) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);

        CognitoHelper cognitoHelper = new CognitoHelper();
        String validator = cognitoHelper.ValidateUser(username, password);
        System.out.println("validator = " + validator);
        if (validator != null) {
            loggedIn = true;
            Application.username = username;
            return HttpStatus.SC_OK;
        } else {
            return HttpStatus.SC_BAD_REQUEST;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    int logout() {
        try {
            // Clear local auth values
            loggedIn = false;
            username = null;
            return HttpStatus.SC_OK;
        } catch (Exception e) {
            e.printStackTrace();
            return HttpStatus.SC_BAD_REQUEST;
        }
    }

    /**
     * Create a set of user credentials using Cognito using inputted values
     *
     * @param username the user inputted username
     * @param email    the user inputted email
     * @return 200 if successful, 400 otherwise
     */
    @RequestMapping(value = "/change_email", method = RequestMethod.POST)
    int signUpRequest(
        @RequestParam(value = "username") String username,
        @RequestParam(value = "email") String email
    ) {
        System.out.println("username = " + username);
        System.out.println("email = " + email);
        CognitoHelper cognitoHelper = new CognitoHelper();
        boolean validator = cognitoHelper.changeEmail(username, email);
        if (validator) {
            loggedIn = true;
            Application.username = username;
            return HttpStatus.SC_OK;
        } else {
            return HttpStatus.SC_BAD_REQUEST;
        }
    }

    /**
     * Create a set of user credentials using Cognito using inputted values
     *
     * @param username the user inputted username
     * @param email    the user inputted email
     * @param password the user inputted password
     * @return 200 if successful, 400 otherwise
     */
    @RequestMapping(value = "/sign_up", method = RequestMethod.POST)
    String signUpRequest(
        @RequestParam(value = "username") String username,
        @RequestParam(value = "email") String email,
        @RequestParam(value = "password") String password
    ) {
        System.out.println("username = " + username);
        System.out.println("email = " + email);
        System.out.println("password = " + password);
        CognitoHelper cognitoHelper = new CognitoHelper();
        String validator = cognitoHelper.SignUpUser(username, password, email);
        if (validator == "true") {
            loggedIn = true;
            Application.username = username;
            return "success";
        } else {
            errorMessage = validator;
            return errorMessage;
        }
    }
}
