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

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

/**
 * Represents a CIDRNotation range.
 *
 * @author Johannes Donath
 */
@ThreadSafe
public interface CIDRNotation {

  /**
   * Retrieves a CIDR notation based on an encoded address.
   *
   * @param address The address.
   * @return the notation.
   * @throws IllegalArgumentException when the mask is invalid.
   * @throws UnknownHostException when the system cannot find the address.
   */
  @Nonnull
  static CIDRNotation of(@Nonnull String address)
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
   * Retrieves a CIDR notation based on a human readable address and prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the address type, address or resulting mask is invalid.
   * @throws UnknownHostException when the passed address cannot be resolved.
   */
  @Nonnull
  static CIDRNotation of(@Nonnull String address, @Nonnegative int prefixLength)
      throws IllegalArgumentException, UnknownHostException {
    return of(InetAddress.getByName(address), prefixLength);
  }

  /**
   * Retrieves a CIDR notation based on an address and a prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the address type or resulting mask is invalid.
   */
  @Nonnull
  static CIDRNotation of(@Nonnull InetAddress address, @Nonnegative int prefixLength)
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
   * Retrieves a CIDR notation based on an IPv4 address and prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the resulting mask is invalid.
   */
  @Nonnull
  static CIDR4Notation of(@Nonnull Inet4Address address, @Nonnegative int prefixLength) {
    if (prefixLength == -1) {
      prefixLength = 32;
    }

    return (new CIDR4Notation(address, prefixLength));
  }

  /**
   * Retrieves a CIDR notation based on an IPv6 address and prefix length.
   *
   * @param address the address.
   * @param prefixLength the prefix length.
   * @return the notation.
   * @throws IllegalArgumentException when the resulting mask is invalid.
   */
  @Nonnull
  static CIDR6Notation of(@Nonnull Inet6Address address, @Nonnegative int prefixLength) {
    if (prefixLength == -1) {
      prefixLength = 64;
    }

    return (new CIDR6Notation(address, prefixLength));
  }

  /**
   * Retrieves the base address.
   *
   * @return the address.
   */
  @Nonnull
  InetAddress base();

  /**
   * Retrieves the amount of addresses within the block.
   *
   * @return the size.
   */
  @Nonnegative
  long blockSize();

  /**
   * Retrieves a binary encoded CIDRNotation mask.
   *
   * @return the bytes.
   */
  @Nonnull
  byte[] encoded();

  /**
   * Checks whether an address matches.
   *
   * @param address the address.
   * @return {@code true} if the address matches, {@code false} otherwise.
   */
  boolean matches(@Nonnull InetAddress address);

  /**
   * Checks whether an address matches.
   *
   * @param address the address.
   * @return {@code true} if the address matches, {@code false} otherwise.
   * @throws IllegalArgumentException when an invalid address was supplied.
   * @throws UnknownHostException when an unknown host was supplied.
   */
  boolean matches(@Nonnull String address) throws IllegalArgumentException, UnknownHostException;

  /**
   * Checks whether an address matches and calls {@code consumer} if so.
   *
   * @param address the address.
   * @param consumer the consumer.
   * @param <T> the address type.
   * @return the CIDRNotation.
   */
  @Nonnull
  <T extends InetAddress> CIDRNotation matches(@Nonnull T address, @Nonnull Consumer<T> consumer);

  /**
   * Checks whether an address matches andd calls {@code consumer} if so.
   *
   * @param address the address.
   * @param consumer the consumer.
   * @return the CIDRNotation.
   * @throws IllegalArgumentException when an invalid address was supplied.
   * @throws UnknownHostException when an unknown host was supplied.
   */
  @Nonnull
  CIDRNotation matches(@Nonnull String address, @Nonnull Consumer<String> consumer)
      throws IllegalArgumentException, UnknownHostException;

  /**
   * Filters a set and outputs all matching addresses.
   *
   * @param addresses the address.
   * @return the result set.
   */
  @Nonnull
  Set<InetAddress> matching(@Nonnull Set<InetAddress> addresses);

  /**
   * Filters a set and passes all matching addresses to {@code consumer}.
   *
   * @param addresses the addresses.
   * @param consumer the consumer.
   * @return the CIDRNotation.
   */
  @Nonnull
  CIDRNotation matching(@Nonnull Set<InetAddress> addresses,
      @Nonnull Consumer<InetAddress> consumer);

  /**
   * Retrieves the prefix length.
   *
   * @return the length.
   */
  @Nonnegative
  int prefixLength();

  /**
   * Retrieves a mutated CIDRNotation with the specified prefix length.
   *
   * @param prefixLength the prefix length.
   * @return the mutated CIDRNotation.
   */
  @Nonnull
  CIDRNotation prefixLength(@Nonnegative int prefixLength);

  /**
   * Retrieves a string representation of the CIDRNotation notation.
   *
   * @return the string.
   */
  @Nonnull
  String toString();
}
