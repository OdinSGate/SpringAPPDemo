// Сделать так, чтобы при обновлении страницы выводилось только содержимое активной вкладки
document.addEventListener('DOMContentLoaded', function () {
    var tabs = document.querySelectorAll('.nav-link');

    tabs.forEach(function (tab) {
        tab.addEventListener('click', function (e) {
            e.preventDefault();
            var target = this.getAttribute('href');
            var content = document.querySelector(target);

            tabs.forEach(function (tab) {
                tab.classList.remove('active');
            });

            this.classList.add('active');

            var tabContents = content.parentNode.querySelectorAll('.tab-pane');
            tabContents.forEach(function (tabContent) {
                tabContent.classList.remove('active');
            });

            content.classList.add('active');
        });
    });
});

// Кнопка Logout
document.getElementById("logout-button").addEventListener("click", function () {
    const urlLogout = "/logout";
    const options = {
        method: "POST",
        credentials: 'same-origin'
    };
    fetch(urlLogout, options)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Ошибка HTTP: ${response.status}`);
            }
            return "/login";
        })
        .then(data => {
            window.location.href = "/login";
        })
        .catch(error => {
            console.error("Произошла ошибка при выполнении logout:", error);
        });
});

// Отображение залогиневшегося пользователя и доступ к левому меню в зависимости от роли
async function fetchData() {
    try {
        const response = await fetch('/currentUser');
        const user = await response.json();
        const userEmail = user.email;
        const userRole = JSON.stringify(user.roles.map(role => role.role).join(', '));
        document.getElementById('userEmail').textContent = userEmail;
        document.getElementById('userRole').textContent = userRole;

        if (userRole.includes('ROLE_ADMIN')) {
            document.getElementById('admin-menu').style.display = 'block';
            document.getElementById('user-menu').style.display = 'block';
        } else {
            document.getElementById('admin-menu').style.display = 'none';
        }

    } catch (error) {
        console.error('Ошибка при загрузке данных:', error);
    }
}

fetchData();

// Запрос на JSON пользователей
document.addEventListener("DOMContentLoaded", function () {
    fetch("/allUsers")
        .then(response => response.json())
        .then(data => {
            displayUsers(data);
        })
        .catch(error => {
            console.error("Ошибка при получении данных о пользователях:", error);
        });
});

// Таблица всех пользователей
function displayUsers(users) {
    const tbody = document.getElementById("users-table");
    tbody.innerHTML = "";
    users.forEach(user => {
        const row = document.createElement("tr");
        const cell1 = document.createElement("td");
        const cell2 = document.createElement("td");
        const cell3 = document.createElement("td");
        const cell4 = document.createElement("td");
        const cell5 = document.createElement("td");
        const cell6 = document.createElement("td");
        const cell7 = document.createElement("td");

        cell1.textContent = user.id;
        cell2.textContent = user.firstName;
        cell3.textContent = user.lastName;
        cell4.textContent = user.email;
        cell5.textContent = JSON.stringify(user.roles.map(role => role.role).join(', '));

        const editButton = document.createElement("button");
        editButton.textContent = "Edit";
        editButton.addEventListener("click", function () {
            openModal(user.id);
        });
        cell6.appendChild(editButton);

        const deleteButton = document.createElement("button");
        deleteButton.textContent = "Delete";
        deleteButton.addEventListener("click", function () {
            openModalDelete(user.id);
        });
        cell7.appendChild(deleteButton);

        row.appendChild(cell1);
        row.appendChild(cell2);
        row.appendChild(cell3);
        row.appendChild(cell4);
        row.appendChild(cell5);
        row.appendChild(cell6);
        row.appendChild(cell7);

        tbody.appendChild(row);
    })
}

function openModal() {
    document.getElementById("modal").style.display = "block";
}

function closeModal() {
    document.getElementById("modal").style.display = "none";
}

function saveChanges() {
    closeModal();
}

function openModalDelete() {
    document.getElementById("modal_delete").style.display = "block";
}

function closeModalDelete() {
    document.getElementById("modal_delete").style.display = "none";
}

function saveChangesDelete() {
    closeModal();
}

function switchTab(event, tabId) {
    event.preventDefault();

    var tabs = document.querySelectorAll('.nav-link');
    var tabContent = document.querySelectorAll('.tab-pane');

    tabs.forEach(function (tab) {
        tab.classList.remove('active');
    });
    tabContent.forEach(function (content) {
        content.classList.remove('active');
    });

    event.target.classList.add('active');
    document.getElementById(tabId).classList.add('active');
}