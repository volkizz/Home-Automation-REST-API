package com.nago.device;

import com.nago.control.Control;
import com.nago.core.BaseEntity;
import com.nago.room.Room;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Device extends BaseEntity{
  private String name;

  @ManyToOne
  private Room room;

  @OneToMany(mappedBy = "device", cascade = CascadeType.ALL)
  private List<Control> controls;

  protected Device(){
    super();
    controls = new ArrayList<>();
  }

  public Device(String name) {
    this();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public List<Control> getControls() {
    return controls;
  }

  public void addControl(Control control) {
    control.setDevice(this);
    controls.add(control);
  }
}
