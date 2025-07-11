// Aqui comienza el script para el login 

// Mostrar el nombre del admin en el menú de administrador
document.addEventListener("DOMContentLoaded", function () {
    const nombre = sessionStorage.getItem("nombreUsuario");
    const apellido = sessionStorage.getItem("apellidoUsuario");

    const nombreAdminDiv = document.getElementById("NombreAdmin");

    if (nombreAdminDiv) {
        if (nombre && apellido) {
            nombreAdminDiv.textContent = `${nombre} ${apellido}`;
        } else {
            nombreAdminDiv.textContent = "ADMINISTRADOR";
        }
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const nombre = sessionStorage.getItem("nombreUsuario");
    const apellido = sessionStorage.getItem("apellidoUsuario");

    const nombreAdminDiv = document.getElementById("NombreLector");

    if (nombreAdminDiv) {
        if (nombre && apellido) {
            nombreAdminDiv.textContent = `${nombre} ${apellido}`;
        } else {
            nombreAdminDiv.textContent = "LECTOR";
        }
    }
});


// Cambiar entre Login y Registro
document.addEventListener("DOMContentLoaded", () => {
    const loginToggle = document.getElementById("login-toggle");
    const registerToggle = document.getElementById("register-toggle");
    const loginForm = document.getElementById("login-form");
    const registerForm = document.getElementById("register-form");

    if (loginToggle && registerToggle && loginForm && registerForm) {
        loginToggle.addEventListener("click", () => {
            loginToggle.classList.add("active");
            registerToggle.classList.remove("active");
            loginForm.classList.add("active");
            registerForm.classList.remove("active");
        });

        registerToggle.addEventListener("click", () => {
            registerToggle.classList.add("active");
            loginToggle.classList.remove("active");
            registerForm.classList.add("active");
            loginForm.classList.remove("active");
        });
    }

    // Envío del formulario de registro
    const registerFormEl = document.getElementById("register-form");
    if (registerFormEl) {
        registerFormEl.addEventListener("submit", function (event) {
            event.preventDefault();

            const nombre = document.getElementById("nombre").value;
            const correo = document.getElementById("correo").value;
            const apellidos = document.getElementById("apellidos").value;
            const contraseña = document.getElementById("contraseña").value;
            const dni = document.getElementById("dni").value;

            if (!nombre || !correo || !contraseña || !dni || !apellidos) {
                alert("Todos los campos son obligatorios.");
                return;
            }

            const contraseñaValida = /^(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]).{8,}$/;
            if (!contraseñaValida.test(contraseña)) {
                alert("La contraseña debe tener al menos una letra mayúscula, un número, un carácter especial y mínimo 8 caracteres.");
                return;
            }

            fetch(`/api/users/dni/${dni}`)
                .then(response => {
                    if (response.ok) {
                        throw new Error("El DNI ya está registrado.");
                    } else if (response.status === 404) {
                        const datosUsuario = {
                            name: nombre,
                            lastName: apellidos,
                            email: correo,
                            password: contraseña,
                            dni: dni,
                            role: "LECTOR"
                        };

                        return fetch("/api/users", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify(datosUsuario)
                        });
                    } else {
                        throw new Error("Error al verificar el DNI.");
                    }
                })
                .then(response => {
                    if (!response.ok) throw new Error("Error al registrar el usuario.");
                    return response.json();
                })
                .then(() => alert("Registro exitoso."))
                .catch(error => alert("Error: " + error.message));
        });
    }


    // Envío del formulario de login
    const loginFormEl = document.getElementById("login-form");
    if (loginFormEl) {
        loginFormEl.addEventListener("submit", function (event) {
            event.preventDefault();

            const email = loginFormEl.querySelector("input[type='email']").value;
            const password = loginFormEl.querySelector("input[type='password']").value;

            if (!email || !password) {
                alert("Por favor, completa todos los campos.");
                return;
            }

            const loginData = { email, password };

            fetch("/api/users/login", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(loginData)
            })
                .then(response => {
                    if (!response.ok) throw new Error("Credenciales incorrectas");
                    return response.json();
                })
                .then(data => {
                    sessionStorage.setItem("userId", data.id);
                    sessionStorage.setItem("nombreUsuario", data.name);
                    sessionStorage.setItem("apellidoUsuario", data.lastName);

                    if (data.role === "ADMINISTRADOR") {
                        window.location.href = "/Administracion/PresentacionAdmin.html";
                    } else if (data.role === "LECTOR") {
                        window.location.href = "/Lectores/PresentacionLector_Inicio.html";
                    } else {
                        alert("Rol no reconocido.");
                    }
                })
                .catch(error => alert("Error: " + error.message));
        });
    }
});

//=============================================
// Aqui comienza el scrip para administradores
//=============================================

function MostrarCadaSeccióndelNav(id) {
    const secciones = document.querySelectorAll('.contenido');
    const itemsNav = document.querySelectorAll('.navbar li');

    secciones.forEach(sec => sec.classList.remove('visible'));
    itemsNav.forEach(item => item.classList.remove('active'));

    document.getElementById(id).classList.add('visible');

    const activeItem = Array.from(itemsNav).find(item => item.dataset.id === id);
    if (activeItem) activeItem.classList.add('active');
}

function mostrarSeccionesDeReporte(seccion) {
    const seccionLista = document.getElementById('seccion-lista');
    const seccionGenerar = document.getElementById('seccion-generar');
    const botonSolucionado = document.getElementById('botonSolucionado');
    const botonSeleccionar = document.getElementById('botonSeleccionar');
    const formulario = document.querySelector('form.form-container');

    // Ocultar todo al inicio
    seccionLista.classList.add('oculto');
    seccionGenerar.classList.add('oculto');
    botonSolucionado.classList.add('oculto');
    botonSeleccionar.classList.add('oculto');
    formulario.classList.add('oculto');

    if (seccion === 'lista') {
        seccionLista.classList.remove('oculto');
        botonSolucionado.classList.remove('oculto');
    } else if (seccion === 'generar') {
        seccionGenerar.classList.remove('oculto');
        botonSeleccionar.classList.remove('oculto');
    }

    // Activar estilo del menú
    const menuItems = document.querySelectorAll('.menu-reporte li');
    menuItems.forEach(item => item.classList.remove('activo'));
    if (seccion === 'lista') {
        menuItems[0].classList.add('activo');
    } else {
        menuItems[1].classList.add('activo');
    }
}

function MostrarFormularioReporte(seccion) {
    const seccionGenerar = document.getElementById('seccion-generar');
    const botonSeleccionar = document.getElementById('botonSeleccionar');
    const formularioreporte = document.querySelector('form.form-container');

    const seleccionado = document.querySelector('input[name="SeleccionLibroReportado"]:checked');
    if (!seleccionado) {
        alert("Por favor, selecciona un libro antes de continuar.");
        return;
    }

    if (seccion === 'formulario') {
        seccionGenerar.classList.add('oculto');
        botonSeleccionar.classList.add('oculto');
        formularioreporte.classList.remove('oculto');
    }

    const IDBOOK = seleccionado.value;
    const NOMBREBOOK = seleccionado.dataset.nombre;

    formularioreporte.dataset.bookId = IDBOOK;
    formularioreporte.dataset.title = NOMBREBOOK;
}

function mostrarSeccionesDeSanción(seccion) {
    const seccionLista = document.getElementById('seccion-lista-sancion');
    const seccionGenerar = document.getElementById('seccion-generar-sancion');
    const formulario = document.getElementById('formulario-sancion');
    const botonSancion = document.getElementById('boton-sancion');
    const botonSeleccionar = document.getElementById('botonSeleccionarSancion');

    // Ocultar todo al inicio
    seccionLista.classList.add('oculto');
    seccionGenerar.classList.add('oculto');
    formulario.classList.add('oculto');
    botonSancion.classList.add('oculto');
    botonSeleccionar.classList.add('oculto');

    if (seccion === 'lista') {
        seccionLista.classList.remove('oculto');
        botonSancion.classList.remove('oculto');
    } else if (seccion === 'generar') {
        seccionGenerar.classList.remove('oculto');
        botonSeleccionar.classList.remove('oculto');
    }

    const menuItems = document.querySelectorAll('.menu-sancion li');
    menuItems.forEach(item => item.classList.remove('activo'));

    if (seccion === 'lista') {
        menuItems[0].classList.add('activo');
    } else {
        menuItems[1].classList.add('activo');
    }
}

function MostrarFormularioSanción(seccion) {
    const seccionGenerar = document.getElementById('seccion-generar-sancion');
    const formulario = document.getElementById('formulario-sancion');
    const botonSeleccionar = document.getElementById('botonSeleccionarSancion');

    // 👇 Validar si se seleccionó un usuario
    const seleccionado = document.querySelector('input[name="seleccionUsuario"]:checked');
    if (!seleccionado) {
        alert("Por favor, selecciona un usuario antes de continuar.");
        return;
    }

    if (seccion === 'formulario') {
        seccionGenerar.classList.add('oculto');
        botonSeleccionar.classList.add('oculto');
        formulario.classList.remove('oculto');
    }

    // 👇 Guardamos temporalmente el ID y el nombre del usuario seleccionado
    const idLector = seleccionado.value;
    const nombreLector = seleccionado.dataset.nombre;

    // Guardamos en atributos del formulario
    formulario.dataset.bookId = idLector;
    formulario.dataset.book = nombreLector;
}

// MOSTRAR USUARIOS EN LA SECCIÓN GENERAR SANCIÓN
let ListadeLectores_Sanción = [];

function CargarTablaUsuariosSanción(users) {
    const tbody = document.getElementById("users-tbodyadmin");
    tbody.innerHTML = "";
    users.forEach(user => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td><input type="radio" name="seleccionUsuario" value="${user.id}" data-nombre="${user.name} ${user.lastName}"></td>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.lastName}</td>
            <td>${user.email}</td>
            <td>${user.dni}</td>
        `;
        tbody.appendChild(tr);
    });
}

function ObtenerUsuariosSanción() {
    fetch('/api/users')
        .then(response => response.json())
        .then(users => {
            // Filtrar solo usuarios con rol LECTOR
            const lectores = users.filter(user => user.role === "LECTOR");
            ListadeLectores_Sanción = lectores;
            CargarTablaUsuariosSanción(lectores);
        })
        .catch(error => console.error("Error al obtener los usuarios:", error));
}

document.addEventListener("DOMContentLoaded", function () {
    ObtenerUsuariosSanción();
});

////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////       FORMULARIO REPORTE       //////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
document.querySelector("form.form-container").addEventListener("submit", function (e) {
    e.preventDefault();

    const formulario = e.target;

    const fechaReposicion = document.getElementById("fecha").value;
    const descripcion = document.getElementById("descripcionreporte").value.trim();
    const tipo = document.getElementById("tipo-categoria").value;
    const cantidad = document.getElementById("cantidadreportado").value;

    const bookId = formulario.dataset.bookId;
    const title = formulario.dataset.title;

    if (!bookId || !title) {
        alert("Debes seleccionar un libro antes de enviar el reporte.");
        return;
    }

    if (!tipo) {
        alert("Selecciona un tipo de reporte.");
        return;
    }

    if (!cantidad){
        alert("Debes indicar un cantidad de libros reportados")
        return;
    }

    if (!descripcion) {
        alert("La descripción no puede estar vacía.");
        return;
    }

    if (!fechaReposicion) {
        alert("Selecciona una fecha de reposición.");
        return;
    }

    // Obtener la cantidad disponible del libro seleccionado
    let cantidadDisponible = null;
    if (bookId) {
        const filaLibro = document.querySelector(`tr[data-book-id="${bookId}"]`);
        if (filaLibro) {
            const celdaCantidad = filaLibro.querySelector('.cantidad-disponible');
            if (celdaCantidad) {
                cantidadDisponible = parseInt(celdaCantidad.textContent);
            }
        }
    }

    // Validación frontend
    if (!cantidad || cantidad <= 0) {
        alert("La cantidad reportada debe ser mayor que cero.");
        return;
    }
    if (cantidadDisponible !== null && cantidad > cantidadDisponible) {
        alert("La cantidad reportada no puede ser mayor a la cantidad disponible (" + cantidadDisponible + ").");
        return;
    }

    const datos = {
        bookId: parseInt(bookId),
        type: tipo,
        description: descripcion,
        restockDate: fechaReposicion,
        cantidad: cantidad, // <-- Agregado
        resolved: false // Estado por defecto
    };

    console.log(" Enviando reporte:", datos);

    fetch("/api/reports", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(datos)
    })
        .then(async response => {
            const body = await response.text();
            if (!response.ok) {
                throw new Error(body);
            }
            return JSON.parse(body);
        })
        .then(reporte => {
            alert("✅ Reporte enviado correctamente.");
            formulario.reset();
            agregarReporteATabla(reporte);
        })
        .catch(error => {
            console.error("❌ Error al enviar reporte:", error);
            alert("Error al enviar el reporte:\n" + error.message);
        });
});

function agregarReporteATabla(reporte) {
    const tbody = document.querySelector(".tbody-reporte");
    const tr = document.createElement("tr");

    tr.innerHTML = `
        <td><input type="radio" name="seleccionReporte" data-id="${reporte.id}"></td>
        <td>${reporte.id}</td>
        <td>${reporte.book.id}</td>
        <td>${reporte.book.title}</td>
        <td>${reporte.type}</td>
        <td>${reporte.description}</td>
        <td>${reporte.reportDate}</td>
        <td>${reporte.restockDate}</td>
        <td>${reporte.cantidad}</td> <!-- Nueva columna -->
        <td>${reporte.resolved ? "Resuelto" : "Pendiente"}</td>
    `;

    tbody.appendChild(tr);
    // Actualizar la disponibilidad del libro correspondiente
    actualizarDisponibilidadLibro(reporte.book.id, reporte.cantidad);
}

// Función para actualizar la cantidad disponible y estado en la tabla de libros
function actualizarDisponibilidadLibro(bookId, cantidadReportada) {
    // Busca la fila del libro en la tabla de libros (ajusta el selector a tu estructura real)
    const filaLibro = document.querySelector(`tr[data-book-id="${bookId}"]`);
    if (!filaLibro) return;

    // Ajusta el selector a la celda de cantidad disponible (usa la clase o el índice correcto)
    const celdaCantidad = filaLibro.querySelector('.cantidad-disponible');
    let cantidadActual = parseInt(celdaCantidad.textContent);
    let nuevaCantidad = cantidadActual - cantidadReportada;
    if (nuevaCantidad < 0) nuevaCantidad = 0;
    celdaCantidad.textContent = nuevaCantidad;

    // Ajusta el selector a la celda de estado (usa la clase o el índice correcto)
    const celdaEstado = filaLibro.querySelector('.estado-libro');
    if (nuevaCantidad <= 0) {
        celdaEstado.textContent = "No disponible";
    }
}
////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////       FORMULARIO SANCIÓN       ///////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
document.getElementById("formulario-sancion").addEventListener("submit", function (e) {
    e.preventDefault();
    const fechaTermino = document.getElementById("fecha-sancion").value;
    const multa = document.getElementById("multa-sancion").value;
    const detalles = document.getElementById("motivo-sancion").value;
    // Capturamos correctamente el valor del select
    const select = document.getElementById("motivo-categoria");
    const categoria = select?.value?.trim();
    // 🧪 Depuración en consola
    console.log("🟡 Fecha:", fechaTermino);
    console.log("🟡 Multa:", multa);
    console.log("🟡 Detalles:", detalles);
    console.log("🟡 Categoria seleccionada:", categoria);
    const fechaActual = new Date().toISOString().split('T')[0];  // Esto devuelve la fecha en formato yyyy-MM-dd
    const radioSeleccionado = document.querySelector('input[name="seleccionUsuario"]:checked');
    if (!radioSeleccionado) {
        alert("Por favor, selecciona un lector.");
        return;
    }
    if (!categoria || categoria === "") {
        alert("Por favor, selecciona una categoría de sanción.");
        return;
    }
    const idLector = radioSeleccionado.value;
    const datos = {
        userId: parseInt(idLector),
        amount: parseFloat(multa),
        reason: categoria,
        description: detalles,
        suspensionDate: fechaActual,  // La fecha actual (en formato yyyy-MM-dd)
        suspensionEndDate: fechaTermino,  // La fecha de término (en formato yyyy-MM-dd)
        paid: false
    };
    console.log("📤 Datos a enviar:", datos)

    fetch("/api/penalties", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(datos)
    })
        .then(async response => {
            const body = await response.text();
            if (!response.ok) {
                throw new Error(`Error ${response.status}: ${body}`);
            }
            return JSON.parse(body);
        })
        .then(penalty => {
            alert("✅ Sanción registrada correctamente.");
            document.getElementById("formulario-sancion").reset();
            agregarSancionATabla(penalty);
        })
        .catch(error => {
            console.error("❌ Error:", error);
            alert("No se pudo registrar la sanción:\n" + error.message);
        });
});

function agregarSancionATabla(penalty) {
    const tbody = document.getElementById("tabla-sanciones");

    const tr = document.createElement("tr");
    tr.innerHTML = `
        <td><input type="radio" name="seleccionSancion" data-id="${penalty.id}"></td>
        <td>${penalty.id}</td>
        <td>${penalty.user.id}</td>
        <td>${penalty.user.name} ${penalty.user.lastName}</td>
        <td>${penalty.reason}</td>
        <td>${penalty.description}</td>
        <td>${penalty.suspensionDate}</td>
        <td>${penalty.suspensionEndDate}</td>
        <td>S/. ${penalty.amount.toFixed(2)}</td>
        <td>${penalty.paid ? "Pagado" : "No pagado"}</td>
    `;
    tbody.appendChild(tr);
}
/////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////    CARGAR TABLAS GENERADAS LOS FORM   ////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////
function cargarTodosLosReportes() {
    fetch("/api/reports")
        .then(response => response.json())
        .then(reportes => {
            const tbody = document.querySelector(".tbody-reporte");
            tbody.innerHTML = "";
            reportes.forEach(reporte => agregarReporteATabla(reporte));
        })
        .catch(error => {
            console.error("❌ Error al cargar reportes:", error);
        });
}

function cargarTodasLasSanciones() {
    fetch("/api/penalties")
        .then(response => response.json())
        .then(penalties => {
            const tbody = document.getElementById("tabla-sanciones");
            tbody.innerHTML = ""; // Limpiar antes de cargar
            penalties.forEach(penalty => agregarSancionATabla(penalty));
        })
        .catch(error => {
            console.error("❌ Error al cargar sanciones:", error);
        });
}
/*
function cargarTodasLasSanciones() {
    fetch("/api/books")
        .then(response => response.json())
        .then(penalties => {
            const tbody = document.getElementById("libros-tbody");
            tbody.innerHTML = ""; // Limpiar antes de cargar
            penalties.forEach(penalty => agregarSancionATabla(penalty));
        })
        .catch(error => {
            console.error("❌ Error al cargar sanciones:", error);
        });
}
*/
document.addEventListener("DOMContentLoaded", function () {
    cargarTodasLasSanciones();
    cargarTodosLosReportes();
});

// Mostrar libros para la sección de listar libros

let ListaLibros = [];

function cargarLibros(libros) {
    const tbody = document.getElementById("libros-tbody");
    tbody.innerHTML = "";

    libros.forEach(book => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td><input type="checkbox" data-id="${book.id}"></td>
            <td>${book.id}</td>
            <td><img src="${book.imageUrl || '#'}" alt="${book.title}" width="50"></td>
            <td>${book.title}</td>
            <td>${book.author.name}</td>
            <td>${book.category.name}</td>
            <td>${book.description}</td>
            <td>${book.quantity}</td>
            <td>${book.available ? 'Disponible' : 'No disponible'}</td>
        `;
        tbody.appendChild(tr);
    });
}

function obtenerLibros() {
    fetch('/api/books')
        .then(response => response.json())
        .then(books => {
            ListaLibros = books;
            cargarLibros(books);
        })
        .catch(error => console.error("Error al obtener los libros:", error));
}

document.addEventListener("DOMContentLoaded", function () {
    obtenerLibros();
});

// Mostrar libros para la sección de generar reporte
let librosXreportar = [];

function cargarTablaLibrosXReportar(libros) {
    const tbody = document.getElementById("tablalibros-reporte");
    tbody.innerHTML = "";

    libros.forEach(book => {
        const tr = document.createElement("tr");
        tr.setAttribute('data-book-id', book.id); // Atributo para identificar el libro
        tr.innerHTML = `
            <td><input type="radio" name="SeleccionLibroReportado" value="${book.id}" data-nombre="${book.title}"></td>
            <td>${book.id}</td>
            <td><img src="${book.imageUrl || '#'}" alt="${book.title}" width="50"></td>
            <td>${book.title}</td>
            <td>${book.author.name}</td>
            <td>${book.category.name}</td>
            <td>${book.description}</td>
            <td class="cantidad-disponible">${book.quantity}</td>
            <td class="estado-libro">${book.available ? 'Disponible' : 'No disponible'}</td>
        `;
        tbody.appendChild(tr);
    });
}

function OptenerLibrosreporte() {
    fetch('/api/books')
        .then(response => response.json())
        .then(books => {
            librosXreportar = books;
            cargarTablaLibrosXReportar(books);
        })
        .catch(error => console.error("Error al obtener los libros:", error));
}

document.addEventListener("DOMContentLoaded", function () {
    OptenerLibrosreporte();

});

// Aqui termina el script para administradores

// Aqui comienza el scrip para lectores -


/*ACCIONES DE BOTONES*/

// Registrar libro - administrador 
document.getElementById("btnRegistrarLibro").addEventListener("click", function () {
    const titulo = document.getElementById("titulo").value.trim();
    const autorNombre = document.getElementById("autor").value.trim();
    const categoriaNombre = document.getElementById("categoria").value.trim();
    const cantidad = parseInt(document.getElementById("cantidad").value);
    const descripcion = document.getElementById("descripcion").value.trim();
    
    if (!titulo || !autorNombre || !categoriaNombre || !cantidad || !descripcion) {
        alert("Por favor, completa todos los campos obligatorios.");
        return;
    }
    fetch("/api/authors")
        .then(res => res.json())
        .then(autores => {
            let autor = autores.find(a => a.name.toLowerCase() === autorNombre.toLowerCase());
            if (autor) {
                return Promise.resolve(autor);
            } else {
                // Crear el autor si no existe
                return fetch("/api/authors", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ name: autorNombre })
                })
                    .then(res => {
                        if (!res.ok) throw new Error("No se pudo crear el autor");
                        return res.json();
                    });
            }
        })
        .then(autor => {
            // Buscar la categoría
            return fetch("/api/categories")
                .then(res => res.json())
                .then(categorias => {
                    const categoria = categorias.find(c => c.name.toLowerCase() === categoriaNombre.toLowerCase());
                    if (!categoria) throw new Error("Categoría no encontrada");

                    const nuevoLibro = {
                        title: titulo,
                        quantity: cantidad,
                        available: true,
                        description: descripcion,
                        authorId: autor.id,
                        categoryId: categoria.id
                    };

                    return fetch("/api/books", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(nuevoLibro)
                    });
                });
        })
        .then(response => {
            if (!response.ok) throw new Error("Error al registrar el libro");
            return response.json();
        })
        .then(data => {
            alert("Libro registrado con éxito.");
            h

            document.getElementById("titulo").value = "";
            document.getElementById("autor").value = "";
            document.getElementById("categoria").value = "";
            document.getElementById("cantidad").value = "";
            document.getElementById("descripcion").value = "";
        })
        .catch(error => {
            console.error("Error:", error);
            alert(error.message);
        });
});


document.getElementById("guardarLista").addEventListener("click", () => {
    const checkboxes = document.querySelectorAll("#libros-tbody input[type='checkbox']:checked");

    const librosSeleccionados = Array.from(checkboxes).map(checkbox => {
        const id = Number(checkbox.getAttribute("data-id"));
        return librosGlobalusuarios.find(libro => libro.id === id);
    });

    const userId = sessionStorage.getItem("userId");

    if (!userId) {
        alert("Usuario no autenticado. Por favor, inicia sesión.");
        return;
    }

    if (librosSeleccionados.length === 0) {
        alert("Por favor, selecciona al menos un libro.");
        return;
    }

    const requests = librosSeleccionados.map(libro => {
        const request = {
            userId: Number(userId),
            bookId: libro.id
        };

        return fetch("/api/reading-list", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(request)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al guardar libro en la lista");
                }
                return response.json();
            });
    });

    Promise.all(requests)
        .then(results => {
            console.log("Todos los libros guardados:", results);
            alert("¡Libros guardados en tu lista!");
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Ocurrió un error al guardar los libros.");
        });
});


