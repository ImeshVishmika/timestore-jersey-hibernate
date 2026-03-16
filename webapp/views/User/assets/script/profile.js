(function () {
    function setText(id, text) {
        var el = document.getElementById(id);
        if (el) {
            el.textContent = text || "";
        }
    }

    function setValue(id, value) {
        var el = document.getElementById(id);
        if (el) {
            el.value = value || "";
        }
    }

    function setImage(id, src) {
        var el = document.getElementById(id);
        if (!el) {
            return;
        }

        if (src) {
            el.src = src;
            return;
        }

        var fallback = el.getAttribute("data-default-src");
        if (fallback) {
            el.src = fallback;
        }
    }

    function setAddressLines(id, parts) {
        var el = document.getElementById(id);
        if (!el) {
            return;
        }

        el.textContent = "";
        var clean = parts.filter(function (part) {
            return part && String(part).trim() !== "";
        });

        clean.forEach(function (part, index) {
            if (index > 0) {
                el.appendChild(document.createElement("br"));
            }
            el.appendChild(document.createTextNode(part));
        });
    }

    async function loadProfile() {
        try {
            const request = await fetch("/api/user/userProfile", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({})
            });

            if (request.status !== 200) {
                return;
            }

            let data;
            try {
                data = await request.json();
            } catch (error) {
                return;
            }

            if (!data || data.error) {
                return;
            }

            const user = data.user || {};
            const address = data.address || {};
            const fullName = [user.first_name, user.last_name].filter(Boolean).join(" ");

            setImage("profileImage", "/timestore/userImg");
            setText("profileName", fullName);
            setText("profileEmail", user.email || "");

            setText("overviewName", fullName);
            setText("overviewMobile", user.mobile || "");
            setText("overviewEmail", user.email || "");

            setValue("fname", user.first_name || "");
            setValue("lname", user.last_name || "");
            setValue("email", user.email || "");
            setValue("mobile", user.mobile || "");

            setValue("line1", address.line_one || "");
            setValue("line2", address.line_two || "");
            setValue("city", address.city || "");
            setValue("district", address.district || "");
            setValue("province", address.province || "");
            setValue("postal_code", address.postal_code || "");

            const addressParts = [
                address.line_one,
                address.line_two,
                address.city,
                address.district,
                address.province,
                address.postal_code
            ];

            setAddressLines("overviewAddress", addressParts);
            setAddressLines("shippingAddress", addressParts);
            setText("shippingName", fullName);
            setText("shippingAddressLine", addressParts.filter(Boolean).join(", "));
            setText("shippingMobile", user.mobile ? "Mobile: " + user.mobile : "");
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    }

    async function handleProfileUpdate(event) {
        event.preventDefault();

        try {
            const payload = {
                first_name: document.getElementById("fname").value.trim(),
                last_name: document.getElementById("lname").value.trim(),
                mobile: document.getElementById("mobile").value.trim(),
                email: document.getElementById("email").value.trim()
            };

            const request = await fetch("/api/user/updateProfile", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.status !== 200) {
                alert("Profile update failed.");
                return;
            }

            let data;
            try {
                data = await request.json();
            } catch (error) {
                alert("Profile update failed.");
                return;
            }

            if (!data || !data.state) {
                alert((data && data.message) ? data.message : "Profile update failed.");
                return;
            }

            Notiflix.Notify.success('Profile updated successfully');
            loadProfile();
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    }

    async function handleAddressUpdate(event) {
        event.preventDefault();

        try {
            const lineOne = document.getElementById("line1").value.trim();
            const lineTwo = document.getElementById("line2").value.trim();
            const city = document.getElementById("city").value.trim();
            const district = document.getElementById("district").value.trim();
            const province = document.getElementById("province").value.trim();
            const postalCode = document.getElementById("postal_code").value.trim();

            if (!lineOne || !city || !district || !province || !postalCode) {
                alert("Please complete all required address fields.");
                return;
            }

            const payload = {
                line_one: lineOne,
                line_two: lineTwo,
                city: city,
                district: district,
                province: province,
                postal_code: postalCode,
                email: document.getElementById("email").value.trim()
            };

            const request = await fetch("/api/user/updateAddress", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.status !== 200) {
                alert("Address update failed.");
                return;
            }

            let data;
            try {
                data = await request.json();
            } catch (error) {
                alert("Address update failed.");
                return;
            }

            if (!data || !data.state) {
                alert((data && data.message) ? data.message : "Address update failed.");
                return;
            }

            Notiflix.Notify.success('Address updated successfully');
            loadProfile();
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    }

    async function loadUserOrders() {
        try {
            const request = await fetch("/api/order/userOrders", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({})
            });

            if (!request.ok) {
                return;
            }

            const data = await request.json();
            if (data.state && data.data && data.data.length > 0) {
                renderUserOrders(data.data);
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    }

    function renderUserOrders(orders) {
        var tbody = document.querySelector("#ordersTable tbody");
        if (!tbody) return;

        tbody.innerHTML = "";
        orders.forEach(function (order) {
            var row = document.createElement("tr");
            var statusClass = "bg-secondary";
            if (order.order_status === 5) {
                statusClass = "bg-success";
            } else if (order.order_status === 6) {
                statusClass = "bg-danger";
            }

            row.innerHTML = `
                <td>${order.order_id}</td>
                <td>${new Date(order.ordered_date).toLocaleDateString()}</td>
                <td><span class="badge ${statusClass}">${order.status_name || "Unknown"}</span></td>
                <td>Rs. ${parseFloat(order.total).toFixed(2)}</td>
                <td><button class="btn btn-sm btn-outline-dark" onclick="loadOrderDetails(${order.order_id})" data-bs-toggle="modal" data-bs-target="#exampleModal">View</button></td>
            `;
            tbody.appendChild(row);
        });
    }

    async function loadUserWishlist() {
        try {
            const request = await fetch("/api/wishlist/load", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({})
            });

            if (!request.ok) {
                return;
            }

            const data = await request.json();
            if (data.state && data.data && data.data.length > 0) {
                renderUserWishlist(data.data);
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    }

    function renderUserWishlist(items) {
        var container = document.getElementById("wishlistContainer");
        if (!container) return;

        var countEl = document.getElementById("wishlistCount");
        if (countEl) {
            countEl.textContent = items.length + " items";
        }

        container.innerHTML = "";
        items.forEach(function (item) {
            var col = document.createElement("div");
            col.className = "col-6 col-md-4 col-lg-3";
            col.innerHTML = `
                <div class="card h-100 shadow-sm border-0">
                    <div class="p-3 text-center" style="background-color: #f8f9fa;">
                        <img src="/api/model/img/${item.product_id}" 
                             class="card-img-top img-fluid" 
                             alt="${item.model_name}" 
                             style="max-height: 150px; object-fit: contain;">
                    </div>
                    <div class="card-body p-3">
                        <h5 class="card-title fw-bold fs-6 mb-1">${item.model_name || "Product"}</h5>
                        <p class="text-muted small mb-1">${item.brand_name || ""}</p>
                        <p class="text-danger fw-bold mb-2">Rs. ${parseFloat(item.price).toFixed(2)}</p>
                        <div class="d-flex justify-content-between">
                            <a href="/timestore/viewProduct/${item.product_id}" class="btn btn-sm btn-dark">View</a>
                            <button class="btn btn-sm btn-outline-danger" onclick="removeFromWishlist(${item.watchlist_id})">
                                <i class="bi bi-trash"></i>
                            </button>
                        </div>
                    </div>
                </div>
            `;
            container.appendChild(col);
        });
    }

    function removeFromWishlist(watchlistId) {
        // This function can be implemented to call an API to remove from wishlist
        // For now, it reloads the wishlist after removal
        if (confirm("Remove from wishlist?")) {
            loadUserWishlist();
        }
    }

    document.addEventListener("DOMContentLoaded", function () {
        loadProfile();
        loadUserOrders();
        loadUserWishlist();

        var detailsForm = document.getElementById("updateDetailsForm");
        if (detailsForm) {
            detailsForm.addEventListener("submit", handleProfileUpdate);
        }

        var addressForm = document.getElementById("updateAddressForm");
        if (addressForm) {
            addressForm.addEventListener("submit", handleAddressUpdate);
        }
    });
})();
