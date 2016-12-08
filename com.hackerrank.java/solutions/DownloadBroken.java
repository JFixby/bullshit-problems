import java.io.IOException;
import java.net.MalformedURLException;

import com.jfixby.cmns.api.desktop.DesktopSetup;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.json.Json;
import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.net.http.Http;
import com.jfixby.cmns.api.net.http.HttpURL;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.util.path.RelativePath;

public class DownloadBroken {

	public static void main (final String[] args) throws MalformedURLException, IOException {
		DesktopSetup.deploy();
		Json.installComponent("com.jfixby.cmns.adopted.gdx.json.RedJson");

		final File fileBroken = LocalFileSystem.ApplicationHome().child("broken-replacements.json");
		Json.invoke();

		final File uploads = LocalFileSystem.ApplicationHome().child("download");
		uploads.makeFolder();

		final MapList broken = retrive(fileBroken);
		for (int i = 0; i < broken.size(); i++) {
			final Mapping e = broken.get(i);

			final String urlString = e.originalURL;
			final HttpURL url = Http.newURL(urlString);

			final RelativePath relative = url.getRelativePath();

			L.d("relative", relative);

// final HttpConnection connection = Http.newConnection(url);

// connection.open();

// final HttpConnectionInputStream input_stream = connection.getInputStream();
// final ByteArray data = input_stream.readAll();
		}

	}

	private static MapList retrive (final File fileValid) throws IOException {
		MapList valid;
		if (!fileValid.exists()) {
			valid = new MapList();
		} else {
			String json;
			try {
				json = fileValid.readToString();
				valid = Json.deserializeFromString(MapList.class, json);
			} catch (final IOException e) {
				e.printStackTrace();
				Sys.exit();
				valid = null;
			}
		}
		return valid;
	}

}
