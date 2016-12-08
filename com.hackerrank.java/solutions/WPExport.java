import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;

import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.Map;
import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.util.JUtils;
import com.jfixby.cmns.api.util.path.RelativePath;

public class WPExport {

	public static void main (final String[] args) throws Throwable {

		DesktopSetup.deploy();
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

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
		final File fileBroken = LocalFileSystem.ApplicationHome().child("broken-replacements.json");
		Json.invoke();
		final MapList valid = retrive(fileValid);
		final MapList broken = retrive(fileBroken);
		broken.clear();

		final HashSet<String> processed = new HashSet<>();
		while (end < data.length() && start >= max) {
			final String prefix = "href=\"http";
			start = data.indexOf(prefix, end);
			if (start < 0) {
				break;
			}
			end = data.indexOf("\"", start + prefix.length());
			max = Math.max(start, max);
			final String url = data.substring(start + 6, end);
			if (url.contains("//jfix.by/") && url.contains("uploads") && !processed.contains(processed)) {
				processed.add(url);
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

				if (null != valid.find(url, transfer)) {
					continue;
				}
				final int code = checkCode(transfer);
				if (code != 200) {
					final int original = checkCode(url);

					if (original == 200) {
						L.d(url + " >>>> " + transfer);
						L.d("    [" + code + "] ");
						failed.put(url, transfer);
						if (!broken.put(url, transfer)) {
// Collections.newList(processed).print("processed");
// Sys.exit();
						}

					} else {
// L.d(" bad origin");
					}
				} else {
					urlsToFix.put(url, transfer);
					valid.put(url, transfer);
				}

			}

// L.d(start);
		}

// urlsToFix.sortKeys();
		urlsToFix.print("transfer Ok to go");
		failed.sortKeys();
		failed.print("failed");

		fileValid.writeString(Json.serializeToString(valid).toString());
		fileBroken.writeString(Json.serializeToString(broken).toString());

// L.d(data);
	}

	private static MapList retrive (final File fileValid) throws IOException {
		MapList valid;
		if (!fileValid.exists()) {
			valid = new MapList();
		} else {
			String json;

			json = fileValid.readToString();
			valid = Json.deserializeFromString(MapList.class, json);

		}
		return valid;
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
