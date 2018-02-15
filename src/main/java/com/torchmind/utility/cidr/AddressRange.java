/*
 * Copyright 2015 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.torchmind.utility.cidr;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

/**
 * Represents an arbitrary range of one or more addresses of a specific address type.
 *
 * @author Johannes Donath
 */
public interface AddressRange {

  /**
   * Parses an address range from its human readable CIDR based notation (such as 192.168.0.0/16).
   *
   * @param address The address.
   * @return the notation.
   * @throws IllegalArgumentException when the mask is invalid.
   * @throws UnknownHostException when the system cannot find the address.
   */
  @NonNull
  static AddressRange of(@NonNull String address)
      throws IllegalArgumentException, UnknownHostException {
    int lengthOffset = address.indexOf('/');

    int prefixLength = -1;
    if (lengthOffset != -1) {
      prefixLength = Integer.parseUnsignedInt(address.substring((lengthOffset + 1)));
      address = address.substring(0, lengthOffset);
    }

    return of(address, prefixLength);
  }

  /**
   * Creates an address range based on a pre-defined address and prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the address type, address or resulting mask is invalid.
   * @throws UnknownHostException when the passed address cannot be resolved.
   */
  @NonNull
  static AddressRange of(@NonNull String address, int prefixLength)
      throws IllegalArgumentException, UnknownHostException {
    return of(InetAddress.getByName(address), prefixLength);
  }

  /**
   * Creates an address range based on a pre-defined address and prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the address type or resulting mask is invalid.
   */
  @NonNull
  static AddressRange of(@NonNull InetAddress address, int prefixLength)
      throws IllegalArgumentException {
    if (address instanceof Inet4Address) {
      return of(((Inet4Address) address), prefixLength);
    }
    if (address instanceof Inet6Address) {
      return of(((Inet6Address) address), prefixLength);
    }

    throw new IllegalArgumentException(
        "Invalid java.net.InetAddress type: " + address.getClass().getCanonicalName());
  }

  /**
   * Creates an address range based on a pre-defined address and prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the resulting mask is invalid.
   */
  @NonNull
  static AddressRange4 of(@NonNull Inet4Address address, int prefixLength) {
    if (prefixLength == -1) {
      prefixLength = 32;
    }

    return (new AddressRange4(address, prefixLength));
  }

  /**
   * Creates an address range based on a pre-defined address and prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the resulting mask is invalid.
   */
  @NonNull
  static AddressRange6 of(@NonNull Inet6Address address, int prefixLength) {
    if (prefixLength == -1) {
      prefixLength = 64;
    }

    return (new AddressRange6(address, prefixLength));
  }

  /**
   * Retrieves the base address which addresses match against.
   *
   * @return a base address.
   */
  @NonNull
  InetAddress base();

  /**
   * Retrieves the amount of addresses within the block.
   *
   * @return a total amount of addresses.
   */
  long blockSize();

  /**
   * Retrieves a binary representation of the address netmask (e.g. a bitmask which exposes the
   * relevant elements of the address).
   *
   * @return a binary mask.
   */
  @NonNull
  byte[] encoded();

  /**
   * Evaluates whether the specified address is part of this address range.
   *
   * @param address the address.
   * @return true if the address matches, false otherwise.
   */
  boolean matches(@NonNull InetAddress address);

  /**
   * Evaluates whether the specified address is part of this address range.
   *
   * @param address the address.
   * @return true if the address matches, false otherwise.
   * @throws IllegalArgumentException when an invalid address was supplied.
   * @throws UnknownHostException when an unknown host was supplied.
   */
  boolean matches(@NonNull String address) throws IllegalArgumentException, UnknownHostException;

  /**
   * Creates a new set consisting only of addresses which fall within this address range (omitting
   * any of the addresses contained within the set which are of an incompatible address type or fall
   * outside of this range).
   *
   * @param addresses a set of addresses.
   * @return a set of matching addresses.
   */
  @NonNull
  Set<InetAddress> matching(@NonNull Set<InetAddress> addresses);

  /**
   * Retrieves the total prefix length (in bits).
   *
   * @return a prefix length.
   */
  int prefixLength();

  /**
   * Constructs a mutated address range with the same base address and an altered prefix length.
   *
   * @param prefixLength a new prefix length.
   * @return a mutated address range.
   */
  @NonNull
  AddressRange prefixLength(int prefixLength);

  /**
   * Encodes an address range into its human readable CIDR notation.
   *
   * @return a human readable notation.
   */
  @NonNull
  String toString();
}
