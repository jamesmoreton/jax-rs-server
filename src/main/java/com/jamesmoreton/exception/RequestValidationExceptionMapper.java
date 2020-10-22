package com.jamesmoreton.exception;

import com.google.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Provider
@Singleton
public class RequestValidationExceptionMapper implements ExceptionMapper<RequestValidationException> {

  @Override
  public Response toResponse(RequestValidationException e) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(new ErrorResponse(e.getErrorCode().name(), e.getMessage()))
        .type(APPLICATION_JSON)
        .build();
  }
}
