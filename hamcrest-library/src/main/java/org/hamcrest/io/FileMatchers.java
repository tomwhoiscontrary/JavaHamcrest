package org.hamcrest.io;

import org.hamcrest.Description;
import org.hamcrest.FunctionMatcher;
import org.hamcrest.FunctionMatcher.Feature;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.core.IsEqual.equalTo;

public final class FileMatchers {

    public static Matcher<File> anExistingDirectory() { return AN_EXISTING_DIRECTORY; }

    public static Matcher<File> anExistingFileOrDirectory() { return EXISTING_FILE_OR_DIRECTORY; }

    public static Matcher<File> anExistingFile() {
        return EXISTING_FILE;
    }

    public static Matcher<File> aReadableFile() {
        return READABLE_FILE;
    }

    public static Matcher<File> aWritableFile() { return WRITEABLE_FILE; }

    public static Matcher<File> aFileWithSize(long size) {
        return aFileWithSize(equalTo(size));
    }

    public static Matcher<File> aFileWithSize(final Matcher<Long> expected) {
        return new FunctionMatcher<>("A file with size", "size", expected, LENGTH);
    }

    public static Matcher<File> aFileNamed(final Matcher<String> expected) {
        return new FunctionMatcher<>("A file with name", "name", expected, NAME);
    }

    public static Matcher<File> aFileWithCanonicalPath(final Matcher<String> expected) {
        return new FunctionMatcher<>("A file with canonical path", "path", expected, CANONICAL_PATH);
    }

    public static Matcher<File> aFileWithAbsolutePath(final Matcher<String> expected) {
        return new FunctionMatcher<>("A file with absolute path", "path", expected, ABSOLUTE_PATH);
    }

    private static final Matcher<File> AN_EXISTING_DIRECTORY = fileChecker("an existing directory", "is not a directory",
        new FileStatus() { @Override public Boolean from(File actual) { return actual.isDirectory(); } });

    private static final Matcher<File> EXISTING_FILE_OR_DIRECTORY = fileChecker("an existing file or directory", "does not exist",
        new FileStatus() { @Override public Boolean from(File actual) { return actual.exists(); } });

    public static final Matcher<File> EXISTING_FILE = fileChecker("an existing File", "is not a file",
        new FileStatus() { @Override public Boolean from(File actual) { return actual.isFile(); } });

    public static final Matcher<File> READABLE_FILE = fileChecker("a readable File", "cannot be read",
        new FileStatus() { @Override public Boolean from(File actual) { return actual.canRead(); } });

    public static final Matcher<File> WRITEABLE_FILE = fileChecker("a writable File", "cannot be written to",
        new FileStatus() { @Override public Boolean from(File actual) { return actual.canWrite(); } });


    public static interface FileStatus extends Feature<File, Boolean> { }

    public static Matcher<File> fileChecker(final String successDescription, final String failureDescription, final FileStatus fileStatus) {
        return new TypeSafeDiagnosingMatcher<File>() {
            public boolean matchesSafely(File actual, Description mismatchDescription) {
                final boolean result = fileStatus.from(actual);
                if (!result) {
                    mismatchDescription.appendText(failureDescription);
                }
                return result;
            }

            public void describeTo(Description description) {
                description.appendText(successDescription);
            }
        };
    }

    private static final Feature<File, String> ABSOLUTE_PATH = new Feature<File, String>() {
        @Override public String from(File actual) { return actual.getAbsolutePath(); }
    };
    public static final Feature<File, Long> LENGTH = new Feature<File, Long>() {
        @Override public Long from(File actual) { return actual.length(); }
    };

    public static final Feature<File, String> NAME = new Feature<File, String>() {
        @Override public String from(File actual) { return actual.getName(); }
    };

    public static final Feature<File, String> CANONICAL_PATH = new Feature<File, String>() {
        @Override public String from(File actual) {
            try {
                return actual.getCanonicalPath();
            } catch (IOException e) {
                return "Exception: " + e.getMessage();
            }
        }
    };
}
