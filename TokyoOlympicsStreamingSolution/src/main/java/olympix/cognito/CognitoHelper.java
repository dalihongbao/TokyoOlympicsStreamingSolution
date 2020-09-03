package olympix.cognito;

/*
https://github.com/aws-samples/aws-cognito-java-desktop-app

// Copyright 2013-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: MIT-0
 */

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClientBuilder;
import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesRequest;
import com.amazonaws.services.cognitoidp.model.AdminUpdateUserAttributesResult;
import com.amazonaws.services.cognitoidp.model.AttributeType;
import com.amazonaws.services.cognitoidp.model.ConfirmForgotPasswordRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmForgotPasswordResult;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpRequest;
import com.amazonaws.services.cognitoidp.model.ConfirmSignUpResult;
import com.amazonaws.services.cognitoidp.model.ForgotPasswordRequest;
import com.amazonaws.services.cognitoidp.model.ForgotPasswordResult;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.amazonaws.services.cognitoidp.model.SignUpResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;

import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * The CognitoHelper class abstracts the functionality of connecting to the Cognito user pool and Federated Identities.
 */
public class CognitoHelper {

    private String CLIENTAPP_ID;
    private String CUSTOMDOMAIN;
    private String FED_POOL_ID;
    private String POOL_ID;
    private String REGION;

    public CognitoHelper() {

        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = getClass().getClassLoader().getResourceAsStream("config.properties");

            // load a properties file
            prop.load(input);

            // Read the property values
            POOL_ID = prop.getProperty("POOL_ID");
            CLIENTAPP_ID = prop.getProperty("CLIENTAPP_ID");
            FED_POOL_ID = prop.getProperty("FED_POOL_ID");
            CUSTOMDOMAIN = prop.getProperty("CUSTOMDOMAIN");
            REGION = prop.getProperty("REGION");

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Start reset password procedure by sending reset code
     *
     * @param username user to be reset
     * @return returns code delivery details
     */
    public String ResetPassword(String username) {
        AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(REGION))
            .build();

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setUsername(username);
        forgotPasswordRequest.setClientId(CLIENTAPP_ID);
        ForgotPasswordResult forgotPasswordResult = new ForgotPasswordResult();

        try {
            forgotPasswordResult = cognitoIdentityProvider.forgotPassword(forgotPasswordRequest);
        } catch (Exception e) {
            // handle exception here
        }
        return forgotPasswordResult.toString();
    }

    /**
     * Sign up the user to the user pool
     *
     * @param username User name for the sign up
     * @param password Password for the sign up
     * @param email    email used to sign up
     * @return whether the call was successful or not.
     */
    public String SignUpUser(String username, String password, String email) {
        AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(REGION))
            .build();

        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setClientId(CLIENTAPP_ID);
        signUpRequest.setUsername(username);
        signUpRequest.setPassword(password);
        List<AttributeType> list = new ArrayList<>();

        AttributeType attributeType1 = new AttributeType();
        attributeType1.setName("email");
        attributeType1.setValue(email);
        list.add(attributeType1);

        signUpRequest.setUserAttributes(list);

        try {
            SignUpResult result = cognitoIdentityProvider.signUp(signUpRequest);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
            return e.getMessage();
        }
        return "true";
    }

    /**
     * complete reset password procedure by confirming the reset code
     *
     * @param username user to be reset
     * @param newpw    new password of aforementioned user
     * @param code     code sent for password reset from the ResetPassword() method above
     * @return returns code delivery details
     */
    public String UpdatePassword(String username, String newpw, String code) {
        AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(REGION))
            .build();

        ConfirmForgotPasswordRequest confirmPasswordRequest = new ConfirmForgotPasswordRequest();
        confirmPasswordRequest.setUsername(username);
        confirmPasswordRequest.setPassword(newpw);
        confirmPasswordRequest.setConfirmationCode(code);
        confirmPasswordRequest.setClientId(CLIENTAPP_ID);
        ConfirmForgotPasswordResult confirmPasswordResult = new ConfirmForgotPasswordResult();

        try {
            confirmPasswordResult = cognitoIdentityProvider.confirmForgotPassword(confirmPasswordRequest);
        } catch (Exception e) {
            // handle exception here
        }
        return confirmPasswordResult.toString();
    }

    /**
     * Helper method to validate the user
     *
     * @param username represents the username in the cognito user pool
     * @param password represents the password in the cognito user pool
     * @return returns the JWT token after the validation
     */
    public String ValidateUser(String username, String password) {
        AuthenticationHelper helper = new AuthenticationHelper(POOL_ID, CLIENTAPP_ID, "");
        return helper.PerformSRPAuthentication(username, password);
    }

    /**
     * Verify the verification code sent on the user phone.
     *
     * @param username User for which we are submitting the verification code.
     * @param code     Verification code delivered to the user.
     * @return if the verification is successful.
     */
    public boolean VerifyAccessCode(String username, String code) {
        AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(REGION))
            .build();
        ConfirmSignUpRequest confirmSignUpRequest = new ConfirmSignUpRequest();
        confirmSignUpRequest.setUsername(username);
        confirmSignUpRequest.setConfirmationCode(code);
        confirmSignUpRequest.setClientId(CLIENTAPP_ID);

        System.out.println("username=" + username);
        System.out.println("code=" + code);
        System.out.println("clientid=" + CLIENTAPP_ID);

        try {
            ConfirmSignUpResult confirmSignUpResult = cognitoIdentityProvider.confirmSignUp(confirmSignUpRequest);
            System.out.println("confirmSignupResult=" + confirmSignUpResult.toString());
        } catch (Exception ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }

    /**
     * Update the email of the user
     *
     * @param username User name for the sign up
     * @param email    email used to sign up
     * @return whether the call was successful or not.
     */
    public boolean changeEmail(String username, String email) {
        AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
        AWSCognitoIdentityProvider cognitoIdentityProvider = AWSCognitoIdentityProviderClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(REGION))
            .build();

        List<AttributeType> list = new ArrayList<>();

        AttributeType attributeType1 = new AttributeType();
        attributeType1.setName("email");
        attributeType1.setValue(email);
        list.add(attributeType1);

        AdminUpdateUserAttributesRequest updateRequest = new AdminUpdateUserAttributesRequest();
        updateRequest.setUsername(username);
        updateRequest.setUserPoolId(POOL_ID);
        updateRequest.setUserAttributes(list);

        try {
            AdminUpdateUserAttributesResult result = cognitoIdentityProvider.adminUpdateUserAttributes(updateRequest);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    /**
     * Returns the AWS credentials
     *
     * @param idprovider the IDP provider for the login map
     * @param id         the username for the login map.
     * @return returns the credentials based on the access token returned from the user pool.
     */
    Credentials GetCredentials(String idprovider, String id) {
        AnonymousAWSCredentials awsCreds = new AnonymousAWSCredentials();
        AmazonCognitoIdentity provider = AmazonCognitoIdentityClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(REGION))
            .build();

        GetIdRequest idrequest = new GetIdRequest();
        idrequest.setIdentityPoolId(FED_POOL_ID);
        idrequest.addLoginsEntry(idprovider, id);
        GetIdResult idResult = provider.getId(idrequest);

        GetCredentialsForIdentityRequest request = new GetCredentialsForIdentityRequest();
        request.setIdentityId(idResult.getIdentityId());
        request.addLoginsEntry(idprovider, id);

        GetCredentialsForIdentityResult result = provider.getCredentialsForIdentity(request);
        return result.getCredentials();
    }

    /**
     * Returns the AWS credentials
     *
     * @param accesscode access code
     * @return returns the credentials based on the access token returned from the user pool.
     */
    Credentials GetCredentials(String accesscode) {
        Credentials credentials = null;

        try {
            Map<String, String> httpBodyParams = new HashMap<String, String>();
            httpBodyParams.put(Constants.TOKEN_GRANT_TYPE, Constants.TOKEN_GRANT_TYPE_AUTH_CODE);
            httpBodyParams.put(Constants.DOMAIN_QUERY_PARAM_CLIENT_ID, CLIENTAPP_ID);
            httpBodyParams.put(Constants.DOMAIN_QUERY_PARAM_REDIRECT_URI, Constants.REDIRECT_URL);
            httpBodyParams.put(Constants.TOKEN_AUTH_TYPE_CODE, accesscode);

            AuthHttpClient httpClient = new AuthHttpClient();
            URL url = new URL(GetTokenURL());
            String result = httpClient.httpPost(url, httpBodyParams);
            System.out.println(result);

            JSONObject payload = CognitoJWTParser.getPayload(result);
            String provider = payload.get("iss").toString().replace("https://", "");
            credentials = GetCredentials(provider, result);

            return credentials;
        } catch (Exception exp) {
            System.out.println(exp);
        }
        return credentials;
    }

    String GetHostedSignInURL() {
        String customurl = "https://%s.auth.%s.amazoncognito.com/login?response_type=code&client_id=%s&redirect_uri=%s";

        return String.format(customurl, CUSTOMDOMAIN, REGION, CLIENTAPP_ID, Constants.REDIRECT_URL);
    }

    String GetTokenURL() {
        String customurl = "https://%s.auth.%s.amazoncognito.com/oauth2/token";

        return String.format(customurl, CUSTOMDOMAIN, REGION);
    }

    /**
     * This method returns the details of the user and bucket lists.
     *
     * @param credentials Credentials to be used for displaying buckets
     */
    String ListBucketsForUser(Credentials credentials) {
        BasicSessionCredentials
            awsCreds =
            new BasicSessionCredentials(credentials.getAccessKeyId(), credentials.getSecretKey(),
                                        credentials.getSessionToken());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .withRegion(Regions.fromName(REGION))
            .build();
        StringBuilder bucketslist = new StringBuilder();

        bucketslist.append("===========Credentials Details.=========== \n");
        bucketslist.append("Accesskey = " + credentials.getAccessKeyId() + "\n");
        bucketslist.append("Secret = " + credentials.getSecretKey() + "\n");
        bucketslist.append("SessionToken = " + credentials.getSessionToken() + "\n");
        bucketslist.append("============Bucket Lists===========\n");

        for (Bucket bucket : s3Client.listBuckets()) {
            bucketslist.append(bucket.getName());
            bucketslist.append("\n");

            System.out.println(" - " + bucket.getName());
        }
        return bucketslist.toString();
    }
}
