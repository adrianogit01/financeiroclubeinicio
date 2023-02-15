$(document).ready(function () {
	
	//evento que possibilita o exibir e manipular o modal de deletar ficha
    $('#id_da_tabela #botao_Do_Deletar').on('click', function (evento) {
        evento.preventDefault();
        var href = $(this).attr('href');
        $('#id_Do_Modal_Do_Deletar #botao_De_Deletar_Do_Modal').attr('href', href);
        $('#id_Do_Modal_Do_Deletar').modal({backdrop: 'static', keyboard: false});
    });
    
  //ação de fechar a mensagem de sucesso depois de 2 segundos
	setTimeout(function() {
        $("#alertaRemovido").alert('close');
    }, 2000);


	//evitar que com botão (Esc) feche o modal e evitar que clicar fora do modal o feche
	$('#botaoAdicionar').on('click', function (evento) {
        evento.preventDefault();
        $('#form_modal').modal({backdrop: 'static', keyboard: false});
    });
	
});