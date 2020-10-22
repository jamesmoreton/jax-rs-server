package com.jamesmoreton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jamesmoreton.exception.ConstraintExceptionMapper;
import com.jamesmoreton.exception.RequestValidationExceptionMapper;
import com.jamesmoreton.user.UserService;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;

import static com.jamesmoreton.ServerMainline.UserModule;

public class ServerApplication extends ResourceConfig {

  public ServerApplication() {
    Injector injector = Guice.createInjector(new UserModule());
    UserService userService = injector.getInstance(UserService.class);
    RequestValidationExceptionMapper requestValidationExceptionMapper = injector.getInstance(RequestValidationExceptionMapper.class);
    ConstraintExceptionMapper constraintExceptionMapper = injector.getInstance(ConstraintExceptionMapper.class);
    register(userService);
    register(requestValidationExceptionMapper);
    register(constraintExceptionMapper);

    ObjectMapper mapper = JsonMapper.builder()
        .addModule(new Jdk8Module())
        .addModule(new JavaTimeModule())
        .build();
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
    provider.setMapper(mapper);
    register(provider);
  }
}
