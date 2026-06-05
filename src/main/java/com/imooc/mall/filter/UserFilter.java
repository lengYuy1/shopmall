package com.imooc.mall.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.imooc.mall.common.Constant;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class UserFilter implements Filter {

    public static User currentUser;

    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest servletRequest1 = (HttpServletRequest) servletRequest;
        HttpSession session = servletRequest1.getSession();

        String token = servletRequest1.getHeader(Constant.JWT_TOKEN);

        if (token == null) {
            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
            out.write("{ \n"
                    + "    \"status\": 10007,\n"
                    + "    \"msg\": \"NEED_TOKEN\",\n"
                    + "    \"data\": null\n"
                    + "}");

            out.flush();
            out.close();
            return;
        }
        Algorithm algorithm = Algorithm.HMAC256(Constant.JWT_KEY);
        JWTVerifier build = JWT.require(algorithm).build();

        try {
            DecodedJWT verify = build.verify(token);
            currentUser = new User();
            currentUser.setId(verify.getClaim(Constant.USER_ID).asInt());
            currentUser.setRole(verify.getClaim(Constant.USER_ROLE).asInt());
            currentUser.setUsername(verify.getClaim(Constant.USER_NAME).asString());
        }
        catch (TokenExpiredException e) {
            throw new ImoocMallException(ImoocMallExceptionEnum.TOKEN_EXPIRE);
        } catch (JWTDecodeException e) {
            throw new ImoocMallException(ImoocMallExceptionEnum.TOKEN_WRONG);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
