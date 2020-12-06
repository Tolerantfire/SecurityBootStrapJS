$(document).ready(function () {
    $('.table .btn-danger').on('click', function (event) {

        event.preventDefault();


        var href = $(this).attr('href');

        $.get(href, function (User, status) {
            $('#idDelete').val(User.id);
            $('#nameDelete').val(User.name);
            $('#lastnameDelete').val(User.lastname);
            $('#ageDelete').val(User.age);
            $('#emailDelete').val(User.email);
            $('#usernameDelete').val(User.username);
            $('#passwordDelete').val(User.password);

        });

        $('#deleteModal').modal();

    });

})