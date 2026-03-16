async function signIn() {
    try {
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        alert(email + " " + password);

        let rememberMe;

        if (document.getElementById("rememberMe").checked) {
            rememberMe = 1
        } else {
            rememberMe = 0
        }

        const payload = {
            email,
            password,
            rememberMe
        };

        const request = await fetch("/api/user/logIn", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const response = await request.text();

            if (response == "success") {
                window.location = "index.php"
            } else {
                alert(response);
            }
        } else {
            Notiflix.Notify.failure('Sign in failed');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}