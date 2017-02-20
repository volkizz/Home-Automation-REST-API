package com.nago.device;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeviceRepositoryTest {
  @Mock
  private DeviceRepository devices;

  private List<Device> deviceList = new ArrayList<>();

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(devices.save(any(Device.class))).then(answer ->
        deviceList.add(new Device("device"))
    );
    devices.save(new Device("device"));
  }

  @Test
  public void findByNameContainingReturnsDevice() throws Exception {
    when(devices.findByNameStartsWith(eq("device"), any(PageRequest.class)))
        .thenReturn(new PageImpl<>(deviceList.stream().filter(device -> device.getName().contains("device"))
                .collect(Collectors.toList()))
        );
    Page<Device> devicePage = devices.findByNameStartsWith("device", new PageRequest(1, 10));

    assertEquals(devicePage.getTotalElements(), 1);
  }
}