//mascara do campo telefone do modal de adicionar usuário
			var behavior = function(val) {
				return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000'
						: '(00) 0000-00009';
			}, options = {
				onKeyPress : function(val, e, field, options) {
					field.mask(behavior.apply({}, arguments), options);
				}
			};
			
			$('#telefone_add_user').mask(behavior, options);
			
			//mascara do campo telefone do modal de atualizar usuário
			var behavior = function(val) {
				return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000'
						: '(00) 0000-00009';
			}, options = {
				onKeyPress : function(val, e, field, options) {
					field.mask(behavior.apply({}, arguments), options);
				}
			};
			
			$('#telefone_edit').mask(behavior, options);