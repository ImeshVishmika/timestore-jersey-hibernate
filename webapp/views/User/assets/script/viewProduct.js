const models = {};
const OPEN_BUY_MODAL_KEY = "timestore:openBuyModal";
let buyingModelId = 0;
let isLoggedIn = false;

window.addEventListener("load", event => {
    const buyNowButton = document.getElementById("buyNow");

    var parm = new URLSearchParams(window.location.search);
    let id = parm.get("id");
    loadModels(id);

});

document.getElementById("modelsTable").addEventListener("click", function(event) {
    var button = event.target.closest(".btn");
    changeModel(button.dataset.model_id);
});

async function handleBuyNow() {
    try {
        const response = await fetch("/api/user/signinstatus");
        const data = await response.json();
        
        if (data.status) {
            buyingProduct();
            new bootstrap.Modal(document.getElementById("buyNowModal")).show();
        } else {
            new bootstrap.Modal(document.getElementById("signInModal")).show();
        }
    } catch (error) {
        console.error('Error checking sign in status:', error);
        new bootstrap.Modal(document.getElementById("signInModal")).show();
    }
}

const checkoutForm = document.getElementById("checkoutSignInForm");
if (checkoutForm) {
    checkoutForm.addEventListener("submit", function (event) {
        event.preventDefault();
        checkoutSignIn();
    });
}

async function loadModels(productId) {
    try {
        const payload = {
            productId: [productId]
        };

        const request = await fetch("/api/model/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const jsonObject = await request.json();
            const modelsTable = document.getElementById("modelsTable");
            modelsTable.innerHTML = "";
            const fragment = document.createDocumentFragment();

            jsonObject.data.forEach(model => {
                models[model.modelId] = model;
                const button = document.createElement("button");
                button.dataset.model_id=model.modelId;
                button.classList.add("btn", "border", "rounded-3");
                button.innerHTML=`<img src=api/model/img/${model.modelId} width="50" alt="Side View">`;
                fragment.appendChild(button);
            });
            modelsTable.appendChild(fragment);
            changeModel(jsonObject.data[0].modelId);
            maybeOpenBuyModal();
        } else {
            Notiflix.Notify.failure('Failed to load models');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

function changeModel(modelId) {
    buyingModelId = modelId;
    const model = models[modelId];
    document.getElementById("product_label").innerText = model.model;
    document.getElementById("model").innerText = model.model;
    document.getElementById("price").innerText = "Rs." + model.price;
    document.getElementById("vimg").src = `api/model/img/${modelId}`;
}

function buyingProduct() {
    const model = models[buyingModelId];
    const buyingProductId = document.getElementById("buying_product_id");
    const buyingProductBrand = document.getElementById("buying_product_brand");
    const buyingProductModel = document.getElementById("buying_product_model");
    const buyingProductPrice = document.getElementById("buying_product_price");
    const buyingProductImg = document.getElementById("mimg");

    if (!model || !buyingProductId || !buyingProductBrand || !buyingProductModel || !buyingProductPrice || !buyingProductImg) {
        return;
    }

    buyingProductId.value = buyingModelId;
    buyingProductBrand.textContent = model.brandId;
    buyingProductModel.textContent = model.modelName;
    buyingProductPrice.textContent = "Rs." + model.price;
    buyingProductImg.src = `api/model/img/${buyingModelId}`;
}

function toCheckout() {

    var buyingProductQty = document.getElementById("pqty");
    var buyingProductId = document.getElementById("buying_product_id");

    window.location = "/checkout.html/?" + buyingProductId.value + "/" + buyingProductQty.value;

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
    buyingProduct();

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