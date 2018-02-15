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

/**
 * Provides a CIDR notation implementation for IPv4 address ranges.
 *
 * @author Johannes Donath
 */
public final class CIDR4Notation extends AbstractCIDRNotation<Inet4Address> {

  protected CIDR4Notation(@NonNull Inet4Address base, int prefixLength) {
    super(base, prefixLength);
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public CIDRNotation base(@NonNull Inet4Address base) {
    return (new CIDR4Notation(base, this.prefixLength()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long blockSize() {
    return ((long) Math.pow(2, (32 - this.prefixLength())));
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public byte[] encoded() {
    return this.encoded(4);
  }

  /**
   * {@inheritDoc}
   */
  @NonNull
  @Override
  public CIDRNotation prefixLength(int prefixLength) {
    return (new CIDR4Notation(this.base(), prefixLength));
  }
}
