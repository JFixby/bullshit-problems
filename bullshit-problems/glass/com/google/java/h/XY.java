
package com.google.java.h;

public class XY {

	public int x;
	public int y;

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.x;
		result = prime * result + this.y;
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final XY other = (XY)obj;
		if (this.x != other.x) {
			return false;
		}
		if (this.y != other.y) {
			return false;
		}
		return true;
	}

}

class S {

	String s;

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.s == null) ? 0 : this.s.hashCode());
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final S other = (S)obj;
		if (this.s == null) {
			if (other.s != null) {
				return false;
			}
		} else if (!this.s.equals(other.s)) {
			return false;
		}
		return true;
	}

}

class FG {

	double f;
	double g;

	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.f);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.g);
		result = prime * result + (int)(temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals (final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final FG other = (FG)obj;
		if (Double.doubleToLongBits(this.f) != Double.doubleToLongBits(other.f)) {
			return false;
		}
		if (Double.doubleToLongBits(this.g) != Double.doubleToLongBits(other.g)) {
			return false;
		}
		return true;
	}

}
