package com.nago.room;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import com.nago.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;


public class RoomEventHandlerTest {
  @Mock
  private RoomRepository rooms;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = AccessDeniedException.class)
  public void onlyAdminCanCreateTheRoom() throws Exception {
    User user = new User("non-admin", "Tester", "password", new String[]{"ROLE_USER"});

    doAnswer(answer -> {
      if(!Arrays.equals(user.getRoles(), new String[]{"ROLE_USER", "ROLE_ADMIN"})) {
        throw new AccessDeniedException("Access denied");
      }
      return null;
    }).when(rooms).save(any(Room.class));
    rooms.save(new Room("room", 777));
  }
}