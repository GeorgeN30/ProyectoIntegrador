document.addEventListener("DOMContentLoaded", () => {
    // Registra el plugin de Data Labels para Chart.js
    Chart.register(ChartDataLabels);
  
    // *** ¡CORRECCIÓN AQUÍ! URL BASE DE LA API ***
    const API_BASE_URL = "http://localhost:8080/api/graphics"; // URL base de tu GraphicController
  
    // Función para obtener datos y crear un gráfico de barras (para datos de listas de objetos)
    async function createBarChart(
      canvasId,
      endpoint,
      title,
      labelKey,
      valueKey,
      topN = null
    ) {
      try {
        let url = `${API_BASE_URL}${endpoint}`;
        if (topN) {
          url += `?top=${topN}`; // Si se necesita un parámetro 'top'
        }
  
        const response = await fetch(url);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
  
        let labels;
        let values;
  
        // Si los datos son un array de objetos (ej. Top Reserved Books, Penalties by Type)
        if (Array.isArray(data)) {
          labels = data.map((item) => item[labelKey]);
          values = data.map((item) => item[valueKey]);
        } else {
          // Si los datos son un objeto/mapa (ej. Book Category)
          labels = Object.keys(data);
          values = Object.values(data);
        }
  
        const ctx = document.getElementById(canvasId).getContext("2d");
        new Chart(ctx, {
          type: "bar",
          data: {
            labels: labels,
            datasets: [
              {
                label: title,
                data: values,
                backgroundColor: [
                  "#3498db",
                  "#2ecc71",
                  "#e74c3c",
                  "#f39c12",
                  "#9b59b6",
                  "#1abc9c",
                  "#d35400",
                  "#c0392b",
                  "#2980b9",
                  "#8e44ad",
                  "#4CAF50",
                  "#FFC107",
                  "#00BCD4",
                  "#FF5722",
                  "#673AB7",
                ],
                borderColor: [
                  "#2980b9",
                  "#27ae60",
                  "#c0392b",
                  "#e67e22",
                  "#8e44ad",
                  "#16a085",
                  "#a04000",
                  "#a93226",
                  "#2471a3",
                  "#7d3c98",
                  "#388E3C",
                  "#FFA000",
                  "#0097A7",
                  "#E64A19",
                  "#512DA8",
                ],
                borderWidth: 1,
              },
            ],
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
              y: {
                beginAtZero: true,
                title: {
                  display: true,
                  text: "Cantidad",
                },
                ticks: {
                  callback: function (value) {
                    if (Number.isInteger(value)) {
                      return value;
                    }
                  },
                },
              },
              x: {
                title: {
                  display: true,
                  text: "Tipo / Nombre",
                },
              },
            },
            plugins: {
              title: {
                display: true,
                text: title,
                font: { size: 18 },
              },
              legend: {
                display: false,
              },
              datalabels: {
                anchor: "end",
                align: "top",
                formatter: (value) => value,
                font: {
                  weight: "bold",
                },
              },
            },
          },
        });
      } catch (error) {
        console.error(`Error al cargar los datos para ${title}:`, error);
        document.getElementById(
          canvasId
        ).parentElement.innerHTML = `<p class="error-message">Error al cargar el gráfico de ${title}.</p>`;
      }
    }
  
    // Función para obtener datos y crear un gráfico de pastel/donut (para datos de mapas)
    async function createDoughnutChart(canvasId, endpoint, title) {
      // No necesita labelKey/valueKey si siempre es un Map<String, Long>
      try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
  
        const labels = Object.keys(data);
        const values = Object.values(data);
  
        const ctx = document.getElementById(canvasId).getContext("2d");
        new Chart(ctx, {
          type: "doughnut",
          data: {
            labels: labels,
            datasets: [
              {
                label: title,
                data: values,
                backgroundColor: [
                  "#3498db",
                  "#2ecc71",
                  "#e74c3c",
                  "#f39c12",
                  "#9b59b6",
                  "#1abc9c",
                  "#d35400",
                  "#c0392b",
                  "#2980b9",
                  "#8e44ad",
                  "#4CAF50",
                  "#FFC107",
                  "#00BCD4",
                  "#FF5722",
                  "#673AB7",
                ],
                borderColor: "#ffffff",
                borderWidth: 2,
              },
            ],
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
              title: {
                display: true,
                text: title,
                font: { size: 18 },
              },
              legend: {
                position: "right",
                labels: {
                  padding: 15,
                },
              },
              datalabels: {
                formatter: (value, ctx) => {
                  let sum = 0;
                  let dataArr = ctx.chart.data.datasets[0].data;
                  dataArr.map((data) => {
                    sum += data;
                  });
                  let percentage = ((value * 100) / sum).toFixed(2) + "%";
                  return ctx.chart.data.labels[ctx.dataIndex] + "\n" + percentage;
                },
                color: "#fff",
                font: {
                  weight: "bold",
                  size: 10,
                },
              },
            },
          },
        });
      } catch (error) {
        console.error(`Error al cargar los datos para ${title}:`, error);
        document.getElementById(
          canvasId
        ).parentElement.innerHTML = `<p class="error-message">Error al cargar el gráfico de ${title}.</p>`;
      }
    }
  
    // Función para obtener datos y crear un gráfico de línea
    async function createLineChart(
      canvasId,
      endpoint,
      title,
      labelKey,
      valueKey,
      numMonths = 6
    ) {
      try {
        const response = await fetch(
          `${API_BASE_URL}${endpoint}?numMonths=${numMonths}`
        );
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
  
        // Mapea los datos del DTO: los nombres de las claves del JSON
        const labels = data.map((item) => item[labelKey]);
        const values = data.map((item) => item[valueKey]);
  
        const ctx = document.getElementById(canvasId).getContext("2d");
        new Chart(ctx, {
          type: "line",
          data: {
            labels: labels,
            datasets: [
              {
                label: "Cantidad", // Etiqueta genérica para la cantidad
                data: values,
                fill: true,
                borderColor: "#3498db",
                tension: 0.3,
                backgroundColor: "rgba(52, 152, 219, 0.2)",
                pointBackgroundColor: "#3498db",
                pointBorderColor: "#fff",
                pointHoverBackgroundColor: "#fff",
                pointHoverBorderColor: "#3498db",
              },
            ],
          },
          options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
              y: {
                beginAtZero: true,
                title: {
                  display: true,
                  text: "Cantidad",
                },
                ticks: {
                  callback: function (value) {
                    if (Number.isInteger(value)) {
                      return value;
                    }
                  },
                },
              },
              x: {
                title: {
                  display: true,
                  text: "Mes",
                },
              },
            },
            plugins: {
              title: {
                display: true,
                text: title,
                font: { size: 18 },
              },
              legend: {
                display: true,
                position: "top",
              },
              datalabels: {
                anchor: "end",
                align: "top",
                formatter: (value) => value,
                font: {
                  weight: "bold",
                },
              },
            },
          },
        });
      } catch (error) {
        console.error(`Error al cargar los datos para ${title}:`, error);
        document.getElementById(
          canvasId
        ).parentElement.innerHTML = `<p class="error-message">Error al cargar el gráfico de ${title}.</p>`;
      }
    }
  
    async function loadKpi(
      endpoint,
      valueId,
      percentageId,
      trendIconId,
      labelId
    ) {
      try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`);
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        const kpiData = await response.json();
  
        // El 'labelId' es opcional si no lo envías desde el DTO
        if (labelId && kpiData.label) {
          document.getElementById(labelId).textContent = kpiData.label;
        }
  
        document.getElementById(valueId).textContent = kpiData.currentValue;
  
        const percentageElement = document.getElementById(percentageId);
        const trendIconElement = document.getElementById(trendIconId);
  
        // Formatea el porcentaje para mostrar solo dos decimales
        percentageElement.textContent = `${kpiData.percentageChange.toFixed(2)}%`;
        percentageElement.className = "kpi-percentage " + kpiData.trend; // Añade la clase de color
  
        // Actualiza el icono de tendencia
        trendIconElement.className = "kpi-trend-icon"; // Limpia clases anteriores
        if (kpiData.trend === "up") {
          trendIconElement.classList.add("fas", "fa-arrow-up", "up");
        } else if (kpiData.trend === "down") {
          trendIconElement.classList.add("fas", "fa-arrow-down", "down");
        } else {
          trendIconElement.classList.add("fas", "fa-minus", "flat"); // Icono para 'sin cambio'
        }
      } catch (error) {
        console.error(`Error al cargar el KPI de Reservas del Mes:`, error);
        // Muestra un mensaje de error en la UI
        document.getElementById(valueId).textContent = "Error";
        document.getElementById(percentageId).textContent = "N/A";
        document.getElementById(trendIconId).className =
          "kpi-trend-icon fas fa-exclamation-triangle"; // Icono de advertencia
        document.getElementById(trendIconId).style.color = "#e74c3c"; // Color rojo para el error
      }
    }
  
    // --- Llamadas para crear los gráficos con las claves JSON correctas ---
  
    // Gráfico de Barras: Libros por Categoría (devuelve Map<String, Long>)
    // No necesita labelKey/valueKey explícitos para Map, la función lo maneja
    createBarChart(
      "bookCategoryChart",
      "/bar/book-category",
      "Libros por Categoría"
    );
  
    // Gráfico de Pastel: Libros Disponibles (devuelve Map<String, Long>)
    // No necesita labelKey/valueKey explícitos para Map
    createDoughnutChart(
      "bookAvailableChart",
      "/circle/book-available",
      "Disponibilidad de Libros"
    );
  
    // Gráfico de Línea: Reservas por Mes
    // JSON: { "period": "...", "quantity": ... } -> labelKey='period', valueKey='quantity'
    createLineChart(
      "reservationsByMonthChart",
      "/lineal/reservation-month",
      "Reservas Mensuales",
      "period",
      "quantity",
      6
    );
  
    // Gráfico de Barras: Top Libros Más Reservados
    // JSON: { "title": "...", "quantity": ... } -> labelKey='title', valueKey='quantity'
    createBarChart(
      "topReservedBooksChart",
      "/bar/top-reserved-books",
      "Top 5 Libros Más Reservados",
      "title",
      "quantity",
      5
    );
  
    // Gráfico de Barras: Penalidades por Tipo
    // JSON: { "type": "...", "quantity": ... } -> labelKey='type', valueKey='quantity'
    createBarChart(
      "penaltiesByTypeChart",
      "/bar/penalties-by-type",
      "Penalidades por Tipo",
      "type",
      "quantity"
    );
  
    // Gráfico de Barras: Top Autores Más Reservados
    // JSON: { "name": "...", "quantity": ... } -> labelKey='name', valueKey='quantity'
    createBarChart(
      "topReservedAuthorsChart",
      "/bar/top-reserved-authors",
      "Top 5 Autores Más Reservados",
      "name",
      "quantity",
      5
    );
  
    // --- Llamadas para cargar los KPIs ---
    loadKpi(
      "/kpi/monthly-reservations",
      "kpiReservationsValue",
      "kpiReservationsPercentage",
      "kpiReservationsTrendIcon",
      "kpiReservationsLabel" // Este es opcional si eliminas 'label' del DTO
    );
  });
  