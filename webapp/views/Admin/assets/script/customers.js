document.getElementById("customers-tab").addEventListener("click", function() {
    loadUsers();
    loadUserCountSummary();
});

const totalUsersCard = document.getElementById("totalUsersCard");
const activeUsersCard = document.getElementById("activeUsersCard");
const blockedUsersCard = document.getElementById("blockedUsersCard");

const searchQuery = document.getElementById("customerSearchInput");
const minOrderCount = document.getElementById("minOrderCountFilter");
const maxOrderCount = document.getElementById("maxOrderCountFilter");
const minSpent = document.getElementById("minSpentFilter");
const maxSpent = document.getElementById("maxSpentFilter");
const joinedDateFrom = document.getElementById("joinedDateFromFilter");
const joinedDateTo = document.getElementById("joinedDateToFilter");
const clearCustomerFiltersBtn = document.getElementById("clearCustomerFiltersBtn");
const userStatusId = document.getElementById("userStatusFilter");

const filters = {
    searchQuery,
    userStatusId,
    minOrderCount,
    maxOrderCount,
    minSpent,
    maxSpent,
    joinedDateFrom,
    joinedDateTo
};

function clearAllFilters() {
    Object.entries(filters).forEach(([variableName, element]) => {
        element.value = "";
        userFilterData[variableName] = "";
    });
    searchQuery.value = "";
    userFilterData["searchQuery"] = "";
}

// searchQuery.addEventListener("keyup", () => {
//     userFilterData["searchQuery"] = searchQuery.value;
//     loadUsers();
// });

let userFilterData = {};

Object.entries(filters).forEach(([variableName, element]) => {
    element.addEventListener("keyup", () => {
        userFilterData[variableName] = numOrNull(element.value);
        loadUsers();
    });
});

const userStatusCards={
    totalUsersCard,
    activeUsersCard,
    blockedUsersCard
}

Object.entries(userStatusCards).forEach(([cardName,element])=>{
    element.addEventListener("click",()=>{
        userStatusId.value=element.dataset.statusid;
        userStatusId.dispatchEvent(new Event('change'));
    });
})

clearCustomerFiltersBtn.addEventListener("click", () => {
    clearAllFilters();
    loadUsers();
});

// Add modal show handler to populate customer details
// document.getElementById("userModal").addEventListener('show.bs.modal', async function (event) {
//     const button = event.relatedTarget;
//     const email = button.dataset.email;
//
//     if (!email) return;
//
//     try {
//         const payload = {
//             email: email
//         };
//
//         const request = await fetch("/api/user/details", {
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json"
//             },
//             body: JSON.stringify(payload)
//         });
//
//         if (request.ok) {
//             const jsonObject = await request.json();
//
//             if (jsonObject && jsonObject.user) {
//                 const user = jsonObject.user;
//                 const address = jsonObject.address || {};
//                 const orders = jsonObject.orders || [];
//
//                 // Populate personal information
//                 document.getElementById("userName").textContent = (user.first_name || '') + ' ' + (user.last_name || '');
//                 document.getElementById("userEmail").textContent = user.email || '';
//                 document.getElementById("orderCount").textContent = user.order_count || '0';
//                 document.getElementById("totalSpend").textContent = 'Rs. ' + (user.total_spent ?
//                     new Intl.NumberFormat('en-LK').format(user.total_spent) : '0');
//                 document.getElementById("mobile").textContent = user.mobile || '-';
//
//                 // Populate address
//                 document.getElementById("line_one").textContent = address.line_one || '';
//                 document.getElementById("line_two").textContent = address.line_two ? ', ' + address.line_two : '';
//                 document.getElementById("city").textContent = address.city || '';
//                 document.getElementById("district").textContent = address.district || '';
//                 document.getElementById("province").textContent = address.province || '';
//                 document.getElementById("postalCode").textContent = address.postal_code || '';
//
//                 // Populate recent orders
//                 const recenOtrders = document.getElementById("recenOtrders");
//                 recenOtrders.innerHTML = '';
//
//                 if (orders && orders.length > 0) {
//                     orders.slice(0, 5).forEach(order => {
//                         const orderDiv = document.createElement("div");
//                         orderDiv.className = "list-group-item px-0 d-flex justify-content-between align-items-center border-bottom";
//                         orderDiv.innerHTML = `
//                             <div>
//                                 <p class="mb-0 fw-bold small">#${order.order_id}</p>
//                                 <small class="text-muted" style="font-size: 11px;">${order.ordered_date}</small>
//                             </div>
//                             <span class="badge bg-success bg-opacity-10 text-success rounded-pill">${order.status}</span>
//                             <span class="fw-bold small">Rs. ${new Intl.NumberFormat('en-LK').format(order.total)}</span>
//                         `;
//                         recenOtrders.appendChild(orderDiv);
//                     });
//                 } else {
//                     recenOtrders.innerHTML = '<p class="text-muted small">No orders yet</p>';
//                 }
//             }
//         } else {
//             Notiflix.Notify.failure('Failed to fetch user details');
//         }
//     } catch (error) {
//         console.error('Error:', error);
//         Notiflix.Notify.failure('Error ' + error);
//     }
// });

async function loadUserCountSummary() {
    try {
        const request = await fetch("/api/user/countSummary", {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (request.ok) {
            const jsonObject = await request.json();

            if (jsonObject.state && jsonObject.data) {
                document.getElementById("totalUserCount").textContent = jsonObject.data.totalUsers || 0;

                if (jsonObject.data.statusBreakdown && Array.isArray(jsonObject.data.statusBreakdown)) {
                    jsonObject.data.statusBreakdown.forEach(status => {
                        if (status.statusId === 1) {
                            document.getElementById("activeUserCount").textContent = status.count || 0;
                        } else if (status.statusId === 2) {
                            document.getElementById("blockedUserCount").textContent = status.count || 0;
                        }
                    });
                }
            } else {
                Notiflix.Notify.failure('Failed to fetch user count summary');
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch user count summary');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error fetching user count: ' + error);
    }
}

async function loadUsers() {
    try {

        const request = await fetch("/api/user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(userFilterData)
        });

        if (request.ok) {
            let jsonObject = await request.json();
            console.log(jsonObject);

            const customerTableBody = document.getElementById("customerTableBody");
            customerTableBody.innerHTML = "";

            const fragment = document.createDocumentFragment();

            if (!jsonObject.state) {
                Notiflix.Notify.failure('Failed to fetch users');
                return;
            }

            jsonObject.data.forEach(user => {
                const tr = document.createElement("tr");
                let statusBadgeClass = "bg-success bg-opacity-10 text-success";

                tr.innerHTML = `
                    <td class="ps-4">
                        <div class="d-flex align-items-center">
                            <div class="avatar-circle me-3"></div>
                            <div>
                                <h6 class="fw-bold text-dark mb-0">${user.firstName + " " + user.lastName}</h6>
<!--                                <small class="text-muted">ID: #USR-<?php echo rand(100, 999); ?></small>-->
                            </div>
                        </div>
                    </td>
                    <td>
                        <div class="d-flex flex-column">
                            <small class="text-dark fw-bold mb-1"><i class="bi bi-envelope me-1 text-secondary"></i> ${user.email} </small>
                            <small class="text-secondary"><i class="bi bi-phone me-1"></i> ${user.mobile} </small>
                        </div>
                    </td>
                    
                    <td> 
                        <div class="d-flex flex-column">
                            <small class="text-dark fw-bold">${user.orderCount} Orders</small>
                            <small class="text-success fw-bold">LKR ${new Intl.NumberFormat('en-LK').format(user.totalSpent || 0)}</small>
                        </div>
                    </td>
                    <td class="text-secondary small fw-bold">${user.joinedDate}</td>
                    <td class="text-center">
                        <span class="badge ${statusBadgeClass} rounded-pill px-3">${user.status}</span>
                    </td>
                    <td class="text-end pe-4">
                        <button class="btn btn-sm btn-light border me-1" data-bs-toggle="modal" data-email=${user.email} data-bs-target="#userModal" title="View Details">
                            <i class="bi bi-eye-fill text-dark"></i>
                        </button>
                        
                            <button class="btn btn-sm btn-light border text-danger" title="Block User">
                                <i class="bi bi-slash-circle"></i>
                            </button>
                       
                            <button class="btn btn-sm btn-light border text-success" title="Unblock User">
                                <i class="bi bi-check-circle"></i>
                            </button>
                        
                    </td>
                `;
                fragment.appendChild(tr);
            });

            customerTableBody.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch users');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

function numOrNull(value){

    return Number.isFinite(value.length>0 && Number(value.trim()))
        ? value
        : null;
}