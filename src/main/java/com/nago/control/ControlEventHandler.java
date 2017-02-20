package com.nago.control;


import com.nago.user.User;
import com.nago.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler(Control.class)
public class ControlEventHandler {
  private final UserRepository users;

  @Autowired
  public  ControlEventHandler(UserRepository users) {
    this.users = users;
  }

  @HandleBeforeCreate
  public void setLastModifiedByOnControlCreate(Control control) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = users.findByUsername(username);
    if(!control.getDevice().getRoom().getAdministrators().contains(user)) {
      throw new AccessDeniedException("Must be a user of this room to edit controls");
    }
    control.setLastModifiedBy(user);
  }

  @HandleBeforeSave
  public void setLastModifiedByOnSaveControl(Control control) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = users.findByUsername(username);
    if(!control.getDevice().getRoom().getAdministrators().contains(user)) {
      throw new AccessDeniedException("Must be a user of this room to edit controls");
    }
    control.setLastModifiedBy(user);
  }
}
