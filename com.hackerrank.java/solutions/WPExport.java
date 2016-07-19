import java.net.HttpURLConnection;
import java.net.URL;

import com.jfixby.cmns.adopted.gdx.json.RedJson;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.api.util.path.RelativePath;
import com.jfixby.red.desktop.DesktopSetup;

public class WPExport {

	public static void main (final String[] args) throws Throwable {

		DesktopSetup.deploy();
		Json.installComponent(new RedJson());

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

		final File fileValid = LocalFileSystem.ApplicationHome().child("valid-replacements.json");
		Json.invoke();
		final MapList replace;
		if (!fileValid.exists()) {
			replace = new MapList();
		} else {
			final String json = fileValid.readToString();
			replace = Json.deserializeFromString(MapList.class, json);
			if (replace == null) {
				Sys.exit();
			}
		}

		while (end < data.length() && start >= max) {
			final String prefix = "href=\"http";
			start = data.indexOf(prefix, end);
			if (start < 0) {
				break;
			}
			end = data.indexOf("\"", start + prefix.length());
			max = Math.max(start, max);
			final String url = data.substring(start + 6, end);
			if (url.contains("//jfix.by/") && url.contains("uploads")) {
				String transfer = url.replaceAll(byPrefix, wpPrefix).toLowerCase();
				final RelativePath relative = JUtils.newRelativePath(transfer);
				String name = relative.nameWithoutExtension();
				name = name.replaceAll("\\.", "-");
				final String ext = relative.getExtension();
				if (ext == null) {
					continue;
				}
				transfer = relative.parent() + "/" + name + "." + ext;
				transfer = transfer.replaceAll(":/jfix", "://jfix");
// L.d(url + " >>>> " + transfer);

				if (null != replace.contains(url, transfer)) {
					continue;
				}
				final int code = checkCode(transfer);
				if (code != 200) {
					final int original = checkCode(url);

					if (original == 200) {
						L.d(url + " >>>> " + transfer);
						L.d("    [" + code + "] ");
						failed.put(url, transfer);
					} else {
// L.d(" bad origin");
					}
				} else {
					urlsToFix.put(url, transfer);
					replace.put(url, transfer);
				}

			}

// L.d(start);
		}

// urlsToFix.sortKeys();
		urlsToFix.print("transfer Ok to go");
		failed.sortKeys();
		failed.print("failed");

		fileValid.writeString(Json.serializeToString(replace).toString());

// L.d(data);
	}

	private static int checkCode (final String transferURL) {
		try {
			final URL url = new URL(transferURL);
			final HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
			urlConn.setRequestMethod("HEAD");
			urlConn.setConnectTimeout(5000); // set timeout to 5 seconds
			urlConn.connect();

// L.d(transferURL, urlConn.getResponseCode());
			final int code = urlConn.getResponseCode();
			return code;
		} catch (final Throwable t) {
			t.printStackTrace();
			return -2;
		}
	}

}
