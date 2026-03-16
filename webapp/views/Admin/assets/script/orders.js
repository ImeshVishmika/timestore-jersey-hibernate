document.getElementById("orders-tab").addEventListener("click", loadOrders);

async function loadOrders() {
    try {
        const orderTableBody = document.getElementById("orderTableBody");
        orderTableBody.innerHTML = "";

        const request = await fetch("/api/order/load", {
            method: "POST"
        });

        if (request.ok) {
            const jsonObject = await request.json();
            const fragment = document.createDocumentFragment();

            jsonObject.orders.forEach(order => {
                const tr = document.createElement("tr");
                tr.innerHTML = `<td class="ps-4 fw-bold text-danger">#ORD-${order.order_id}</td>
                                                        <td>
                                                            <div class="d-flex align-items-center">
                                                                <div class="bg-dark text-white rounded-circle d-flex align-items-center justify-content-center me-2" style="width: 35px; height: 35px; font-size: 0.8rem;">JS</div>
                                                                <div>
                                                                    <h6 class="mb-0 text-dark small fw-bold">${order.first_name + " " + order.last_name}</h6>
                                                                    <small class="text-muted" style="font-size: 0.75rem;">${order.user_email}</small>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td class="text-secondary small fw-bold">${order.ordered_date}</td>
                                                        <td class="fw-bold">Rs.${order.total}</td>
                                                        <td><span class="badge bg-light text-secondary border">Card</span></td>
                                                       <td><span class="status-badge">${order.status}</span></td>
                                                        <td class="text-end pe-4">
                                                            <div class="dropdown">
                                                                <button class="btn btn-sm btn-light border" type="button" data-bs-toggle="dropdown">
                                                                    <i class="bi bi-three-dots-vertical"></i>
                                                                </button>
                                                                <ul class="dropdown-menu dropdown-menu-end shadow border-0 rounded-3">
                                                                    <li><a class="dropdown-item small py-2" data-order_id="${order.order_id}" data-bs-toggle="modal" data-bs-target="#orderModal"><i class="bi bi-eye me-2"></i>View Details</a></li>
                                                                    <li><a class="dropdown-item small py-2" href="#"><i class="bi bi-truck me-2"></i>Mark as Shipped</a></li>
                                                                    <li>
                                                                        <hr class="dropdown-divider">
                                                                    </li>
                                                                    <li><a class="dropdown-item small py-2 text-danger" href="#"><i class="bi bi-x-circle me-2"></i>Cancel Order</a></li>
                                                                </ul>
                                                            </div>
                                                        </td>` ;
                fragment.appendChild(tr)

            });
            orderTableBody.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch orders');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

document.getElementById("orderModal").addEventListener('show.bs.modal', async function (event) {
    try {
        const button = event.relatedTarget;

        if (!button || !button.dataset.order_id) {
            return;
        }

        const form = new FormData();
        form.append("order_id", button.dataset.order_id);

        const request = await fetch("/api/order/details", {
            method: "POST",
            body: form
        });

        if (request.ok) {
            const jsonObject = await request.json();

            if (jsonObject.state) {
                const data = jsonObject.data;

                document.getElementById("viewOrderId").innerHTML = data.order.order_id;
                document.getElementById("viewCustomerName").textContent = data.user.first_name + " " + data.user.last_name;
                document.getElementById("viewCustomerEmail").textContent = data.user.email;
                document.getElementById("viewCustomerPhone").textContent = data.user.mobile;
                document.getElementById("viewOrderDate").textContent = data.order.ordered_date;
                document.getElementById("viewCustomerAddressLine1").textContent = data.user.address_line1;
                document.getElementById("viewCustomerAddressLine2").textContent = data.user.address_line2;
                document.getElementById("viewCustomerCity").textContent = data.user.city;
                document.getElementById("viewCustomerProvince").textContent = data.user.province + "province";
                document.getElementById("viewCustomerDistrict").textContent = data.user.district;
                document.getElementById("viewDelivery").textContent = "Rs." + data.order.delivery_fee;
                document.getElementById("viewSubTotal").textContent = "Rs. " + data.order.sub_total;
                document.getElementById("viewGrandTotal").textContent = "Rs. " + data.order.grand_total;

                const orderItemsTable = document.getElementById("orderItemsTable");
                orderItemsTable.innerHTML = "";

                const fragment = document.createDocumentFragment();

                data.order_items.forEach(order => {
                    const tr = document.createElement("tr");
                    tr.innerHTML =
                        `
                                                        <td class="ps-4 py-3">
                                                            <div class="d-flex align-items-center">
                                                                <div class="bg-white border rounded-3 d-flex align-items-center justify-content-center me-3" style="width: 50px; height: 50px;">
                                                                    <img src=${order.img_src} class="img-fluid p-1" style="max-height: 100%; object-fit: contain;" alt="Product">
                                                                </div>

                                                                <div>
                                                                    <h6 class="fw-bold text-dark mb-0 small">${order.model}</h6>
                                                                    <small class="text-muted" style="font-size: 11px;">${order.product_name}</small>
                                                                </div>
                                                            </div>
                                                        </td>

                                                        <td class="text-center align-middle">
                                                            <span class="fw-bold text-secondary">${order.qty}</span>
                                                        </td>

                                                        <td class="text-end align-middle">
                                                            <span class="small fw-bold text-dark">Rs.${order.price}</span>
                                                        </td>

                                                        <td class="text-end align-middle pe-4">
                                                            <span class="fw-bold text-dark">Rs.${order.price * order.qty}</span>
                                                        </td>
                                                    `;
                    fragment.appendChild(tr);

                });

                orderItemsTable.appendChild(fragment);
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch order details');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
})