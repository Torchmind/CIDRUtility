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
import java.net.Inet6Address;

/**
 * Represents a 128-bit address range (as implemented by IPv6).
 *
 * @author Johannes Donath
 */
public final class AddressRange6 extends AbstractAddressRange<Inet6Address> {

  /**
   * Defines the maximum permitted amount of bits within an address prefix.
   */
  public static final int MAX_PREFIX_LENGTH = 128;

  protected AddressRange6(@NonNull Inet6Address base, int prefixLength) {
    super(base, prefixLength);
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public AddressRange base(@NonNull Inet6Address base) {
    return (new AddressRange6(base, this.prefixLength()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long blockSize() {
    return ((long) Math.pow(2, (MAX_PREFIX_LENGTH - this.prefixLength())));
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public byte[] encoded() {
    return this.encoded(16);
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public AddressRange prefixLength(int prefixLength) {
    return (new AddressRange6(this.base(), prefixLength));
  }
}
