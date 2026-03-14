<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TimeStore | Customer Login</title>

    <link rel="stylesheet" href="/timestore/public/assets/style/bootstrap.css">
    <link rel="stylesheet" href="/timestore/public/assets/style/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

    <style>
        /* --- Luxury Background Styling --- */
        body {
            /* Dark Watch Texture Background */
            background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
                url('https://images.unsplash.com/photo-1547996160-81dfa63595aa?q=80&w=2000&auto=format&fit=crop');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        /* --- Card Styling --- */
        .login-container {
            max-height: 85vh;
            max-width: 800px;
            position: relative;

            /* Glassmorphism-ish border */
            border: 1px solid rgba(255, 255, 255, 0.1);
        }

        .image-overlay {
            background: linear-gradient(to bottom, rgba(0, 0, 0, 0.1), rgba(0, 0, 0, 0.8));
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
        }

        .form-col {
            background-color: rgba(255, 255, 255, 0.95);
            /* Slight transparency for premium feel */
        }

        .btn-dark {
            background-color: #1a1a1a;
            border: none;
            transition: 0.3s;
        }

        .btn-dark:hover {
            background-color: #333;
            transform: translateY(-1px);
        }

        .login-img {
            /* Side Image inside the card */
            background-image: url('https://images.unsplash.com/photo-1522312346375-d1a52e2b99b3?q=80&w=1000&auto=format&fit=crop');
            background-size: cover;
            background-position: center;
            position: relative;
            left: 0;
            transition: opacity 1s ease-in-out;
            transition: left 1s ease-out, background-color 0.5s;

        }

        .signup-img {
            background-image: url('https://images.unsplash.com/photo-1619134778706-7015533a6150?q=80&w=1000&auto=format&fit=crop');
            background-size: cover;
            background-position: center;
            position: relative;
            left: 0;
            transition: opacity 1s ease-in-out;
            transition: left 1s ease-out, background-color 0.5s;
        }

        .moved {
            left: 450px;
            /* The new position when the 'moved' class is applied */
            background-color: blue;
        }

        .move-right {
            transform: translateX(100%);
            border-radius: 0 20px 20px 0;
            opacity: 1;
        }

        .move-left {
            transform: translateX(0%);
            border-radius: 0 20px 20px 0;
            opacity: 1;
        }

        #singUpText {
            transition: opacity 1s ease-in-out;
        }

        #logInText {
            transition: opacity 1s ease-in-out;
        }


        #Img {
            position: absolute;
            top: 0;
            left: 0;
            width: 50%;
            /* same as col-md-6 */
            height: 100%;
            z-index: 10;
            transition: all 0.8s ease;
        }

        .fade-out {
            opacity: 0;
        }

        .fade-in {
            opacity: 1;
        }
    </style>
</head>

<body class="d-flex align-items-center justify-content-center vh-100">

    <div class="card login-container shadow-lg rounded-4 overflow-hidden  w-100 m-3 ">
        <div class="row  g-0 ">

            <div id="logIn" class="col-md-6 form-col h-100 p-5 d-flex flex-column  order-2 order-md-1 ">

                <div class="mb-4">
                    <h3 class="fw-bold text-dark">Welcome Back</h3>
                    <p class="text-muted small">Please enter your details to sign in.</p>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold text-secondary">Email</label>
                    <input type="email" class="form-control bg-light border-0" id="email" placeholder="name@example.com"
                        value="<?php if (isset($_COOKIE['email'])) {
                                    echo $_COOKIE['email'];
                                } ?>">
                    <span id="msg-email" class="text-danger small fw-bold"></span>
                </div>

                <div class="mb-3">
                    <label class="form-label small fw-bold text-secondary">Password</label>
                    <input type="password" class="form-control bg-light border-0" id="password" placeholder="••••••••"
                        value="<?php if (isset($_COOKIE['pw'])) {
                                    echo $_COOKIE['pw'];
                                } ?>">
                    <span id="msg-pass" class="text-danger small fw-bold"></span>
                </div>

                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div class="form-check">
                        <input class="form-check-input" type="checkbox" id="rememberMe"
                            <?php if (isset($_COOKIE['email'])) {
                                echo "checked";
                            } ?>>
                        <label class="form-check-label small text-secondary" for="rememberMe">Remember me</label>
                    </div>
                    <a href="forgotPassword.php" class="text-decoration-none small text-dark fw-bold">Forgot Password?</a>
                </div>

                <div class="d-grid mb-4">
                    <button class="btn btn-dark py-2 fw-bold rounded-2 shadow-sm" onclick="signIn();">Sign In</button>
                </div>

                <div class="position-relative text-center mb-4">
                    <hr class="text-secondary opacity-25">
                    <span class="position-absolute top-50 start-50 translate-middle px-3 bg-white text-secondary small">or</span>
                </div>

                <div class="text-center">
                    <span class="text-muted small">Don't have an account?</span>
                    <button id="test1" class="btn text-decoration-none fw-bold text-danger ms-1">Create account</button>
                </div>

            </div>

            <div id="Img" class="login-img">
                <div id="logInText" class="image-overlay d-flex flex-column justify-content-end p-5 ">
                    <h2 class="text-white fw-bold display-6">Time is Luxury.</h2>
                    <p class="text-white-50">Log in to track your orders and access exclusive collections.</p>
                </div>
                <div id="signUpText" class="image-overlay d-flex flex-column justify-content-end p-5 text-end d-none">
                    <h2 class="text-white fw-bold display-6">Define Your Moment.</h2>
                    <p class="text-white-50">Experience precision engineering and timeless design.</p>
                </div>
            </div>

            <div id="signUp" class="col-md-6 form-col h-100 d-flex flex-column">
                <div class="h-100 overflow-y-auto p-5 d-flex flex-column justify-content-center custom-scrollbar">

                    <div class="mb-4">
                        <h3 class="fw-bold text-dark">Join TimeStore</h3>
                        <p class="text-muted small">Create an account to unlock exclusive member benefits.</p>
                    </div>

                    <div class="row g-2 mb-3">
                        <div class="col-6">
                            <label class="form-label small fw-bold text-secondary">First Name</label>
                            <input type="text" class="form-control bg-light border-0" id="fname" placeholder="John">
                        </div>
                        <div class="col-6">
                            <label class="form-label small fw-bold text-secondary">Last Name</label>
                            <input type="text" class="form-control bg-light border-0" id="lname" placeholder="Doe">
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-bold text-secondary">Email Address</label>
                        <input type="email" class="form-control bg-light border-0" id="email" placeholder="name@example.com">
                        <span id="msg-email" class="text-danger small fw-bold"></span>
                    </div>

                    <div class="mb-3">
                        <label class="form-label small fw-bold text-secondary">Password</label>
                        <div class="input-group">
                            <input type="password" class="form-control bg-light border-0" id="password" placeholder="Min. 8 characters">
                            <span class="input-group-text bg-light border-0 text-secondary" style="cursor: pointer;" onclick="togglePasswordVisibility()">
                                <i class="bi bi-eye-slash" id="eyeIcon"></i>
                            </span>
                        </div>
                        <span id="msg-pass" class="text-danger small fw-bold"></span>
                    </div>

                    <div class="mb-4">
                        <label class="form-label small fw-bold text-secondary">Mobile Number</label>
                        <input type="tel" class="form-control bg-light border-0" id="mobile" placeholder="07xxxxxxxx">
                        <span id="msg-mobile" class="text-danger small fw-bold"></span>
                    </div>

                    <div class="mb-4">
                        <label class="form-label small fw-bold text-secondary d-block">Gender</label>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="gender" id="genderMale" value="1" checked>
                            <label class="form-check-label small" for="genderMale">Male</label>
                        </div>
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" type="radio" name="gender" id="genderFemale" value="2">
                            <label class="form-check-label small" for="genderFemale">Female</label>
                        </div>
                    </div>

                    <div class="d-grid mb-4">
                        <button id="createAccount" class="btn btn-dark py-2 fw-bold rounded-2 shadow-sm" onclick="signUp();">Create Account</button>
                    </div>

                    <div class="text-center pb-3"> <span class="text-muted small">Already have an account?</span>
                        <button id="test" class="btn text-decoration-none fw-bold text-danger ms-1">Log in</button>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script>
        var img = document.getElementById("Img");
        var signUpText = document.getElementById("signUpText");
        var logInText = document.getElementById("logInText");

        //Switch to login form
        document.getElementById("test1").addEventListener("click", () => {
            img.classList.remove("move-left", "login-img");
            img.classList.add("move-right", "signup-img", "fade-in");

            signUpText.classList.add("d-none");
            logInText.classList.remove("d-none");

        })

        //Switch to signup form
        document.getElementById("test").addEventListener("click", () => {
            img.classList.remove("move-right", "signup-img");
            img.classList.add("move-left", "login-img", "fade-out");

            signUpText.classList.remove("d-none");
            logInText.classList.add("d-none");

        })
    </script>

    <script src="/timestore/public/assets/Script/bootstrap.bundle.js"></script>
    <script src="/timestore/public/assets/Script/User/signin.js"></script>

</body>

</html>




<!-- <style>
    /* --- Luxury Background Styling --- */
    body {
        /* Consistent Background with Login Page */
        background: linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
            url('https://images.unsplash.com/photo-1547996160-81dfa63595aa?q=80&w=2000&auto=format&fit=crop');
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        background-attachment: fixed;
    }

    .signup-container {
        min-height: 85vh;
        max-width: 1000px;
    }

    .image-col {
        background-image: url('https://images.unsplash.com/photo-1619134778706-7015533a6150?q=80&w=1000&auto=format&fit=crop');
        background-size: cover;
        background-position: center;
        position: relative;
    }

    .image-overlay {
        background: linear-gradient(to top, rgba(0, 0, 0, 0.8), rgba(0, 0, 0, 0.2));
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
    }

    .form-col {
        background-color: rgba(255, 255, 255, 0.95);
    }
</style> -->