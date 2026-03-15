window.addEventListener("load", loadRevenue);

document.getElementById("revenuePeriod").addEventListener("change", loadRevenue);

let revenueChart = null;

async function loadRevenue() {
    try {
        const ctx = document.getElementById('revenueChart').getContext('2d');
        const revenuePeriod = document.getElementById("revenuePeriod");

        const form = new FormData();
        form.append('revenuePeriod', revenuePeriod.value)

        const request = await fetch("/timestore/api/product/revenue", {
            method: "POST",
            body: form
        });

        if (request.ok) {
            if (revenueChart) {
                revenueChart.destroy();
            }

            const jsonObject = await request.json();

            // Create Red Gradient to match your theme
            const gradient = ctx.createLinearGradient(0, 0, 0, 400);
            gradient.addColorStop(0, 'rgba(220, 53, 69, 0.2)'); // Bootstrap Danger Color (Low Opacity)
            gradient.addColorStop(1, 'rgba(220, 53, 69, 0)'); // Fade out

            revenueChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: jsonObject.dates,
                    datasets: [{
                        label: 'Revenue (Rs)',
                        data: jsonObject.revenues,
                        borderColor: '#dc3545', // Your Red Accent
                        backgroundColor: gradient,
                        borderWidth: 3,
                        tension: 0.3, // Slightly less curve for a cleaner look
                        fill: true,
                        pointBackgroundColor: '#fff',
                        pointBorderColor: '#dc3545',
                        pointBorderWidth: 2,
                        pointRadius: 6,
                        pointHoverRadius: 8
                    }]
                },

                options: {
                    responsive: true,
                    maintainAspectRatio: false,

                    animation: {
                        duration: 2000, // Animation duration in milliseconds (2 seconds)
                        easing: 'easeInOutQuart', // Smooth starting and stopping
                        loop: false, // Don't loop the animation

                        // Specific animation for the Y-axis (growing up)
                        y: {
                            from: 0 // Start from y=0
                        }
                    },
                    // -----------------------------------

                    plugins: {
                        legend: { display: false },
                        tooltip: {
                            backgroundColor: '#000',
                            titleFont: { size: 13 },
                            bodyFont: { size: 14, weight: 'bold' },
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
                                color: '#6c757d',
                                font: { weight: 'bold' },
                                callback: function (value) {
                                    return 'Rs ' + value / 1000 + 'k';
                                }
                            },
                            border: { display: false }
                        },
                        x: {
                            grid: { display: false },
                            ticks: {
                                color: '#6c757d',
                                font: { weight: 'bold' }
                            }
                        }
                    }
                }
            });
        } else {
            Notiflix.Notify.failure('Failed to fetch revenue data');
        }
    } catch (error) {
        console.error('Error:', error);
        Notiflix.Notify.failure('Error ' + error);
    }
}

async function loadDashboardStats() {
    try {
        const request = await fetch("/timestore/api/admin/dashboardStats", {
            method: "POST"
        });

        if (request.ok) {
            const jsonObject = await request.json();
            
            if (jsonObject.state && jsonObject.data) {
                // Update revenue
                const totalRevenueEl = document.getElementById("totalRevenueValue");
                if (totalRevenueEl) {
                    totalRevenueEl.textContent = "Rs. " + parseFloat(jsonObject.data.total_revenue).toLocaleString('en-US', {minimumFractionDigits: 2, maximumFractionDigits: 2});
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

// Load dashboard stats when page loads
document.addEventListener("DOMContentLoaded", loadDashboardStats);
