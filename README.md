InfiniteScrollListView
======================

Simple infinite scrolling ListView for Android.

### InfiniteScrollListView
The simple ListView.

### InfiniteScrollOnScrollListener
The implementation of OnScrollListener that detects whether the ListView is close to its end.

### IInfiniteScrollListener
Contains the method called by the InfiniteScrollOnScrollListener when the end is reached. Can be implemented by an Activity or Fragment.

### InfiniteScrollAdapter
Abstract adapter which determines which view to show (the real view or the loading placeholder view). Implementations must specify the underlying Collection of items and the views to display.
