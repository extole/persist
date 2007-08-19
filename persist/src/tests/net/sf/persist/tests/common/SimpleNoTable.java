
//$Id: Simple.java 7 2007-08-17 19:32:18Z jcamaia $

package net.sf.persist.tests.common;

import net.sf.persist.annotations.NoTable;

@NoTable
public class SimpleNoTable {

	private long id;
	private String stringCol;
	private long intCol;

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public String getStringCol() { return stringCol; }
	public void setStringCol(String stringCol) { this.stringCol = stringCol; }

	public long getIntCol() { return intCol; }
	public void setIntCol(long intCol) { this.intCol = intCol; }
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		final SimpleNoTable other = (SimpleNoTable) obj;

		// do not compare ids -- will be tested in the autogeneratedkeys tests
		// if (id != other.id) return false;

		if (intCol != other.intCol)
			return false;
		if (stringCol == null) {
			if (other.stringCol != null)
				return false;
		} else if (!stringCol.equals(other.stringCol))
			return false;

		return true;
	}

	public String toString() {
		return "id=" + id + " intCol=" + intCol + " stringCol=" + stringCol;
	}
	

}

