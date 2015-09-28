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
package com.torchmind.utility.cidr.test;

import com.torchmind.utility.cidr.CIDR4Notation;
import com.torchmind.utility.cidr.CIDRNotation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Provides test cases for {@link com.torchmind.utility.cidr.CIDRNotation} and all of it's implementations.
 *
 * @author Johannes Donath
 */
@RunWith (MockitoJUnitRunner.class)
public class CIDRNotationTest {

        /**
         * Tests {@link com.torchmind.utility.cidr.CIDRNotation#equals(Object)}.
         */
        @Test
        public void testEquals () throws UnknownHostException {
                CIDRNotation range00 = CIDRNotation.of ("10.0.0.0/8");
                CIDRNotation range01 = CIDRNotation.of ("10.0.0.0/8");
                CIDRNotation range02 = CIDRNotation.of ("10.10.0.0/16");
                CIDRNotation range03 = CIDRNotation.of ("10.10.0.0/16");
                CIDRNotation range04 = CIDRNotation.of ("10.10.10.0/24");
                CIDRNotation range05 = CIDRNotation.of ("10.10.10.0/24");
                CIDRNotation range06 = CIDRNotation.of ("10.10.10.10/32");
                CIDRNotation range07 = CIDRNotation.of ("10.10.10.10/32");
                CIDRNotation range08 = CIDRNotation.of ("127.0.0.0/31");
                CIDRNotation range09 = CIDRNotation.of ("127.0.0.0/31");

                {
                        CIDRNotation current = range00;

                        Assert.assertEquals (current, range00);
                        Assert.assertEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range01;

                        Assert.assertEquals (current, range00);
                        Assert.assertEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range02;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertEquals (current, range02);
                        Assert.assertEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range03;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertEquals (current, range02);
                        Assert.assertEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range04;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertEquals (current, range04);
                        Assert.assertEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range05;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertEquals (current, range04);
                        Assert.assertEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range06;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertEquals (current, range06);
                        Assert.assertEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range07;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertEquals (current, range06);
                        Assert.assertEquals (current, range07);
                        Assert.assertNotEquals (current, range08);
                        Assert.assertNotEquals (current, range09);
                }

                {
                        CIDRNotation current = range08;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertEquals (current, range08);
                        Assert.assertEquals (current, range09);
                }

                {
                        CIDRNotation current = range09;

                        Assert.assertNotEquals (current, range00);
                        Assert.assertNotEquals (current, range01);
                        Assert.assertNotEquals (current, range02);
                        Assert.assertNotEquals (current, range03);
                        Assert.assertNotEquals (current, range04);
                        Assert.assertNotEquals (current, range05);
                        Assert.assertNotEquals (current, range06);
                        Assert.assertNotEquals (current, range07);
                        Assert.assertEquals (current, range08);
                        Assert.assertEquals (current, range09);
                }
        }

        /**
         * Tests {@link com.torchmind.utility.cidr.CIDRNotation#matches(java.net.InetAddress)}.
         */
        @Test
        public void testMatch () throws UnknownHostException {
                CIDRNotation range01 = CIDRNotation.of ("10.0.0.0/8");
                CIDRNotation range02 = CIDRNotation.of ("10.10.0.0/16");
                CIDRNotation range03 = CIDRNotation.of ("10.10.10.0/24");
                CIDRNotation range04 = CIDRNotation.of ("10.10.10.10/32");
                CIDRNotation range05 = CIDRNotation.of ("127.0.0.0/31");

                Inet4Address address01 = ((Inet4Address) InetAddress.getByName ("10.0.0.0"));
                Inet4Address address02 = ((Inet4Address) InetAddress.getByName ("10.0.0.1"));
                Inet4Address address03 = ((Inet4Address) InetAddress.getByName ("10.0.0.2"));
                Inet4Address address04 = ((Inet4Address) InetAddress.getByName ("10.0.0.255"));

                Inet4Address address05 = ((Inet4Address) InetAddress.getByName ("10.10.0.0"));
                Inet4Address address06 = ((Inet4Address) InetAddress.getByName ("10.10.0.1"));
                Inet4Address address07 = ((Inet4Address) InetAddress.getByName ("10.10.0.2"));
                Inet4Address address08 = ((Inet4Address) InetAddress.getByName ("10.10.0.255"));

                Inet4Address address09 = ((Inet4Address) InetAddress.getByName ("10.10.10.0"));
                Inet4Address address10 = ((Inet4Address) InetAddress.getByName ("10.10.10.1"));
                Inet4Address address11 = ((Inet4Address) InetAddress.getByName ("10.10.10.2"));
                Inet4Address address12 = ((Inet4Address) InetAddress.getByName ("10.10.10.255"));

                Inet4Address address13 = ((Inet4Address) InetAddress.getByName ("10.10.10.10"));
                Inet4Address address14 = ((Inet4Address) InetAddress.getByName ("127.0.0.0"));
                Inet4Address address15 = ((Inet4Address) InetAddress.getByName ("127.0.0.1"));
                Inet4Address address16 = ((Inet4Address) InetAddress.getByName ("127.0.0.2"));

                {
                        CIDRNotation currentRange = range01;

                        Assert.assertTrue (currentRange.matches (address01));
                        Assert.assertTrue (currentRange.matches (address02));
                        Assert.assertTrue (currentRange.matches (address03));
                        Assert.assertTrue (currentRange.matches (address04));

                        Assert.assertTrue (currentRange.matches (address05));
                        Assert.assertTrue (currentRange.matches (address06));
                        Assert.assertTrue (currentRange.matches (address07));
                        Assert.assertTrue (currentRange.matches (address08));

                        Assert.assertTrue (currentRange.matches (address09));
                        Assert.assertTrue (currentRange.matches (address10));
                        Assert.assertTrue (currentRange.matches (address11));
                        Assert.assertTrue (currentRange.matches (address12));

                        Assert.assertTrue (currentRange.matches (address13));
                        Assert.assertFalse (currentRange.matches (address14));
                        Assert.assertFalse (currentRange.matches (address15));
                        Assert.assertFalse (currentRange.matches (address16));
                }

                {
                        CIDRNotation currentRange = range02;

                        Assert.assertFalse (currentRange.matches (address01));
                        Assert.assertFalse (currentRange.matches (address02));
                        Assert.assertFalse (currentRange.matches (address03));
                        Assert.assertFalse (currentRange.matches (address04));

                        Assert.assertTrue (currentRange.matches (address05));
                        Assert.assertTrue (currentRange.matches (address06));
                        Assert.assertTrue (currentRange.matches (address07));
                        Assert.assertTrue (currentRange.matches (address08));

                        Assert.assertTrue (currentRange.matches (address09));
                        Assert.assertTrue (currentRange.matches (address10));
                        Assert.assertTrue (currentRange.matches (address11));
                        Assert.assertTrue (currentRange.matches (address12));

                        Assert.assertTrue (currentRange.matches (address13));
                        Assert.assertFalse (currentRange.matches (address14));
                        Assert.assertFalse (currentRange.matches (address15));
                        Assert.assertFalse (currentRange.matches (address16));
                }

                {
                        CIDRNotation currentRange = range03;

                        Assert.assertFalse (currentRange.matches (address01));
                        Assert.assertFalse (currentRange.matches (address02));
                        Assert.assertFalse (currentRange.matches (address03));
                        Assert.assertFalse (currentRange.matches (address04));

                        Assert.assertFalse (currentRange.matches (address05));
                        Assert.assertFalse (currentRange.matches (address06));
                        Assert.assertFalse (currentRange.matches (address07));
                        Assert.assertFalse (currentRange.matches (address08));

                        Assert.assertTrue (currentRange.matches (address09));
                        Assert.assertTrue (currentRange.matches (address10));
                        Assert.assertTrue (currentRange.matches (address11));
                        Assert.assertTrue (currentRange.matches (address12));

                        Assert.assertTrue (currentRange.matches (address13));
                        Assert.assertFalse (currentRange.matches (address14));
                        Assert.assertFalse (currentRange.matches (address15));
                        Assert.assertFalse (currentRange.matches (address16));
                }

                {
                        CIDRNotation currentRange = range04;

                        Assert.assertFalse (currentRange.matches (address01));
                        Assert.assertFalse (currentRange.matches (address02));
                        Assert.assertFalse (currentRange.matches (address03));
                        Assert.assertFalse (currentRange.matches (address04));

                        Assert.assertFalse (currentRange.matches (address05));
                        Assert.assertFalse (currentRange.matches (address06));
                        Assert.assertFalse (currentRange.matches (address07));
                        Assert.assertFalse (currentRange.matches (address08));

                        Assert.assertFalse (currentRange.matches (address09));
                        Assert.assertFalse (currentRange.matches (address10));
                        Assert.assertFalse (currentRange.matches (address11));
                        Assert.assertFalse (currentRange.matches (address12));

                        Assert.assertTrue (currentRange.matches (address13));
                        Assert.assertFalse (currentRange.matches (address14));
                        Assert.assertFalse (currentRange.matches (address15));
                        Assert.assertFalse (currentRange.matches (address16));
                }

                {
                        CIDRNotation currentRange = range05;

                        Assert.assertFalse (currentRange.matches (address01));
                        Assert.assertFalse (currentRange.matches (address02));
                        Assert.assertFalse (currentRange.matches (address03));
                        Assert.assertFalse (currentRange.matches (address04));

                        Assert.assertFalse (currentRange.matches (address05));
                        Assert.assertFalse (currentRange.matches (address06));
                        Assert.assertFalse (currentRange.matches (address07));
                        Assert.assertFalse (currentRange.matches (address08));

                        Assert.assertFalse (currentRange.matches (address09));
                        Assert.assertFalse (currentRange.matches (address10));
                        Assert.assertFalse (currentRange.matches (address11));
                        Assert.assertFalse (currentRange.matches (address12));

                        Assert.assertFalse (currentRange.matches (address13));
                        Assert.assertTrue (currentRange.matches (address14));
                        Assert.assertTrue (currentRange.matches (address15));
                        Assert.assertFalse (currentRange.matches (address16));
                }
        }

        /**
         * Tests {@link com.torchmind.utility.cidr.CIDRNotation#of(String)}.
         */
        @Test
        public void testParse () throws UnknownHostException {
                CIDR4Notation address00 = ((CIDR4Notation) CIDRNotation.of ("10.0.0.0/8"));
                CIDR4Notation address01 = ((CIDR4Notation) CIDRNotation.of ("10.10.0.0/16"));
                CIDR4Notation address02 = ((CIDR4Notation) CIDRNotation.of ("10.10.10.0/24"));
                CIDR4Notation address03 = ((CIDR4Notation) CIDRNotation.of ("10.10.10.10/32"));

                {
                        Assert.assertEquals (InetAddress.getByName ("10.0.0.0"), address00.base ());
                        Assert.assertEquals (8, address00.prefixLength ());
                }

                {
                        Assert.assertEquals (InetAddress.getByName ("10.10.0.0"), address01.base ());
                        Assert.assertEquals (16, address01.prefixLength ());
                }

                {
                        Assert.assertEquals (InetAddress.getByName ("10.10.10.0"), address02.base ());
                        Assert.assertEquals (24, address02.prefixLength ());
                }

                {
                        Assert.assertEquals (InetAddress.getByName ("10.10.10.10"), address03.base ());
                        Assert.assertEquals (32, address03.prefixLength ());
                }
        }

        /**
         * Tests {@link com.torchmind.utility.cidr.CIDRNotation#toString()}.
         */
        @Test
        public void testToString () throws UnknownHostException {
                Assert.assertEquals ("10.0.0.0/8", CIDRNotation.of ("10.0.0.0/8").toString ());
                Assert.assertEquals ("10.10.0.0/16", CIDRNotation.of ("10.10.0.0/16").toString ());
                Assert.assertEquals ("10.10.10.0/24", CIDRNotation.of ("10.10.10.0/24").toString ());
                Assert.assertEquals ("10.10.10.10/32", CIDRNotation.of ("10.10.10.10/32").toString ());
                Assert.assertEquals ("127.0.0.0/31", CIDRNotation.of ("127.0.0.0/31").toString ());
        }

        /**
         * Tests {@link com.torchmind.utility.cidr.CIDRNotation#blockSize()}.
         */
        @Test
        public void testBlockSize () throws UnknownHostException {
                Assert.assertEquals (4294967296L, CIDRNotation.of ("0.0.0.0/0").blockSize ());

                Assert.assertEquals (2147483648L, CIDRNotation.of ("128.0.0.0/1").blockSize ());
                Assert.assertEquals (1073741824L, CIDRNotation.of ("192.0.0.0/2").blockSize ());
                Assert.assertEquals (536870912L, CIDRNotation.of ("224.0.0.0/3").blockSize ());
                Assert.assertEquals (268435456L, CIDRNotation.of ("240.0.0.0/4").blockSize ());
                Assert.assertEquals (134217728L, CIDRNotation.of ("248.0.0.0/5").blockSize ());
                Assert.assertEquals (67108864L, CIDRNotation.of ("252.0.0.0/6").blockSize ());
                Assert.assertEquals (33554432L, CIDRNotation.of ("254.0.0.0/7").blockSize ());
                Assert.assertEquals (16777216L, CIDRNotation.of ("255.0.0.0/8").blockSize ());

                Assert.assertEquals (8388608L, CIDRNotation.of ("255.128.0.0/9").blockSize ());
                Assert.assertEquals (4194304L, CIDRNotation.of ("255.192.0.0/10").blockSize ());
                Assert.assertEquals (2097152L, CIDRNotation.of ("255.224.0.0/11").blockSize ());
                Assert.assertEquals (1048576L, CIDRNotation.of ("255.240.0.0/12").blockSize ());
                Assert.assertEquals (524288L, CIDRNotation.of ("255.248.0.0/13").blockSize ());
                Assert.assertEquals (262144L, CIDRNotation.of ("255.252.0.0/14").blockSize ());
                Assert.assertEquals (131072L, CIDRNotation.of ("255.254.0.0/15").blockSize ());
                Assert.assertEquals (65536L, CIDRNotation.of ("255.255.0.0/16").blockSize ());

                Assert.assertEquals (32768L, CIDRNotation.of ("255.255.128.0/17").blockSize ());
                Assert.assertEquals (16384L, CIDRNotation.of ("255.255.192.0/18").blockSize ());
                Assert.assertEquals (8192L, CIDRNotation.of ("255.255.224.0/19").blockSize ());
                Assert.assertEquals (4096L, CIDRNotation.of ("255.255.240.0/20").blockSize ());
                Assert.assertEquals (2048L, CIDRNotation.of ("255.255.248.0/21").blockSize ());
                Assert.assertEquals (1024L, CIDRNotation.of ("255.255.252.0/22").blockSize ());
                Assert.assertEquals (512L, CIDRNotation.of ("255.255.254.0/23").blockSize ());
                Assert.assertEquals (256L, CIDRNotation.of ("255.255.255.0/24").blockSize ());

                Assert.assertEquals (128L, CIDRNotation.of ("255.255.255.128/25").blockSize ());
                Assert.assertEquals (64L, CIDRNotation.of ("255.255.255.192/26").blockSize ());
                Assert.assertEquals (32L, CIDRNotation.of ("255.255.255.224/27").blockSize ());
                Assert.assertEquals (16L, CIDRNotation.of ("255.255.255.240/28").blockSize ());
                Assert.assertEquals (8L, CIDRNotation.of ("255.255.255.248/29").blockSize ());
                Assert.assertEquals (4L, CIDRNotation.of ("255.255.255.252/30").blockSize ());
                Assert.assertEquals (2L, CIDRNotation.of ("255.255.255.254/31").blockSize ());
                Assert.assertEquals (1L, CIDRNotation.of ("255.255.255.255/32").blockSize ());
        }
}
