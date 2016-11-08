
package com.jfixby.hrank;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.assets.Names;
import com.jfixby.cmns.api.collections.CollectionScanner;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.file.ChildrenList;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.FileInputStream;
import com.jfixby.cmns.api.file.FileOutputStream;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.log.L;

public class SolutionRunner<T extends AbstractSolution> {

	private final Class<T> solutionClass;

	public SolutionRunner (final Class<T> solutionClass) {
		this.solutionClass = solutionClass;

	}

	public void run () throws Throwable {

		final AssetID name = Names.newAssetID(this.solutionClass.getCanonicalName()).parent();
		final String testName = name.toString();
		L.d("running solution", testName);
		final File home = LocalFileSystem.ApplicationHome();
		final File input_folder = home.child("in").child(testName);

		L.d("reading input", input_folder);
		final ChildrenList testInputs = input_folder.listDirectChildren();
		testInputs.print("tests");

		final CollectionScanner<File> testsScanner = (inputFile, i) -> {
			final String testFileName = inputFile.getName();
			final File expectedOutputFile = home.child("out-expected").child(testName).child(testFileName);
			final File actualOutputFile = home.child("out-actual").child(testName).child(testFileName);
			try {
				if (!expectedOutputFile.exists()) {
					Err.reportError("Missing expected output file " + expectedOutputFile);
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
			actualOutputFile.parent().makeFolder();

			final TestResult result = this.runTest(inputFile, expectedOutputFile, actualOutputFile);
			if (result.isPassed()) {
				result.print(testFileName);
			} else {
				result.print(testFileName);
			}

		};
		Collections.scanCollection(testInputs, testsScanner);

	}

	@SuppressWarnings("static-access")
	private TestResult runTest (final File inputFile, final File expectedOutputFile, final File actualOutputFile) {
		final FileInputStream is = inputFile.newInputStream();
		final FileOutputStream os = actualOutputFile.newOutputStream();
		is.open();
		os.open();
		try {
			final T solution = this.solutionClass.newInstance();

			solution.input = is.toJavaInputStream();

			final OutputStream jos = os.toJavaOutputStream();
			solution.output = new PrintStream(jos);

			solution.run(new String[0]);

			solution.output.flush();

			return this.compareResults(inputFile, expectedOutputFile, actualOutputFile);

		} catch (final Throwable e) {
			e.printStackTrace();
// IO.forceClose(solution.output);

		}
		os.close();
		is.close();
		final TestResult result = this.compareResults(inputFile, expectedOutputFile, actualOutputFile);
		return result;
	}

	private TestResult compareResults (final File inputFile, final File expectedOutputFile, final File actualOutputFile) {
		try {
			final String input = inputFile.readToString();
			final String expected = expectedOutputFile.readToString();
			final String actual = actualOutputFile.readToString();
			final TestResult result = new TestResult(input, expected, actual);
			return result;
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T extends AbstractSolution> void run (final Class<T> class1) throws Throwable {
		final SolutionRunner<T> runner = new SolutionRunner<>(class1);
		runner.run();
	}

}
