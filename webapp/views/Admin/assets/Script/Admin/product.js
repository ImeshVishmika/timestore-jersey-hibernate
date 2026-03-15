var productsList = null;
var brandList = null;
var brandId = null;
var sortId = null;

document.getElementById("product-tab").addEventListener("click", () => {
    loadProducts(showProducts);
    loadBrands();
});

var filerByBrand = document.getElementById("filerByBrand");
filerByBrand.addEventListener("change", () => {
    filerByBrand.selectedIndex > 0 ? brandId = filerByBrand.value : brandId = null;
    showProducts();
});

var sort = document.getElementById("sortByPrice");
sort.addEventListener("change", () => {
    sort.selectedIndex > 0 ? sortId = sort.value : sortId = null;
    loadProducts(showProducts);
});

function loadProducts(next) {
    (async () => {
        try {
            const form = new FormData();
            if (brandId != null) {
                form.append("brand", brandId);
            }
            if (sortId != null) {
                form.append("sort", sortId);
            }

            const request = await fetch("/timestore/api/product/load", {
                method: "POST",
                body: form
            });

            if (request.ok) {
                productsList = await request.json();
                if (next) next();
            } else {
                Notiflix.Notify.failure('Failed to fetch products');
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    })();
};

function showProducts() {
    var modelList = document.getElementById("modelTable");
    modelList.innerHTML = "";
    const fragment = document.createDocumentFragment();

    productsList.data.models.forEach(i => {
        if (i.brand_id == brandId || brandId == null) {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                    <td class="ps-4">
                        <div class="bg-white border rounded-3 d-flex align-items-center justify-content-center" style="width: 50px; height: 50px;">
                            <img src="`+ i.img_path + `" class="product-thumb" style="max-width: 100%; max-height: 100%;" alt="Thumb">
                        </div>
                    </td>
                    <td>
                        <h6 class="fw-bold text-dark mb-0">`+ i.product_name + `</h6>
                        <small class="text-muted">`+ i.product_name + `</small>

                    </td>
                    <td><span class="badge bg-light text-dark border">`+ i.brand + `</span></td>
                    <td class="fw-bold">Rs.`+ i.price + ` </td>
                    <td>
                        <h6 class="fw-bold text-success mb-0">Rs.${i.revenue}</h6>
                        <small class="text-secondary fw-bold" style="font-size: 11px;">${i.order_qty} Sold</small>
                    </td>
                    <td>
                        <span class="fw-bold">
                            `+ i.qty + `
                        </span>
                    </td>
                    
                    <td>
                        ${i.qty > 0
                    ? `<span class="badge bg-success bg-opacity-10 text-success rounded-pill px-3">In Stock</span>`
                    : `<span class="badge bg-danger bg-opacity-10 text-danger rounded-pill px-3">Out of Stock</span>`}

                    </td>
                    <td class="text-end pe-4">
                        <div class="btn-group">
                            <button  class="btn btn-sm btn-outline-dark border-0" data-bs-toggle="modal" data-bs-target="#productDetailsModal"
                                data-product_id="`+ i.product_id + `" > 
                                <i class="bi bi-eye-fill"></i>
                            </button>
                           
                            <button class="btn btn-sm btn-outline-danger border-0"><i class="bi bi-trash-fill"></i></button>
                        </div>
                    </td>
                 </tr>`;
            fragment.appendChild(tr);
        };

    });
    modelList.appendChild(fragment);
}

function loadBrands() {
    (async () => {
        try {
            const request = await fetch("/timestore/api/brand/load", {
                method: "POST"
            });

            if (request.ok) {
                brandList = await request.json();

                const brands = document.getElementById("filerByBrand");
                const brandSelect = document.getElementById("brandSelect");

                const fragment = document.createDocumentFragment();

                brandList.brands.forEach(brand => {
                    const select = document.createElement("option");
                    select.textContent = brand.name;
                    select.value = brand.id;
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
    })();
}


//-------------------------------------------Add Product Modal functions---------------------------------------------------

var modelBrandId = null;

var brandInput = document.getElementById("brandInput");
var brandSelect = document.getElementById("brandSelect");
var modelInput = document.getElementById("modelInput");
var modelSelect = document.getElementById("modelSelect");

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

    brandList.brands.forEach(brand => {
        var opotion = document.createElement("option");
        opotion.textContent = brand.name;
        opotion.value = brand.id;
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
    };
});

brandSelect.addEventListener("change", () => {
    brandSelect.selectedIndex > 0 ? modelBrandId = brandSelect.value : modelBrandId = null;
    modelSelect.innerHTML = ``;
    productsList.data.models.forEach(model => {
        if (model.brand_id == modelBrandId || modelBrandId == null) {
            var select = document.createElement("option");
            select.textContent = model.product_name;
            select.value = model.product_id;
            select.dataset.brand = model.brand_id;
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
        modelInput.value="";
        var fragment = document.createDocumentFragment();

        if (modelBrandId == null) {
            var defaultSelect = document.createElement("option");
            defaultSelect.textContent = "Select Product";
            defaultSelect.value = 0;
            fragment.appendChild(defaultSelect);

        }

        modelSelect.innerHTML = ``;
        productsList.data.models.forEach(model => {
            if (model.brand_id == modelBrandId || modelBrandId == null) {
                var select = document.createElement("option");
                select.textContent = model.product_name;
                select.value = model.product_id;
                select.dataset.brand = model.brand_id;
                fragment.appendChild(select);
            }
        });
        modelSelect.appendChild(fragment);

        modelInput.classList.add("d-none");
        modelSelect.classList.remove("d-none");
        modelToggle.innerHTML = "<i class=\"bi bi-plus-circle me-1\"></i>Add new Product";
    };
};

modelSelect.addEventListener("change", () => {
    if (modelSelect.value != null) {
        brandSelect.value = modelSelect.options[modelSelect.selectedIndex].dataset.brand;
    }
});


document.getElementById("addProduct").addEventListener("click", () => {
    (async () => {
        try {
            const form = new FormData();

            if (!brandSelect.classList.contains("d-none")) {
                form.append("brand_id", brandSelect.value);
            } else {
                form.append("brand_name", brandInput.value);
            }

            if (!modelSelect.classList.contains("d-none")) {
                form.append("product_id", modelSelect.value);
            } else {
                form.append("product_name", modelInput.value);
            }

            form.append("img", document.getElementById("productImg").files[0]);
            form.append("price", document.getElementById("productPrice").value);
            form.append("qty", document.getElementById("productQty").value);
            form.append("model_name", document.getElementById("modelName").value);

            const request = await fetch("/timestore/api/product/add", {
                method: "POST",
                body: form
            });

            if (request.ok) {
                Notiflix.Notify.success('Product added successfully');
                loadProducts();
                loadModelBrands();
            } else {
                Notiflix.Notify.failure('Failed to add product');
            }
        } catch (error) {
            console.error('Error:', error);
            Notiflix.Notify.failure('Error ' + error);
        }
    })();
});


//-------------------------------------------Product Details Modal functions---------------------------------------------------


const productDetailsModal = document.getElementById('productDetailsModal');

var updateProductBtn = document.getElementById("updateProductBtn");

productDetailsModal.addEventListener('show.bs.modal', (event) => {
    loadModels(event.relatedTarget.dataset.product_id);
});

function loadModels(product_id) {
    (async () => {
        try {
            document.getElementById("modalProductTitle").innerHTML = "";

            const variantsTableBody = document.getElementById("variantsTableBody");
            variantsTableBody.innerHTML = "";

            let modalSold = 0;
            let modalQty = 0;

            updateProductBtn.dataset.product_id = product_id;
            renderProductChart(product_id);

            const form = new FormData();
            form.append("product_id", product_id);

            const request = await fetch("/timestore/api/model/load", {
                method: "POST",
                body: form
            });

            if (request.ok) {
                const jsonObject = await request.json();
                document.getElementById("modalImg").src = jsonObject.models[0].img_path;

                const fragment = document.createDocumentFragment();
                jsonObject.models.forEach(model => {
                    modalSold += parseInt(model.order_qty);
                    modalQty += parseInt(model.qty);
                    const modelItem =
                        `<td class="ps-4">
                            <img src=`+ model.img_path + `
                                class="rounded border bg-white p-1"
                                width="40" height="40"
                                style="object-fit: contain;">
                        </td>

                        <td>
                            <span class="fw-bold text-dark small">`+ model.model_name + `</span>
                        </td>

                        <td class="small fw-bold">`+ model.order_qty + `</td>

                        <td class="small fw-bold">Rs.`+ model.price + `</td>

                        <td class="small fw-bold">`+ model.qty + `</td>

                        <td>
                            <span class="badge bg-success bg-opacity-10 text-success" style="font-size: 10px;">In Stock</span>
                        </td>
                                      
                                   
                        <td>
                            <button class="btn btn-sm btn-outline-secondary border-0" data-bs-toggle="modal" 
                                                                data-product_id="`+ model.product_id + `"
                                                                data-model_id = "`+ model.model_id + `"
                            data-bs-target="#updateProductModal"><i class="bi bi-pencil-fill"></i></button>
                            <button class="btn btn-sm btn-outline-danger border-0"><i class="bi bi-trash-fill"></i></button>
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
    })();
};


const updateProductModal = document.getElementById('updateProductModal');

updateProductModal.addEventListener('show.bs.modal', async function (event) {
    try {
        const button = event.relatedTarget;

        const form = new FormData();
        form.append("product_id", button.dataset.product_id);
        form.append("model_id", button.dataset.model_id);

        const request = await fetch("/timestore/api/model/load", {
            method: "POST",
            body: form
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
            const product_id = document.getElementById("product_id");
            const update_id = document.getElementById("update_id");
            const update_price = document.getElementById("update_price");
            const update_qty = document.getElementById("update_qty");
            const update_img = document.getElementById("update_img");
            const update_desc = document.getElementById("update_desc");

            const form = new FormData();
            form.append("model_id", update_id.value);
            form.append("img", update_img.files[0]);
            form.append("model_name", update_model.value);
            form.append("price", update_price.value);
            form.append("qty", update_qty.value);
            form.append("desc", update_desc.value);

            const request = await fetch("/timestore/api/model/update", {
                method: "POST",
                body: form
            });

            if (request.ok) {
                Notiflix.Notify.success('Product updated successfully');
                loadModels(product_id.value);
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


function renderProductChart(id) {
    (async () => {
        try {
            let productChart = null;
            const ctx = document.getElementById('productSalesChart').getContext('2d');

            if (productChart) {
                productChart.destroy();
            }

            const form = new FormData();
            form.append("product_id", id);

            const request = await fetch("/timestore/api/product/revenue", {
                method: "POST",
                body: form
            });

            if (request.ok) {
                const jsonObject = await request.json();
                document.getElementById("modalTotalSales").textContent = "Rs." + jsonObject.total;

                const gradient = ctx.createLinearGradient(0, 0, 0, 300);
                gradient.addColorStop(0, 'rgba(220, 53, 69, 0.4)');
                gradient.addColorStop(1, 'rgba(220, 53, 69, 0)');

                productChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: jsonObject.dates,
                        datasets: [{
                            label: 'Revenue Rs',
                            data: jsonObject.revenues,
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
