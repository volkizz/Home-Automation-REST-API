package com.nago.room;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import com.nago.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=Application.class)
@WebAppConfiguration
public class RoomRepositoryTest {
  @Mock
  private RoomRepository rooms;

  private List<Room> roomList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(rooms.save(any(Room.class))).then(answer ->
        roomList.add(new Room("room", 500))
    );
    rooms.save(new Room("room", 500));
  }

  @Test
  public void findByNameReturnsRoom() throws Exception {
    when(rooms.findByNameStartsWith(eq("ro"), any(PageRequest.class))).thenReturn(new PageImpl<>(
        roomList.stream().filter(room -> room.getName().contains("ro")).collect(Collectors.toList()))
    );
    Page<Room> roomPage = rooms.findByNameStartsWith("ro", new PageRequest(1,10));

    assertEquals(roomPage.getTotalElements(), 1);
  }

  @Test
  public void findByAreaLessThanReturnsRoom() throws Exception {
    when(rooms.findByAreaLessThan(eq(777), any(PageRequest.class))).thenReturn(
        new PageImpl<>(
            roomList.stream().filter(room -> room.getArea() < 777).collect(Collectors.toList())
        ));
    Page<Room> roomPage = rooms.findByAreaLessThan(777, new PageRequest(1, 10));

    assertEquals(roomPage.getTotalElements(), 1);
  }
}
