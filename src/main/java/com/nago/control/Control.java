package com.nago.control;

import com.nago.core.BaseEntity;
import com.nago.device.Device;
import com.nago.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Control extends BaseEntity {
  private String name;

  @ManyToOne
  private Device device;

  private int value;

  @ManyToOne
  private User lastModifiedBy;

  protected Control() {
    super();
  }

  public Control(String name, Device device, int value, User lastModifiedBy) {
    this();
    this.name = name;
    this.device = device;
    this.value = value;
    this.lastModifiedBy = lastModifiedBy;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public User getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(User lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }
}
