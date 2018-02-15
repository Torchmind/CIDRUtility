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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Provides test cases for {@link AddressRange} and all of it's implementations.
 *
 * @author Johannes Donath
 */
@RunWith(MockitoJUnitRunner.class)
public class AddressRangeTest {

  /**
   * Tests whether {@link AbstractAddressRange#AbstractAddressRange(InetAddress, int)} verifies
   * passed masks correctly.
   */
  @Test
  public void testBaseVerification() {
    BiConsumer<String, Boolean> verify = (m, b) -> {
      try {

        if (!b) {
          try {
            AddressRange.of(m);
            throw new AssertionError(
                "Expected IllegalArgumentException due to invalid mask specification: " + m);
          } catch (IllegalArgumentException ignore) {
          }

          return;
        }

        AddressRange.of(m);
      } catch (UnknownHostException ex) {
        throw new AssertionError("Unexpected exception: " + ex.getMessage(), ex);
      }
    };

    Function<Integer, String> address = (i) -> ((i & 0xFF000000) >>> 24) + "." + ((i & 0x00FF0000)
        >>> 16) + "." + ((i & 0x0000FF00) >>> 8) + "." + (i & 0x000000FF);

    Consumer<Integer> a = (m) -> {
      int number = 0x0;
      for (int i = 0; i < (32 - m); i++) {
        number |= (0x1 << i);

        verify.accept(address.apply(number) + "/" + m, false);
      }

      number = 0x0;
      for (int i = -1; i < m; i++) {
        if (i != -1) {
          number |= (0x80000000L >>> i);
        }

        verify.accept(address.apply(number) + "/" + m, true);
      }
    };

    for (int i = 0; i < 32; i++) {
      a.accept(i);
    }
  }

  /**
   * Tests {@link AddressRange#blockSize()}.
   */
  @Test
  public void testBlockSize() throws UnknownHostException {
    Assert.assertEquals(4294967296L, AddressRange.of("0.0.0.0/0").blockSize());

    Assert.assertEquals(2147483648L, AddressRange.of("128.0.0.0/1").blockSize());
    Assert.assertEquals(1073741824L, AddressRange.of("192.0.0.0/2").blockSize());
    Assert.assertEquals(536870912L, AddressRange.of("224.0.0.0/3").blockSize());
    Assert.assertEquals(268435456L, AddressRange.of("240.0.0.0/4").blockSize());
    Assert.assertEquals(134217728L, AddressRange.of("248.0.0.0/5").blockSize());
    Assert.assertEquals(67108864L, AddressRange.of("252.0.0.0/6").blockSize());
    Assert.assertEquals(33554432L, AddressRange.of("254.0.0.0/7").blockSize());
    Assert.assertEquals(16777216L, AddressRange.of("255.0.0.0/8").blockSize());

    Assert.assertEquals(8388608L, AddressRange.of("255.128.0.0/9").blockSize());
    Assert.assertEquals(4194304L, AddressRange.of("255.192.0.0/10").blockSize());
    Assert.assertEquals(2097152L, AddressRange.of("255.224.0.0/11").blockSize());
    Assert.assertEquals(1048576L, AddressRange.of("255.240.0.0/12").blockSize());
    Assert.assertEquals(524288L, AddressRange.of("255.248.0.0/13").blockSize());
    Assert.assertEquals(262144L, AddressRange.of("255.252.0.0/14").blockSize());
    Assert.assertEquals(131072L, AddressRange.of("255.254.0.0/15").blockSize());
    Assert.assertEquals(65536L, AddressRange.of("255.255.0.0/16").blockSize());

    Assert.assertEquals(32768L, AddressRange.of("255.255.128.0/17").blockSize());
    Assert.assertEquals(16384L, AddressRange.of("255.255.192.0/18").blockSize());
    Assert.assertEquals(8192L, AddressRange.of("255.255.224.0/19").blockSize());
    Assert.assertEquals(4096L, AddressRange.of("255.255.240.0/20").blockSize());
    Assert.assertEquals(2048L, AddressRange.of("255.255.248.0/21").blockSize());
    Assert.assertEquals(1024L, AddressRange.of("255.255.252.0/22").blockSize());
    Assert.assertEquals(512L, AddressRange.of("255.255.254.0/23").blockSize());
    Assert.assertEquals(256L, AddressRange.of("255.255.255.0/24").blockSize());

    Assert.assertEquals(128L, AddressRange.of("255.255.255.128/25").blockSize());
    Assert.assertEquals(64L, AddressRange.of("255.255.255.192/26").blockSize());
    Assert.assertEquals(32L, AddressRange.of("255.255.255.224/27").blockSize());
    Assert.assertEquals(16L, AddressRange.of("255.255.255.240/28").blockSize());
    Assert.assertEquals(8L, AddressRange.of("255.255.255.248/29").blockSize());
    Assert.assertEquals(4L, AddressRange.of("255.255.255.252/30").blockSize());
    Assert.assertEquals(2L, AddressRange.of("255.255.255.254/31").blockSize());
    Assert.assertEquals(1L, AddressRange.of("255.255.255.255/32").blockSize());
  }

  /**
   * Tests {@link AddressRange#equals(Object)}.
   */
  @Test
  public void testEquals() throws UnknownHostException {
    AddressRange range00 = AddressRange.of("10.0.0.0/8");
    AddressRange range01 = AddressRange.of("10.0.0.0/8");
    AddressRange range02 = AddressRange.of("10.10.0.0/16");
    AddressRange range03 = AddressRange.of("10.10.0.0/16");
    AddressRange range04 = AddressRange.of("10.10.10.0/24");
    AddressRange range05 = AddressRange.of("10.10.10.0/24");
    AddressRange range06 = AddressRange.of("10.10.10.10/32");
    AddressRange range07 = AddressRange.of("10.10.10.10/32");
    AddressRange range08 = AddressRange.of("127.0.0.0/31");
    AddressRange range09 = AddressRange.of("127.0.0.0/31");

    {
      AddressRange current = range00;

      Assert.assertEquals(current, range00);
      Assert.assertEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range01;

      Assert.assertEquals(current, range00);
      Assert.assertEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range02;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertEquals(current, range02);
      Assert.assertEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range03;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertEquals(current, range02);
      Assert.assertEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range04;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertEquals(current, range04);
      Assert.assertEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range05;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertEquals(current, range04);
      Assert.assertEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range06;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertEquals(current, range06);
      Assert.assertEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range07;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertEquals(current, range06);
      Assert.assertEquals(current, range07);
      Assert.assertNotEquals(current, range08);
      Assert.assertNotEquals(current, range09);
    }

    {
      AddressRange current = range08;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertEquals(current, range08);
      Assert.assertEquals(current, range09);
    }

    {
      AddressRange current = range09;

      Assert.assertNotEquals(current, range00);
      Assert.assertNotEquals(current, range01);
      Assert.assertNotEquals(current, range02);
      Assert.assertNotEquals(current, range03);
      Assert.assertNotEquals(current, range04);
      Assert.assertNotEquals(current, range05);
      Assert.assertNotEquals(current, range06);
      Assert.assertNotEquals(current, range07);
      Assert.assertEquals(current, range08);
      Assert.assertEquals(current, range09);
    }
  }

  /**
   * Tests {@link AddressRange#matches(InetAddress)}.
   */
  @Test
  public void testMatch() throws UnknownHostException {
    AddressRange range01 = AddressRange.of("10.0.0.0/8");
    AddressRange range02 = AddressRange.of("10.10.0.0/16");
    AddressRange range03 = AddressRange.of("10.10.10.0/24");
    AddressRange range04 = AddressRange.of("10.10.10.10/32");
    AddressRange range05 = AddressRange.of("127.0.0.0/31");

    Inet4Address address01 = ((Inet4Address) InetAddress.getByName("10.0.0.0"));
    Inet4Address address02 = ((Inet4Address) InetAddress.getByName("10.0.0.1"));
    Inet4Address address03 = ((Inet4Address) InetAddress.getByName("10.0.0.2"));
    Inet4Address address04 = ((Inet4Address) InetAddress.getByName("10.0.0.255"));

    Inet4Address address05 = ((Inet4Address) InetAddress.getByName("10.10.0.0"));
    Inet4Address address06 = ((Inet4Address) InetAddress.getByName("10.10.0.1"));
    Inet4Address address07 = ((Inet4Address) InetAddress.getByName("10.10.0.2"));
    Inet4Address address08 = ((Inet4Address) InetAddress.getByName("10.10.0.255"));

    Inet4Address address09 = ((Inet4Address) InetAddress.getByName("10.10.10.0"));
    Inet4Address address10 = ((Inet4Address) InetAddress.getByName("10.10.10.1"));
    Inet4Address address11 = ((Inet4Address) InetAddress.getByName("10.10.10.2"));
    Inet4Address address12 = ((Inet4Address) InetAddress.getByName("10.10.10.255"));

    Inet4Address address13 = ((Inet4Address) InetAddress.getByName("10.10.10.10"));
    Inet4Address address14 = ((Inet4Address) InetAddress.getByName("127.0.0.0"));
    Inet4Address address15 = ((Inet4Address) InetAddress.getByName("127.0.0.1"));
    Inet4Address address16 = ((Inet4Address) InetAddress.getByName("127.0.0.2"));

    {
      AddressRange currentRange = range01;

      Assert.assertTrue(currentRange.matches(address01));
      Assert.assertTrue(currentRange.matches(address02));
      Assert.assertTrue(currentRange.matches(address03));
      Assert.assertTrue(currentRange.matches(address04));

      Assert.assertTrue(currentRange.matches(address05));
      Assert.assertTrue(currentRange.matches(address06));
      Assert.assertTrue(currentRange.matches(address07));
      Assert.assertTrue(currentRange.matches(address08));

      Assert.assertTrue(currentRange.matches(address09));
      Assert.assertTrue(currentRange.matches(address10));
      Assert.assertTrue(currentRange.matches(address11));
      Assert.assertTrue(currentRange.matches(address12));

      Assert.assertTrue(currentRange.matches(address13));
      Assert.assertFalse(currentRange.matches(address14));
      Assert.assertFalse(currentRange.matches(address15));
      Assert.assertFalse(currentRange.matches(address16));
    }

    {
      AddressRange currentRange = range02;

      Assert.assertFalse(currentRange.matches(address01));
      Assert.assertFalse(currentRange.matches(address02));
      Assert.assertFalse(currentRange.matches(address03));
      Assert.assertFalse(currentRange.matches(address04));

      Assert.assertTrue(currentRange.matches(address05));
      Assert.assertTrue(currentRange.matches(address06));
      Assert.assertTrue(currentRange.matches(address07));
      Assert.assertTrue(currentRange.matches(address08));

      Assert.assertTrue(currentRange.matches(address09));
      Assert.assertTrue(currentRange.matches(address10));
      Assert.assertTrue(currentRange.matches(address11));
      Assert.assertTrue(currentRange.matches(address12));

      Assert.assertTrue(currentRange.matches(address13));
      Assert.assertFalse(currentRange.matches(address14));
      Assert.assertFalse(currentRange.matches(address15));
      Assert.assertFalse(currentRange.matches(address16));
    }

    {
      AddressRange currentRange = range03;

      Assert.assertFalse(currentRange.matches(address01));
      Assert.assertFalse(currentRange.matches(address02));
      Assert.assertFalse(currentRange.matches(address03));
      Assert.assertFalse(currentRange.matches(address04));

      Assert.assertFalse(currentRange.matches(address05));
      Assert.assertFalse(currentRange.matches(address06));
      Assert.assertFalse(currentRange.matches(address07));
      Assert.assertFalse(currentRange.matches(address08));

      Assert.assertTrue(currentRange.matches(address09));
      Assert.assertTrue(currentRange.matches(address10));
      Assert.assertTrue(currentRange.matches(address11));
      Assert.assertTrue(currentRange.matches(address12));

      Assert.assertTrue(currentRange.matches(address13));
      Assert.assertFalse(currentRange.matches(address14));
      Assert.assertFalse(currentRange.matches(address15));
      Assert.assertFalse(currentRange.matches(address16));
    }

    {
      AddressRange currentRange = range04;

      Assert.assertFalse(currentRange.matches(address01));
      Assert.assertFalse(currentRange.matches(address02));
      Assert.assertFalse(currentRange.matches(address03));
      Assert.assertFalse(currentRange.matches(address04));

      Assert.assertFalse(currentRange.matches(address05));
      Assert.assertFalse(currentRange.matches(address06));
      Assert.assertFalse(currentRange.matches(address07));
      Assert.assertFalse(currentRange.matches(address08));

      Assert.assertFalse(currentRange.matches(address09));
      Assert.assertFalse(currentRange.matches(address10));
      Assert.assertFalse(currentRange.matches(address11));
      Assert.assertFalse(currentRange.matches(address12));

      Assert.assertTrue(currentRange.matches(address13));
      Assert.assertFalse(currentRange.matches(address14));
      Assert.assertFalse(currentRange.matches(address15));
      Assert.assertFalse(currentRange.matches(address16));
    }

    {
      AddressRange currentRange = range05;

      Assert.assertFalse(currentRange.matches(address01));
      Assert.assertFalse(currentRange.matches(address02));
      Assert.assertFalse(currentRange.matches(address03));
      Assert.assertFalse(currentRange.matches(address04));

      Assert.assertFalse(currentRange.matches(address05));
      Assert.assertFalse(currentRange.matches(address06));
      Assert.assertFalse(currentRange.matches(address07));
      Assert.assertFalse(currentRange.matches(address08));

      Assert.assertFalse(currentRange.matches(address09));
      Assert.assertFalse(currentRange.matches(address10));
      Assert.assertFalse(currentRange.matches(address11));
      Assert.assertFalse(currentRange.matches(address12));

      Assert.assertFalse(currentRange.matches(address13));
      Assert.assertTrue(currentRange.matches(address14));
      Assert.assertTrue(currentRange.matches(address15));
      Assert.assertFalse(currentRange.matches(address16));
    }
  }

  /**
   * Tests {@link AddressRange#of(String)}.
   */
  @Test
  public void testParse() throws UnknownHostException {
    AddressRange4 address00 = ((AddressRange4) AddressRange.of("10.0.0.0/8"));
    AddressRange4 address01 = ((AddressRange4) AddressRange.of("10.10.0.0/16"));
    AddressRange4 address02 = ((AddressRange4) AddressRange.of("10.10.10.0/24"));
    AddressRange4 address03 = ((AddressRange4) AddressRange.of("10.10.10.10/32"));

    {
      Assert.assertEquals(InetAddress.getByName("10.0.0.0"), address00.base());
      Assert.assertEquals(8, address00.prefixLength());
    }

    {
      Assert.assertEquals(InetAddress.getByName("10.10.0.0"), address01.base());
      Assert.assertEquals(16, address01.prefixLength());
    }

    {
      Assert.assertEquals(InetAddress.getByName("10.10.10.0"), address02.base());
      Assert.assertEquals(24, address02.prefixLength());
    }

    {
      Assert.assertEquals(InetAddress.getByName("10.10.10.10"), address03.base());
      Assert.assertEquals(32, address03.prefixLength());
    }
  }

  /**
   * Tests {@link AddressRange#toString()}.
   */
  @Test
  public void testToString() throws UnknownHostException {
    Assert.assertEquals("10.0.0.0/8", AddressRange.of("10.0.0.0/8").toString());
    Assert.assertEquals("10.10.0.0/16", AddressRange.of("10.10.0.0/16").toString());
    Assert.assertEquals("10.10.10.0/24", AddressRange.of("10.10.10.0/24").toString());
    Assert.assertEquals("10.10.10.10/32", AddressRange.of("10.10.10.10/32").toString());
    Assert.assertEquals("127.0.0.0/31", AddressRange.of("127.0.0.0/31").toString());
  }
}
