package com.imooc.mall.util;

import com.imooc.mall.filter.UserFilter;
import com.imooc.mall.model.pojo.User;

/**
 * 用户上下文工具类
 *
 * 功能：
 * - 获取当前请求的用户信息
 * - 获取当前用户ID和角色
 * - 检查用户权限
 * - 统一的用户信息访问接口
 */
public class UserContextUtil {

    /**
     * 获取当前用户信息
     *
     * @return 当前用户对象，如果未认证返回null
     */
    public static User getCurrentUser() {
        return UserFilter.getCurrentUser();
    }

    /**
     * 获取当前用户ID
     *
     * @return 用户ID，如果未认证返回null
     */
    public static Integer getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    /**
     * 获取当前用户名
     *
     * @return 用户名，如果未认证返回null
     */
    public static String getCurrentUsername() {
        User user = getCurrentUser();
        return user != null ? user.getUsername() : null;
    }

    /**
     * 获取当前用户角色
     *
     * @return 用户角色，如果未认证返回null
     */
    public static Integer getCurrentUserRole() {
        User user = getCurrentUser();
        return user != null ? user.getRole() : null;
    }

    /**
     * 检查用户是否已认证
     *
     * @return 已认证返回true，否则返回false
     */
    public static boolean isAuthenticated() {
        return getCurrentUser() != null;
    }

    /**
     * 检查用户是否为管理员
     *
     * @return 是管理员返回true，否则返回false
     */
    public static boolean isAdmin() {
        User user = getCurrentUser();
        // 角色1表示管理员
        return user != null && user.getRole() != null && user.getRole() == 1;
    }

    /**
     * 检查用户是否为普通用户
     *
     * @return 是普通用户返回true，否则返回false
     */
    public static boolean isNormalUser() {
        User user = getCurrentUser();
        // 角色0表示普通用户
        return user != null && user.getRole() != null && user.getRole() == 0;
    }

    /**
     * 检查当前用户ID是否匹配指定ID
     * 用于检查用户是否在访问自己的资源
     *
     * @param userId 要检查的用户ID
     * @return 匹配返回true，否则返回false
     */
    public static boolean isCurrentUser(Integer userId) {
        Integer currentUserId = getCurrentUserId();
        return currentUserId != null && currentUserId.equals(userId);
    }

    /**
     * 清理用户上下文
     * 不建议手动调用，框架会自动处理
     */
    public static void clear() {
        UserFilter.removeCurrentUser();
    }

    /**
     * 获取用户的简要信息字符串
     *
     * @return 用户信息字符串，格式: "用户名[ID]"
     */
    public static String getUserInfo() {
        User user = getCurrentUser();
        if (user == null) {
            return "未认证用户";
        }
        return String.format("%s[%d]", user.getUsername(), user.getId());
    }
}
