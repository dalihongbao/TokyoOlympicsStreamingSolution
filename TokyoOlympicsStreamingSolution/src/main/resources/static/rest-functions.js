function login() {
    let params = new URLSearchParams();
    params.append('username', document.getElementById('inputUsername').value);
    params.append('password', document.getElementById('inputPassword').value);
    localStorage.setItem("loginUsername", document.getElementById('inputUsername').value);
    axios.post('/api/login', params)
        .then(function (response) {
            // handle success
            console.log(response.data === 200 ? "We good" : "Something's wrong");
            if (response.data === 200) {
                window.location = 'live';
            } else {
                alert("Invalid username or password");
            }
        })
        .catch(function (error) {
            // handle error
            console.log("Something's wrong");
            console.log(error);
        });
}

function logout() {
    axios.post('/api/logout')
        .then(function () {
            localStorage.clear();
        })
}

function reset_password() {
    let params = new URLSearchParams();
    params.append('username', document.getElementById('inputUsername').value);
    axios.post('/api/reset_password', params)
        .then(function (response) {
            // handle success
            console.log(response.data === 200 ? "We good" : "Something's wrong");
        })
        .catch(function (error) {
            // handle error
            console.log("Something's wrong");
            console.log(error);
        })
        .finally(function () {
            // always executed
        });
}

function update() {
    let params = new URLSearchParams();
    params.append('username', document.getElementById('inputUsername').value);
    params.append('newpw', document.getElementById('inputPassword').value);
    params.append('code', document.getElementById('inputCode').value);
    axios.post('/api/update_password', params)
        .then(function (response) {
            // handle success
            console.log(response.data === 200 ? "We good" : "Something's wrong");
            if (response.data === 200) {
                window.location = 'index';
            }
        })
        .catch(function (error) {
            // handle error
            console.log("Something's wrong");
            console.log(error);
        })
}

function signUp() {
    let params = new URLSearchParams();
    params.append('username', document.getElementById('inputUsername').value);
    params.append('email', document.getElementById('inputEmail').value);
    params.append('password', document.getElementById('inputPassword').value);
    localStorage.setItem("username", document.getElementById('inputUsername').value);
    axios.post('/api/sign_up', params)
        .then(function (response) {
            // handle success
            console.log(response.data == "success" ? "We good" : "Something's wrong");
            if (response.data == "success") {
                window.location = 'verification';
            }
            else
            {
                alert(response.data);
            }
        })
        .catch(function (error) {
            // handle error
            console.log("Something's wrong");
            console.log(error);
        })
}

function verify() {
    let params = new URLSearchParams();
    params.append('username', localStorage.getItem("username"));
    params.append('code', document.getElementById('inputCode').value);
    axios.post('/api/verification', params)
        .then(function (response) {
            // handle success
            console.log(response.data === 200 ? "We good" : "Something's wrong");
            if (response.data === 200) {
                window.location = 'email_confirmed';
            } else {
                alert("Invalid verification code provided or an account with the email already exists, please try again.");
            }
        })
        .catch(function (error) {
            // handle error
            console.log("Something's wrong");
            console.log(error);
        })
}