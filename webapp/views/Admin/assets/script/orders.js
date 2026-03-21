document.getElementById("orders-tab").addEventListener("click", loadOrders);
document.addEventListener('DOMContentLoaded', loadOrderStatuses);


let filterData ={};
const orderStatusSelect = document.getElementById("orderStatusSelect");
const priceMinFilter = document.getElementById("priceMinFilter");
const priceMaxFilter = document.getElementById("priceMaxFilter");
const dateFromFilter = document.getElementById("dateFromFilter");
const dateToFilter = document.getElementById("dateToFilter");
const clearFiltersBtn = document.getElementById("clearFiltersBtn");

orderStatusSelect.addEventListener("change",async ()=>{
    filterData.orderStausId=orderStatusSelect.value;
    if(orderStatusSelect.selectedIndex===0){
        filterData.orderStausId=null;
    }

    await loadOrders();
});

priceMinFilter.addEventListener("change", async () => {
    const minValue = priceMinFilter.value.trim();
    if (minValue) {
        filterData.minPrice = parseFloat(minValue);
    } else {
        delete filterData.minPrice;
    }
    await loadOrders();
});

priceMaxFilter.addEventListener("change", async () => {
    const maxValue = priceMaxFilter.value.trim();
    if (maxValue) {
        filterData.maxPrice = parseFloat(maxValue);
    } else {
        delete filterData.maxPrice;
    }
    await loadOrders();
});

dateFromFilter.addEventListener("change", async () => {
    const dateValue = dateFromFilter.value;
    if (dateValue) {
        filterData.dateFrom = dateValue;
    } else {
        delete filterData.dateFrom;
    }
    await loadOrders();
});

dateToFilter.addEventListener("change", async () => {
    const dateValue = dateToFilter.value;
    if (dateValue) {
        filterData.dateTo = dateValue;
    } else {
        delete filterData.dateTo;
    }
    await loadOrders();
});

clearFiltersBtn.addEventListener("click", async () => {
    // Clear all filter inputs
    orderStatusSelect.selectedIndex = 0;
    priceMinFilter.value = "";
    priceMaxFilter.value = "";
    dateFromFilter.value = "";
    dateToFilter.value = "";
    
    // Clear filter data
    filterData = {};
    
    // Reload orders
    await loadOrders();
});
async function loadOrderStatuses() {
    try {
        const request = await fetch("/api/order/statuses", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
        });

        if (request.ok) {
            const jsonObject = await request.json();
            
            if (jsonObject.state && jsonObject.data) {
                // Add default option
                orderStatusSelect.innerHTML = '<option selected>Status: All</option>';
                
                // Add each status as an option
                jsonObject.data.forEach(status => {
                    const option = document.createElement("option");
                    option.value = status.orderStatusId;
                    option.textContent = status.status;
                    orderStatusSelect.appendChild(option);
                });
            }
        }
    } catch (error) {
        console.error('Error loading order statuses:', error);
    }
}

function getStatusBadgeClass(statusName) {
    if (!statusName) return "bg-secondary";
    
    const status = statusName.toLowerCase();
    
    if (status.includes("pending") || status.includes("processing")) {
        return "bg-warning text-dark";
    } else if (status.includes("shipped") || status.includes("in-transit")) {
        return "bg-info text-white";
    } else if (status.includes("delivered") || status.includes("completed")) {
        return "bg-success text-white";
    } else if (status.includes("cancelled") || status.includes("failed")) {
        return "bg-danger text-white";
    } else if (status.includes("refund")) {
        return "bg-warning text-dark";
    }
    
    return "bg-secondary text-white";
}

async function loadOrders() {
    try {
        const orderTableBody = document.getElementById("orderTableBody");
        orderTableBody.innerHTML = "";

        const request = await fetch("/api/order/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(filterData)
        });

        if (request.ok) {
            const jsonObject = await request.json();
            const fragment = document.createDocumentFragment();

            jsonObject.data.forEach(order => {
                const tr = document.createElement("tr");
                const statusBadgeClass = getStatusBadgeClass(order.orderStatusName);
                tr.innerHTML = `<td class="ps-4 fw-bold text-danger">#ORD-${order.orderId}</td>
                                                        <td>
                                                            <div class="d-flex align-items-center">
                                                                <div class="bg-dark text-white rounded-circle d-flex align-items-center justify-content-center me-2" style="width: 35px; height: 35px; font-size: 0.8rem;">JS</div>
                                                                <div>
                                                                    <h6 class="mb-0 text-dark small fw-bold">${order.firstName + " " + order.lastName}</h6>
                                                                    <small class="text-muted" style="font-size: 0.75rem;">${order.email}</small>
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td class="text-secondary small fw-bold">${order.orderedDate}</td>
                                                        <td class="fw-bold">Rs.${(order.total).toLocaleString('en-us')}</td>
                                                        <td><span class="badge bg-light text-secondary border">Card</span></td>
                                                       <td><span class="badge ${statusBadgeClass}">${order.orderStatusName}</span></td>
                                                        <td class="text-end pe-4">
                                                            <div class="dropdown">
                                                                <button class="btn btn-sm btn-light border" type="button" data-bs-toggle="dropdown">
                                                                    <i class="bi bi-three-dots-vertical"></i>
                                                                </button>
                                                                <ul class="dropdown-menu dropdown-menu-end shadow border-0 rounded-3">
                                                                    <li><a class="dropdown-item small py-2" data-order_id="${order.orderId}" data-bs-toggle="modal" data-bs-target="#orderModal"><i class="bi bi-eye me-2"></i>View Details</a></li>
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

        const payload = {
            orderId: button.dataset.order_id
        };

        const request = await fetch("/api/order/details", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const jsonObject = await request.json();

            if (jsonObject.state) {
                const data = jsonObject.data;
                const orderData = data.order;

                document.getElementById("viewOrderId").innerHTML = orderData.orderId;
                document.getElementById("viewCustomerName").textContent = orderData.firstName + " " + orderData.lastName;
                document.getElementById("viewCustomerEmail").textContent = orderData.email;
                document.getElementById("viewCustomerPhone").textContent = orderData.mobile;
                document.getElementById("viewOrderDate").textContent = orderData.ordered_date;
                document.getElementById("viewCustomerAddressLine1").textContent = orderData.address_line1;
                document.getElementById("viewCustomerAddressLine2").textContent = orderData.address_line2;
                document.getElementById("viewCustomerCity").textContent = orderData.city;
                document.getElementById("viewCustomerProvince").textContent = orderData.province + "province";
                document.getElementById("viewCustomerDistrict").textContent = orderData.district;
                document.getElementById("viewDelivery").textContent = "Rs." + orderData.deliveryFee;

                let subtotal=0;

                const orderItemsTable = document.getElementById("orderItemsTable");
                orderItemsTable.innerHTML = "";

                const fragment = document.createDocumentFragment();

                data.orderItemsDetails.forEach(item => {
                    const tr = document.createElement("tr");
                    subtotal +=item.price * item.qty;
                    tr.innerHTML =
                        `
                                                        <td class="ps-4 py-3">
                                                            <div class="d-flex align-items-center">
                                                                <div class="bg-white border rounded-3 d-flex align-items-center justify-content-center me-3" style="width: 50px; height: 50px;">
                                                                    <img src=api/model/img/${item.modelId} class="img-fluid p-1" style="max-height: 100%; object-fit: contain;" alt="Product">
                                                                </div>

                                                                <div>
                                                                    <h6 class="fw-bold text-dark mb-0 small">${item.modelName}</h6>
                                                                    <small class="text-muted" style="font-size: 11px;">${item.productName}</small>
                                                                </div>
                                                            </div>
                                                        </td>

                                                        <td class="text-center align-middle">
                                                            <span class="fw-bold text-secondary">${item.qty}</span>
                                                        </td>

                                                        <td class="text-end align-middle">
                                                            <span class="small fw-bold text-dark">Rs.${item.price.toLocaleString('en-us')}</span>
                                                        </td>

                                                        <td class="text-end align-middle pe-4">
                                                            <span class="fw-bold text-dark">Rs.${(item.price * item.qty).toLocaleString('en-us')}</span>
                                                        </td>
                                                    `;
                    fragment.appendChild(tr);

                });

                orderItemsTable.appendChild(fragment);

                document.getElementById("viewSubTotal").textContent = "Rs. " + subtotal.toLocaleString('en-us');
                document.getElementById("viewGrandTotal").textContent = "Rs. " +parseInt(subtotal+orderData.deliveryFee).toLocaleString('us-en');
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch order details');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
})