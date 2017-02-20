package com.nago.room;

import com.nago.user.User;
import com.nago.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RepositoryEventHandler(Room.class)
public class RoomEventHandler {

  private final UserRepository users;

  @Autowired
  public RoomEventHandler(UserRepository users) {
    this.users = users;
  }

  @HandleBeforeCreate
  public void onlyAdminCanCreateTheRoom(Room room){
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = users.findByUsername(username);
    if(Arrays.equals(user.getRoles(), new String[]{"ROLE_USER", "ROLE_ADMIN"})) {
      room.addAdministrators(user);
    } else {
      throw new AccessDeniedException("Only administrators can create a room.");
    }
  }
}
