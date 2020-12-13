$(document).ready(function () {

    getTable();

});

function getTable() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/api/user',
        contentType: 'application/json;',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        dataType: 'JSON',

        success: function (data) {



            let htmlTable = "";


                let json = JSON.stringify(data);
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
                        <td id = "tableId" >${data.id}</td>
                        <td id = "tableName">${data.name}</td>
                        <td id = "tableLastname" >${data.lastname}</td>
                        <td id = "tableAge" >${data.age}</td>
                        <td id = "tableEmail" >${data.email}</td>
                        <td id = "tableUsername" >${data.username}</td>
                        <td id = "tableRole" >${str}</td>
                        </tr>`;

            $("#output #list").remove();
            $("#output thead").after(htmlTable);
        }
    })
}