package com.commonsware.empublite;

import android.util.Log;

/**
 * Created by Wung on 08/05/16.
 */
public class BookLoadedEvent {
    private BookContents contents = null;

    public BookLoadedEvent(BookContents contents) {
        this.contents = contents;
    }

    public BookContents getBook() {
        return(contents);
    }
}
