const models = {};
let buyingModelId = 0;
let model;
let isLoggedIn;

//------------------------Load Model by product id When the Page is loaded------------------------
window.addEventListener("load", event => {
    const parm = new URLSearchParams(window.location.search);
    let id = parm.get("id");
    loadModels(id);
    checkLoginStatus();
});

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
            console.log(jsonObject);
            const modelsTable = document.getElementById("modelsTable");
            modelsTable.innerHTML = "";
            const fragment = document.createDocumentFragment();

            colorGroup.innerHTML = "";

            jsonObject.data.forEach(model => {
                models[model.modelId] = model;
                const button = document.createElement("button");
                button.dataset.modelId = model.modelId;
                button.classList.add("btn", "border", "rounded-3");
                button.innerHTML = `<img src=api/model/img/${model.modelId} width="50" alt="Side View">`;
                fragment.appendChild(button);

                const input = document.createElement("input");
                input.checked = jsonObject.data.indexOf(model) === 0;
                input.type = "radio";
                input.classList.add("btn-check");
                input.name = "color";
                input.id = `${model.modelId}colorBtn`;
                input.dataset.modelId = model.modelId;
                input.autocomplete = "off";
                colorGroup.appendChild(input);

                const label = document.createElement("label");
                label.textContent = model.color;
                label.htmlFor = `${model.modelId}colorBtn`;
                label.classList.add("btn", "btn-outline-dark");
                colorGroup.appendChild(label);

                description.textContent = model.description;

            });
            console.log(models);
            modelsTable.appendChild(fragment);

            changeModel(jsonObject.data[0].modelId);

        } else {
            Notiflix.Notify.failure('Failed to load models');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

//==============================================================================================


//------------------------------------Changing Selected Model-------------------------------------
document.getElementById("modelsTable").addEventListener("click", (event) => {
    const button = event.target.closest(".btn");
    changeModel(button.dataset.modelId);
});

const colorGroup = document.getElementById("colors");
colorGroup.addEventListener("click", (event) => {
    let colorBtn = event.target.closest("input");
    changeModel(colorBtn.dataset.modelId);
})

const description = document.getElementById("description");

function changeModel(modelId) {
    buyingModelId = modelId;
    let model = models[modelId];
    description.textContent = model.description;
    document.getElementById(`${model.modelId}colorBtn`).checked = true;
    document.getElementById("product_label").innerText = model.model;
    document.getElementById("model").innerText = model.model;
    document.getElementById("price").innerText = "Rs." + model.price;
    document.getElementById("vimg").src = `api/model/img/${buyingModelId}`;
}

//=================================================================================================


//------------------------------Checks User login Status-------------------------------------------
const signInModalElement = document.getElementById("signInModal");
const signInModalInstance = window.bootstrap.Modal.getOrCreateInstance(signInModalElement);

const buyNowModalElement = document.getElementById("buyNowModal");
const modalInstance = window.bootstrap.Modal.getOrCreateInstance(buyNowModalElement);

async function checkLoginStatus() {
    try {
        const response = await fetch("/api/user/signinstatus", {
            method: "GET"
        });

        if (response.ok) {
            const jsonResponse = await response.json();
            isLoggedIn = jsonResponse.status;
        }

    } catch (error) {
        console.error('Error checking login status:', error);
        isLoggedIn = false;
    }
}

async function handleBuyNow() {
    try {
        await checkLoginStatus();

        if (isLoggedIn) {
            // User is logged in
            buyingProduct();
            modalInstance.show();

        } else {
            // User is not logged in
            signInModalInstance.show();
        }

    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error: ' + error);
    }
}


//================================================================================================

//--------------------------Handle Signing before going to checkout-----------------------------

const checkoutForm = document.getElementById("checkoutSignInForm");
const buyNowModal = new bootstrap.Modal(document.getElementById("buyNowModal"));

checkoutForm.addEventListener("submit", (event) => {
    event.preventDefault();
    signIn();
});

async function signIn() {
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
            signInModalInstance.hide();
            buyingProduct();
            buyNowModal.show();
        } else {
            alert(response.message || "Invalid email or password.");
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

//==============================================================================================

//-------------------Adding details of the buying model to Buy Now Modal-------------------------
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
    buyingProductBrand.textContent = model.brand_id;
    buyingProductModel.textContent = model.model_name;
    buyingProductPrice.textContent = "Rs." + model.price;
    buyingProductImg.src = `api/model/img/${buyingModelId}`;
}

//===============================================================================================

//----------------------------------Adjusting Buying model Count--------------------------------

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

//===============================================================================================

//---------------------------------Redirection to checkout page----------------------------------
function toCheckout() {
    const buyingProductQty = document.getElementById("pqty");
    const buyingProductId = document.getElementById("buying_product_id");
    window.location = "/checkout.html?id=" + buyingProductId.value + "&qty=" + buyingProductQty.value;
}

//=================================================================================================


