$(document).ready(function() {

	var session_token = sessionStorage.getItem('session_token');

	$('#btnInscrever').click(function(e) {
		var disciplinaSelecionada = $('select[name="disciplina"]').val();
		if (disciplinaSelecionada == '') {
			e.preventDefault();
			alert("Selecione uma disciplina para se inscrever");
		} else {
			var confirmarInscricao = confirm("Voce deseja se inscrever na disciplina " + disciplinaSelecionada + "?");
			if (confirmarInscricao == true) {
				e.preventDefault();
			    $('#resultadoInscricao').html('<p class="alert alert-success">Voce se inscreveu na disciplina ' + disciplinaSelecionada + '</p>');
			} else {
				e.preventDefault();
			}
		}
	});

	$('#disciplina').on('change', function() {
		$('#processosDistribuicao').dataTable().fnDestroy();
		$('#processosDistribuicao').DataTable({
			ajax: {
	            url: 'http://45.55.147.9:8080/processes',
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
	            { data: "semester" },
	            { data: "clazz_registry_date" },
	            { data: "teacher_intent_date" },
	            { data: "first_resolution_date" },
	            { data: "substitute_distribution_date" },
				{ data: "finish_date" }
	        ]
		});
	});

});