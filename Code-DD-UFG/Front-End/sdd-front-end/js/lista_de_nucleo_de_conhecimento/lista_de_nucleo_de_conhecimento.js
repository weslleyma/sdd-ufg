$(document).ready(function(){
	
	var token = sessionStorage.getItem("session_token");
	console.log("Token: " +token);

	var selectedId;
	
	var table = $("#dataTable-nucleosDeConhecimento").DataTable({
		ajax: {
			url: 'http://private-anon-33b49d585-sddufg.apiary-mock.com/knowledges',
		    type: 'GET',
		    headers: {
	    		"Content-Type": "application/json",
		    	"Session-Token": token
		    },
		    dataType: 'json',
		    data: {
		        
		    },
		    statusCode: {
		    	403: function(response){
		    		console.log(response['status'] +": " +response['message']);
		    	}
		    }
		},
		columns: [
            { "data" : "id" },
            { "data" : "name" }
        ]
	});

	$("#dataTable-nucleosDeConhecimento tbody").delegate("tr", "click", function() {
	  	selectedId = $("td:first", this).text();
	});

	$('#dataTable-nucleosDeConhecimento tbody').on( 'click', 'tr', function () {
        if ( $(this).hasClass('selected') ) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    } );
 
    $('#buttonRemove').click( function () {
        
        if (!selectedId) {
            alert("Selecione um registro!");
            return false;
        }
        
        console.log("Removendo núcleo de conhecimento com id: " +selectedId);

        //Abrindo requisição para remoção de núcleo de conhecimento
        var deleteUrl = 'http://private-e6e9d-sddufg.apiary-mock.com/knowledges/' +selectedId ;
        console.log("Delete URL: " +deleteUrl);

    	$.ajax({
            url: deleteUrl,
            type: 'DELETE',
            headers: {
		    	"Session-Token": token
		    },
            success: function(response){
            	
            }, 
            statusCode: {
            	204: function(response){
            		table.row('.selected').remove().draw( false );
            		alert("Núcleo de conhecimento deletado com sucesso!");
            	},
            	403: function(response){
            		console.log(response['status'] +": " +response['responseJSON']['message']);
            		alert(response['status'] +": " +response['responseJSON']['message']);
            	},
            	404: function(response){
            		console.log(response['status'] +": " +response['responseJSON']['message']);
            		alert(response['status'] +": " +response['responseJSON']['message']);
            	},
            	500: function(response){
            		console.log(response['status'] +": " +response['responseJSON']['message']);
            		alert(response['status'] +": " +response['responseJSON']['message']);
            	}
            }
    	});
    } );
    
    
    $('#buttonEdit').click(function () {

        if (!selectedId) {
            alert("Selecione um registro!");
            return false;
        }
        console.log("Pegar os dados do nucleo com id: " + selectedId);

        var url = 'http://private-e6e9d-sddufg.apiary-mock.com/knowledges/' + selectedId;
        console.log("URL: " + url);

        $.ajax({
            url: url,
            type: 'GET',
            headers: {
                "Session-Token": token
            },
            success: function (response) {
                $('#editPopUp').attr('data-id', selectedId);
                $('#editPopUp').attr('data-name', response.name);
                $('#editPopUp').modal();
            },
            statusCode: {
                403: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                },
                404: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                },
            }
        });
    });
    
    $('#submitAlteracoesModal').click(function () {

        if (!selectedId) {
            alert("Selecione um registro!");
            return false;
        }

        console.log("Editar nucleo com id: " + selectedId);

        $.ajax({
            url: 'http://private-e6e9d-sddufg.apiary-mock.com/knowledges/' + selectedId,
            type: 'PUT',
            dataType: 'json',
            headers: {
                "Session-Token": token,
                "Content-Type": 'application/json'
            },
            data: {
                'id': selectedId,
                'name': $("#name").val(),
            },
            success: function (response) {
                console.log("Núcleo Editado com sucesso");
                $('#editPopUp').modal('toggle');
                $('#finalPopUpEdit').modal();
            },
            statusCode: {
                201: function (response, status, xhr) {
                    console.log("201");
                    console.log(response);
                },
                400: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                },
                403: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                },
                404: function (response) {
                    console.log(response['status'] + ": " + response['responseJSON']['message']);
                    alert(response['status'] + ": " + response['responseJSON']['message']);
                }
            },
            complete: function (xhr) {

            }
        });

    });

    $('#editPopUp').on('show.bs.modal', function (event) {
        $('#id').val($(this).attr('data-id'));
        $('#name').val($(this).attr('data-name'));
       
    });
    
    
});


