package dev.training.microservices.service;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.stereotype.Service;

@Service
public class BaseUrlService {
    private final EurekaClient eurekaClient;

    public BaseUrlService(EurekaClient eurekaClient) {
        this.eurekaClient = eurekaClient;
    }

    public String createBaseUrlForService(String serviceName) {
        InstanceInfo serviceInstance = eurekaClient
                .getApplication(serviceName)
                .getInstances()
                .getFirst();

        String hostName = serviceInstance.getHostName();
        int port = serviceInstance.getPort();

        return "http://" + hostName + ":" + port;
    }

}
