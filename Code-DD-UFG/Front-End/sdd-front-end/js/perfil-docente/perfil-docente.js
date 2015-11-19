$(document).ready(function() {

	$('#dataIngresso').datepicker({
	    format: "dd/mm/yyyy",
	    language: "pt-BR"
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