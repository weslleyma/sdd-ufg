$(document).ready(function() {

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

});