let id;
let qty;
let modelData;
let deliveryData ={};
let address;
let currentUserProfile;

window.addEventListener("load", async function () {

    let param = new URLSearchParams(window.location.search);

    id = param.get("id");
    qty = param.get("qty");

    await loadUserDetails();
    await loadModels();
    await loadDeliveryDetails();

});

let previousMethod = null;

let deliveryDetails = document.getElementById("deliveryDetails");
deliveryDetails.addEventListener("click", function (event) {

    let method = event.target.closest(".btn");

    if (!method) {
        return;
    }

    method.classList.add("bg-primary-subtle", "border-2", "border-primary");

    method.querySelector("input").checked = true;

    if (previousMethod != null) {
        previousMethod.classList.remove("bg-primary-subtle", "border-2", "border-primary");
        previousMethod.querySelector("input").checked = false;
    }

    let subTotal = document.getElementById("subTotal").innerHTML.replace("Rs.", "");

    document.getElementById("deliveryFee").innerHTML = "Rs." + (method.dataset.price).toLocaleString('us-en');
    document.getElementById("grandTotal").innerHTML = "Rs." + (parseFloat(modelData[0].price*qty)+parseFloat(method.dataset.price)).toLocaleString('us-en');

    previousMethod = method;
    const deliveryMethodWarning = document.getElementById("delivery_method_warning");
    if (deliveryMethodWarning) {
        deliveryMethodWarning.innerHTML = "";
    }

});

let payherePayment = document.getElementById("payhere-payment");
payherePayment.addEventListener("click", async function () {
    await paynow();
});

let addressUpdateForm = document.getElementById("addressUpdateForm");
if (addressUpdateForm) {
    addressUpdateForm.addEventListener("submit", async function (event) {
        event.preventDefault();

        try {
            const lineOne = document.getElementById("addressLine1").value.trim();
            const lineTwo = document.getElementById("addressLine2").value.trim();
            const district = document.getElementById("district").value.trim();
            const province = document.getElementById("province").value.trim();
            const city = document.getElementById("city").value.trim();
            const postalCode = document.getElementById("postalCode").value.trim();

            if (!lineOne || !district || !province || !city || !postalCode) {
                const warning = document.getElementById("address_warning");
                if (warning) {
                    warning.innerHTML = " * Please complete all required address fields";
                }
                return;
            }

            const payload = {
                line_one: lineOne,
                line_two: lineTwo,
                district: district,
                province: province,
                city: city,
                postal_code: postalCode
            };

            const request = await fetch("/api/user/updateAddress", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.ok) {
                const response = await request.json();

                if (!response || !response.state) {
                    alert((response && response.message) ? response.message : "Address update failed.");
                    return;
                }

                const warning = document.getElementById("address_warning");
                if (warning) {
                    warning.innerHTML = "";
                }

                Notiflix.Notify.success('Address updated successfully');
                loadUserDetails();
            } else {
                Notiflix.Notify.failure("Address update failed.");
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    });
}


let deliveryMethodId;

async function loadUserDetails() {
    try {
        const request = await fetch("/api/user/userProfile", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
        });

        if (request.ok) {
            const jsonObject = await request.json();
            if (!jsonObject || !jsonObject.state || !jsonObject.data) {
                Notiflix.Notify.failure((jsonObject && jsonObject.message) ? jsonObject.message : "Failed to fetch user details");
                return;
            }


            const profileData = jsonObject.data;
            currentUserProfile = profileData;
            const firstName = profileData.firstName || profileData.first_name || "";
            const lastName = profileData.lastName || profileData.last_name || "";
            const fullName = [firstName, lastName].filter(Boolean).join(" ");

            document.getElementById("deliveryName").innerHTML = fullName;
            document.getElementById("email").innerHTML = profileData.email || "";
            address = {
                line_one: profileData.line_one,
                line_two: profileData.line_two,
                city: profileData.city,
                district: profileData.district,
                province: profileData.province,
                postal_code: profileData.postal_code,
                state: Boolean(profileData.line_one && profileData.city)
            };

            const addressEl = document.getElementById("address");
            if (addressEl) {
                if (address.line_one || address.line_two || address.city || address.district || address.province || address.postal_code) {
                    const parts = [];
                    if (address.line_one) {
                        parts.push(address.line_one);
                    }
                    if (address.line_two) {
                        parts.push(address.line_two);
                    }
                    if (address.city) {
                        parts.push(address.city);
                    }
                    if (address.district) {
                        parts.push(address.district);
                    }
                    if (address.province) {
                        parts.push(address.province);
                    }
                    if (address.postal_code) {
                        parts.push(address.postal_code);
                    }
                    addressEl.textContent = parts.join(", ");
                } else {
                    addressEl.textContent = "No delivery address on file.";
                }
            }

            const lineOne = document.getElementById("addressLine1");
            const lineTwo = document.getElementById("addressLine2");
            const district = document.getElementById("district");
            const province = document.getElementById("province");
            const city = document.getElementById("city");
            const postalCode = document.getElementById("postalCode");

            if (lineOne && lineTwo && district && province && city && postalCode) {
                lineOne.value = address.line_one || "";
                lineTwo.value = address.line_two || "";
                district.value = address.district || "";
                province.value = address.province || "";
                city.value = address.city || "";
                postalCode.value = address.postal_code || "";
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch user details');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

async function loadModels() {
    try {
        const payload = {
            modelId: [id]
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

            if(jsonObject.state) {
                modelData=jsonObject.data;
                modelData.forEach(model => {
                    document.getElementById("modelImg").src = `/api/model/img/${id}`;
                    document.getElementById("productName").innerHTML = model.model;
                    document.getElementById("brand").innerHTML = model.brandName;
                    document.getElementById("price").innerHTML = "Rs." + model.price + " For each iteme(s) ";
                    document.getElementById("qty").innerHTML = qty + " items";
                    document.getElementById("subTotal").innerHTML = "Rs." + (parseFloat(model.price) * parseFloat(qty)).toLocaleString('en-US');
                    document.getElementById("total").innerHTML = "Rs." + (parseFloat(model.price) * parseFloat(qty)).toLocaleString('en-US');
                });
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch models');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

async function loadDeliveryDetails() {
    try {
        const request = await fetch("/api/delivery/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({})
        });

        if (request.ok) {
            const jsonObject = await request.json();

            deliveryDetails = document.getElementById("deliveryDetails");
            deliveryDetails.innerHTML = "";

            const fragment = document.createDocumentFragment();

            if (jsonObject.state) {
                jsonObject.data.forEach(delivery => {
                    deliveryData[delivery.id]=delivery;
                    const div = document.createElement("div");
                    div.classList = "card col-10 col-md-6 border-0";
                    div.innerHTML = `
                            <button class="btn  m-1 p-0 border-secondary-subtle" data-price="${delivery.price}" data-id="${delivery.id}" >
                                <div class="card-body  row  text-start">
                                    <div class="col-1">
                                        <input value="${delivery.id}" class="form-check-input" type="checkbox"></input>
                                    </div>

                                    <div class="col-10">
                                        <h5 class="text-primary fw-bold">${delivery.deliveryMethod}</h5>
                                        <p class="card-title text-success fw-bold">Rs.${delivery.price}</p>
                                        </p>
                                        <p class="card-title fw-bold">Guaranteed In</p>
                                        <p class="card-title text-secondary fw-bolder">${delivery.deliveryDays} Days</p>
                                    </div>

                                </div>
                            </button>`;
                    fragment.appendChild(div);
                });
            }
            deliveryDetails.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch delivery details');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

async function paynow() {
    try {
        //Check if address is valid (should be an object with keys, not null or empty)
        if (!address || !address.state) {
            document.getElementById("address_warning").innerHTML = " * Please add a delivery address";
            return;
        }

        if (previousMethod == null) {
            const deliveryMethodWarning = document.getElementById("delivery_method_warning");
            deliveryMethodWarning.innerHTML = " * Please choose a delivery method";
            return;
        }

        const payload = {
            id: id,
            qty: qty,
            delivery_method_id: previousMethod.dataset.id
        };

        const request = await fetch("/api/order/new", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        let jsonObject = null;
        try {
            jsonObject = await request.json();
        } catch (error) {
            jsonObject = null;
        }

        if (!request.ok) {
            Notiflix.Notify.failure((jsonObject && (jsonObject.message || jsonObject.error))
                ? (jsonObject.message || jsonObject.error)
                : 'Failed to create order');
            return;
        }

        if (!jsonObject || !jsonObject.state || !jsonObject.data) {
            Notiflix.Notify.failure((jsonObject && (jsonObject.message || jsonObject.error))
                ? (jsonObject.message || jsonObject.error)
                : 'Failed to create order');
            return;
        }

        const orderData = jsonObject.data;
        const payhereSdk = window.payhere;
        if (!payhereSdk || typeof payhereSdk.startPayment !== "function") {
            Notiflix.Notify.failure("PayHere is not available. Please disable ad blockers and try again.");
            return;
        }

            // Payment completed. It can be a successful failure.
            payhereSdk.onCompleted = function onCompleted() {
                // Mark order as paid by updating order status
                (async () => {
                    try {
                        const statusUpdate = {
                            order_id: orderData.orderId || orderData.order_id
                        };

                        const statusRequest = await fetch("/api/order/updateStatusAfterPayment", {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify(statusUpdate)
                        });

                        if (statusRequest.ok) {
                            const statusResponse = await statusRequest.json();
                            if (statusResponse.state) {
                                // Show success message
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Payment Successful!',
                                    text: 'Your order has been placed and payment confirmed.',
                                    confirmButtonText: 'Continue Shopping'
                                }).then((result) => {
                                    // Redirect to home or order tracking page
                                    window.location.href = "/profile.html";
                                });
                            } else {
                                // Payment received but status update failed - still a success
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Payment Received!',
                                    text: 'Your payment has been received. Order ID: ' + (orderData.orderId || orderData.order_id),
                                    confirmButtonText: 'View Orders'
                                }).then((result) => {
                                    window.location.href = "/profile.html";
                                });
                            }
                        } else {
                            throw new Error('Failed to update order status');
                        }
                    } catch (error) {
                        console.error('Error:', error);
                        Notiflix.Notify.failure('Error ' + error);
                    }
                })();
            };

            // Payment window closed
            payhereSdk.onDismissed = function onDismissed() {
                cancelOrder(orderData.orderId || orderData.order_id);
            };

            // Error occurred
            payhereSdk.onError = function onError(error) {
                // Log error and show user-friendly message
                console.error("PayHere Payment Error:", error);
                Swal.fire({
                    icon: 'error',
                    title: 'Payment Failed',
                    text: 'An error occurred during payment. Please try again or contact support.',
                    confirmButtonText: 'Back to Checkout'
                }).then((result) => {
                    // Option to retry by staying on page or cancel
                    window.location.href = "/timestore/checkout/" + id + "/" + qty;
                });
            };

            // Put the payment variables here
            const selectedModel = Array.isArray(modelData) && modelData.length > 0 ? modelData[0] : null;
            const firstName = (currentUserProfile && (currentUserProfile.firstName || currentUserProfile.first_name)) || "Customer";
            const lastName = (currentUserProfile && (currentUserProfile.lastName || currentUserProfile.last_name)) || "";
            const phone = (currentUserProfile && currentUserProfile.mobile) || "0770000000";
            const items = (jsonObject.items || orderData.items || (selectedModel ? selectedModel.model : null));
            const amountValue = Number(jsonObject.amount || orderData.amount || orderData.total || 0);
            const amount = Number.isFinite(amountValue) && amountValue > 0 ? amountValue.toFixed(2) : null;
            const addressLine = jsonObject.address || orderData.address || [address?.line_one, address?.line_two].filter(Boolean).join(", ");
            const city = jsonObject.city || orderData.city || address?.city || "Colombo";
            const country = jsonObject.country || orderData.country || "Sri Lanka";

            const merchantId = jsonObject.merchant_id || orderData.merchant_id;
            const hash = jsonObject.hash || orderData.hash;
            const orderId = orderData.orderId || orderData.order_id;

            if (!merchantId || !hash) {
                Notiflix.Notify.failure("Payment initialization data is missing (merchant_id/hash). Please contact support.");
                cancelOrder(orderId);
                return;
            }

            if (!items || !amount || !orderId) {
                Notiflix.Notify.failure("Payment details are incomplete. Please try again.");
                cancelOrder(orderId);
                return;
            }

            const payment = {
                "sandbox": true,
                "merchant_id": merchantId,
                "return_url": "http://localhost/index.php",
                "cancel_url": "http://localhost/index.php",
                "notify_url": "http://localhost/index.php",
                "order_id": orderId,
                "items": items,
                "amount": amount,
                "currency": jsonObject.currency || orderData.currency || "LKR",
                "hash": hash,
                "first_name": jsonObject.first_name || orderData.first_name || firstName,
                "last_name": jsonObject.last_name || orderData.last_name || lastName,
                "email": jsonObject.email || orderData.email || (currentUserProfile ? currentUserProfile.email : ""),
                "phone": jsonObject.phone || orderData.phone || phone,
                "address": addressLine,
                "city": city,
                "country": country,
                "delivery_address": addressLine,
                "delivery_city": city,
                "delivery_country": country,
                "custom_1": "",
                "custom_2": ""
            };
            // Show the payhere.js popup, when "PayHere Pay" is clicked
            payhereSdk.startPayment(payment);
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

async function cancelOrder(orderId) {
    try {
        if (!orderId) {
            return;
        }

        const payload = {
            orderId: orderId
        };

        const request = await fetch("/api/order/cancel", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            // No UI update needed for cancel callback
        } else {
            Notiflix.Notify.failure('Failed to cancel order');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}





