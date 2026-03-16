var id;
var qty;
var address;


window.addEventListener("load", function () {
    let pathname = window.location.pathname;
    let pattern = /^\/timestore\/checkout\/([0-9]+)\/([0-9]+)$/;
    let match = pathname.match(pattern);
    
    // Check if URL matches pattern before proceeding
    if (!match) {
        console.error("Invalid checkout URL format. Expected: /timestore/checkout/{id}/{qty}");
        window.location.href = "/timestore/index.php";
        return;
    }
    
    let matches = Array.from(match);
    id = matches[1];
    qty = matches[2];

    loadUserDetails();
    loadModels();
    loadDeliveryDetails();
});

var previous_method = null;

var deliveryDetails = document.getElementById("deliveryDetails");
deliveryDetails.addEventListener("click", function (event) {

    var method = event.target.closest(".btn");
    
    // Guard against null if clicking on non-button element
    if (!method) {
        return;
    }
    
    method.classList.add("bg-primary-subtle", "border-2", "border-primary");

    method.querySelector("input").checked = true;

    if (previous_method != null) {
        previous_method.classList.remove("bg-primary-subtle", "border-2", "border-primary");
        previous_method.querySelector("input").checked = false;
    }

    let subTotal = document.getElementById("subTotal").innerHTML.replace("Rs.", "");

    document.getElementById("deliveryFee").innerHTML = "Rs." + method.dataset.price;
    document.getElementById("grandTotal").innerHTML = "Rs." + (parseFloat(method.dataset.price) + parseFloat(subTotal));

    previous_method = method;
    delivery_method_warning.innerHTML = "";

});

var payhere_payment = document.getElementById("payhere-payment");
payhere_payment.addEventListener("click", function () {
    paynow();
});

var addressUpdateForm = document.getElementById("addressUpdateForm");
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

            const form = new FormData();
            form.append("line_one", lineOne);
            form.append("line_two", lineTwo);
            form.append("district", district);
            form.append("province", province);
            form.append("city", city);
            form.append("postal_code", postalCode);

            const request = await fetch("/api/user/updateAddress", {
                method: "POST",
                body: form
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


var delivery_method_id;

async function loadUserDetails() {
    try {
        const request = await fetch("/api/user/userProfile", {
            method: "POST"
        });

        if (request.ok) {
            const jsonObject = await request.json();
            const user = jsonObject.user;

            document.getElementById("deliveryName").innerHTML = user.first_name + " " + user.last_name;
            document.getElementById("email").innerHTML = user.email;
            address = jsonObject.address;

            const addressEl = document.getElementById("address");
            if (addressEl) {
                if (address) {
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

            if (address && lineOne && lineTwo && district && province && city && postalCode) {
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
        const form = new FormData();
        form.append("model_id", id);

        const request = await fetch("/api/model/load", {
            method: "POST",
            body: form
        });

        if (request.ok) {
            const jsonObject = await request.json();

            jsonObject.models.forEach(model => {
                document.getElementById("modelImg").src = model.img_path;
                document.getElementById("productName").innerHTML = model.model_name;
                document.getElementById("brand").innerHTML = model.brand_name;
                document.getElementById("price").innerHTML = "Rs." + model.price + " For each iteme(s) ";
                document.getElementById("qty").innerHTML = qty + " items";
                document.getElementById("subTotal").innerHTML = "Rs." + (parseFloat(model.price) * parseFloat(qty));
                document.getElementById("total").innerHTML = "Rs." + (parseFloat(model.price) * parseFloat(qty));
            });
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
            method: "POST"
        });

        if (request.ok) {
            const jsonObject = await request.json();

            deliveryDetails = document.getElementById("deliveryDetails");
            deliveryDetails.innerHTML = "";

            const fragment = document.createDocumentFragment();

            jsonObject.forEach(delivery => {
                const div = document.createElement("div");
                div.classList = "card col-10 col-md-6 border-0";
                div.innerHTML = `
                            <button class="btn  m-1 p-0 border-secondary-subtle" data-price="${delivery.price}" data-id="${delivery.id}" >
                                <div class="card-body  row  text-start">
                                    <div class="col-1">
                                        <input value="${delivery.id}" class="form-check-input" type="checkbox"></input>
                                    </div>

                                    <div class="col-10">
                                        <h5 class="text-primary fw-bold">${delivery.method}</h5>
                                        <p class="card-title text-success fw-bold">Rs.${delivery.price}</p>
                                        </p>
                                        <p class="card-title fw-bold">Guaranteed by</p>
                                        <p class="card-title text-secondary fw-bolder">${delivery.delivery_days}</p>

                                    </div>

                                </div>
                            </button>`;
                fragment.appendChild(div);
            });
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
        // Check if address is valid (should be an object with keys, not null or empty)
        if (!address || Object.keys(address).length === 0) {
            document.getElementById("address_warning").innerHTML = " * Please add a delivery address";
            return;
        }

        if (previous_method == null) {
            const delivery_method_warning = document.getElementById("delivery_method_warning");
            delivery_method_warning.innerHTML = " * Please choose a delivery method";
            return;
        }

        const form = new FormData();
        form.append("id", id);
        form.append("qty", qty);
        form.append("delivery_method_id", previous_method.dataset.id);

        const request = await fetch("/api/order/new", {
            method: "POST",
            body: form
        });

        if (request.ok) {
            const jsonObject = await request.json();

            // Payment completed. It can be a successful failure.
            payhere.onCompleted = function onCompleted() {
                // Mark order as paid by updating order status
                (async () => {
                    try {
                        const statusUpdate = new FormData();
                        statusUpdate.append("order_id", jsonObject.order_id);

                        const statusRequest = await fetch("/api/order/updateStatusAfterPayment", {
                            method: "POST",
                            body: statusUpdate
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
                                    window.location.href = "/timestore/profile";
                                });
                            } else {
                                // Payment received but status update failed - still a success
                                Swal.fire({
                                    icon: 'success',
                                    title: 'Payment Received!',
                                    text: 'Your payment has been received. Order ID: ' + jsonObject.order_id,
                                    confirmButtonText: 'View Orders'
                                }).then((result) => {
                                    window.location.href = "/timestore/profile";
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
            payhere.onDismissed = function onDismissed() {
                cancelOrder(jsonObject.order_id);
            };

            // Error occurred
            payhere.onError = function onError(error) {
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
            const payment = {
                "sandbox": true,
                "merchant_id": jsonObject.merchant_id,    // Replace your Merchant ID
                "return_url": "http://localhost/timestore/index.php",
                "cancel_url": "http://localhost/timestore/index.php",
                "notify_url": "http://localhost/timestore/index.php",
                "order_id": jsonObject.order_id,
                "items": jsonObject.items,
                "amount": jsonObject.amount,
                "currency": jsonObject.currency,
                "hash": jsonObject.hash, // *Replace with generated hash retrieved from backend
                "first_name": jsonObject.first_name,
                "last_name": jsonObject.last_name,
                "email": jsonObject.email,
                "phone": jsonObject.phone,
                "address": jsonObject.address,
                "city": jsonObject.city,
                "country": jsonObject.country,
                "delivery_address": jsonObject.address,
                "delivery_city": jsonObject.city,
                "delivery_country": jsonObject.country,
                "custom_1": "",
                "custom_2": ""
            };
            // Show the payhere.js popup, when "PayHere Pay" is clicked
            payhere.startPayment(payment);
        } else {
            Notiflix.Notify.failure('Failed to create order');
        }
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

        const form = new FormData();
        form.append("orderId", orderId);

        const request = await fetch("/api/order/cancel", {
            method: "POST",
            body: form
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





