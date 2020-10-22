package com.jamesmoreton.user;

import com.jamesmoreton.user.model.User;
import com.jamesmoreton.user.model.UserCreateRequest;
import com.jamesmoreton.user.model.UserType;
import com.jamesmoreton.user.model.UserUpdateRequest;
import com.jamesmoreton.user.model.Users;
import com.neovisionaries.i18n.CountryCode;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("api/v1/users")
public interface UserResource {

  @POST
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  UUID createUser(@NotNull @Valid UserCreateRequest userCreateRequest);

  @PUT
  @Path("{userUid}")
  @Consumes(APPLICATION_JSON)
  @Produces(APPLICATION_JSON)
  void updateUser(@PathParam("userUid") UUID userUid, @NotNull @Valid UserUpdateRequest userType);

  @DELETE
  @Path("{userUid}")
  void deleteUser(@PathParam("userUid") UUID userUid);

  @GET
  @Path("{userUid}")
  @Produces(APPLICATION_JSON)
  Optional<User> getUser(@PathParam("userUid") UUID userUid);

  @GET
  @Produces(APPLICATION_JSON)
  Users getUsers(@QueryParam("userType") UserType userType, @QueryParam("countryCode") CountryCode countryCode);
}
