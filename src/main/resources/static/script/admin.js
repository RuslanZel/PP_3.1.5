let userInfo = document.querySelector('#adminInfo')
fetch('http://localhost:8080/api/user')
    .then(res => res.json())
    .then(user => {
        userInfo.innerHTML = `
                                <span class="font-weight-bold">${user.email}</span>
                                with roles:
                                ${user.roles.map(r => ' ' + r.name.replaceAll('ROLE_', ' '))}
                                 `;
    });

// ----- Информация о пользователе -----

function showUserPage() {
    let userTable = document.querySelector('#userTable')
    fetch('http://localhost:8080/api/user')
        .then(res => res.json())
        .then(user => {
            userTable.innerHTML = `
                                <td>${user.id}</td>
                                <td>${user.name}</td>
                                <td>${user.age}</td>
                                <td>${user.email}</td>                                
                                <td> ${user.roles.map(r => ' ' + r.name.replaceAll('ROLE_', ' '))}</td>
                                 `
        })
}

showUserPage();


//------ Таблица users -----

function refreshTableUsers() {
    fetch('http://localhost:8080/api/admin')
        .then(response => response.json())
        .then(result => refreshTable(result))
}


function refreshTable(users) {
    let tBody = ''
    $.each(users, function (key, object) {
        let roles = ''
        $.each(object.roles, function (k, o) {
            roles += o.name + ' '
        })
        tBody += ('<tr>');
        tBody += ('<td>' + object.id + '</td>');
        tBody += ('<td>' + object.name + '</td>');
        tBody += ('<td>' + object.age + '</td>');
        tBody += ('<td>' + object.email + '</td>');
        tBody += ('<td>' + roles.replaceAll('ROLE_', ' ') + '</td>');
        tBody += ('<td><button type="button" onclick="editModal(' + object.id + ')" ' +
            'class="btn btn-primary">Edit</button></td>');
        tBody += ('<td><button type="button" onclick="deleteModal(' + object.id + ')" ' +
            'class="btn btn-danger">Delete</button></td>');
        tBody += ('<tr>');
    });
    $('#usersTable').html(tBody);

}

//----- Новый пользователь -----

function addNewUser() {
    fetch('http://localhost:8080/api/admin', {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: 'POST',

        body: JSON.stringify({
            name: $('#name').val(),
            age: $('#age').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            roles: [
                document.getElementById('role').value
            ]
        })
    }).then(() => {
        $('form input[type="text"], form input[type="password"], form input[type="number"], form textarea')
            .val('')
    })
    // refreshTableUsers();
}


//----- Редактирование пользователя -----

function editModal(id) {
    fetch('http://localhost:8080/api/admin/' + id)
        .then(response => response.json())
        .then(result => writeFields(result))

    function writeFields(user) {
        $('#idEdit').val(user.id);
        $('#nameEdit').val(user.name);
        $('#ageEdit').val(user.age);
        $('#emailEdit').val(user.email);
        $('#passwordEdit').empty();
        $('#roleEdit').val(user.role);
        $('#edit').attr('onclick', 'editUser(' + user.id + ')')
        $('#editModal').modal()
    }
}

function editUser(id) {
    fetch('http://localhost:8080/api/admin/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "PATCH",
        body: JSON.stringify(
            {
                id: document.getElementById('idEdit').value,
                name: document.getElementById('nameEdit').value,
                age: document.getElementById('ageEdit').value,
                email: document.getElementById("emailEdit").value,
                password: document.getElementById('passwordEdit').value,
                roles: [
                    document.getElementById('roleEdit').value
                ]
            })
    }).then(() => {
        $('#editModal').modal()
        // refreshTableUsers()
    })

}


//----- Удаление пользователя -----

function deleteModal(id) {
    fetch('http://localhost:8080/api/admin/' + id)
        .then(response => response.json())
        .then(result => writeFields(result))

    function writeFields(user) {
        $('#idDel').val(user.id);
        $('#nameDel').val(user.name);
        $('#ageDel').val(user.age);
        $('#emailDel').val(user.email);
        $('#roleDel').val(user.role);
        $('#delete').attr('onclick', 'deleteUser(' + user.id + ')')
        $('#deleteModal').modal();
    }
}

function deleteUser(id) {
    fetch('http://localhost:8080/api/admin/' + id, {
        method: 'DELETE'
    }).then(() => {
        $('#deleteModalHtml').modal('hide')

    })
    // refreshTableUsers();
}

refreshTableUsers();