window.addEventListener("load", () => {
    alert("Welcome to the search page! Explore our products and find what you need.");
    loadProducts();
    loadBrands();
});

let filter = document.getElementById("filter");
filter.addEventListener("change", loadProducts);

var brands = document.getElementById("brands");
brands.addEventListener("change", loadProducts);

function getModelImageUrl(model) {
    const modelId = model.model_id ?? model.modelId;
    return modelId ? `/api/model/img/${modelId}` : (model.img_path || "");
}

async function loadProducts() {
    try {
        const payload = {};
        if (filter.selectedIndex != 0) {
            payload.sort = filter.value;
        }

        const selectedBrands = [];
        brands.querySelectorAll("input").forEach(input => {
            
            if (input.checked) {
                selectedBrands.push(input.value);
            }
        });
        if (selectedBrands.length > 0) {
            payload.brand = selectedBrands.join(",");
        }

        const request = await fetch("/api/product/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const jsonObject = await request.json();
            console.log(jsonObject);

            const productsTable = document.getElementById("modelsTable");
            productsTable.innerHTML = "";
            const fragment = document.createDocumentFragment();

            jsonObject.data.forEach(model => {
                const div = document.createElement("div");

                div.classList.add("col-12", "col-sm-6", "col-lg-3");
                div.innerHTML = `
                <a href="/viewProduct/${model.productId}"  class="text-decoration-none">
                    <div class="card border-0 h-100">
                        <div class="bg-light rounded-3 p-4 text-center mb-3">
                            <img src=/api/model/img/${model.productId} class="img-fluid" style="height: 180px; object-fit: contain;" alt="Watch">
                        </div>
                        <div class="card-body px-0 pt-0">
                            <small class="text-muted fw-semibold">${model.brand}</small>
                            <h6 class="card-title  fw-bold mb-1">${model.product_name}</h6>
                            <p class="fw-bold text-dark">Rs.${model.price}</p>
                        </div>
                    </div>
                </a>
                `;
                fragment.appendChild(div);
            });
            productsTable.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch products');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

async function loadBrands() {
    try {
        const request = await fetch("/api/brand/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({})
        });

        if (request.ok) {
            const jsonObject = await request.json();

            const brands = document.getElementById("brands");
            brands.innerHTML = "";
            const fragment = document.createDocumentFragment();

            jsonObject.data.forEach(brand => {
                const div = document.createElement("div");
                div.classList.add("form-check", "mb-2");
                div.innerHTML = `
                                <input class="form-check-input" type="checkbox" value="${brand.brandId}" >
                                <label class="form-check-label" for="brand${brand.brandId}">${brand.brandName}</label>
                `;
                fragment.appendChild(div);
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