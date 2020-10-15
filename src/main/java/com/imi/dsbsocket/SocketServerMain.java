package com.imi.dsbsocket;

import com.corundumstudio.socketio.SocketIOServer;
import com.imi.dsbsocket.service.DsbSocketDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;

/**
 * SpringBoot启动之后执行
 */

@SpringBootApplication
@EnableTransactionManagement
@EnableJpaAuditing
@PropertySource({"classpath:dsbSocketServer.properties", "classpath:redis.properties"})
//@Order(1)
public class SocketServerMain implements CommandLineRunner {

    //在SocketConfig有配置SocketIOServer
    private final SocketIOServer socketIOServer;
    //    private final PubSubStore pubSubStore;
//    private final DispatchHandler dispatchHandler;
    private final DsbSocketDomainService dsbSocketDomainService;

    private Logger log = LoggerFactory.getLogger(SocketServerMain.class);


    @Autowired
    public SocketServerMain(SocketIOServer socketIOServer, DsbSocketDomainService dsbSocketDomainService) {
        this.socketIOServer = socketIOServer;
        this.dsbSocketDomainService = dsbSocketDomainService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SocketServerMain.class, args);
    }

    @Override
    public void run(String... args) {
        //訂閱發布消息機制
//        pubSubStore.subscribe(PubSubType.DISPATCH, dispatchHandler, DispatchMessage.class);
        log.info("---------- SocketServer 開始啟動 ----------");
        socketIOServer.start();
        log.info("---------- SocketServer 啟動成功 ----------");
        dsbSocketDomainService.addOneOrElseResetBySelf();
    }

    @PreDestroy
    public void onExit() {
        log.info("---------- SocketServer 開始關閉 ----------");
        socketIOServer.stop();
        log.info("---------- SocketServer 關閉成功 ----------");
        dsbSocketDomainService.deleteOneBySelf();
    }

}