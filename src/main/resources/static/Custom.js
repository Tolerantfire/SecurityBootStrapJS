$(document).ready(function () {

    $(".table .btn-primary").on('click', function (event) {

        event.preventDefault();


        var href = $(this).attr('href');

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

})



