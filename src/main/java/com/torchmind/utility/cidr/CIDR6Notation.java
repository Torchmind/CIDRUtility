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

import java.net.Inet6Address;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Provides an {@link java.net.Inet6Address} based {@link com.torchmind.utility.cidr.CIDRNotation}
 * implementation.
 *
 * @author Johannes Donath
 */
public final class CIDR6Notation extends AbstractCIDRNotation<Inet6Address> {

  protected CIDR6Notation(@Nonnull Inet6Address base, @Nonnegative int prefixLength) {
    super(base, prefixLength);
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public CIDRNotation base(@Nonnull Inet6Address base) {
    return (new CIDR6Notation(base, this.prefixLength()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public long blockSize() {
    return ((long) Math.pow(2, (64 - this.prefixLength())));
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public byte[] encoded() {
    return this.encoded(8);
  }

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @Override
  public CIDRNotation prefixLength(@Nonnegative int prefixLength) {
    return (new CIDR6Notation(this.base(), prefixLength));
  }
}
