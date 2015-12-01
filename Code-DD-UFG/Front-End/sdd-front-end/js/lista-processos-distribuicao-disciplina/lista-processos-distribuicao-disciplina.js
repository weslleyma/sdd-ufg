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
        console.log("Pegar os dados de distribuição com id: " + selectedId);

        var url = 'http://private-e6e9d-sddufg.apiary-mock.com/processes/' + selectedId;
        console.log("URL: " + url);

        $.ajax({
            url: url,
            type: 'GET',
            headers: {
                "Session-Token": token
            },
            success: function (response) {
                $('#editPopUp').attr('data-id', selectedId);
                $('#editPopUp').attr('data-semester', response.semester);
                $('#editPopUp').attr('data-clazz_registry_date', response.clazz_registry_date);
                $('#editPopUp').attr('data-teacher_intent_date', response.teacher_intent_date);
                $('#editPopUp').attr('data-first_resolution_date', response.first_resolution_date);
                $('#editPopUp').attr('data-substitute_distribution_date', response.substitute_distribution_date);
                $('#editPopUp').attr('data-finish_date', response.finish_date);
                $('#editPopUp').attr('data-clazzes', response.clazzes);
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

        console.log("Editar processo de distribuição com id: " + selectedId);

        $.ajax({
            url: 'http://private-e6e9d-sddufg.apiary-mock.com/processes/' + selectedId,
            type: 'PUT',
            dataType: 'json',
            headers: {
                "Session-Token": token,
                "Content-Type": 'application/json'
            },
            data: {
                'id': selectedId,
                'semester': $("#semester").val(),
                'clazz_registry_date': $('#clazz_registry_date').val(),
                'teacher_intent_date' : $('#teacher_intent_date').val(),
                'first_resolution_date': $('#first_resolution_date').val(),
                'substitute_distribution_date': $('#substitute_distribution_date').val(),
                'finish_date': $('#finish_date').val(),
            },
            success: function (response) {
                console.log("Processo Editado com sucesso");
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
        $('#semester').val($(this).attr('data-semester'));
        $('#clazz_registry_date').val($(this).attr('data-clazz_registry_date'));
        $('#teacher_intent_date').val($(this).attr('data-teacher_intent_date'));
        $('#first_resolution_date').val($(this).attr('data-first_resolution_date'));
        $('#substitute_distribution_date').val($(this).attr('data-substitute_distribution_date'));
        $('#finish_date').val($(this).attr('data-finish_date'));
        $('#clazzes').val($(this).attr('data-clazzes'));
    });
	
	$('#buttonViewClazzes').click(function () {

        if (!selectedId) {
            alert("Selecione um registro!");
            return false;
        }
        console.log("Pegar os dados de distribuição com id: " + selectedId);

        var url = 'http://private-e6e9d-sddufg.apiary-mock.com/processes/' + selectedId;
        console.log("URL: " + url);

        $.ajax({
            url: url,
            type: 'GET',
            headers: {
                "Session-Token": token
            },
            success: function (response) {
				
				var clazzes = response.clazzes;
				$('#viewClazzesPopUp').attr('data-length', clazzes.length);
				
				for(var i = 0; i < clazzes.length; i++) {
					
					var clazz_id = clazzes[i].id;
					var grade_id = clazzes[i].grade_id;
					
					$('#viewClazzesPopUp').attr('data-workload-' + i, clazzes[i].workload);
					$('#viewClazzesPopUp').attr('data-status-' + i, clazzes[i].status);
					
					var gradeURL = 'http://private-e6e9d-sddufg.apiary-mock.com/grades/' + grade_id;
					
					$.ajax({
						url: gradeURL,
						type: 'GET',
						async: false,
						headers: {
							"Session-Token": token
						},
						success: function (response) {
							$('#viewClazzesPopUp').attr('data-grade_name-' + i, response.name);
							$('#viewClazzesPopUp').attr('data-course_name-' + i, response.course.name);
							$('#viewClazzesPopUp').attr('data-knowledge_name-' + i, response.knowledge.name);
						},
						statusCode: {
							403: function (response) {
								console.log(response['status'] + ": " + response['responseJSON']['message']);
								alert(response['status'] + ": " + response['responseJSON']['message']);
								return false;
							},
							404: function (response) {
								console.log(response['status'] + ": " + response['responseJSON']['message']);
								alert(response['status'] + ": " + response['responseJSON']['message']);
								return false;
							},
						}
					});
				}
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
            },
			complete: function(xhr) {
				$('#viewClazzesPopUp').modal();
			}
        });
    });
	
	$('#viewClazzesPopUp').on('show.bs.modal', function (event) {
		
		var length = $(this).attr('data-length');
		
		$('#viewClazzesPopUp').find('.modal-body').first().empty();
		$('#viewClazzesPopUp').find('.modal-body').first().append('Lista de turmas para esse Processo Seletivo:<br><br>')
		
        for (var i = 0; i < length; i++) {
			$('#viewClazzesPopUp').find('.modal-body').first().append(
									'<div><label>Turma ' + (i + 1) + ':</label></div>' +
									'<div class="form-group"> ' + 
                                        '<label>Disciplina</label>' +
                                        '<input type="text" class="form-control" '+
										'id="grade_name-' + i + '" name="grade_name-' + i + '" disabled ' +
										'value="' + $(this).attr('data-grade_name-' + i) + '" />' +
                                   ' </div>' +
								   '<div class="form-group"> ' +
                                        '<label>Curso</label>' +
                                        '<input type="text" class="form-control" '+
										'id="course_name-' + i + '" name="course_name-' + i + '" disabled ' +
										'value="' + $(this).attr('data-course_name-' + i) + '" />' +
                                   ' </div>' +
								   '<div class="form-group"> ' +
                                        '<label>Knowledge</label>' +
                                        '<input type="text" class="form-control" '+
										'id="knowledge_name-' + i + '" name="knowledge_name-' + i + '" disabled ' +
										'value="' + $(this).attr('data-knowledge_name-' + i) + '" />' +
                                   ' </div>' +
								   '<div class="form-group"> ' +
                                        '<label>Carga Horária</label>' +
                                        '<input type="text" class="form-control" '+
										'id="workload-' + i + '" name="workload-' + i + '" disabled ' +
										'value="' + $(this).attr('data-workload-' + i) + '" />' +
                                   ' </div>' +
								   '<div class="form-group"> ' +
                                        '<label>Status</label>' +
                                        '<input type="text" class="form-control" '+
										'id="status-' + i + '" name="status-' + i + '" disabled ' +
										'value="' + $(this).attr('data-status-' + i) + '" />' +
                                   ' </div>' +
								   '<hr>');
		}
        
    });

});


