/*PRESENTACIÓNLECTOR_LIBROS*/
/*PRESENTACIÓNLECTOR_LIBROS*/
/*PRESENTACIÓNLECTOR_LIBROS*/
/*PRESENTACIÓNLECTOR_LIBROS*/
let librosGlobal = [];

function cargarLibros(libros) {
    const tbody = document.getElementById("libros-tbody");
    tbody.innerHTML = "";

    libros.forEach(book => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td><input type="checkbox" data-id="${book.id}"></td>
            <td>${book.id}</td>
            <td><img src="#" alt="${book.title}" width="50"></td>
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
            librosGlobal = books;
            cargarLibros(books);
        })
        .catch(error => console.error("Error al obtener los libros:", error));
}

document.addEventListener("DOMContentLoaded", function () {
    obtenerLibros();

    document.getElementById("mostrarTodos").addEventListener("click", () => {
        cargarLibros(librosGlobal);
    });

    document.getElementById("mostrarDisponibles").addEventListener("click", () => {
        const disponibles = librosGlobal.filter(libro => libro.available);
        cargarLibros(disponibles);
    });
});

/*PRESENTACIÓNLECTOR_LIBROS*/
/*PRESENTACIÓNLECTOR_LIBROS*/
/*PRESENTACIÓNLECTOR_LIBROS*/

/*ACCIONES DE BOTONES*/
/*ACCIONES DE BOTONES*/
/*ACCIONES DE BOTONES*/
document.getElementById("guardarLista").addEventListener("click", () => {
    const checkboxes = document.querySelectorAll("#libros-tbody input[type='checkbox']:checked");

    const librosSeleccionados = Array.from(checkboxes).map(checkbox => {
        const id = Number(checkbox.getAttribute("data-id"));
        return librosGlobal.find(libro => libro.id === id);
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


/*ACCIONES DE BOTONES*/
/*ACCIONES DE BOTONES*/
/*ACCIONES DE BOTONES*/

/*PRESENTACIÓNLECTOR_MILISTA*/
/*PRESENTACIÓNLECTOR_MILISTA*/
/*PRESENTACIÓNLECTOR_MILISTA*/



/*PRESENTACIÓNLECTOR_MILISTA*/
/*PRESENTACIÓNLECTOR_MILISTA*/
/*PRESENTACIÓNLECTOR_MILISTA*/


