package com.nago.room;

import com.nago.core.BaseEntity;
import com.nago.device.Device;
import com.nago.user.User;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Room extends BaseEntity{
  @NotNull
  private String name;

  @NotNull
  @Min(1)
  @Max(value = 999, message = "must be less than 1000")
  private int area;

  @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
  private List<Device> devices;

  @ManyToMany
  private List<User> administrators;

  protected Room(){
    super();
    devices = new ArrayList<>();
    administrators = new ArrayList<>();
  }

  public Room(String name, int area) {
    this();
    this.name = name;
    this.area = area;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getArea() {
    return area;
  }

  public void setArea(int area) {
    this.area = area;
  }

  public List<Device> getDevices() {
    return devices;
  }

  public void addDevice(Device device) {
    device.setRoom(this);
    devices.add(device);
  }

  public List<User> getAdministrators() {
    return administrators;
  }

  public void addAdministrators(User administrator) {
    administrators.add(administrator);
  }
}
