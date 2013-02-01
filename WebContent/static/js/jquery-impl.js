$(document).ready(function() {
	$('.button').button();

	$("ul").sortable({
		connectWith: ".sortable",
		placeholder: "ui-state-highlight",
		receive: function( event, ui ) {
			var tarefaId = $(ui.item).attr('tarefaId');
			var id = $(this).parents('div:first').attr('id');
			var quadro = id.substr(3);

			$.ajax({
				type: 'POST',
				url: 'canvas?acao=alterarQuadro',
				cache: false,
				data: 'tarefaId='+ tarefaId +'&quadro=' + quadro
			});
		}
	}).disableSelection();


	var tarefaIdTemp;
	$("li.item").dblclick(function(){
		tarefaIdTemp = $(this).attr('tarefaId');
		$('#window_excluir_tarefa').dialog('open');

	});

	$('#novoProjetoButton').button().click(function(){$('#window_novo_projeto').dialog('open');});
	$('#novaTarefaButton').button().click(function(){$('#window_nova_tarefa').dialog('open');});
	$('#salvarWindow').button().click(function(){

		$.ajax({
			type: 'POST',
			url: 'canvas?acao=salvarTarefa',
			cache: false,
			data: $('#formWindow').serialize()
		}).done(function(data){
			var titulo = $('#field-titulo').val();
			var cor = $('#field-cor').val();			
			$('ul:first').append('<li class="item ui-corner-all" style="background: #'+ cor +';" tarefaId="'+ data +'">'+ titulo +'</li>');
			$('#window_nova_tarefa').dialog('close');
		});
		return false;
	});

	$('#field-projetoId').change(function() {
		$(this).parents('form:first').submit();
	});

	$('#window_novo_projeto').dialog({
		title:'Novo projeto',
		autoOpen: false,
		width: 450,
		modal: true
	});

	$('#window_nova_tarefa').dialog({
		title:'Nova tarefa',
		autoOpen: false,
		width: 450,
		modal: true
	});

	$('#window_excluir_tarefa').dialog({
		title:'Excluir',
		autoOpen: false,
		width: 450,
		modal: true,
		buttons: {
			'Sim': function() {
				$.ajax({
					type: 'POST',
					url: 'canvas?acao=apagarTarefa',
					cache: false,
					data: 'tarefaId='+ tarefaIdTemp
				});
				$('li[tarefaId="'+ tarefaIdTemp +'"]').hide();
				$('#window_excluir_tarefa').dialog('close');
			}, 
			'Não': function() {
				$('#window_excluir_tarefa').dialog('close');
			}
		}
	});
});