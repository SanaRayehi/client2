package com.example.server;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

@SpringBootApplication
public class ClientApplication extends Application {



    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ClientApplication.class, args);
        ServiceHandler serviceHandler = (ServiceHandler) applicationContext.getBean("serviceHandler");
        ClientSide.serviceHandler = serviceHandler;
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            Login login = new Login();
            login.show();

    }

    @Bean
    public RmiProxyFactoryBean serviceHandler(){
        RmiProxyFactoryBean serviceHandler = new RmiProxyFactoryBean();
        serviceHandler.setServiceInterface(ServiceHandler.class);
        serviceHandler.setServiceUrl("rmi://localhost:5050/serverService");
        return serviceHandler;
    }

}




