package financeiroclube;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/funcionario/**").hasAuthority("FUNCIONARIO")
			.antMatchers("/socio/**").hasAuthority("SOCIO")
			.antMatchers("/admin/**").hasAuthority("ADMIN")
			.antMatchers("/autenticado/**","/conta/cadastro/**").authenticated()
		.and().formLogin()
		  	.loginPage("/entrar")
		  	.failureUrl("/entrar?erro")
		  	.defaultSuccessUrl("/autenticado")
		  	.usernameParameter("username").passwordParameter("password")
		.and().logout()
			.logoutSuccessUrl("/entrar?sair")
			.logoutUrl("/sair")
			.invalidateHttpSession(true)
			.clearAuthentication(true)
			.and().rememberMe()
		  	.tokenRepository(persistentTokenRepository())
		  	.tokenValiditySeconds(120960)
		.and().csrf();
		
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select username,password,ativo from usuarios where username=?")
				.authoritiesByUsernameQuery(
						"select username,autorizacao from usuarios join autorizacoes on id = id_usuario where username=?");
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Bean
	public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
		SavedRequestAwareAuthenticationSuccessHandler auth = new SavedRequestAwareAuthenticationSuccessHandler();
		auth.setTargetUrlParameter("targetUrl");
		return auth;
	}
}
