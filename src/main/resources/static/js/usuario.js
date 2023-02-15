$(document).ready(function () {
	
	// operação de adicionar usuário
	$("#form_add").on('submit', function (event) {
		      event.preventDefault();
		      var dados = $("#form_add").serialize();
		      $.post("/usuario/adicionar", dados, function (retorna) {
		    	  
		        if (retorna == 1) {
		        	$("#alert_msg_sucesso").html('<h5 style="color: black; font-weight: bold;">Usuário Adicionado com sucesso!</h5>');
		            $("#alert_modal_sucesso").modal("show");
		          	$("#form_modal").modal("hide");
		          	setTimeout(function() {
			            $("#alert_modal_sucesso").modal("hide");
			        }, 2000);
		          	
		          	$(function() {
		          	    setTimeout(function(){ location.reload(); }, 2500);
		          	});
		        } else {
		            $("#alert_msg_erro").html('<h5 style="color: black; font-weight: bold;"> Erro, usuário já existe!</h5>');
		            $("#alert_modal_erro").modal("show");
		            $("#form_modal").modal("hide");
		            setTimeout(function() {
			            $("#alert_modal_erro").modal("hide");
			        }, 2000);
		          	
		          	$(function() {
		          	    setTimeout(function(){ location.reload(); }, 2000);
		          	});
		          } 
		      });
		    });

	// evita de o form de adicionar usuário seja submetido se algo não estiver validado/preenchido
	$('#btnSalvar').on('click', function (e) {
	        var button = $('#btnSalvar');
	
	        button.prop('disabled', true);
	
	        var valid = $("#form_add").valid();
	        if (!valid) {
	
	            e.preventDefault();
	            button.prop('disabled', false);
	        } else {
	            $('#form_add').submit();
	        }
	    });

	// evita de o form de atualizar usuário seja submetido se algo não estiver validado/preenchido	
	$('#btnAtualizar').on('click', function (e) {
	        var button = $('#btnAtualizar');
	        button.prop('disabled', true);
	        var valid = $("#form_edit").valid();
	        if (!valid) {
	
	            e.preventDefault();
	            button.prop('disabled', false);
	        } else {
	            $('#form_edit').submit();
	        }
	    });

	$('#escolha').change(function () {
	
		var es = document.getElementById('escolha');

		esValor = es.options[es.selectedIndex].value;
	
		if (esValor == "S") {
			$("#senha_edit").attr("disabled", false);
		    }
		else {
			$("#senha_edit").attr("disabled", true);
		    $("#senha_edit").val("");
		     }
		});

	
});