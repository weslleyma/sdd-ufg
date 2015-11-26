$(document).ready(function() {

	var session_token = sessionStorage.getItem('session_token');

	$('#processosSeletivos').DataTable({
		ajax: {
			url: 'http://private-46f19-sddufg.apiary-mock.com/processes',
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