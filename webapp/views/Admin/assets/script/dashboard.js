const ADMIN_API_BASE = "/admin/api";
let revenueChart = null;

function getApiError(jsonObject, fallbackMessage) {
    return (jsonObject && jsonObject.error) ? jsonObject.error : fallbackMessage;
}

function toNumber(value, fallbackValue) {
    const parsed = Number(value);
    return Number.isFinite(parsed) ? parsed : fallbackValue;
}

async function postAdminApi(path, payload) {
    const request = await fetch(ADMIN_API_BASE + path, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "same-origin",
        body: JSON.stringify(payload || {})
    });

    let jsonObject = null;
    try {
        jsonObject = await request.json();
    } catch (ignored) {
        jsonObject = null;
    }

    if (request.status === 401) {
        window.location = "/admin/signin.html";
        return null;
    }

    if (!request.ok) {
        throw new Error(getApiError(jsonObject, "Request failed"));
    }

    if (!jsonObject || !jsonObject.state) {
        throw new Error(getApiError(jsonObject, "Request failed"));
    }

    return jsonObject;
}

function updateDashboardStatsUI(statsData) {
    const totalRevenue = toNumber(statsData.totalRevenue ?? statsData.total_revenue, 0);
    const totalOrders = toNumber(statsData.totalOrders ?? statsData.total_orders, 0);
    const revenueGrowth = toNumber(statsData.revenueGrowth ?? statsData.revenue_growth, 0);

    const totalRevenueEl = document.getElementById("totalRevenueValue");
    if (totalRevenueEl) {
        totalRevenueEl.textContent = "Rs. " + totalRevenue.toLocaleString("en-US", {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        });
    }

    const totalOrdersEl = document.getElementById("totalOrdersValue");
    if (totalOrdersEl) {
        totalOrdersEl.textContent = totalOrders;
    }

    const revenueGrowthEl = document.getElementById("revenueGrowthPercent");
    if (revenueGrowthEl) {
        revenueGrowthEl.textContent = Math.abs(revenueGrowth);
    }

    const growthBadge = document.getElementById("revenueGrowthBadge");
    if (growthBadge) {
        const isPositive = revenueGrowth >= 0;
        growthBadge.className = "badge " +
            (isPositive
                ? "bg-success bg-opacity-10 text-success h-50 align-self-center"
                : "bg-danger bg-opacity-10 text-danger h-50 align-self-center");

        const growthIcon = growthBadge.querySelector("i");
        if (growthIcon) {
            growthIcon.className = isPositive ? "bi bi-arrow-up-short" : "bi bi-arrow-down-short";
        }
    }
}

async function loadRevenue() {
    const chartCanvas = document.getElementById("revenueChart");
    const revenuePeriod = document.getElementById("revenuePeriod");
    if (!chartCanvas || !revenuePeriod) {
        return;
    }

    try {
        const jsonObject = await postAdminApi("api/product/revenue", {
            revenuePeriod: revenuePeriod.value
        });
        if (!jsonObject) {
            return;
        }

        const payloadData = jsonObject.data || {};
        const dates = payloadData.dates || jsonObject.dates || [];
        const revenues = payloadData.revenues || jsonObject.revenues || [];

        if (revenueChart) {
            revenueChart.destroy();
        }

        const ctx = chartCanvas.getContext("2d");
        const gradient = ctx.createLinearGradient(0, 0, 0, 400);
        gradient.addColorStop(0, "rgba(220, 53, 69, 0.2)");
        gradient.addColorStop(1, "rgba(220, 53, 69, 0)");

        revenueChart = new Chart(ctx, {
            type: "line",
            data: {
                labels: dates,
                datasets: [{
                    label: "Revenue (Rs)",
                    data: revenues,
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
            options: {
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
                    legend: { display: false },
                    tooltip: {
                        backgroundColor: "#000",
                        titleFont: { size: 13 },
                        bodyFont: { size: 14, weight: "bold" },
                        padding: 10,
                        cornerRadius: 8,
                        displayColors: false
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true,
                        grid: { borderDash: [5, 5] },
                        ticks: {
                            color: "#6c757d",
                            font: { weight: "bold" },
                            callback: function (value) {
                                return "Rs " + value / 1000 + "k";
                            }
                        },
                        border: { display: false }
                    },
                    x: {
                        grid: { display: false },
                        ticks: {
                            color: "#6c757d",
                            font: { weight: "bold" }
                        }
                    }
                }
            }
        });
    } catch (error) {
        console.error("Revenue load error:", error);
        Notiflix.Notify.failure(error.message || "Failed to fetch revenue data");
    }
}

async function loadDashboardStats() {
    try {
        const jsonObject = await postAdminApi("/admin/dashboardStats", {});
        if (!jsonObject) {
            return;
        }

        updateDashboardStatsUI(jsonObject.data || {});
    } catch (error) {
        console.error("Dashboard stats error:", error);
        Notiflix.Notify.failure(error.message || "Failed to fetch dashboard stats");
    }
}

document.addEventListener("DOMContentLoaded", function () {
    const revenuePeriod = document.getElementById("revenuePeriod");
    if (revenuePeriod) {
        revenuePeriod.addEventListener("change", loadRevenue);
    }

    loadDashboardStats();
    loadRevenue();
});
