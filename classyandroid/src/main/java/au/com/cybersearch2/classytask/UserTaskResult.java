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
// Original copyright - code mostly unchanged except for promotion to separate class
/*
 * Copyright (C) 2008 The Android Open Source Project, Romain Guy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package au.com.cybersearch2.classytask;

/**
 * UserTaskResult
 * Message object used for posting progress updates
 * @author Romain Guy
 * 25/04/2014
 */
public class UserTaskResult<DATA>
{
    @SuppressWarnings("rawtypes")
    final UserTask task;
    final DATA[] data;

    /**
     * Create UserTaskResult object
     * @param task UserTask
     * @param data Object array of generic type "DATA"
     */
    @SuppressWarnings("rawtypes")
    public UserTaskResult(UserTask task, DATA... data) 
    {
        this.task = task;
        this.data = data;
    }
}
