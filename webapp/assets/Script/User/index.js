document.addEventListener("DOMContentLoaded", async () =>{
    loadPopularItems();

});


async function loadPopularItems() {

    try {
        // const data = {
        //     "sort": 3,
        //     "page_size": 4,
        //     "page": 1
        // }
        
        const request = await fetch("/api/products",{
            method:"GET"
        });

        if (request.ok) {
            const jsonObject = await request.json();

            const newItemesBody = document.getElementById("popularItemesBody");
            newItemesBody.innerHTML = "";
            const fragment = document.createDocumentFragment();

            const models = jsonObject.data;

            models.forEach(model => {
                const div = document.createElement("div");

                div.classList.add("col-12", "col-sm-6", "col-lg-3");
                div.innerHTML = `
                <a href="/timestore/viewProduct.html?id=${model.productId}" class="text-decoration-none text-dark">
                <div class="card h-100 border-0 shadow-sm transition-hover">
                <div class="m-2 bg-light rounded-3 p-4 text-center d-flex align-items-center justify-content-center" style="height: 240px;">
                 <img src="/api/products/img/${model.productId}" 
                 class="img-fluid" 
                 style="max-height: 100%; object-fit: contain;" 
                 alt="${model.productName}">
        </div>
        
        <div class="card-body px-3 pb-4">
            <p class="text-uppercase text-muted fw-bold mb-1" style="font-size: 0.85rem; letter-spacing: 1.2px;">
                ${model.brandName}
            </p>
            
            <h5 class="card-title fw-bold text-dark mb-2">
                ${model.productName}
            </h5>
            
            <div class="mt-3">
                <span class="fs-4 fw-black fw-bold">
                    Rs.${model.price}
                </span>
            </div>
        </div>
    </div>
</a>            `;
                fragment.appendChild(div);
            });
            newItemesBody.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch popular items');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

async function loadNewItems() {
    try {
        const form = new FormData();
        form.append("sort", 4);
        form.append("page_size", 4);
        form.append("page", 1);
        
        const request = await fetch("/api/product/load", {
            method: "POST",
            body: form
        });

        if (request.ok) {
            const jsonObject = await request.json();

            const newItemesBody = document.getElementById("newItemesBody");
            newItemesBody.innerHTML = "";
            const fragment = document.createDocumentFragment();

            const models = jsonObject.data.models;

            models.forEach(model => {
                const div = document.createElement("div");

                div.classList.add("col-12", "col-sm-6", "col-lg-3");
                div.innerHTML = `
                <a href="/timestore/viewProduct/${model.product_id}" class="text-decoration-none text-dark">
    <div class="card h-100 border-0 shadow-sm transition-hover">
        <div class="m-2 bg-light rounded-3 p-4 text-center d-flex align-items-center justify-content-center" style="height: 240px;">
            <img src="${model.img_path}" 
                 class="img-fluid" 
                 style="max-height: 100%; object-fit: contain;" 
                 alt="${model.product_name}">
        </div>
        
        <div class="card-body px-3 pb-4">
            <p class="text-uppercase text-muted fw-bold mb-1" style="font-size: 0.85rem; letter-spacing: 1.2px;">
                ${model.brand}
            </p>
            
            <h5 class="card-title fw-bold text-dark mb-2">
                ${model.product_name}
            </h5>
            
            <div class="mt-3">
                <span class="fs-4 fw-black fw-bold">
                    Rs.${model.price}
                </span>
            </div>
        </div>
    </div>
</a> 
                `;
                fragment.appendChild(div);
            });
            newItemesBody.appendChild(fragment);
        } else {
            Notiflix.Notify.failure('Failed to fetch new items');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}


