jQuery.validator.addMethod("dddValido", function(valor){
			var ddd = valor.substring(1,3);
			if (ddd == "11" || ddd == "12" || ddd == "13" || ddd == "14" || ddd == "15" || ddd == "16"
				|| ddd == "17" || ddd == "18" || ddd == "19" || ddd == "21" || ddd == "22" || ddd == "24"
				|| ddd == "27" || ddd == "28" || ddd == "31" || ddd == "32" || ddd == "33" || ddd == "34"
				|| ddd == "35" || ddd == "37" || ddd == "38" || ddd == "41" || ddd == "42" || ddd == "43"
				|| ddd == "44" || ddd == "45" || ddd == "46" || ddd == "47" || ddd == "48" || ddd == "49"
				|| ddd == "51" || ddd == "53" || ddd == "54" || ddd == "55" || ddd == "61" || ddd == "62"
				|| ddd == "63" || ddd == "64" || ddd == "65" || ddd == "66" || ddd == "67" || ddd == "68"
				|| ddd == "69" || ddd == "71" || ddd == "73" || ddd == "74" || ddd == "75" || ddd == "77"
				|| ddd == "79" || ddd == "81" || ddd == "82" || ddd == "83" || ddd == "84" || ddd == "85"
				|| ddd == "86" || ddd == "87" || ddd == "88" || ddd == "89" || ddd == "91" || ddd == "92"
				|| ddd == "93" || ddd == "94" || ddd == "95" || ddd == "96" || ddd == "97" || ddd == "98"
				|| ddd == "99"){
				return true
			} else {
				return false
			  }
		}, "Por favor, forneça um DDD válido.")
		
		jQuery.validator.addMethod("fixoValido", function(valor){
			var fixo = valor.substring(5,14);
			if (fixo != "0000-0000" && fixo != "1111-1111" && fixo != "2222-2222" && fixo != "3333-3333"
				&& fixo != "4444-4444" && fixo != "5555-5555" && fixo != "6666-6666" && fixo != "7777-7777"
				&& fixo != "8888-8888" && fixo != "9999-9999"){ 
				return true
			} else {
				return false
			  }
		}, "Por favor, forneça um número de telefone válido.")
    
		jQuery.validator.addMethod("celularValido", function(valor){
			var celular = valor.substring(5,15);
			if (celular != "00000-0000" && celular != "11111-1111" && celular != "22222-2222" && 
				celular != "33333-3333" && celular != "44444-4444" && celular != "55555-5555" &&
				celular != "66666-6666" && celular != "77777-7777" && celular != "88888-8888" &&
				celular != "99999-9999"){ 
				return true
			} else {
				return false
			  }
		}, "Por favor, forneça um número de celular válido.")
		
		jQuery.validator.addMethod("tamanhoTelefoneValido", function(valor){
			if (valor.length == 14 || valor.length == 15){ 
				return true
			} else {
				return false
			  }
		}, "Por favor, forneça um número válido.")
		
		jQuery.validator.addMethod("nomeValido", function(valor){
			const regex = /[0-9]/;
			var resultado = regex.test(valor);
			if (resultado == true){ 
				return false
			} else {
				return true
			  }
		}, "Por favor, forneça um nome sem conter números.")
		
    	$(document).ready(function() {
    		$('#formFicha').validate({
    			rules:{
    				nome: {
    					required: true,
    					maxlength: 100,
    					minlength: 5,
    					minWords: 2,
    					nomeValido: true
    				},
    				dataNascimento: {
    					required: true,
    					dateITA: true
    				},
    				paroquia: {
    					required: true
    				},
    				endereco: {
    					required: true,
    					maxlength: 200,
    					minlength: 10
    				},
    				telefone: {
    					required: true,
    					dddValido: true,
    					fixoValido: true,
    					celularValido: true,
    					tamanhoTelefoneValido: true
    				},
    				valorMensal: {
    					required: true,
    					maxlength: 30,
    					minlength: 8
    				},
    				formaPagamento: {
    					required: true
    				}
    			}
    		})
    	})

		$(document).ready(function() {
    		$('#form_add').validate({
    			rules:{
    				nomecompleto: {
    					required: true,
    					maxlength: 100,
    					minlength: 5,
    					minWords: 2,
    					nomeValido: true
    				},
    				telefone: {
    					required: true,
    					dddValido: true,
    					fixoValido: true,
    					celularValido: true,
    					tamanhoTelefoneValido: true
    				},
					email: {
						required: true,
						email: true
					},
					login: {
						required: true,
					},
					senha: {
						required: true,
					}
    			}
    		})
    	})

	
	
	$(document).ready(function() {
    		$('#form_edit').validate({
    			rules:{
    				nomecompleto: {
    					required: true,
    					maxlength: 100,
    					minlength: 5,
    					minWords: 2,
    					nomeValido: true
    				},
    				telefone: {
    					required: true,
    					dddValido: true,
    					fixoValido: true,
    					celularValido: true,
    					tamanhoTelefoneValido: true
    				},
					email: {
						required: true,
						email: true
					},
					login: {
						required: true,
					},
					senha: {
						required: true,
					}
    			}
    		})
    	})
