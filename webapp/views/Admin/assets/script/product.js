let productsList = null;
let brandList = null;
let brandId = null;
let sortId = null;

document.getElementById("product-tab").addEventListener("click", async () => {
    await loadProducts(showProducts);
    await loadBrands();
});

const filerByBrand = document.getElementById("filerByBrand");
filerByBrand.addEventListener("change", async () => {
    filerByBrand.selectedIndex > 0 ? brandId = filerByBrand.value : brandId = null;
    await loadProducts();
});

const sort = document.getElementById("sortByPrice");
sort.addEventListener("change", async () => {
    sort.selectedIndex > 0 ? sortId = sort.value : sortId = null;
    await loadProducts();
});

async function loadProducts() {
    try {
        const payload = {};
        if (brandId != null) {
            payload.brandId = [Number(brandId)];
        }
        if (sortId != null) {
            payload.sort = sortId;
        }

        const request = await fetch("/api/admin/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            let jsonObject = await request.json();
            if (jsonObject.state) {
                productsList = jsonObject.data;
                showProducts();
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch products');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

let modelList = document.getElementById("modelTable");

function showProducts() {

    modelList.innerHTML = "";
    const fragment = document.createDocumentFragment();

    productsList.forEach(i => {

        const tr = document.createElement("tr");
        tr.innerHTML = `
                    <td class="ps-4">
                        <div class="bg-white border rounded-3 d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                            <img src=api/model/img/${i.productId} class="product-thumb" style="max-width: 100%; max-height: 100%;" alt="Thumb">
                        </div>
                    </td>
                    <td>
                        <h6 class="fw-bold text-dark mb-0">` + i.productName + `</h6>
                        <small class="text-muted">` + i.brandName + `</small>

                    </td>
                   
                    <td>
                        <h6 class="fw-bold text-success mb-0">Rs.${(i.revenue).toLocaleString('en-us')}</h6>
                        <small class="text-secondary fw-bold" style="font-size: 11px;">${i.soldCount} Sold</small>
                    </td>
                    <td>
                        <span class="fw-bold">
                            ` + i.stock + `
                        </span>
                    </td>
                    
                    <td>
                        ${i.stock > 0
            ? `<span class="badge bg-success bg-opacity-10 text-success rounded-pill px-3">In Stock</span>`
            : `<span class="badge bg-danger bg-opacity-10 text-danger rounded-pill px-3">Out of Stock</span>`}

                    </td>
                    <td class="text-end pe-4">
                        <div class="btn-group">
                            <button  class="btn btn-sm btn-outline-dark border-0" data-bs-toggle="modal" data-bs-target="#productDetailsModal"
                                data-product_id="` + i.productId + `" > 
                                <i class="bi bi-eye-fill"></i>
                            </button>
                           
                            <button  data-id=${i.productId} class="btn btn-sm btn-outline-danger border-0"><i class="bi bi-trash-fill"></i></button>
                        </div>
                    </td>
                 </tr>`;
        fragment.appendChild(tr);
    });
    modelList.appendChild(fragment);
}

async function loadBrands() {
    try {
        const request = await fetch("/api/brand/");

        if (request.ok) {
            let jsonObject = await request.json();
            if (jsonObject.state) {
                brandList = jsonObject.data;
            }

            const brands = document.getElementById("filerByBrand");
            brands.innerHTML="";
            const brandSelect = document.getElementById("brandSelect");

            const fragment = document.createDocumentFragment();

            brandList.forEach(brand => {
                const select = document.createElement("option");
                select.textContent = brand.brandName;
                select.value = brand.brandId;
                fragment.appendChild(select);
            });
            brands.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch brands');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }

}

modelList.addEventListener("click", async (event) => {
    let deleteBtn = event.target.closest(".btn-outline-danger");
    if (!deleteBtn) {
        return;
    }

    let productId = deleteBtn.dataset.id;

    try {

        const request = await fetch(`/api/product/${productId}`, {
            method: "DELETE"
        });

        if (request.ok) {
            const response = await request.json();
            if (response.state) {
                Notiflix.Notify.success(response.message);
                await loadProducts();
            } else {
                Notiflix.Notify.failure(response.message);
            }
        } else {
            Notiflix.Notify.failure('Failed to delete product');
        }

    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error: ' + error);
    }
});

//-------------------------------------------Add Product Modal functions---------------------------------------------------

let modelBrandId = null;

const brandInput = document.getElementById("brandInput");
const brandSelect = document.getElementById("brandSelect");
const modelInput = document.getElementById("modelInput");
const modelSelect = document.getElementById("modelSelect");

// Declare modal and toggle elements
const addProductModal = document.getElementById("addProductModal");
const brandToggle = document.getElementById("brandToggle");
const modelToggle = document.getElementById("modelToggle");

addProductModal.addEventListener('show.bs.modal', () => {
    loadModelBrands(true);
});

function loadModelBrands(firstTime = false) {

    if (firstTime) {
        var defaultSelect = document.createElement("option");
        defaultSelect.textContent = "Select Brand";

        var fragment = document.createDocumentFragment();
        fragment.appendChild(defaultSelect);
    }

    brandList.forEach(brand => {
        var opotion = document.createElement("option");
        opotion.textContent = brand.brandName;
        opotion.value = brand.brandId;
        fragment.append(opotion);
    });

    brandSelect.innerHTML = ``;
    brandSelect.appendChild(fragment);
};

brandToggle.addEventListener("click", () => {

    if (brandInput.classList.contains("d-none")) {
        brandInput.classList.remove("d-none");
        brandSelect.classList.add("d-none");
        brandToggle.innerHTML = "Select Brand";

    } else if (brandSelect.classList.contains("d-none")) {
        brandInput.classList.add("d-none");
        brandSelect.classList.remove("d-none");
        brandToggle.innerHTML = "<i class=\"bi bi-plus-circle me-1\"></i>Add new brand";
    }
    ;
});

brandSelect.addEventListener("change", () => {
    brandSelect.selectedIndex > 0 ? modelBrandId = brandSelect.value : modelBrandId = null;
    modelSelect.innerHTML = ``;
    productsList.forEach(model => {
        if (model.brand_id === modelBrandId || modelBrandId == null) {
            var select = document.createElement("option");
            select.textContent = model.productName;
            select.value = model.productId;
            select.dataset.brand = model.brandId;
            modelSelect.appendChild(select);
        }
    });
});


modelToggle.addEventListener("click", changeModelInputMethod);

function changeModelInputMethod() {

    if (modelInput.classList.contains("d-none")) {
        modelInput.classList.remove("d-none");
        modelSelect.classList.add("d-none");
        modelToggle.innerHTML = "Select Product";

    } else if (modelSelect.classList.contains("d-none")) {
        modelInput.value = "";
        var fragment = document.createDocumentFragment();

        if (modelBrandId == null) {
            var defaultSelect = document.createElement("option");
            defaultSelect.textContent = "Select Product";
            defaultSelect.value = 0;
            fragment.appendChild(defaultSelect);

        }

        modelSelect.innerHTML = ``;
        productsList.forEach(model => {
            // if (model.brandId === modelBrandId || modelBrandId == null) {
            var select = document.createElement("option");
            select.textContent = model.productName;
            select.value = model.productId;
            select.dataset.brand = model.brandId;
            fragment.appendChild(select);
            // }
        });
        modelSelect.appendChild(fragment);

        modelInput.classList.add("d-none");
        modelSelect.classList.remove("d-none");
        modelToggle.innerHTML = "<i class=\"bi bi-plus-circle me-1\"></i>Add new Product";
    }
    ;
};

modelSelect.addEventListener("change", () => {
    if (modelSelect.value != null) {
        brandSelect.value = modelSelect.options[modelSelect.selectedIndex].dataset.brand;
    }
});


document.getElementById("addProduct").addEventListener("click", async () => {
    try {
        const payload = {};

        if (!brandSelect.classList.contains("d-none")) {
            payload.brandId = brandSelect.value;
        } else {
            payload.brandName = brandInput.value;
        }

        if (!modelSelect.classList.contains("d-none")) {
            payload.productId = modelSelect.value;
        } else {
            payload.productName = modelInput.value;
        }

        const model = {};
        model.model = document.getElementById("modelName").value;
        model.price = document.getElementById("productPrice").value;
        model.qty = document.getElementById("productQty").value;

        payload.models = [model];

        console.log(payload);

        const request = await fetch("/api/product/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {

            let response = await request.json();

            if (response.state) {
                Notiflix.Notify.success('Product added successfully');
                await loadProducts();
            }

        } else {
            Notiflix.Notify.failure('Failed to add product');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
});


//-------------------------------------------Product Details Modal functions---------------------------------------------------


const productDetailsModal = document.getElementById('productDetailsModal');

let updateProductBtn = document.getElementById("updateProductBtn");

productDetailsModal.addEventListener('show.bs.modal', (event) => {
    loadModels(event.relatedTarget.dataset.product_id);
});

const variantsTableBody = document.getElementById("variantsTableBody");
variantsTableBody.addEventListener("click", async (event) => {
    let modelDeleteBtn = event.target.closest(".btn-outline-danger");
    if (!modelDeleteBtn) {
        return;
    }

    let modelId = modelDeleteBtn.dataset.model_id;
    alert(modelId);

    try {
        const request = await fetch(`/api/model/${modelId}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (request.ok) {
            const response = await request.json();
            if (response.state) {
                Notiflix.Notify.success(response.message);
                loadModels(updateProductBtn.dataset.product_id);
            } else {
                Notiflix.Notify.failure(response.message);
            }
        } else {
            Notiflix.Notify.failure('Failed to delete model');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error: ' + error);
    }
});

async function loadModels(productId) {

    try {
        document.getElementById("modalProductTitle").innerHTML = "";

        variantsTableBody.innerHTML = "";

        let modalSold = 0;
        let modalQty = 0;

        updateProductBtn.dataset.product_id = productId;
        renderProductChart(productId);

        const payload = {
            productId: [productId]
        };

        const request = await fetch("/api/admin/model/", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const response = await request.json();
            const jsonObject = response.data;
            document.getElementById("modalImg").src = `api/model/img/${productId}`;

            const fragment = document.createDocumentFragment();
            jsonObject.forEach(model => {
                const modelItem =
                    `<td class="ps-4">
                            <img src= api/model/img/${model.modelId}
                                class="rounded border bg-white p-1"
                                width="40" height="40"
                                style="object-fit: contain;">
                        </td>

                        <td>
                            <span class="fw-bold text-dark small">` + model.model + `</span>
                        </td>
                        
                        <td>
                            <h6 class="fw-bold text-success mb-0">Rs.${Number(model.revenue).toLocaleString('en-us')}</h6>
                            <small class="text-secondary fw-bold" style="font-size: 11px;">${model.soldCount} Sold</small>
                        </td>

                        <td class="small fw-bold">Rs.` + model.price + `</td>

                        <td class="small fw-bold">` + model.qty + `</td>

                        <td>
                            <span class="badge bg-success bg-opacity-10 text-success" style="font-size: 10px;">In Stock</span>
                        </td>
                                      
                                   
                        <td>
                            <button class="btn btn-sm btn-outline-secondary border-0" data-bs-toggle="modal" 
                                                                data-product_id="` + model.productId + `"
                                                                data-model_id = "` + model.modelId + `"
                            data-bs-target="#updateProductModal"><i class="bi bi-pencil-fill"></i></button>
                            <button  data-model_id =${model.modelId} class="btn btn-sm btn-outline-danger border-0"><i class="bi bi-trash-fill"></i></button>
                        </td>
                        `;

                const tr = document.createElement("tr");
                tr.innerHTML = modelItem;
                fragment.appendChild(tr);
            });
            variantsTableBody.appendChild(fragment);
            document.getElementById("modalSold").textContent = modalSold;
            document.getElementById("modalStock").textContent = modalQty;
        } else {
            Notiflix.Notify.failure('Failed to fetch models');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }

};

// async function functionName() {
//     try {
//         const payload = {};
//
//         const request = await fetch("/api/....");
//
//         if (request.ok) {
//             const response = await request.json();
//             const jsonObject = response.data;
//         } else {
//             Notiflix.Notify.failure('Failed to fetch models');
//         }
//     } catch (error) {
//         console.error('Error:', error);
//         Notiflix.Notify.failure('Error ' + error);
//     }
// };


const updateProductModal = document.getElementById('updateProductModal');

updateProductModal.addEventListener('show.bs.modal', async function (event) {
    try {
        const button = event.relatedTarget;

        const payload = {
            product_id: button.dataset.product_id,
            model_id: button.dataset.model_id
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

            document.getElementById("product_id").value = jsonObject.models[0].product_id;
            document.getElementById("update_id").value = button.dataset.model_id;
            document.getElementById("update_model").value = jsonObject.models[0].model_name;
            document.getElementById("update_qty").value = jsonObject.models[0].qty;
            document.getElementById("update_price").value = jsonObject.models[0].price;
        } else {
            Notiflix.Notify.failure('Failed to fetch model details');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
});

updateProductBtn.addEventListener("click", (event) => {
    (async () => {
        try {
            const productId = document.getElementById("product_id");
            const updateId = document.getElementById("update_id");
            const updateModel = document.getElementById("update_model");
            const updatePrice = document.getElementById("update_price");
            const updateQty = document.getElementById("update_qty");
            const updateDesc = document.getElementById("update_desc");

            const payload = {
                model_id: updateId.value,
                model_name: updateModel.value,
                price: updatePrice.value,
                qty: updateQty.value,
                desc: updateDesc.value
            };

            const request = await fetch("/api/model/update", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.ok) {
                Notiflix.Notify.success('Product updated successfully');
                loadModels(productId.value);
                loadProducts();
                new bootstrap.Modal(productDetailsModal).show();
            } else {
                Notiflix.Notify.failure('Failed to update product');
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    })();
});

let productChart = null;

function renderProductChart(id) {
    (async () => {
        try {

            if (productChart) {
                productChart.destroy();
            }

            const ctx = document.getElementById('productSalesChart').getContext('2d');

            const payload = {
                productId: [id]
            };

            const request = await fetch("/api/product/revenue", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(payload)
            });

            if (request.ok) {

                const response = await request.json();

                if (!response.state) {
                    return;
                }

                const data = response.data;

                document.getElementById("modalTotalSales").textContent = "Rs." + data.total;

                const gradient = ctx.createLinearGradient(0, 0, 0, 300);
                gradient.addColorStop(0, 'rgba(220, 53, 69, 0.4)');
                gradient.addColorStop(1, 'rgba(220, 53, 69, 0)');

                productChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: data.dateList,
                        datasets: [{
                            label: 'Revenue Rs',
                            data: data.revenueList,
                            borderColor: '#dc3545',
                            backgroundColor: gradient,
                            borderWidth: 2,
                            tension: 0.4,
                            fill: true,
                            pointBackgroundColor: '#fff',
                            pointBorderColor: '#dc3545',
                            pointRadius: 4
                        }]
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        plugins: {
                            legend: {
                                display: false
                            }
                        },
                        scales: {
                            y: {
                                beginAtZero: true,
                                grid: {
                                    borderDash: [5, 5]
                                }
                            },
                            x: {
                                grid: {
                                    display: false
                                }
                            }
                        }
                    }
                });


            } else {
                Notiflix.Notify.failure('Failed to fetch product revenue');
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    })();
};

// Handle Add Model form submission
document.getElementById("submitAddModel").addEventListener("click", async () => {
    try {
        const modelName = document.getElementById("addModelName").value;
        const modelPrice = document.getElementById("addModelPrice").value;
        const modelQty = document.getElementById("addModelQty").value;
        const productId = updateProductBtn.dataset.product_id;

        // Validate inputs
        if (!modelName || modelName.trim() === "") {
            Notiflix.Notify.failure('Model name is required');
            return;
        }

        if (!modelPrice || modelPrice <= 0) {
            Notiflix.Notify.failure('Model price must be greater than 0');
            return;
        }

        if (!modelQty || modelQty <= 0) {
            Notiflix.Notify.failure('Model quantity must be greater than 0');
            return;
        }

        const payload = {
            model: modelName,
            price: parseFloat(modelPrice),
            qty: parseInt(modelQty),
            productId: parseInt(productId)
        };

        const request = await fetch(`/api/model/add`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const response = await request.json();
            if (response.state) {
                Notiflix.Notify.success(response.message);

                // Clear form inputs
                document.getElementById("addModelName").value = "";
                document.getElementById("addModelPrice").value = "";
                document.getElementById("addModelQty").value = "";

                // Close modal
                const addModelModal = bootstrap.Modal.getInstance(document.getElementById("addModelModal"));
                if (addModelModal) {
                    addModelModal.hide();
                }

                // Reload models list
                loadModels(productId);
            } else {
                Notiflix.Notify.failure(response.message);
            }
        } else {
            Notiflix.Notify.failure('Failed to add model');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error: ' + error);
    }
});
