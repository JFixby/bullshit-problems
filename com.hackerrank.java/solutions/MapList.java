import java.util.ArrayList;

import com.jfixby.scarabei.api.collections.Collections;

public class MapList {

	public MapList () {
	}

	public final ArrayList<Mapping> list = new ArrayList<Mapping>();

	public Mapping find (final String url, final String transfer) {
		for (int i = 0; i < this.list.size(); i++) {
			final Mapping e = this.list.get(i);
			if (e.originalURL.equals(url)) {
				return e;
			}
		}
		return null;
	}

	public boolean put (final String url, final String transfer) {
		final Mapping e = this.find(url, transfer);
		if (e != null) {
			Collections.newList(this.list).print("register");
// throw new Error("already stored " + e);
			return false;
		}
		final Mapping map = new Mapping();
		map.originalURL = url;
		map.newURL = transfer;
		this.list.add(map);
		return true;
	}

	public void clear () {
		this.list.clear();
	}

	public int size () {
		return this.list.size();
	}

	public Mapping get (final int i) {
		return this.list.get(i);
	}

}
