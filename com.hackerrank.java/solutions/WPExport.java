import java.net.HttpURLConnection;
import java.net.URL;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.log.L;
import com.jfixby.red.desktop.DesktopSetup;

public class WPExport {

	public static void main (final String[] args) throws Throwable {

		DesktopSetup.deploy();

// final String java_path = "D:\\[DEV]\\jfixby.wordpress.2016-07-19.xml";
		final String java_path = "D:\\[DEV]\\posts-only-jfixby.wordpress.2016-07-19.xml";

		final File inputfile = LocalFileSystem.newFile(java_path);
		final String data = inputfile.readToString();

		int start = 0;
		int end = 0;
		int max = 0;
// href="http://jfixbi.wordpress.com/feed/"
		final String wpPrefix = "https://jfixbi.files.wordpress.com/";
		final String byPrefix = "http://jfix.by/wp-content/uploads/";
		final Map<String, String> urlsToFix = Collections.newMap();
		final Map<String, String> failed = Collections.newMap();
		while (end < data.length() && start >= max) {
			final String prefix = "href=\"http";
			start = data.indexOf(prefix, end);
			if (start < 0) {
				break;
			}
			end = data.indexOf("\"", start + prefix.length());

			final String url = data.substring(start + 6, end);
			if (url.contains("/jfix.by/") || !true) {
				final String transfer = url.replaceAll(byPrefix, wpPrefix).toLowerCase();
//
				final int code = checkTransfer(transfer);
				if (code != 200) {
					L.d("[" + code + "] " + url + " >>>> " + transfer);
					failed.put(url, transfer);
				} else {
					urlsToFix.put(url, transfer);
				}

			}
			max = Math.max(start, max);
// L.d(start);
		}

// urlsToFix.sortKeys();
		urlsToFix.print("transfer Ok to go");
		failed.print("failed");

// L.d(data);
	}

	private static int checkTransfer (final String transferURL) throws Throwable {
		final URL url = new URL(transferURL);
		final HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
		urlConn.connect();

// L.d(transferURL, urlConn.getResponseCode());
		return urlConn.getResponseCode();

	}

}
