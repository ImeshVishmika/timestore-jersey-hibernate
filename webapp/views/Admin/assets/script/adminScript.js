async function adminLogIn() {
    try {
        const email = document.getElementById("email").value;
        const password = document.getElementById("pw").value;

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

        const request = await fetch("/admin/api/admin/logIn", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const jsonObject = await request.json();
            if (jsonObject.state) {
                alert("test");
                window.location = "/admin/dashboard.html";
            } else {
                Notiflix.Notify.failure((jsonObject && jsonObject.error) ? jsonObject.error : "Admin login failed");
            }
        } else {
            Notiflix.Notify.failure('Admin login failed');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error: ' + error);
    }
}
