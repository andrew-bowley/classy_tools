/**
    Copyright (C) 2014  www.cybersearch2.com.au

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/> */
package au.com.cybersearch2.classyapp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import au.com.cybersearch2.classybean.BeanMapTest;
import au.com.cybersearch2.classybean.BeanUtilTest;
import au.com.cybersearch2.classyinject.DI_Test;
import au.com.cybersearch2.classyinject.ObjectGraphManagerTest;

/**
 * JUnitTestSuite
 * @author Andrew Bowley
 * 19/06/2014
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    BeanMapTest.class,
    BeanUtilTest.class,
    DI_Test.class,
    ObjectGraphManagerTest.class
})
public class JUnitTestSuite 
{   
}


