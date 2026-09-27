window.addEventListener("load", () => {
    loadProducts();
    loadBrands();
});

let pageNo=1;

let filter = document.getElementById("filter");
filter.addEventListener("change", async ()=>{
    pageNo=1;
    changePageNo();
    await loadProducts();
});

let brands = document.getElementById("brands");
brands.addEventListener("change", async ()=>{
    pageNo=1;
    changePageNo();
    await loadProducts();
});

async function loadProducts() {
    try {
        const payload = {};
        payload.limit=4;
        payload.pageNo=pageNo;

        if (filter.selectedIndex !== 0) {
            payload.sort = filter.value;
            alert(JSON.stringify(payload));
        }

        const selectedBrands = [];
        brands.querySelectorAll("input").forEach(input => {
            
            if (input.checked) {
                selectedBrands.push(input.value);
            }
        });

        if (selectedBrands.length > 0) {
            payload.brandId = selectedBrands;
        }

        const request = await fetch("/api/model/load", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        if (request.ok) {
            const jsonObject = await request.json();

            console.log(jsonObject);

            if(jsonObject.state===false || jsonObject.data.length===0){
                return false;
            }

            const productsTable = document.getElementById("modelsTable");
            productsTable.innerHTML = "";
            const fragment = document.createDocumentFragment();

            jsonObject.data.forEach(model => {
                const div = document.createElement("div");

                div.classList.add("col-12", "col-sm-6", "col-lg-3");
                div.innerHTML = `
                <a href="/viewProduct.html?id=${model.productId}"  class="text-decoration-none">
                    <div class="card border-0 h-100">
                        <div class="bg-light rounded-3 p-4 text-center mb-3">
                            <img src=/api/model/img/${model.modelId} class="img-fluid" style="height: 180px; object-fit: contain;" alt="Watch">
                        </div>
                        <div class="card-body px-0 pt-0">
                            <small class="text-muted fw-semibold">${model.brandName}</small>
                            <h6 class="card-title  fw-bold mb-1">${model.model}</h6>
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

    return true;
}

async function loadBrands() {
    try {
        const request = await fetch("/api/brand", {
            method: "GET"
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

//---------------------------------Pagination---------------------------------

let pageNoComponent = document.getElementById("pagNo");

let prevButton = document.getElementById("prev-button");
prevButton.addEventListener("click",async ()=>{
    if(pageNo === 1){return}
    pageNo--;
    await loadProducts()  //used await so the page no change after the function completed
    changePageNo();
});

let nextButton = document.getElementById("next-button");
nextButton.addEventListener("click",async ()=>{
    pageNo++;
    let state=await loadProducts();
    if (!state){
        pageNo--
        return
    }
    changePageNo();
});

function changePageNo(){
    pageNoComponent.textContent = (pageNo).toString();
}
