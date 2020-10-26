package cl.bgmp.butils.bungee;

/**
 * Basic representation of things there are to a Server. Created to avoid boilerplate when wanting
 * to refer server components
 */
public class Server {
  private String name;
  private String ip;
  private int port;

  public Server(String name, String ip, int port) {
    this.name = name;
    this.ip = ip;
    this.port = port;
  }

  public String getName() {
    return name;
  }

  public String getIp() {
    return ip;
  }

  public int getPort() {
    return port;
  }
}
