package com.imooc.mall.common;

import com.google.common.collect.Sets;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Constant {
    public static final String SALT = "sfdsafaewrwe";

    public static final String IMOOC_MALL_USER = "imooc_mall";
    public static final String EMAIL_SUBJECT = "您的验证码";

    public static final String EMAIL_FROM = "1013217513@qq.com";

    public static final String JWT_KEY = "imooc-mall";
    public static final String JWT_TOKEN = "jwt_token";

    public static final String USER_NAME = "user_name";
    public static final String USER_ID = "user_id";
    public static final String USER_ROLE = "user_role";
    public static final Long EXPIRE_TIME = 60 * 1000 * 24 * 60 * 1000l;

    public static String FILE_UPLOAD_DIR;

    public interface SaleStatus {
        int NOT_SALE = 0;
        int SALE = 1;
    }

    public interface Cart {
        int UN_CHECKED = 0;
        int CHECKED = 1;
    }

    @Value("${file.upload.dir}")
    public void setFileUploadDir(String fileUploadDir) {
        FILE_UPLOAD_DIR = fileUploadDir;
    }

    public interface ProductListOrderBy {
        Set<String> PRICE_ORDER_ENUM = Sets.newHashSet("price desc", "price asc");
    }

    public enum OrderStatusEnum {
        CANCELED(0,"用戶已取消"),
        NOE_PAY(10,"未付款"),
        PAY(20,"已付款"),
        DELIVERED(30,"已发货"),
        FINISHED(40,"交易完成");

        private String value;
        private int Code;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return Code;
        }

        public static OrderStatusEnum codeOf(int code) {
            for ( OrderStatusEnum orderStatusEnum:values()) {
                if(orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new ImoocMallException(ImoocMallExceptionEnum.NO_ENUM);
        }

        public void setCode(int code) {
            Code = code;
        }

        OrderStatusEnum(int code, String value) {
            this.value = value;
            Code = code;
        }
    }
}
