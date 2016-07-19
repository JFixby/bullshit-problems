import java.util.Vector;

public class MapList {

	public MapList () {
	}

	public final Vector<Mapping> list = new Vector<Mapping>();

	public Mapping contains (final String url, final String transfer) {
		for (int i = 0; i < this.list.size(); i++) {
			final Mapping e = this.list.get(i);
			if (e.originalURL.equals(url)) {
				return e;
			}
		}
		return null;
	}

	public void put (final String url, final String transfer) {
		final Mapping e = this.contains(url, transfer);
		if (e != null) {
			throw new Error("already stored " + e);
		}
		final Mapping map = new Mapping();
		map.originalURL = url;
		map.newURL = transfer;
		this.list.add(map);
	}

}
