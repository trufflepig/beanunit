/*
 * Copyright 2011 Ivo Woltring
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.ivonet.beanunit;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import builder.AddressDto;
import immutable.BusinessLocationDto;
import immutable.Employee;
import immutable.InventoryDto;
import immutable.NotImmutableBuildingDto;
import immutable.Person;

import static nl.ivonet.beanunit.Asserter.registerTypeAndDefaultArgument;
import static nl.ivonet.beanunit.Asserter.resetToDefaultTypes;
import static nl.ivonet.beanunit.ConstructedBeanAsserter.assertBean;
import static nl.ivonet.beanunit.ConstructedBeanAsserter.assertEqualsHashCode;
import static nl.ivonet.beanunit.ConstructedBeanAsserter.assertGettersOnConstructorImmutableObject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the {@link ConstructedBeanAsserter} class.
 *
 * @author Ivo Woltring
 */
public class ConstructedBeanAsserterTest {
    @Before
    public void setUp() throws Exception {
        registerTypeAndDefaultArgument(AddressDto.class, new AddressDto.Builder().setCity("c").build());
        registerTypeAndDefaultArgument(BigDecimal.class, new BigDecimal(42));

    }

    @After
    public void tearDown() throws Exception {
        resetToDefaultTypes();
    }

    //The following tests demonstrate the how to use beanunit

    @Test
    public void testBean() throws Exception {
        assertBean(Person.class);
    }

    @Test(expected = AssertionError.class)
    public void testBeanWrong() throws Exception {
        assertBean(BusinessLocationDto.class);
    }

    @Test
    public void testCreateObject() throws Exception {
        final Employee employee = ConstructedBeanAsserter.createObject(Employee.class);
        assertNotNull(employee);
        assertEquals("Employee{id='String'}", employee.toString());
    }

    //Tests below are to test the code itself not to demonstrate the usage

    @Test(expected = AssertionError.class)
    public void testImmutableObjectThatIsNotImmutable() {
        assertGettersOnConstructorImmutableObject(NotImmutableBuildingDto.class);
    }

    @Test
    public void testImmutableDto() throws Exception {
        assertGettersOnConstructorImmutableObject(BusinessLocationDto.class);
    }

    @Test
    public void testExcludePropertiesFromTest() throws Exception {
        assertGettersOnConstructorImmutableObject(InventoryDto.class, "insuredAmount");
    }

    @Test
    public void testExcludePropertiesFromTestNoExclusions() throws Exception {
        assertGettersOnConstructorImmutableObject(InventoryDto.class, "insuredAmount");
    }

    @Test
    public void testEqualsHashCode() throws Exception {
        assertGettersOnConstructorImmutableObject(InventoryDto.class);
        assertEqualsHashCode(InventoryDto.class);
    }

    @Test(expected = AssertionError.class)
    public void testNoHashCode() throws Exception {
        assertEqualsHashCode(BusinessLocationDto.class);
    }

    @Test(expected = AssertionError.class)
    public void testNotOverriddenInSameClassEqualsHashCode() throws Exception {
        assertEqualsHashCode(Employee.class);
    }

}
