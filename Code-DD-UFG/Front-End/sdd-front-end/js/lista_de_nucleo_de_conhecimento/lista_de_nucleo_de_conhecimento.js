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
    
    
    $('#buttonEdit').click( function () {
        
        if (!selectedId) {
            alert("Selecione um registro!");
            return false;
        }
        
        console.log("Editar núcleo de conhecimento com id: " +selectedId);

        //Abrindo requisição para remoção de núcleo de conhecimento
        var editURL = 'http://private-e6e9d-sddufg.apiary-mock.com/knowledges/' +selectedId ;
        console.log("Edit URL: " + editURL);
        
        window.location.href='../sdd-pages/cadastro-edicao-nucleo-conhecimento.html';

    } );
    
    
});


