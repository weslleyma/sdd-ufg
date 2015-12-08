$(document).ready(function() {

	var session_token = sessionStorage.getItem('session_token');

	$('#dataIngresso').datepicker({
	    format: "dd/mm/yyyy",
	    language: "pt-BR"
	});

	$('#quadroHorarios').DataTable({
		ajax: {
            url: '/backend/grades',
			type: 'GET',
			dataType: 'JSON',
			headers: {
				'Session-Token': session_token
			},
			statusCode: {
		      403: function(response){
		       console.log(response['status'] + ": " + response['message']);
		      }
		    }
        },
        columns: [
            { data: "id" },
            { data: "name" },
            { data: "course_id" },
            { data: "knowledge_id" }
        ]
	});

	$('#nucleosConhecimento').DataTable({
		ajax: {
            url: '/backend/knowledges',
			type: 'GET',
			dataType: 'JSON',
			headers: {
				'Session-Token': session_token
			},
			statusCode: {
		      403: function(response){
		       console.log(response['status'] + ": " + response['message']);
		      }
		    }
        },
        columns: [
            { data: "id" },
            { data: "name" }
        ]
	});

	$('#btnEditarInformacoes').click(function() {
		$('#nome').prop('disabled', false);
		$('#matricula').prop('disabled', false);
		$('#dataIngresso').prop('disabled', false);
		$('#formacao').prop('disabled', false);
		$('#nucleoAcademico').prop('disabled', false);
		$('#regime').prop('disabled', false);
		$('#descricao').prop('disabled', false);
		$('#observacoes').prop('disabled', false);
		$('#btnEnviarInformacoes').css('display', 'block');
	});

});