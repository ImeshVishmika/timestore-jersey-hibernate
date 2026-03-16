document.getElementById("customers-tab").addEventListener("click", loadUsers);
document.getElementById("userStatusFilter").addEventListener("change", loadUsers);

// Add modal show handler to populate customer details
document.getElementById("userModal").addEventListener('show.bs.modal', async function (event) {
    const button = event.relatedTarget;
    const email = button.dataset.email;
    
    if (!email) return;
    
    try {
        const payload = {
            email: email
        };
        
        const request = await fetch("/api/user/details", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const jsonObject = await request.json();
            
            if (jsonObject && jsonObject.user) {
                const user = jsonObject.user;
                const address = jsonObject.address || {};
                const orders = jsonObject.orders || [];
                
                // Populate personal information
                document.getElementById("userName").textContent = (user.first_name || '') + ' ' + (user.last_name || '');
                document.getElementById("userEmail").textContent = user.email || '';
                document.getElementById("orderCount").textContent = user.order_count || '0';
                document.getElementById("totalSpend").textContent = 'Rs. ' + (user.total_spent ? 
                    new Intl.NumberFormat('en-LK').format(user.total_spent) : '0');
                document.getElementById("mobile").textContent = user.mobile || '-';
                
                // Populate address
                document.getElementById("line_one").textContent = address.line_one || '';
                document.getElementById("line_two").textContent = address.line_two ? ', ' + address.line_two : '';
                document.getElementById("city").textContent = address.city || '';
                document.getElementById("district").textContent = address.district || '';
                document.getElementById("province").textContent = address.province || '';
                document.getElementById("postalCode").textContent = address.postal_code || '';
                
                // Populate recent orders
                const recenOtrders = document.getElementById("recenOtrders");
                recenOtrders.innerHTML = '';
                
                if (orders && orders.length > 0) {
                    orders.slice(0, 5).forEach(order => {
                        const orderDiv = document.createElement("div");
                        orderDiv.className = "list-group-item px-0 d-flex justify-content-between align-items-center border-bottom";
                        orderDiv.innerHTML = `
                            <div>
                                <p class="mb-0 fw-bold small">#${order.order_id}</p>
                                <small class="text-muted" style="font-size: 11px;">${order.ordered_date}</small>
                            </div>
                            <span class="badge bg-success bg-opacity-10 text-success rounded-pill">${order.status}</span>
                            <span class="fw-bold small">Rs. ${new Intl.NumberFormat('en-LK').format(order.total)}</span>
                        `;
                        recenOtrders.appendChild(orderDiv);
                    });
                } else {
                    recenOtrders.innerHTML = '<p class="text-muted small">No orders yet</p>';
                }
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch user details');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
});

async function loadUsers() {
    try {
        const status = document.getElementById("userStatusFilter").value;

        const payload = {
            status: status
        };

        const request = await fetch("/api/user/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const jsonObject = await request.json();

            document.getElementById("totalUserCount").innerHTML = jsonObject.users.length;
            document.getElementById("activeUserCount").innerHTML = jsonObject.active;
            document.getElementById("blockedUserCount").innerHTML = jsonObject.blocked;

            const customerTableBody = document.getElementById("customerTableBody");
            customerTableBody.innerHTML = "";

            const fragment = document.createDocumentFragment();

            jsonObject.users.forEach(user => {
                const tr = document.createElement("tr");
                let statusBadgeClass = "bg-success bg-opacity-10 text-success";

                if (user.status === "Blocked") {
                    statusBadgeClass = "bg-success bg-opacity-10 text-danger";
                }

                tr.innerHTML = `
                    <td class="ps-4">
                        <div class="d-flex align-items-center">
                            <div class="avatar-circle me-3"></div>
                            <div>
                                <h6 class="fw-bold text-dark mb-0">${user.first_name + " " + user.last_name}</h6>
                                <small class="text-muted">ID: #USR-<?php echo rand(100, 999); ?></small>
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
                            <small class="text-dark fw-bold">${user.order_count} Orders</small>
                            <small class="text-success fw-bold">LKR ${new Intl.NumberFormat('en-LK').format(user.total_spent || 0)}</small>
                        </div>
                    </td>
                    <td class="text-secondary small fw-bold">${user.joined_date}</td>
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