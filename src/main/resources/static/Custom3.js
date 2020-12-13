

$(document).ready(function () {
      getTable();

});

$("#addFormBtn").click(function (event) {
    event.preventDefault()
   addForm();
    $('#nameAdd').val('');
    $('#lastnameAdd').val('');
    $('#ageAdd').val('');
    $('#emailAdd').val('');
    $('#usernameAdd').val('');
    $('#passwordAdd').val('');
});
$("#resetTable").click(function () {
    getTable();
});

//addForm
function addForm() {
    let roles2;
    if (document.getElementById("optionAddUser").selected & !document.getElementById("optionAddAdmin").selected){
        roles2 = [{"id":2,"name":"ROLE_USER","authority":"ROLE_USER"}]
    }
    if (document.getElementById("optionAddAdmin").selected & !document.getElementById("optionAddUser").selected){
        roles2 = [{"id":1,"name":"ROLE_ADMIN","authority":"ROLE_ADMIN"}]
    }
    if (document.getElementById("optionAddAdmin").selected & document.getElementById("optionAddUser").selected){
        roles2 = [{"id":1,"name":"ROLE_ADMIN","authority":"ROLE_ADMIN"},{"id":2,"name":"ROLE_USER","authority":"ROLE_USER"}]
    }

    let user1 = {
        'name': $("#nameAdd").val(),
        'lastname': $(" #lastnameAdd").val(),
        'age': $("#ageAdd").val(),
        'email': $(" #emailAdd").val(),
        'username': $(" #usernameAdd").val(),
        'password': $("#passwordAdd").val(),
        'roles': roles2,
        //  'authorities':[{"authority":"ROLE_ADMIN"},{"authority":"ROLE_USER"}],
        'accountNonExpired':true,
        'credentialsNonExpired':true,
        'accountNonLocked':true,
    //    'shortRoles':["ADMIN","USER"],
        'enabled':true
    };

    $.ajax({

        type: 'POST',
        url: "http://localhost:8080/api/user",
        contentType: 'application/json;',
        data: JSON.stringify(user1),
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        async: true,
        dataType: 'JSON',
        success: function () {
            getTable();
        }

    });
}

//Получаем editModal
    $(document).on('click', '#editUserBtn', function (event) {

        event.preventDefault();

        let href = $(this).attr('href');


        $.get(href, function (User, status) {
            $('.modal #id').val(User.id);
            $('.modal #name').val(User.name);
            $('.modal #lastname').val(User.lastname);
            $('.modal #age').val(User.age);
            $('.modal #email').val(User.email);
            $('.modal #username').val(User.username);
            $('.modal #password').val(User.password);

        });

        $('#editModal').modal();

    });


//updateForm
$("#editFormBtn").click(function () {
     updateForm();
     getTable();
});

 function updateForm() {
     let roles;

     if (document.getElementById("optionEditUser").selected & !document.getElementById("optionEditAdmin").selected){
         roles = [{"id":2,"name":"ROLE_USER","authority":"ROLE_USER"}]

     }
     if (document.getElementById("optionEditAdmin").selected & !document.getElementById("optionEditUser").selected){
         roles = [{"id":1,"name":"ROLE_ADMIN","authority":"ROLE_ADMIN"}]

     }
     if (document.getElementById("optionEditAdmin").selected & document.getElementById("optionEditUser").selected){
         roles = [{"id":1,"name":"ROLE_ADMIN","authority":"ROLE_ADMIN"},{"id":2,"name":"ROLE_USER","authority":"ROLE_USER"}]

     }

    let user1 = {
        'id': $(".modal #id").val(),
        'name': $(".modal #name").val(),
        'lastname': $(".modal #lastname").val(),
        'age': $(".modal #age").val(),
        'email': $(".modal #email").val(),
        'username': $(".modal #username").val(),
        'password': $(".modal #password").val(),
        'roles': roles,
      //  'authorities':[{"authority":"ROLE_ADMIN"},{"authority":"ROLE_USER"}],
        'accountNonExpired':true,
        'credentialsNonExpired':true,
        'accountNonLocked':true,
       // 'shortRoles':shortRoles,
        'enabled':true
    };

      $.ajax({

        type: 'PUT',
        url: "http://localhost:8080/api/user",
        contentType: 'application/json;',
        data: JSON.stringify(user1),
        headers: {},
        success: function () {
            getTable();
        }
    });
}

//Получаем deleteModal
$(document).on('click', '#deleteUserBtn', function (event) {

    event.preventDefault();

    let href = $(this).attr('href');


    $.get(href, function (User, status) {
        $('.modal #idDelete').val(User.id);
        $('.modal #nameDelete').val(User.name);
        $('.modal #lastnameDelete').val(User.lastname);
        $('.modal #ageDelete').val(User.age);
        $('.modal #emailDelete').val(User.email);
        $('.modal #usernameDelete').val(User.username);
        $('.modal #passwordDelete').val(User.password);

    });

    $('#deleteModal').modal();

});

//deleteForm
$(document).on('click', '#deleteFormBtn', function () {
   let deleteUserId = $('.modal #idDelete').val();

    deleteUser(deleteUserId);
});

function deleteUser(id) {

    $.ajax({
        type: 'DELETE',
        crossDomain: true,
        url: "http://localhost:8080/api/user/" + id,

        contentType: 'application/json;',
        data: JSON.stringify(id),
        headers:{},

        success: function () {
            getTable();
        }
    });
}


function getTable() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/users',
        contentType: 'application/json;',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        dataType: 'JSON',

        success: function (data) {
                        let htmlTable = "";
            for (let i = 0; i < data.length; i++) {
                let json = JSON.stringify(data[i]);
                let str;
                if (json.includes("ADMIN")&json.includes("USER")){
                    str ="ADMIN, USER";
                }
                if (json.includes("USER")&!json.includes("ADMIN")){
                    str ="USER";
            }
                if (!json.includes("USER")&json.includes("ADMIN")){
                    str ="ADMIN";
                }

                htmlTable += `<tr id = "list" valign="center">
                        <td id = "tableId" >${data[i].id}</td>
                        <td id = "tableName" >${data[i].name}</td>
                        <td id = "tableLastname" >${data[i].lastname}</td>
                        <td id = "tableAge" >${data[i].age}</td>
                        <td id = "tableEmail" >${data[i].email}</td>
                        <td id = "tableUsername" >${data[i].username}</td>
                        <td id = "tableRole" >${str}</td>
                        <td align="center">
                            <a href="/admin/getOne/?id=${data[i].id}" id="editUserBtn"  class="btn btn-primary">Edit</a>                            
                        </td>
                        <td align="center">
                            <a href="/admin/getOne/?id=${data[i].id}" id="deleteUserBtn" class="btn btn-danger" >Delete</a>
                        </td>
                        </tr>`;
            }
            $("#output #list").remove();
            $("#output thead").after(htmlTable);
        }
    })
}




