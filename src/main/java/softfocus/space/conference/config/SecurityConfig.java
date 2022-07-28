package softfocus.space.conference.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import softfocus.space.conference.module.member.MemberRepository;
import softfocus.space.conference.module.member.OAuthMemberService;
import softfocus.space.conference.module.member.handler.LoginSuccessHandler;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuthMemberService oAuthMemberService;

    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                .antMatchers("/loginPage")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login(oauth ->
                        oauth.loginPage("/loginPage")
                                .successHandler(successHandler())
                                .userInfoEndpoint()
                                .userService(oAuthMemberService)
                );

        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler(memberRepository);
    }
}
