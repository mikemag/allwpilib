// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.net;

import java.util.Map;

/** Class to announce over mDNS that a service is available. */
public class MulticastServiceAnnouncer implements AutoCloseable {
  private int m_handle;

  /**
   * Creates a MulticastServiceAnnouncer.
   *
   * @param serviceName service name
   * @param serviceType service type
   * @param port port
   * @param txt txt
   */
  public MulticastServiceAnnouncer(
      String serviceName, String serviceType, int port, Map<String, String> txt) {
    String[] keys = txt.keySet().stream().toArray(String[]::new);
    String[] values = txt.values().stream().toArray(String[]::new);
    m_handle =
        WPINetJNI.createMulticastServiceAnnouncer(serviceName, serviceType, port, keys, values);
  }

  /**
   * Creates a MulticastServiceAnnouncer.
   *
   * @param serviceName service name
   * @param serviceType service type
   * @param port port
   */
  public MulticastServiceAnnouncer(String serviceName, String serviceType, int port) {
    m_handle =
        WPINetJNI.createMulticastServiceAnnouncer(serviceName, serviceType, port, null, null);
  }

  @SuppressWarnings("NoFinalizer")
  @Override
  protected void finalize() throws Throwable {
    close();
  }

  @Override
  public void close() {
    if (m_handle >= 0) {
      WPINetJNI.freeMulticastServiceAnnouncer(m_handle);
      m_handle = -1;
    }
  }

  /** Starts multicast service announcer. */
  public void start() {
    WPINetJNI.startMulticastServiceAnnouncer(m_handle);
  }

  /** Stops multicast service announcer. */
  public void stop() {
    WPINetJNI.stopMulticastServiceAnnouncer(m_handle);
  }

  /**
   * Returns true if there's a multicast service announcer implementation.
   *
   * @return True if there's a multicast service announcer implementation.
   */
  public boolean hasImplementation() {
    return WPINetJNI.getMulticastServiceAnnouncerHasImplementation(m_handle);
  }
}
