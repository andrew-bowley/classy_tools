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
package au.com.cybersearch2.example;

import au.com.cybersearch2.classyapp.ContextModule;
import au.com.cybersearch2.classyapp.TestRoboApplication;
import au.com.cybersearch2.classyinject.DI;

/**
 * AndroidManyToManyTest
 * @author Andrew Bowley
 * 23 Sep 2014
 */
public class AndroidManyToMany extends ManyToManyMain
{
    AndroidManyToManyModule androidManyToManyModule;
    
    @Override
    protected void createObjectGraph()
    {
        // Set up dependency injection, which creates an ObjectGraph from a ManyToManyModule configuration object
        androidManyToManyModule = new AndroidManyToManyModule();
        new DI(androidManyToManyModule, new ContextModule(TestRoboApplication.getTestInstance())).validate();
    }
}
