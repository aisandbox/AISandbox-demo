package dev.aisandbox.demo;

import java.net.InetAddress;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NetworkInformation {

  private final Environment environment;

  @Autowired
  public NetworkInformation(Environment environment) {
    this.environment = environment;
  }

  public String getServerAddress() {
    try {
      return InetAddress.getLocalHost().getHostAddress();
    } catch (Exception e) {
      log.warn("Error trying to get server address");
      return "localhost";
    }
  }

  public int getServerPort() {
    int port = 8080;
    try {
      port = Integer.parseInt(environment.getProperty("local.server.port"));
    } catch (Exception e) {
      log.warn("Error trying to get server port",e);
    }
    return port;
  }
}
