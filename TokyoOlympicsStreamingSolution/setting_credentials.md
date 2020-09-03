## Setting AWS Credentials

Setting your credentials for use by the AWS SDK for Java can be done in a number of ways, but here are the recommended approaches:

- Set credentials in the AWS credentials profile file on your local system, located at:
    - `~/.aws/credentials` on Linux, macOS, or Unix
    - `C:\Users\USERNAME\.aws\credentials` on Windows

    This file should contain lines in the following format:
    ```
    [default]
    aws_access_key_id = your_access_key_id
    aws_secret_access_key = your_secret_access_key
    ```
    Substitute your own AWS credentials values for the values your_access_key_id and your_secret_access_key.
    
---
#### OR


- Set the `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` environment variables.

    - To set these variables on Linux, macOS, or Unix, use `export` :
    ```
    export AWS_ACCESS_KEY_ID=your_access_key_id
    export AWS_SECRET_ACCESS_KEY=your_secret_access_key
    ```
  
    - To set these variables on Windows, use `set` :
    ```
    set AWS_ACCESS_KEY_ID=your_access_key_id
    set AWS_SECRET_ACCESS_KEY=your_secret_access_key
    ```

___
Once you have set your AWS credentials using one of these methods, they will be loaded automatically by the AWS SDK for Java by using the default credential provider chain. For further information about working with AWS credentials in your Java applications, see [Working with AWS Credentials](https://docs.aws.amazon.com/en_pv/sdk-for-java/v1/developer-guide/credentials.html).