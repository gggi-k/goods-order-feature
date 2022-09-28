package kr.submit.goodsorderfeature.core.swagger.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import kr.submit.goodsorderfeature.core.security.dto.Login;
import org.springdoc.core.SpringDocConfiguration;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.lang.reflect.Field;
import java.util.Optional;

@Configuration
@Import(SpringDocConfiguration.class)
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "jwtToken", scheme = "bearer", bearerFormat = "JWT")
@OpenAPIDefinition(
    info = @Info(title = "주문, 상품관리 기능", description = "주문, 상품관리<br> 아이디: test1, 비밀번호: test1<br> 아이디: test2, 비밀번호: test2", version = "1.0.0"),
    security = @SecurityRequirement(name = "jwtToken")
)
public class SwaggerConfig {

    private static final String LOGIN_TAG_NAME = "로그인";

    @Bean
    public OpenApiCustomiser openApiCustomiser(ApplicationContext applicationContext) {
        FilterChainProxy filterChainProxy = applicationContext.getBean(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, FilterChainProxy.class);
        return openAPI -> {
            for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                Optional<UsernamePasswordAuthenticationFilter> optionalFilter =
                        filterChain.getFilters().stream()
                                .filter(UsernamePasswordAuthenticationFilter.class::isInstance)
                                .map(UsernamePasswordAuthenticationFilter.class::cast)
                                .findAny();
                if (optionalFilter.isPresent()) {
                    UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter = optionalFilter.get();
                    Operation operation = new Operation();
                    Schema<?> schema = new ObjectSchema()
                            .addProperty(Login.Fields.userId, new StringSchema().description("사용자아이디").example("test1"))
                            .addProperty(Login.Fields.password, new StringSchema().description("패스워드").example("test1"));
                    RequestBody requestBody = new RequestBody().content(new Content().addMediaType(org.springframework.http.MediaType.APPLICATION_JSON_VALUE, new MediaType().schema(schema)));
                    operation.requestBody(requestBody);
                    ApiResponses apiResponses = new ApiResponses();
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()), new ApiResponse().description(HttpStatus.OK.getReasonPhrase()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.UNAUTHORIZED.value()), new ApiResponse().description(HttpStatus.UNAUTHORIZED.getReasonPhrase()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.FORBIDDEN.value()), new ApiResponse().description(HttpStatus.FORBIDDEN.getReasonPhrase()));
                    operation.responses(apiResponses);
                    operation.addTagsItem(LOGIN_TAG_NAME);
                    operation.setSummary(LOGIN_TAG_NAME);
                    PathItem pathItem = new PathItem().post(operation);
                    try {
                        Field requestMatcherField = AbstractAuthenticationProcessingFilter.class.getDeclaredField("requiresAuthenticationRequestMatcher");
                        requestMatcherField.setAccessible(true);
                        AntPathRequestMatcher requestMatcher = (AntPathRequestMatcher) requestMatcherField.get(usernamePasswordAuthenticationFilter);
                        String loginPath = requestMatcher.getPattern();
                        requestMatcherField.setAccessible(false);
                        openAPI.getPaths().addPathItem(loginPath, pathItem);
                    } catch (NoSuchFieldException | IllegalAccessException | ClassCastException ignored) {}
                }
            }
        };
    }
}