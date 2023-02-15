$(document).ready(
		//mascara da data de nascimento
		function() {
			$('#dataNascimento').mask('00/00/0000', {
				placeholder : "__/__/____"
			});
			
			//mascara do telefone
			var behavior = function(val) {
				return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000'
						: '(00) 0000-00009';
			}, options = {
				onKeyPress : function(val, e, field, options) {
					field.mask(behavior.apply({}, arguments), options);
				}
			};

			$('#telefone').mask(behavior, options);
			
			//mascara do valor de contribuição
			$(document).ready(function() {
				$("#valorMensal").maskMoney({
					prefix : "R$: ",
					decimal : ",",
					thousands : "."
				});
			});

		});