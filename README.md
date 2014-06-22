InfiniteScrollListView
======================

Simple infinite scrolling ListView for Android. For a technical explanation of how this works, refer to [this blog post](http://sandersdenardi.com/infinite-scrolling-in-android/). Included is a working example that demonstrates how to use the API.

API
---
### InfiniteScrollAdapter
```java
public abstract class InfiniteScrollAdapter extends BaseAdapter {
    Collection getItems();
    void addItems(Collection items);
    Object getRealItem(int position);
    View getRealView(LayoutInflater inflater, int position, View convertView, ViewGroup parent);
    View getLoadingView(LayoutInflater inflater, ViewGroup parent);
}
```
`getItems()` must expose the underlying data as a collection. 
`addItems()` takes in a collection and, depending on whether the collection is empty, either adds the items to the underlying data or sets the internal `doneLoading` flag to `true`. The implementation must also call `notifyDataSetChanged()` at the end of the method.
`getRealItem()` returns the object represented by that position. You can assume the position is within the bounds of whatever collection you expose via `getItems()`.
`getRealView()` works just like `getView()` in a regular `ListAdapter`, returning a view for the particular position.
`getLoadingView()` also works like a regular `ListAdapter`, but returns a view that indicates that data is being loaded in the background.

### InfiniteScrollOnScrollListener
The implementation of OnScrollListener that detects whether the ListView is close to its end.

### IInfiniteScrollListener
```java
public interface IInfiniteScrollListener {
    public void endIsNear();
}
```
`endIsNear()` is called when the ListView reaches near the end of the list, and should initiate fetching of new data. Note this this will be called on every scroll that is near the end, which can be several dozens of times a second, so the implementation of this should set a flag once loading is started to prevent loading multiple times.

Example
-------
The included example is a simple implementation. The application is a single activity that creates an `InfiniteScrollListView` and an implementation of the `InfiniteScrollAdapter` using an `ArrayList` of trivial objects. It populates the list with an amount of items that does not extend beyond the view port, causing the immediate retrieval of data. Data fetching is simulated via an `AsyncTask` with a `sleep()` delay. It will load a maximum of 40 items, then return an empty collection to signify that no more data is available.

License
-------
The MIT License (MIT)

Copyright (c) 2014 Sanders DeNardi

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
