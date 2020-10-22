package com.jamesmoreton.exception;

import com.google.inject.Singleton;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Provider
@Singleton
public class ConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException e) {
    return Response.status(Response.Status.BAD_REQUEST)
        .entity(prepareMessage(e))
        .type(TEXT_PLAIN)
        .build();
  }

  private String prepareMessage(ConstraintViolationException e) {
    StringBuilder msg = new StringBuilder();
    for (ConstraintViolation<?> cv : e.getConstraintViolations()) {
      String[] path = cv.getPropertyPath().toString().split("\\.");
      String property = path.length < 3 ? "Request" : path[path.length-1];
      msg.append(property).append(" ").append(cv.getMessage()).append("\n");
    }
    return msg.toString();
  }
}
