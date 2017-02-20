package com.nago.core;

import com.nago.control.Control;
import com.nago.device.Device;
import com.nago.room.Room;
import com.nago.room.RoomRepository;
import com.nago.user.User;
import com.nago.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {
  private final UserRepository users;
  private final RoomRepository rooms;

  @Autowired
  public DatabaseLoader(RoomRepository rooms, UserRepository users) {
    this.rooms = rooms;
    this.users = users;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    List<User> administrators = Arrays.asList(
        new User("mykola", "Mykola", "12345", new String[]{"ROLE_USER", "ROLE_ADMIN"}),
        new User("jacobproffer", "Jacob", "password", new String[]{"ROLE_USER"}),
        new User("mlnorman", "Mike", "password", new String[]{"ROLE_USER"}),
        new User("k_freemansmith", "Karen", "password", new String[]{"ROLE_USER"}),
        new User("seth_lk", "Seth", "password", new String[]{"ROLE_USER"}),
        new User("mrstreetgrid", "Java", "password", new String[]{"ROLE_USER"}),
        new User("anthonymikhail", "Tony", "password", new String[]{"ROLE_USER"}),
        new User("boog690", "AJ", "password", new String[]{"ROLE_USER"}),
        new User("faelor", "Erik", "password", new String[]{"ROLE_USER"}),
        new User("christophernowack", "Christopher", "password", new String[]{"ROLE_USER"}),
        new User("calebkleveter", "Caleb", "password", new String[]{"ROLE_USER"}),
        new User("richdonellan", "Rich", "password", new String[]{"ROLE_USER"}),
        new User("albertqerimi", "Albert", "password", new String[]{"ROLE_USER"})
    );
    users.save(administrators);

    String[] roomTemplates = {"Living room", "Master bedroom", "Kitchen", "Garage", "Basement"};
    String[] deviceTemplates = {"Device 1", "Device 2", "Device 3", "Device 4", "Device 5"};
    String[] controlTemplates = {"Power", "Volume", "Temperature"};

    List<Room> bunchOfRooms = new ArrayList<>();
    IntStream.range(0, 100)
        .forEach(i -> {
          String roomName = roomTemplates[i % random(roomTemplates.length)];
          Room r = new Room(roomName, random(1000));

          for (int j = 0; j < random(administrators.size()); j++) {
            r.addAdministrators(administrators.get(j));
          }
          Collections.shuffle(administrators); // excludes users duplications in the room

          IntStream.rangeClosed(0, random(deviceTemplates.length))
              .forEach(e -> {
                String deviceName = deviceTemplates[e % deviceTemplates.length];
                Device d = new Device(deviceName);
                r.addDevice(d);
                IntStream.rangeClosed(0, random(controlTemplates.length))
                    .forEach(a -> {
                      String controlName = controlTemplates[a % controlTemplates.length];
                      //TODO: set the last user who modified control (not in DB loader, but separately)
                      Control c = new Control(controlName, d, random(100), administrators.get(random(administrators.size())));
                      d.addControl(c);
                    });
              });
          bunchOfRooms.add(r);
        });
    rooms.save(bunchOfRooms);
  }

  private int random(int i) {
    return (int) (Math.random() * (i - 1) + 1);
  }
}
