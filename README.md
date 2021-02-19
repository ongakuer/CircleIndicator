CircleIndicator
===============
A lightweight indicator like in nexus 5 launcher 

[ ![Download](https://api.bintray.com/packages/ongakuer/maven/CircleIndicator/images/download.svg) ](https://bintray.com/ongakuer/maven/CircleIndicator/_latestVersion)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/me.relex/circleindicator/badge.svg)](https://maven-badges.herokuapp.com/maven-central/me.relex/circleindicator)

<img src="/screenshot.gif" width="300">



Gradle
------------


##### AndroidX
```groovy
dependencies {
    implementation 'me.relex:circleindicator:2.1.6'
}
```

##### Android Support Library
```groovy
dependencies {
    implementation 'me.relex:circleindicator:1.3.2'
}
```


Usage
--------

| Class						| Widget			|
| --------------------------------------------- | -----------------------------	|
| me.relex.circleindicator.CircleIndicator	| ViewPager			|
| me.relex.circleindicator.CircleIndicator2	| RecyclerView			|
| me.relex.circleindicator.CircleIndicator3	| ViewPager2  *(AndroidX)*	|




##### ViewPager (CircleIndicator)


```java
ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
viewpager.setAdapter(adapter);

CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
indicator.setViewPager(viewpager);

// optional
adapter.registerDataSetObserver(indicator.getDataSetObserver());
```


##### RecyclerView (CircleIndicator2)


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


##### ViewPager2 (CircleIndicator3)

```java
ViewPager2 viewpager = view.findViewById(R.id.viewpager);
viewpager.setAdapter(mAdapter);

CircleIndicator3 indicator = view.findViewById(R.id.indicator);
indicator.setViewPager(viewpager);

// optional
adapter.registerAdapterDataObserver(indicator.getAdapterDataObserver());
```

##### Manual control
```java
CircleIndicator indicator = view.findViewById(R.id.indicator);
indicator.createIndicators(5,0);

indicator.animatePageSelected(2)
```



XML Properties
--------

```xml
<me.relex.circleindicator.CircleIndicator
	android:id="@+id/indicator"
	android:layout_width="match_parent"
	android:layout_height="48dp"/>
```

| Properties			| Default Value			|
| ----------------------------- | ----------------------------- |
| app:ci_width	 		| 5dp				|
| app:ci_height			| 5dp				|
| app:ci_margin			| 5dp				|
| app:ci_drawable		| R.drawable.white_radius  	|
| app:ci_drawable_unselected	| R.drawable.white_radius	|
| app:ci_animator		| R.animator.scale_with_alpha	|
| app:ci_animator_reverse	| 0				|
| app:ci_orientation		| horizontal			|
| app:ci_gravity		| center     			|
