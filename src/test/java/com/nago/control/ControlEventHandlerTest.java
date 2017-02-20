package com.nago.control;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;

import com.nago.device.Device;
import com.nago.room.Room;
import com.nago.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.access.AccessDeniedException;


public class ControlEventHandlerTest {
  @Mock
  private ControlRepository controls;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = AccessDeniedException.class)
  public void onlyRoomUsersCanModifyControl() throws Exception {
    User user = new User("non-admin", "Tester", "password", new String[]{"ROLE_USER"});
    Room room = new Room("Toilet", 666);
    Device device = new Device("Device");
    Control control = new Control("AC", device, 74, user);

    room.addDevice(device);
    device.addControl(control);

    doAnswer(answer -> {
      if(!control.getDevice().getRoom().getAdministrators().contains(user)) {
        throw new AccessDeniedException("Access denied");
      }
      return null;
    }).when(controls).save(any(Control.class));
    controls.save(control);
  }
}