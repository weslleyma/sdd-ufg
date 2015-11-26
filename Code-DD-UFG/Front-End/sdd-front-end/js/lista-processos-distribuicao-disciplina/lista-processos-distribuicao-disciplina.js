$(document).ready(function () {

    var token = sessionStorage.getItem("session_token");
    console.log("Token: " + token);

    var selectedId;
    
    $('#dataTable-listaProcessoDistribuicao tfoot th').each( function () {
        $(this).html( '<input type="text" placeholder="Buscar" />' );
    });
    
    var table = $("#dataTable-listaProcessoDistribuicao").DataTable({
        ajax: {
            url: 'http://private-anon-33b49d585-sddufg.apiary-mock.com/processes',
            type: 'GET',
            headers: {
                "Session-Token": token
            },
            dataType: 'json',
            data: {
            },
            statusCode: {
                403: function (response) {
                    console.log(response['status'] + ": " + response['message']);
                }
            }
        },
        columns: [
            {"data": "id"},
            {"data": "semester"},
            {"data": "clazz_registry_date"},
            {"data": "teacher_intent_date"},
            {"data": "first_resolution_date"},
            {"data": "substitute_distribution_date"},
            {"data": "finish_date"},
        ],
        initComplete: function ()
        {
            var r = $('#dataTable-listaProcessoDistribuicao tfoot tr');
            r.find('th').each(function () {
                $(this).css('padding', 8);
            });
            $('#dataTable-listaProcessoDistribuicao thead').append(r);
            $('#search_0').css('text-align', 'center');
        },
    });

    table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );

    $("#dataTable-listaProcessoDistribuicao tbody").delegate("tr", "click", function () {
        selectedId = $("td:first", this).text();
    });

    $('#dataTable-listaProcessoDistribuicao tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

    $('#buttonRemove').click(function () {

        if (!selectedId) {
            alert("Selecione um registro!");
            return false;
        }

        console.log("Removendo processo de distribuicao com id: " + selectedId);

        //Abrindo requisição para remoção de núcleo de conhecimento
        var deleteUrl = 'http://private-e6e9d-sddufg.apiary-mock.com/processes/' + selectedId;
        console.log("Delete URL: " + deleteUrl);

        $.ajax({
            url: deleteUrl,
            type: 'DELETE',
            headers: {
                "Session-Token": token
            },
            success: function (response) {

            },
            statusCode: {
                204: function (response) {
                    table.row('.selected').remove().draw(false);
                    alert("Processo de Distribuição de Disciplinas deletado com sucesso!");
                },
                403: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                },
                404: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                },
                500: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                }
            }
        });
    });


    $('#buttonEdit').click(function () {

        if (!selectedId) {
            alert("Selecione um registro!");
            return false;
        }

        console.log("Editar processo de distribuição com id: " + selectedId);

        //Abrindo requisição para remoção de núcleo de conhecimento
        var editURL = 'http://private-e6e9d-sddufg.apiary-mock.com/processes/' + selectedId;
        console.log("Edit URL: " + editURL);

        window.location.href = '../sdd-pages/criação-edição-de-processo-de-distribuição-de-disciplina.html';

    });


});


