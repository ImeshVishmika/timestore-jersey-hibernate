const revenuePeriod = document.getElementById("revenuePeriod");
const viewBy = document.getElementById("detailPeriodType");
const startDate = document.getElementById("detailStartDate");
const endDate = document.getElementById("detailEndDate");
const minRevenue = document.getElementById("detailMinRevenue");
const maxRevenue = document.getElementById("detailMaxRevenue");
const applyFilters = document.getElementById("detailApplyFilters");
const resetFilters = document.getElementById("detailResetFilters");
const revenueDetailsModal = document.getElementById("revenueDetailsModal");
const filterSection = document.getElementById("filterSection");

let revenueCanvas =document.getElementById("revenueChart");
let detailRevenueCanvas =document.getElementById("detailRevenueChart");

filterSection.addEventListener('change',async (event)=>{
    if(event.target.matches('input, select')){
         await loadRevenue(detailRevenueCanvas);
    }
})

applyFilters.addEventListener("click", async ()=>{
    await loadRevenue(detailRevenueCanvas)
});

resetFilters.addEventListener("click", async () => {

    viewBy.value = "day";
    startDate.value = "";
    endDate.value = "";
    minRevenue.value = "";
    maxRevenue.value = "";

    await loadRevenue(detailRevenueCanvas);
});

window.addEventListener("load", async () => {
    await loadRevenue( revenueCanvas);
});


revenuePeriod.addEventListener("change", async () => {
    await loadRevenue( revenueCanvas);
});

revenueDetailsModal.addEventListener("shown.bs.modal",async ()=>{
    await loadRevenue(detailRevenueCanvas);
});

revenueDetailsModal.addEventListener("hide.bs.modal",async ()=>{
    viewBy.value = "day";
    startDate.value = "";
    endDate.value = "";
    minRevenue.value = "";
    maxRevenue.value = "";
})

function revenueFilterData(filterDetails= false) {
    const payload = {};

    if (!filterDetails) {
        const selectedPeriodDays = parseInt(revenuePeriod ? revenuePeriod.value : "", 10);
        if (Number.isInteger(selectedPeriodDays) && selectedPeriodDays > 0) {
            payload.periodDays = selectedPeriodDays;
        }
        return payload;
    }

    if (viewBy && viewBy.value) {
        payload.viewBy = viewBy.value;
    }

    if (startDate && startDate.value) {
        payload.startDate = startDate.value;
    }

    if (endDate && endDate.value) {
        payload.endDate = endDate.value;
    }


    let minRevValue = minRevenue.value.trim();
    if (minRevValue !=='') {
        payload.minRevenue = minRevenue.value;
    }

    let maxRevValue = maxRevenue.value.trim();
    if (maxRevValue !== '') {
        payload.maxRevenue = maxRevenue.value;
    }

    return payload;
}
let detailRevenueChart = null;
let revenueChart = null;
function renderRevenueChart(ctx,canvasId,data) {

    if (canvasId === "detailRevenueChart") {
        if (detailRevenueChart) {
            detailRevenueChart.destroy();
            detailRevenueChart = null;
        }
    } else {
        if (revenueChart) {
            revenueChart.destroy();
            revenueChart = null;
        }
    }

    const gradient = ctx.createLinearGradient(0, 0, 0, 400);
    gradient.addColorStop(0, "rgba(220, 53, 69, 0.2)");
    gradient.addColorStop(1, "rgba(220, 53, 69, 0)");

    const chart = new Chart(ctx, {
        type: "line",
        data: {
            labels: data.dateList,
            datasets: [{
                label: "Revenue (Rs)",
                data: data.revenueList,
                borderColor: "#dc3545",
                backgroundColor: gradient,
                borderWidth: 3,
                tension: 0.3,
                fill: true,
                pointBackgroundColor: "#fff",
                pointBorderColor: "#dc3545",
                pointBorderWidth: 2,
                pointRadius: 6,
                pointHoverRadius: 8
            }]
        },
        options:  {
            responsive: true,
            maintainAspectRatio: false,
            animation: {
                duration: 2000,
                easing: "easeInOutQuart",
                loop: false,
                y: {
                    from: 0
                }
            },
            plugins: {
                legend: {display: false},
                tooltip: {
                    backgroundColor: "#000",
                    titleFont: {size: 13},
                    bodyFont: {size: 14, weight: "bold"},
                    padding: 10,
                    cornerRadius: 8,
                    displayColors: false
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    grid: {borderDash: [5, 5]},
                    ticks: {
                        color: "#6c757d",
                        font: {weight: "bold"},
                        callback: function (value) {
                            return "Rs " + value / 1000 + "k";
                        }
                    },
                    border: {display: false}
                },
                x: {
                    grid: {display: false},
                    ticks: {
                        color: "#6c757d",
                        font: {weight: "bold"}
                    }
                }
            }
        }
    });

    canvasId === "detailRevenueChart"
        ? (detailRevenueChart = chart)
        : (revenueChart = chart);
}

async function loadRevenue(canvas) {

    if(!canvas){
        Notiflix.Notify.failure("Reload Page");
        return;
    }

    try {
        const ctx = canvas.getContext("2d");
        const filterData = revenueFilterData(canvas.id==="detailRevenueChart");

        const request = await fetch("/api/product/revenue", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(filterData)
        });

        if (!request.ok) {
            Notiflix.Notify.failure("Failed to fetch revenue data");
        }

        const response = await request.json();
        if (!response.state) {
            Notiflix.Notify.failure("Error: "+response.message);
        }

        renderRevenueChart(ctx,canvas.id,response.data);

    } catch (error) {
        console.error("Error:", error);
        Notiflix.Notify.failure("Error: "+error.message);
    }
}


async function loadDashboardStats() {
    try {
        const request = await fetch("/api/admin/dashboardStats", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({})
        });

        if (request.ok) {
            const jsonObject = await request.json();
            console.log(jsonObject);
            if (jsonObject.state && jsonObject.data) {

                // Update revenue
                const totalRevenueEl = document.getElementById("totalRevenueValue");
                if (totalRevenueEl) {
                    totalRevenueEl.textContent = "Rs. " + parseFloat(jsonObject.data.total_revenue).toLocaleString('en-US', {
                        minimumFractionDigits: 2,
                        maximumFractionDigits: 2
                    });
                }

                // Update revenue growth percentage
                const revenueGrowthEl = document.getElementById("revenueGrowthPercent");
                if (revenueGrowthEl) {
                    revenueGrowthEl.textContent = jsonObject.data.revenue_growth;
                }

                // Update badge color based on growth
                const growthBadge = document.getElementById("revenueGrowthBadge");
                if (growthBadge) {
                    if (jsonObject.data.revenue_growth >= 0) {
                        growthBadge.className = "badge bg-success bg-opacity-10 text-success h-50 align-self-center";
                    } else {
                        growthBadge.className = "badge bg-danger bg-opacity-10 text-danger h-50 align-self-center";
                    }
                }

                // Update total orders
                const totalOrdersEl = document.getElementById("totalOrdersValue");
                if (totalOrdersEl) {
                    totalOrdersEl.textContent = jsonObject.data.total_orders;
                }
            }
        } else {
            Notiflix.Notify.failure('Failed to fetch dashboard stats');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}


