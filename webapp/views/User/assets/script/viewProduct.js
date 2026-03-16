const models = {};
const OPEN_BUY_MODAL_KEY = "timestore:openBuyModal";
let buying_model_id = 0;
let isLoggedIn = false;

window.addEventListener("load", event => {
    const buyNowButton = document.getElementById("buyNow");

    var pathname = window.location.pathname;
    let id = pathname.searchParams.get("id");
    alert(id);
    loadModels(id);

});

document.getElementById("modelsTable").addEventListener("click", function(event) {
    var button = event.target.closest(".btn");
    changeModel(button.dataset.model_id);
});
document.getElementById("buyNow").addEventListener("click", function () {
    if (!isLoggedIn) {
        return;
    }
    buying_product();
});

const checkoutForm = document.getElementById("checkoutSignInForm");
if (checkoutForm) {
    checkoutForm.addEventListener("submit", function (event) {
        event.preventDefault();
        checkoutSignIn();
    });
}

async function loadModels(product_id) {
    try {
        const form = new FormData();
        form.append("product_id", product_id);

        const request = await fetch("/api/model/load", {
            method: "POST",
            body: form
        });

        if (request.ok) {
            const jsonObject = await request.json();
            const modelsTable = document.getElementById("modelsTable");
            modelsTable.innerHTML = "";
            const fragment = document.createDocumentFragment();

            jsonObject.models.forEach(model => {
                models[model.model_id] = model;
                const button = document.createElement("button");
                button.dataset.model_id=model.model_id;
                button.classList.add("btn", "border", "rounded-3");
                button.innerHTML=`<img src="${model.img_path}" width="50" alt="Side View">`;
                fragment.appendChild(button);
            });
            modelsTable.appendChild(fragment);
            changeModel(jsonObject.models[0].model_id);
            maybeOpenBuyModal();
        } else {
            Notiflix.Notify.failure('Failed to load models');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

function changeModel(model_id) {
    buying_model_id = model_id;
    const model =models[model_id]
    document.getElementById("product_label").innerText = model.model_name;
    document.getElementById("model").innerText = model.model_name;
    document.getElementById("price").innerText = "Rs." + model.price;
    document.getElementById("vimg").src = model.img_path;
}

function buying_product(){
    const model = models[buying_model_id];
    const buyingProductId = document.getElementById("buying_product_id");
    const buyingProductBrand = document.getElementById("buying_product_brand");
    const buyingProductModel = document.getElementById("buying_product_model");
    const buyingProductPrice = document.getElementById("buying_product_price");
    const buyingProductImg = document.getElementById("mimg");

    if (!model || !buyingProductId || !buyingProductBrand || !buyingProductModel || !buyingProductPrice || !buyingProductImg) {
        return;
    }

    buyingProductId.value = buying_model_id;
    buyingProductBrand.textContent = model.brand_id;
    buyingProductModel.textContent = model.model_name;
    buyingProductPrice.textContent = "Rs." + model.price;
    buyingProductImg.src = model.img_path;
}

function toCheckout() {

    var buying_product_qty = document.getElementById("pqty");
    var buying_product_id = document.getElementById("buying_product_id");

    window.location = "/timestore/checkout/" + buying_product_id.value+"/"+buying_product_qty.value;

}

async function checkoutSignIn() {
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("pw");
    const rememberInput = document.getElementById("rememberMe");

    if (!emailInput || !passwordInput) {
        return;
    }

    const email = emailInput.value.trim();
    const password = passwordInput.value;
    const rememberMe = rememberInput && rememberInput.checked ? 1 : 0;

    if (!email || !password) {
        alert("Please enter your email and password.");
        return;
    }

    try {
        const form = new FormData();
        form.append("email", email);
        form.append("password", password);
        form.append("rememberMe", rememberMe);

        const request = await fetch("/api/user/logIn", {
            method: "POST",
            body: form
        });

        if (request.status !== 200) {
            alert("Sign in failed. Please try again.");
            return;
        }

        let response;
        try {
            response = await request.json();
        } catch (error) {
            alert("Sign in failed. Please try again.");
            return;
        }

        if (response.state) {
            localStorage.setItem(OPEN_BUY_MODAL_KEY, "1");
            window.location.reload();
        } else {
            alert(response.message || "Invalid email or password.");
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

function maybeOpenBuyModal() {
    if (!isLoggedIn) {
        localStorage.removeItem(OPEN_BUY_MODAL_KEY);
        return;
    }

    if (localStorage.getItem(OPEN_BUY_MODAL_KEY) !== "1") {
        return;
    }

    localStorage.removeItem(OPEN_BUY_MODAL_KEY);
    buying_product();

    const modalElement = document.getElementById("exampleModal");
    if (modalElement && window.bootstrap && window.bootstrap.Modal) {
        const modalInstance = window.bootstrap.Modal.getOrCreateInstance(modalElement);
        modalInstance.show();
    }
}

function qtyUp() {
    const pqty = document.getElementById("pqty");
    const qtyWarning = document.getElementById("qtyWarning");

    if (!pqty) {
        return;
    }

    pqty.value = parseInt(pqty.value || "1", 10) + 1;

    if (qtyWarning) {
        qtyWarning.textContent = "";
    }
}

function qtyDown() {
    const pqty = document.getElementById("pqty");
    const qtyWarning = document.getElementById("qtyWarning");

    if (!pqty) {
        return;
    }

    pqty.value = Math.max(1, parseInt(pqty.value || "1", 10) - 1);

    if (qtyWarning) {
        qtyWarning.textContent = "";
    }
}