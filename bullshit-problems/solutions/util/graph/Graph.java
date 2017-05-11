
package util.graph;

public interface Graph<T> {

	void addVertex (String vertexName);

	void addEdge (final String vertexFrom, final String vertexTo, final T attachment);

	void print ();

}
