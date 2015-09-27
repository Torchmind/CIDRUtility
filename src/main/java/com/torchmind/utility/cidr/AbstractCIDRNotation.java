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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Provides an abstract implementation of {@link com.torchmind.utility.cidr.CIDRNotation}.
 *
 * @author Johannes Donath
 */
@ThreadSafe
abstract class AbstractCIDRNotation<A extends InetAddress> implements CIDRNotation {
        private final A base;
        private final int prefixLength;

        protected AbstractCIDRNotation (@Nonnull A base, @Nonnegative int prefixLength) {
                this.base = base;
                this.prefixLength = prefixLength;
        }

        /**
         * Retrieves the base address.
         * @return the address.
         */
        @Nonnull
        public A base () {
                return this.base;
        }

        /**
         * Retrieves a mutated CIDR notation with the base of {@code base}.
         * @param base the base.
         * @return the notation.
         */
        @Nonnull
        public abstract CIDRNotation base (@Nonnull A base);

        /**
         * {@inheritDoc}
         */
        @Nonnull
        protected byte[] encoded (@Nonnegative int size) {
                byte[] encoded = new byte[size];

                for (int i = 0; i < this.prefixLength (); i++) {
                        int index = (i / 8);
                        int offset = (i % 8);

                        encoded[index] |= (0x80 >> offset);
                }

                return encoded;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean matches (@Nonnull InetAddress address) {
                if (!this.base.getClass ().isAssignableFrom (address.getClass ())) {
                        return false;
                }
                byte[] mask = this.encoded ();
                byte[] base = this.base ().getAddress ();
                byte[] encoded = address.getAddress ();

                for (int i = 0; i < mask.length; i++) {
                        if ((encoded[i] & mask[i]) != base[i]) {
                                return false;
                        }
                }

                return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean matches (@Nonnull String address) throws IllegalArgumentException, UnknownHostException {
                return this.matches (InetAddress.getByName (address));
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public <T extends InetAddress> CIDRNotation matches (@Nonnull T address, @Nonnull Consumer<T> consumer) {
                if (this.matches (address)) {
                        consumer.accept (address);
                }

                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public CIDRNotation matches (@Nonnull String address, @Nonnull Consumer<String> consumer) throws IllegalArgumentException, UnknownHostException {
                if (this.matches (address)) {
                        consumer.accept (address);
                }

                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public Set<InetAddress> matching (@Nonnull Set<InetAddress> addresses) {
                // @formatter:off
                return addresses.stream ()
                                .filter (this::matches)
                                        .collect (Collectors.toSet ());
                // @formatter:on
        }

        /**
         * {@inheritDoc}
         */
        @Nonnull
        @Override
        public CIDRNotation matching (@Nonnull Set<InetAddress> addresses, @Nonnull Consumer<InetAddress> consumer) {
                // @formatter:off
                addresses.stream ()
                        .filter (this::matches)
                                .forEach (consumer);
                // @formatter:on

                return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int prefixLength () {
                return this.prefixLength;
        }
}
