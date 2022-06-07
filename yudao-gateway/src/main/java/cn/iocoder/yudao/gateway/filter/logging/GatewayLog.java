package cn.iocoder.yudao.gateway.filter.logging;

import lombok.Data;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.Map;

/**
 * 网关的访问日志
 */
@Data
public class GatewayLog {

    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 路由
     *
     * 类似 ApiAccessLogCreateReqDTO 的 applicationName
     */
    private Route route;

    /**
     * 协议
     */
    private String schema;
    /**
     * 请求方法名
     */
    private String requestMethod;
    /**
     * 访问地址
     */
    private String requestUrl;
    /**
     * 查询参数
     */
    private MultiValueMap<String, String> queryParams;
    /**
     * 请求体
     */
    private String requestBody;
    /**
     * 请求头
     */
    private MultiValueMap<String, String> requestHeaders;
    /**
     * 用户 IP
     */
    private String userIp;

    /**
     * 响应体
     *
     * 类似 ApiAccessLogCreateReqDTO 的 resultCode + resultMsg
     */
    private String responseBody;
    /**
     * 响应头
     */
    private MultiValueMap<String, String> responseHeaders;

    /**
     * 开始请求时间
     */
    private Date startTime;
    /**
     * 结束请求时间
     */
    private Date endTime;
    /**
     * 执行时长，单位：毫秒
     */
    private Integer duration;

}