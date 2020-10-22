package com.jamesmoreton;

import com.google.inject.AbstractModule;
import com.jamesmoreton.exception.ConstraintExceptionMapper;
import com.jamesmoreton.exception.RequestValidationExceptionMapper;
import com.jamesmoreton.user.UserResource;
import com.jamesmoreton.user.UserService;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.RuntimeDelegate;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.time.Clock;

public class ServerMainline {

  private static final Logger logger = LoggerFactory.getLogger(ServerMainline.class);

  private static final int SERVER_PORT = 1234;

  public static class UserModule extends AbstractModule {
    @Override
    protected void configure() {
      bind(UserResource.class).to(UserService.class);
      bind(Clock.class).toInstance(Clock.systemUTC());
      bind(RequestValidationExceptionMapper.class);
      bind(ConstraintExceptionMapper.class);
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    startServer();
  }

  private static void startServer() throws IOException, InterruptedException {
    logger.info("Server starting on {}", getBaseURI());
    final HttpServer server = HttpServer.create(new InetSocketAddress(getBaseURI().getPort()), 0);
    Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop(0)));

    HttpHandler handler = RuntimeDelegate.getInstance().createEndpoint(new ServerApplication(), HttpHandler.class);
    server.createContext(getBaseURI().getPath(), handler);
    server.start();
    logger.info("Server started successfully!");

    Thread.currentThread().join();
  }

  private static URI getBaseURI() {
    return UriBuilder.fromUri("http://localhost/").port(SERVER_PORT).build();
  }
}
