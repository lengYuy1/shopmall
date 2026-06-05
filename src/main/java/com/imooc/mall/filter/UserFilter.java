package com.imooc.mall.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.imooc.mall.model.pojo.User;
import com.imooc.mall.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * JWT用户认证过滤器
 *
 * 主要功能：
 * 1. 拦截HTTP请求，验证JWT Token
 * 2. 提取Token中的用户信息
 * 3. 支持路径白名单（某些路径无需认证）
 * 4. 使用ThreadLocal安全存储当前用户信息
 * 5. 提供日志记录和错误处理
 *
 * 线程安全：使用ThreadLocal存储，每个线程独立拥有一份用户信息副本
 * 内存安全：在finally块中清理ThreadLocal，防止内存泄漏
 */
public class UserFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(UserFilter.class);

    /**
     * ThreadLocal存储用户信息
     * 优点：
     * - 线程安全：每个线程独立存储
     * - 不存在竞态条件：无需同步
     * - 自动隔离：避免用户信息混乱
     */
    private static final ThreadLocal<User> currentUserThreadLocal = new ThreadLocal<>();

    /**
     * 需要排除的路径（不需要认证）
     * 使用startsWith匹配，支持路径前缀
     */
    private static final Set<String> EXCLUDE_PATHS = new HashSet<>(Arrays.asList(
            "/register",                    // 用户注册
            "/login",                       // 用户登录
            "/loginWithJwt",                // JWT登录
            "/user/login",                  // 用户登录接口
            "/product/list",                // 产品列表（公开）
            "/product/detail",              // 产品详情（公开）
            "/product/search",              // 产品搜索（公开）
            "/category/list",               // 分类列表（公开）
            "/swagger",                     // Swagger文档
            "/v2/api-docs",                 // API文档
            "/webjars",                     // Web资源
            "/static"                       // 静态资源
    ));

    @Override
    public void init(FilterConfig config) throws ServletException {
        logger.info("UserFilter 初始化完成");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = requestURI.replace(contextPath, "");

        try {
            // 检查是否是需要排除的路径
            if (shouldExclude(path)) {
                logger.debug("路径在白名单中，跳过认证: {} {}", requestMethod, path);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            // 提取Token
            String token = extractToken(request);
            if (token == null || token.isEmpty()) {
                logger.warn("请求缺少有效Token: {} {}", requestMethod, path);
                sendErrorResponse(response, 10007, "需要登录");
                return;
            }

            // 验证Token
            User user = validateAndParseToken(token);
            if (user == null) {
                logger.warn("Token验证失败: {} {}", requestMethod, path);
                sendErrorResponse(response, 10026, "解码失败");
                return;
            }

            // 将用户信息存储到ThreadLocal
            setCurrentUser(user);
            logger.info("用户 [{}] 认证成功，请求: {} {}", user.getUsername(), requestMethod, path);

            // 继续请求链
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (TokenExpiredException e) {
            logger.warn("Token已过期 - 请求: {} {}", requestMethod, path);
            sendErrorResponse(response, 10027, "token失效");
        } catch (JWTDecodeException e) {
            logger.error("Token解析异常 - 请求: {} {}, 错误: {}", requestMethod, path, e.getMessage());
            sendErrorResponse(response, 10026, "解码失败");
        } catch (Exception e) {
            logger.error("认证过程异常 - 请求: {} {}, 错误: {}", requestMethod, path, e.getMessage(), e);
            sendErrorResponse(response, 10008, "系统异常");
        } finally {
            // 清理ThreadLocal，防止内存泄漏
            removeCurrentUser();
        }
    }

    /**
     * 从ThreadLocal获取当前用户
     */
    public static User getCurrentUser() {
        return currentUserThreadLocal.get();
    }

    /**
     * 设置当前用户到ThreadLocal
     */
    private static void setCurrentUser(User user) {
        currentUserThreadLocal.set(user);
    }

    /**
     * 移除当前用户（清理ThreadLocal）
     */
    public static void removeCurrentUser() {
        currentUserThreadLocal.remove();
    }

    /**
     * 检查路径是否在白名单中
     * 使用startsWith进行前缀匹配
     *
     * @param requestURI 请求路径
     * @return 在白名单中返回true
     */
    private boolean shouldExclude(String requestURI) {
        return EXCLUDE_PATHS.stream().anyMatch(requestURI::startsWith);
    }

    /**
     * 从请求头提取Token
     * 支持两种格式：
     * 1. Authorization: <token>
     * 2. Authorization: Bearer <token>
     *
     * @param request HTTP请求
     * @return 提取的Token，如果不存在返回null
     */
    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader(Constant.JWT_TOKEN);

        if (token == null || token.isEmpty()) {
            logger.debug("请求头中没有Token");
            return null;
        }

        // 支持 Bearer token 格式
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
            logger.debug("检测到Bearer Token格式");
        }

        token = token.trim();

        // 验证Token长度（防止DoS攻击）
        if (token.length() > 2000) {
            logger.warn("Token长度异常，超过限制: {}", token.length());
            return null;
        }

        logger.debug("成功提取Token，长度: {}", token.length());
        return token;
    }

    /**
     * 验证并解析Token
     *
     * @param token JWT Token
     * @return 解析成功返回User对象，失败返回null
     * @throws TokenExpiredException Token已过期
     * @throws JWTDecodeException     Token格式错误
     */
    private User validateAndParseToken(String token) throws TokenExpiredException, JWTDecodeException {
        logger.debug("开始验证Token");

        try {
            // 验证Token（JWT库会检查签名和过期时间）
            JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC256(Constant.JWT_KEY))
                    .build()
                    .verify(token);

            // 解析Token中的用户信息
            Integer userId = JWT.decode(token).getAudience().stream()
                    .findFirst()
                    .map(Integer::valueOf)
                    .orElse(null);

            String username = JWT.decode(token).getClaim("username").asString();
            Integer role = JWT.decode(token).getClaim("role").asInt();

            logger.debug("Token验证成功，用户ID: {}, 用户名: {}, 角色: {}", userId, username, role);

            User user = new User();
            user.setId(userId);
            user.setUsername(username);
            user.setRole(role);

            return user;

        } catch (TokenExpiredException e) {
            logger.warn("Token已过期");
            throw e;
        } catch (JWTDecodeException e) {
            logger.error("Token解析失败: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * 发送错误响应
     * 遵循ApiRestResponse格式
     *
     * @param response HTTP响应
     * @param code     错误代码
     * @param msg      错误消息
     * @throws IOException 写入响应失败
     */
    private void sendErrorResponse(HttpServletResponse response, Integer code, String msg)
            throws IOException {

        // 设置HTTP状态码和响应头
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");

        // 设置CORS响应头
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        // 构建JSON响应
        String jsonResponse = String.format(
                "{\"status\":%d,\"msg\":\"%s\",\"data\":null}",
                code, msg
        );

        logger.debug("发送错误响应: code={}, msg={}", code, msg);

        PrintWriter writer = response.getWriter();
        writer.write(jsonResponse);
        writer.flush();
        writer.close();
    }

    @Override
    public void destroy() {
        logger.info("UserFilter 销毁");
    }
}
