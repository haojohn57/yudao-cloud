package cn.iocoder.yudao.module.driver.service.impl;

import cn.iocoder.yudao.framework.common.enums.DeviceConstants;
import cn.iocoder.yudao.framework.common.enums.DriverConstants;
import cn.iocoder.yudao.framework.common.iot.bean.driver.AttributeInfo;
import cn.iocoder.yudao.framework.common.iot.model.Device;
import cn.iocoder.yudao.framework.common.iot.model.Point;
import cn.iocoder.yudao.framework.driversdk.core.bean.driver.DriverContext;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverCustomService;
import cn.iocoder.yudao.framework.driversdk.core.service.DriverServiceSdk;
import cn.iocoder.yudao.module.driver.service.netty.tcp.NettyTcpServer;
import cn.iocoder.yudao.module.driver.service.netty.udp.NettyUdpServer;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 益莲科技
 */
@Slf4j
@Service
public class DriverCustomServiceImpl implements DriverCustomService {

    @Value("${driver.custom.tcp.port}")
    private Integer tcpPort;
    @Value("${driver.custom.udp.port}")
    private Integer udpPort;

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverServiceSdk driverServiceSdk;
    @Resource
    private NettyTcpServer nettyTcpServer;
    @Resource
    private NettyUdpServer nettyUdpServer;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void initial() {
        threadPoolExecutor.execute(() -> {
            log.debug("Virtual Listening Driver Starting(TCP::{}) incoming data listener", tcpPort);
            nettyTcpServer.start(tcpPort);
        });
        threadPoolExecutor.execute(() -> {
            log.debug("Virtual Listening Driver Starting(UDP::{}) incoming data listener", udpPort);
            nettyUdpServer.start(udpPort);
        });
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        // 因为 Listening Virtual 的数据来源是被动接收的，所以无需实现该 Read 方法
        // 接收数据处理函数在
        // - com.dc3.driver.service.netty.tcp.NettyTcpServerHandler.channelRead
        // - com.dc3.driver.service.netty.udp.NettyUdpServerHandler.channelRead0
        //driverContext.getDriverMetadata().getDeviceMap().keySet().forEach(id -> driverServiceSdk.deviceEventSender(id, DeviceConstants.Event.HEARTBEAT, DriverConstants.Status.ONLINE));
        return "nil";
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception {
        Long deviceId = device.getId();

        // TODO 获取设备的Channel，并向下发送数据
        Channel channel = NettyTcpServer.deviceChannelMap.get(deviceId);
        if (null != channel) {
            channel.writeAndFlush(value.getValue().getBytes(StandardCharsets.UTF_8));
        }
        return true;
    }

    @Override
    public void schedule() {

        /*
        TODO:设备状态
        上传设备状态，可自行灵活拓展，不一定非要在schedule()接口中实现，也可以在read中实现设备状态的设置；
        你可以通过某种判断机制确定设备的状态，然后通过driverService.deviceEventSender接口将设备状态交给SDK管理。

        设备状态（DeviceStatus）如下：
        ONLINE:在线
        OFFLINE:离线
        MAINTAIN:维护
        FAULT:故障
         */
        driverContext.getDriverMetadata().getDeviceMap().keySet().forEach(id -> driverServiceSdk.deviceEventSender(id, DeviceConstants.Event.HEARTBEAT, DriverConstants.Status.ONLINE));
    }

}
