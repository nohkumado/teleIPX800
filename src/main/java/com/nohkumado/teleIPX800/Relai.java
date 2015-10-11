package com.nohkumado.teleIPX800;
/*
 Copyright (c) 2015 Bruno Boettcher. All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of Foobar.

 Foobar is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 Foobar is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with @filename.  If not, see <http://www.gnu.org/licenses/>.
 */

/** 
 descriptor for one of the output relais of an IPX800
 */
public class Relai
{
	protected int pos;
	protected String name;
	protected String label;

	/**
	 represents one relai,
	 @argument pos position of the relai (1-32)
	 @argument name the programmatic name of the relai
	 @argument label the label to display when referring to this relai
	 */
	public Relai(int p, String n, String l)
	{
		pos = p;
		name = n;
		label = l;
	}
	/**
	 return the label for this relai

	 @return label
	 */
	public String label()
	{return label;}
	/**
	 return the position for this relai

	 @return position
	 */
	public int pos()
	{return pos;}
}
