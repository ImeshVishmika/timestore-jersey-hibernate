document.addEventListener("DOMContentLoaded", async () =>{
    loadPopularItems();
    loadNewItems();

});

function getModelImageUrl(model) {
    const modelId = model.model_id ?? model.modelId;
    return modelId ? `/api/model/img/${modelId}` : (model.img_path || "");
}

function getApiError(jsonObject, fallbackMessage) {
    return (jsonObject && jsonObject.error) ? jsonObject.error : fallbackMessage;
}


async function loadPopularItems() {

    try {
        
        const request = await fetch("/api/products",{
            method:"GET"
        });

        if (request.ok) {
            const jsonObject = await request.json();

            const newItemesBody = document.getElementById("popularItemesBody");
            newItemesBody.innerHTML = "";
            const fragment = document.createDocumentFragment();

            if(jsonObject.state) {
                const models = jsonObject.data;

                models.forEach(model => {
                    const div = document.createElement("div");

                    div.classList.add("col-12", "col-sm-6", "col-lg-3");
                    div.innerHTML = `
                <a href="/viewProduct.html?id=${model.productId}" class="text-decoration-none text-dark">
                <div class="card h-100 border-0 shadow-sm transition-hover">
                <div class="m-2 bg-light rounded-3 p-4 text-center d-flex align-items-center justify-content-center" style="height: 240px;">
                 <img src=/api/model/img/${model.productId} 
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
            
        </div>
    </div>
</a>            `;
                    fragment.appendChild(div);
                });
                newItemesBody.appendChild(fragment);
            } else {
                Notiflix.Notify.failure(getApiError(jsonObject, 'Failed to fetch popular items'));
            }
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
        
        const request = await fetch("/api/products", {
            method: "GET"
        });

        if (request.ok) {
            const jsonObject = await request.json();

            const newItemesBody = document.getElementById("newItemesBody");
            newItemesBody.innerHTML = "";
            const fragment = document.createDocumentFragment();

            if(jsonObject.state) {
                const models = jsonObject.data;

                models.forEach(model => {
                    const div = document.createElement("div");

                    div.classList.add("col-12", "col-sm-6", "col-lg-3");
                    div.innerHTML = `
                <a href="/viewProduct.html?id=${model.productId}" class="text-decoration-none text-dark">
    <div class="card h-100 border-0 shadow-sm transition-hover">
        <div class="m-2 bg-light rounded-3 p-4 text-center d-flex align-items-center justify-content-center" style="height: 240px;">
            <img src=/api/model/img/${model.productId}
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
            
        </div>
    </div>
</a> 
                `;
                    fragment.appendChild(div);
                });
                newItemesBody.appendChild(fragment);
            } else {
                Notiflix.Notify.failure(getApiError(jsonObject, 'Failed to fetch new items'));
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch new items');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}


