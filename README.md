CircleIndicator
===============
A lightweight viewpager indicator like in nexus 5 launcher 

[ ![Download](https://api.bintray.com/packages/ongakuer/maven/CircleIndicator/images/download.svg) ](https://bintray.com/ongakuer/maven/CircleIndicator/_latestVersion)

![CircleIndicator](/screenshot.gif)

Gradle
------------


###### AndroidX
```groovy
dependencies {
    implementation 'me.relex:circleindicator:2.0.0'
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
```xml
<me.relex.circleindicator.CircleIndicator
	android:id="@+id/indicator"
	android:layout_width="match_parent"
	android:layout_height="48dp"/>
```
```java
ViewPager viewpager = (ViewPager) view.findViewById(R.id.viewpager);
CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
viewpager.setAdapter(mPageAdapter);
indicator.setViewPager(viewpager);
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



