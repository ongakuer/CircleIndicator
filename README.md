CircleIndicator
===============
A lightweight indicator like in nexus 5 launcher 

[ ![Download](https://api.bintray.com/packages/ongakuer/maven/CircleIndicator/images/download.svg) ](https://bintray.com/ongakuer/maven/CircleIndicator/_latestVersion)

![CircleIndicator](/screenshot.gif)

Gradle
------------


###### AndroidX
```groovy
dependencies {
    implementation 'me.relex:circleindicator:2.1.2'
}
```

###### Android Support Library
```groovy
dependencies {
    compile 'me.relex:circleindicator:1.2.2'
}
```


Usage
--------

###### ViewPager

```xml
<me.relex.circleindicator.CircleIndicator
	android:id="@+id/indicator"
	android:layout_width="match_parent"
	android:layout_height="48dp"/>
```
```java
ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
viewpager.setAdapter(adapter);

CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
indicator.setViewPager(viewpager);

// optional
adapter.registerDataSetObserver(indicator.getDataSetObserver());
```


###### RecyclerView

```xml
<me.relex.circleindicator.CircleIndicator2
	android:id="@+id/indicator"
	android:layout_width="match_parent"
	android:layout_height="48dp"/>
```
```java
RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
recyclerView.setLayoutManager(layoutManager);
recyclerView.setAdapter(adapter);

PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
pagerSnapHelper.attachToRecyclerView(recyclerView);

CircleIndicator2 indicator = view.findViewById(R.id.indicator);
indicator.attachToRecyclerView(recyclerView, pagerSnapHelper);

// optional
adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
```


###### ViewPager2

```xml
<me.relex.circleindicator.CircleIndicator3
	android:id="@+id/indicator"
	android:layout_width="match_parent"
	android:layout_height="48dp"/>
```
```java

ViewPager2 viewpager = view.findViewById(R.id.viewpager);
viewpager.setAdapter(mAdapter);

CircleIndicator3 indicator = view.findViewById(R.id.indicator);
indicator.setViewPager(viewpager);

// optional
adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
```


##### Properties:

* `app:ci_width`
* `app:ci_height`
* `app:ci_margin`
* `app:ci_drawable`
* `app:ci_drawable_unselected`
* `app:ci_animator`
* `app:ci_animator_reverse`
* `app:ci_orientation` (default:horizontal)
* `app:ci_gravity` (default:center)
