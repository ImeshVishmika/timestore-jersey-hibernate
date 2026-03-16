document.getElementById("settings-tab").addEventListener("click", loadDeliveryDetails);

// Handle updateDeliveryForm submission
const updateDeliveryForm = document.getElementById("updateDeliveryForm");
if (updateDeliveryForm) {
    updateDeliveryForm.addEventListener("submit", async (event) => {
        event.preventDefault();
        
        try {
            const deliveryId = document.getElementById("delivery_selector").value;
            const newPrice = document.getElementById("price_input").value;
            const newDays = document.getElementById("days_input").value;
            
            if (!deliveryId || !newPrice || !newDays) {
                alert("Please fill in all fields");
                return;
            }
            
            const payload = {
                delivery_method_id: deliveryId,
                price: newPrice,
                new_days: newDays
            };
            
            const request = await fetch("/api/delivery/update", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.ok) {
                const response = await request.json();
                Notiflix.Notify.success(response.msg || "Delivery updated successfully");
                loadDeliveryDetails();
                // Close modal
                const modal = bootstrap.Modal.getInstance(document.getElementById("deliveryModal"));
                if (modal) modal.hide();
            } else {
                Notiflix.Notify.failure('Failed to update delivery');
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    });
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

            const deliveryDetailsTableBody = document.getElementById("deliveryDetailsTableBody");
            deliveryDetailsTableBody.innerHTML = "";

            const fragment = document.createDocumentFragment();

            jsonObject.forEach(delivery => {
                const div = document.createElement("div");
                div.classList = "row g-3 align-items-end mb-3";
                div.dataset.id = delivery.id;
                div.innerHTML = `
                                           <div class=" gap-3">
    <label class="form-label fw-bold text-secondary small">${delivery.method}</label>
    <div class="row ">
        <div class="col-md-4">
            <div class="input-group">
                <span class="input-group-text bg-light border-end-0 text-secondary">Rs.</span>
                <input type="number" value="${delivery.price}" class="form-control border-start-0 fw-bold" name="new_price" data-field="price" required>
            </div>
        </div>

        <div class="col-md-4">
            <div class="input-group">
                <input type="number" value="${delivery.delivery_days}" class="form-control border-end-0 fw-bold" name="new_days" data-field="days" required>
                <span class="input-group-text bg-light border-start-0 text-secondary">Est. Days</span>
            </div>
        </div>
        <div class="col-md-3 d-flex gap-1 justify-content-end  ">
            <button class="bi bi-bookmark-check-fill btn btn-sm btn-outline-secondary border-0 "></button>
            <button class="bi bi-trash-fill btn btn-sm btn-outline-danger border-0 "></button>
            <button class="btn btn-sm btn-outline-success bg-opacity-10 text-success my-1">Active</button>
        </div>
    </div>
</div>
                                            `;
                fragment.appendChild(div);
            });
            deliveryDetailsTableBody.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch delivery details');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

document.getElementById("deliveryDetailsTableBody").addEventListener("click", async (event) => {
    try {
        const div = event.target.closest(".row.g-3.align-items-end.mb-3");
        if (event.target.classList.contains("btn-outline-secondary")) {

            const priceInput = div.querySelector('input[data-field="price"]');
            const daysInput = div.querySelector('input[data-field="days"]');
            const price = priceInput ? priceInput.value : "";
            const days = daysInput ? daysInput.value : "";
            const deliveryId = div.dataset.id;

            const payload = {
                delivery_method_id: deliveryId,
                price: price,
                new_days: days
            };

            const request = await fetch("/api/delivery/update", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.ok) {
                Notiflix.Notify.success('Delivery updated successfully');
            } else {
                Notiflix.Notify.failure('Failed to update delivery');
            }
        } else if (event.target.classList.contains("btn-outline-danger")) {

            const payload = {
                delivery_method_id: div.dataset.id
            };

            const request = await fetch("/api/delivery/delete", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.ok) {
                Notiflix.Notify.success('Delivery deleted successfully');
                loadDeliveryDetails();
            } else {
                Notiflix.Notify.failure('Failed to delete delivery');
            }
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
})