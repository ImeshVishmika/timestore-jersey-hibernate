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

        const form = new FormData();
        form.append("email", email);
        form.append("password", password);
        form.append("rememberMe", rememberMe);

        const request = await fetch("/timestore/api/user/logIn", {
            method: "POST",
            body: form
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