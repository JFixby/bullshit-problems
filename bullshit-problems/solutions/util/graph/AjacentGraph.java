
package util.graph;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.util.JUtils;

public class AjacentGraph<T> implements Graph<T> {

	final Map<String, List<String>> ajacentList = Collections.newMap();
	final Map<String, T> attachments = Collections.newMap();

	@Override
	public void addVertex (final String vertexName) {
		this.ajacentList.put(vertexName, Collections.newList());
	}

	@Override
	public void addEdge (final String vertexFrom, final String vertexTo, final T attachment) {
		this.ajacentList.get(vertexFrom).add(vertexTo);
		this.attachments.put(edge(vertexFrom, vertexTo), attachment);
	}

	@Override
	public void print () {
		final String tag = "";
		final StringBuilder string = new StringBuilder();
		// final String canonocal_name = "Map[]";
		final int n = this.ajacentList.size();

		final int indent = tag.length() + 3;
		string.append("---[" + tag + "](" + this.ajacentList.size() + ")-------------------------\n");
		final String indent_str = JUtils.prefix(" ", indent);
		for (int i = 0; i < n; i++) {
			string.append(indent_str + "(" + i + ") " + this.ajacentList.getKeyAt(i) + " :-> "
				+ this.toString(this.ajacentList.getKeyAt(i), this.ajacentList.getValueAt(i)) + "\n");
		}

		L.d(string.toString());
	}

	private String toString (final String vertexFrom, final List<String> verticesTo) {
		final int iMax = verticesTo.size() - 1;
		if (iMax == -1) {
			return "[]";
		}

		final StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++) {
			final String vertexTo = verticesTo.getElementAt(i);

			b.append(vertexTo);
			final String key = edge(vertexFrom, vertexTo);
			final T att = this.attachments.get(key);
			if (att != null) {
				b.append("(");
				b.append(att);
				b.append(")");
			}
			if (i == iMax) {
				return b.append(']').toString();
			}
			b.append(", ");
		}

	}

	static private String edge (final String vertexFrom, final String vertexTo) {
		return vertexFrom + "-" + vertexTo;
	}

}
