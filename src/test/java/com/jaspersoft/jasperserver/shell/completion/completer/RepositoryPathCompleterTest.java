package com.jaspersoft.jasperserver.shell.completion.completer;

import jline.console.completer.Completer;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.testng.Assert.assertSame;

@Test
/*** Unit tests for {@link RepositoryPathCompleter} ***/
public class RepositoryPathCompleterTest {

    private Completer completer;

    @BeforeMethod
    public void before() {
        completer = new RepositoryPathCompleter();
        RepositoryPathCompleter.resources = asList("/a/b/c/d/e", "/a/x/y/z", "/b/n/m/o/p");
    }

    public void should_return_proper_index_of_buffer() {
        assertSame(2, completer.complete("/a", 1, new ArrayList<CharSequence>()));
        assertSame(6, completer.complete("/a/b/c", 1, new ArrayList<CharSequence>()));
        assertSame(4, completer.complete("/a/b", 1, new ArrayList<CharSequence>()));
        assertSame(6, completer.complete("/a/b/ ", 1, new ArrayList<CharSequence>()));
    }

    public void should_return_zero_index_of_buffer() {
        assertSame(0, completer.complete("", 1, new ArrayList<CharSequence>()));
    }

    public void should_return_zero_index_when_buffer_is_null() {
        assertSame(0, completer.complete(null, 1, new ArrayList<CharSequence>()));
    }

    @AfterMethod
    public void after() {
        completer = null;
    }
}