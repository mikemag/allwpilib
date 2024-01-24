// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.net;

/** Class to resolve a service over mDNS. */
public class MulticastServiceResolver implements AutoCloseable {
  private int m_handle;

  /**
   * Creates a MulticastServiceResolver.
   *
   * @param serviceType service type to look for
   */
  @SuppressWarnings("this-escape")
  public MulticastServiceResolver(String serviceType) {
    m_handle = WPINetJNI.createMulticastServiceResolver(serviceType);
  }

  @SuppressWarnings("NoFinalizer")
  @Override
  protected void finalize() throws Throwable {
    close();
  }

  @Override
  public void close() {
    if (m_handle >= 0) {
      WPINetJNI.freeMulticastServiceResolver(m_handle);
      m_handle = -1;
    }
  }

  public void start() {
    WPINetJNI.startMulticastServiceResolver(m_handle);
  }

  public void stop() {
    WPINetJNI.stopMulticastServiceResolver(m_handle);
  }

  public boolean hasImplementation() {
    return WPINetJNI.getMulticastServiceResolverHasImplementation(m_handle);
  }

  public int getEventHandle() {
    return WPINetJNI.getMulticastServiceResolverEventHandle(m_handle);
  }

  public ServiceData[] getData() {
    return WPINetJNI.getMulticastServiceResolverData(m_handle);
  }
}
